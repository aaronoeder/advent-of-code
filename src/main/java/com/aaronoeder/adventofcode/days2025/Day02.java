package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day02 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        long total = 0;

        String[] ranges = lines.getFirst().split(",");
        for (String range : ranges) {
            String[] parts = range.split("-");

            long min = Long.parseLong(parts[0]);
            long max = Long.parseLong(parts[1]);

            for (long i = min; i <= max; i++) {
                String str = String.valueOf(i);
                int half = str.length() / 2;

                Set<String> counted = new HashSet<>();
                for (int j = 1; j <= half; j++) {
                    if (part == Part.ONE && (j != half || half * 2 != str.length())) {
                        continue;
                    }
                    if (str.length() % j != 0) {
                        continue;
                    }
                    int times = str.length() / j;
                    String partial = str.substring(0, j);

                    String full = "";
                    for (int k = 0; k < times; k++) {
                        full += partial;
                    }
                    if (full.equals(str) && !counted.contains(full)) {
                        total += i;
                        counted.add(full);
                    }
                }
            }
        }
        return total;
    }
}