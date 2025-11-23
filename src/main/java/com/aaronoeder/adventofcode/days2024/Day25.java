package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class Day25 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<List<Integer>> locks = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();

        List<String> gridLines = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                processGridLines(gridLines, locks, keys);
                gridLines = new ArrayList<>();
            } else {
                gridLines.add(line);
            }
        }
        processGridLines(gridLines, locks, keys);

        int sum = 0;
        for (List<Integer> lock : locks) {
            for (List<Integer> key : keys) {
                boolean valid = true;
                for (int i = 0; i < lock.size(); i++) {
                    if (lock.get(i) + key.get(i) > 5) {
                        valid = false;
                    }
                }
                if (valid) {
                    sum++;
                }
            }

        }
        return sum;
    }

    private void processGridLines(List<String> gridLines, List<List<Integer>> locks, List<List<Integer>> keys) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(gridLines);

        boolean isLock = true;
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[0][j] == '.') {
                isLock = false;
            }
        }

        if (isLock) {
            List<Integer> heights = new ArrayList<>();
            for (int j = 0; j < grid[0].length; j++) {
                int height = 0;
                for (int i = 1; i < grid.length; i++) {
                    if (grid[i][j] == '#') {
                        height++;
                    }
                }
                heights.add(height);
            }
            locks.add(heights);
        } else {
            List<Integer> heights = new ArrayList<>();
            for (int j = 0; j < grid[0].length; j++) {
                int height = 0;
                for (int i = grid.length - 2; i >= 0; i--) {
                    if (grid[i][j] == '#') {
                        height++;
                    }
                }
                heights.add(height);
            }
            keys.add(heights);
        }
    }
}