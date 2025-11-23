package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day17 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Map<String, Long> registers = new HashMap<>();
        registers.put("A", InputUtils.getLongNums(lines.get(0)).get(0));
        registers.put("B", InputUtils.getLongNums(lines.get(1)).get(0));
        registers.put("C", InputUtils.getLongNums(lines.get(2)).get(0));

        List<Integer> program = InputUtils.getNums(lines.get(4));

        if (part == Part.ONE) {
            return getOutput(program, registers).stream().map(String::valueOf).collect(Collectors.joining(","));
        }
        return getA(program, program, registers);
    }

    private List<Integer> getOutput(List<Integer> program, Map<String, Long> registers) {
        List<Integer> output = new ArrayList<>();
        int index = 0;
        while (index < program.size()) {
            int operand = program.get(index);
            int literal = program.get(index + 1);
            long combo = getCombo(literal, registers);

            boolean jump = false;
            if (operand == 0) {
                registers.put("A", (long)(registers.get("A") / Math.pow(2, combo)));
            } else if (operand == 1) {
                registers.put("B", registers.get("B") ^ literal);
            } else if (operand == 2) {
                registers.put("B", combo % 8);
            } else if (operand == 3) {
                if (registers.get("A") != 0) {
                    jump = true;
                    index = literal;
                }
            } else if (operand == 4) {
                registers.put("B", registers.get("B") ^ registers.get("C"));
            } else if (operand == 5) {
                output.add((int)(combo % 8));
            } else if (operand == 6) {
                registers.put("B", (long)(registers.get("A") / Math.pow(2, combo)));
            } else if (operand == 7) {
                registers.put("C", (long)(registers.get("A") / Math.pow(2, combo)));
            }

            if (!jump) {
                index += 2;
            }
        }
        return output;
    }

    private long getCombo(int literal, Map<String, Long> registers) {
        if (literal <= 3) {
            return literal;
        }
        if (literal == 4) {
            return registers.get("A");
        }
        if (literal == 5) {
            return registers.get("B");
        }
        if (literal == 6) {
            return registers.get("C");
        }
        return literal;
    }

    private long getA(List<Integer> program, List<Integer> remainingProgram, Map<String, Long> registers) {
        long a = 0;
        if (remainingProgram.size() > 1) {
            a = 8 * getA(program, remainingProgram.subList(1, remainingProgram.size()), registers);
        }
        while (true) {
            Map<String, Long> registersCopy = new HashMap<>(registers);
            registersCopy.put("A", a);
            List<Integer> output = getOutput(program, registersCopy);
            if (output.equals(remainingProgram)) {
                return a;
            }
            a++;
        }
    }
}