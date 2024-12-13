package me.oeder.aoc.days2016;

import me.oeder.aoc.AdventDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Map<String, Integer> values = new HashMap<>();
        values.put("a", 0);
        values.put("b", 0);
        values.put("c", part == Part.ONE ? 0 : 1);
        values.put("d", 0);

        int index = 0;
        while (index < lines.size()) {
            String line = lines.get(index);
            String[] parts = line.split(" ");

            String instruction = parts[0];
            if (instruction.equals("cpy")) {
                String source = parts[1];
                String target = parts[2];
                if (Character.isLetter(source.charAt(0))) {
                    values.put(target, values.get(source));
                } else {
                    values.put(target, Integer.parseInt(source));
                }
                index++;
            } else if (instruction.equals("inc")) {
                String target = parts[1];
                values.put(target, values.get(target) + 1);
                index++;
            } else if (instruction.equals("dec")) {
                String target = parts[1];
                values.put(target, values.get(target) - 1);
                index++;
            } else if (instruction.equals("jnz")) {
                String source = parts[1];
                int offset = Integer.parseInt(parts[2]);
                int sourceValue = 0;
                if (Character.isLetter(source.charAt(0))) {
                    sourceValue = values.get(source);
                } else {
                    sourceValue = Integer.parseInt(source);
                }
                if (sourceValue != 0) {
                    index += offset;
                } else {
                    index++;
                }
            }
        }

        return values.get("a");
    }
}