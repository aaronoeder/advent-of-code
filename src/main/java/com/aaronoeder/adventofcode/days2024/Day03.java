package com.aaronoeder.adventofcode.days2024;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aaronoeder.adventofcode.AdventDay;

public class Day03 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        String input = "";
        for (String line : lines) {
            input += line;
        }

        int sum = 0;
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String match = matcher.group(0);
            int matchIndex = input.indexOf(match); // Assumes a given mul(x,y) only occurs once in the input (which is true)
            if (shouldIncludeMatch(part, matchIndex, input)) {
                int a = Integer.parseInt(matcher.group(1));
                int b = Integer.parseInt(matcher.group(2));
                sum += a * b;
            }
        }
        return sum;
    }

    private boolean shouldIncludeMatch(Part part, int matchIndex, String input) {
        if (part == Part.ONE) {
            return true;
        }
        String beforeSubstring = input.substring(0, matchIndex);
        int lastDontIndex = beforeSubstring.lastIndexOf("don't()");
        int lastDoIndex = beforeSubstring.lastIndexOf("do()");
        return lastDoIndex > lastDontIndex;
    }
}