package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;

import java.util.List;

public class Day01 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        int total = 0;
        int pos = 50;
        for (String line : lines) {
            String dir = line.substring(0, 1);
            int val = Integer.parseInt(line.substring(1));
            for (int i = 0; i < val; i++) {
                if (dir.equals("L")) {
                    pos = (pos == 0 ? 99 : pos - 1);
                } else {
                    pos = (pos == 99 ? 0 : pos + 1);
                }
                if (pos == 0 && part == Part.TWO) {
                    total++;
                }
            }
            if (pos == 0 && part == Part.ONE) {
                total++;
            }
        }
        return total;
    }
}