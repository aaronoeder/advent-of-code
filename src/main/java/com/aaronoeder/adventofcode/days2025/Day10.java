package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Optimize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day10 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Machine> machines = new ArrayList<>();
        for (String line : lines) {
            Pattern pattern = Pattern.compile("\\[(.+)\\] (.+) \\{(.+)\\}");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String lights = matcher.group(1);
                List<List<Integer>> buttons = Arrays.stream(matcher.group(2).split(" "))
                                .map(str -> str.substring(1, str.length() - 1))
                                .map(str -> Arrays.stream(str.split(","))
                                        .map(Integer::parseInt)
                                        .toList())
                                .toList();
                List<Integer> joltages = Arrays.stream(matcher.group(3).split(","))
                                .map(Integer::parseInt)
                                .toList();

                machines.add(new Machine(lights, buttons, joltages));
            }
        }

        int total = 0;
        for (Machine machine : machines) {
            total += (part == Part.ONE ? getPressesPart1(machine) : getPressesPart2(machine));
        }
        return total;
    }

    /*
     * For part 1, we can use a BFS to find the minimum number of button presses (the "shortest path")
     * Each possible button press represents an edge connecting the current node to one of its neighbors
     */
    private int getPressesPart1(Machine machine) {
        String initialLights = StringUtils.repeat('.', machine.getLights().length());

        List<String> queue = new ArrayList<>();
        queue.add(initialLights);

        Map<String, Integer> distances = new HashMap<>();
        distances.put(initialLights, 0);

        while (!queue.isEmpty()) {
            String currentLights = queue.removeFirst();

            int presses = distances.get(currentLights);
            if (currentLights.equals(machine.getLights())) {
                return presses;
            }

            for (List<Integer> buttons : machine.getButtons()) {
                String nextLights = "";
                for (int i = 0; i < currentLights.length(); i++) {
                    if (buttons.contains(i)) {
                        nextLights += (currentLights.substring(i, i + 1).equals(".") ? "#" : ".");
                    } else {
                        nextLights += currentLights.substring(i, i + 1);
                    }
                }
                if (!distances.containsKey(nextLights)) {
                    distances.put(nextLights, presses + 1);
                    queue.add(nextLights);
                }
            }
        }
        return -1;
    }

    /* For part 2, we need to solve a system of equations.
     *
     * As an example, we can consider this line from the sample input:
     * [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
     *
     * Part two requires us to find the minimum number of times we must press the buttons to arrive at the counters of {3,5,4,7}
     * To solve, we can set up a system of equations:
     * e + f = 3
     * b + f = 5
     * c + d + e = 4
     * a + b + d = 7
     *
     * where a,b,c,d,e,f represent the number of times we press each button
     *
     * In this example, there are 4 equations with 6 unknowns, so there will be infinitely many solutions.
     * We need to find the solution that minimizes sum(a + b + c + d + e + f). I used Z3.
     */
    private int getPressesPart2(Machine machine) {
        Context ctx = new Context();
        Optimize opt = ctx.mkOptimize();

        // Create variable for the total number of presses
        IntExpr totalPresses = ctx.mkIntConst("totalPresses");

        // Create variable for the number of times we press each button
        IntExpr[] buttons = IntStream.range(0, machine.getButtons().size())
                .mapToObj(i -> ctx.mkIntConst("button" + i))
                .toArray(IntExpr[]::new);

        // For each element in the "counter" (ex. {3,5,4,7}), figure out which buttons contribute to it
        Map<Integer, List<IntExpr>> counterMap = new HashMap<>();
        for (int i = 0; i < machine.getButtons().size(); i++) {
            IntExpr button = buttons[i];
            for (int index : machine.getButtons().get(i)) {
                if (!counterMap.containsKey(index)) {
                    counterMap.put(index, new ArrayList<>());
                }
                counterMap.get(index).add(button);
            }
        }

        // Build the system of equations
        for (Map.Entry<Integer, List<IntExpr>> entry : counterMap.entrySet()) {
            opt.Add(
                ctx.mkEq(
                    ctx.mkInt(machine.getJoltages().get(entry.getKey())),
                    ctx.mkAdd(entry.getValue().toArray(IntExpr[]::new))
                )
            );
        }

        // Constraint: we can't press a button a negative amount of times
        for (IntExpr button : buttons) {
            opt.Add(ctx.mkGe(button, ctx.mkInt(0)));
        }

        // Constraint: we need to minimize the total number of presses
        opt.Add(ctx.mkEq(totalPresses, ctx.mkAdd(buttons)));
        opt.MkMinimize(totalPresses);

        opt.Check();
        return ((IntNum)opt.getModel().eval(totalPresses, false)).getInt();
    }

    @Data
    @AllArgsConstructor
    private static class Machine {
        private String lights;
        private List<List<Integer>> buttons;
        private List<Integer> joltages;
    }
}