package me.oeder.aoc.days2016;

import me.oeder.aoc.AdventDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day09 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        if (part == Part.ONE) {
            return decompressLength(lines.get(0), Part.ONE);
        }
        return decompressLength(lines.get(0), Part.TWO);
    }

    private static final Map<String, Long> CACHE = new HashMap<>();

    private long decompressLength(String text, Part part) {
        if (CACHE.containsKey(text)) {
            return CACHE.get(text);
        }
        long length = 0;
        int index = 0;
        while (index < text.length()) {
            char ch = text.charAt(index);
            if (ch == '(') {
                int endParenIndex = text.indexOf(')', index);
                String markerString = text.substring(index + 1, endParenIndex);
                String[] markerParts = markerString.split("x");

                int dupeSize = Integer.parseInt(markerParts[0]);
                int dupeFrequency = Integer.parseInt(markerParts[1]);

                String dupeString = text.substring(endParenIndex + 1, endParenIndex + 1 + dupeSize);

                for (int i = 0; i < dupeFrequency; i++) {
                    length += (part == Part.ONE ? dupeString.length() : decompressLength(dupeString, part));
                }
                index = endParenIndex + 1 + dupeSize;
            } else {
                length++;
                index++;
            }
        }
        if (part == Part.TWO) {
            CACHE.put(text, length);
        }
        return length;
    }
}