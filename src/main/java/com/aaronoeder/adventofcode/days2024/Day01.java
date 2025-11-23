package com.aaronoeder.adventofcode.days2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aaronoeder.adventofcode.AdventDay;

public class Day01 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split("   ");
            left.add(Integer.parseInt(parts[0]));
            right.add(Integer.parseInt(parts[1]));
        }

        Collections.sort(left);
        Collections.sort(right);

        if (part == Part.ONE) {
            int diff = 0;
            for (int i = 0; i < left.size(); i++) {
                diff += Math.abs(right.get(i) - left.get(i));
            }
            return diff;
        }

        int similarity = 0;
        for (int i = 0; i < left.size(); i++) {
            int num = left.get(i);
            int times = 0;
            for (int j = 0; j < right.size(); j++) {
                if (num == right.get(j)) {
                    times++;
                }
            }
            similarity += num * times;
        }
        return similarity;
    }
}