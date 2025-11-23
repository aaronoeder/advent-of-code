package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.List;

public class Day04 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (part == Part.ONE) {
                    sum += isMatchPart1(grid, i, j);
                } else {
                    sum += isMatchPart2(grid, i, j);
                }
            }
        }
        return sum;
    }

    private int isMatchPart1(Character[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[i].length;

        int count = 0;

        // Left to right
        if (j + 3 < cols) {
            if (grid[i][j] == 'X' && grid[i][j + 1] == 'M' && grid[i][j + 2] == 'A' && grid[i][j + 3] == 'S') {
                count++;
            }
        }
        // Right to left
        if (j - 3 >= 0) {
            if (grid[i][j] == 'X' && grid[i][j - 1] == 'M' && grid[i][j - 2] == 'A' && grid[i][j - 3] == 'S') {
                count++;
            }
        }
        // Top to bottom
        if (i + 3 < rows) {
            if (grid[i][j] == 'X' && grid[i + 1][j] == 'M' && grid[i + 2][j] == 'A' && grid[i + 3][j] == 'S') {
                count++;
            }
        }
        // Bottom to top
        if (i - 3 >= 0) {
            if (grid[i][j] == 'X' && grid[i - 1][j] == 'M' && grid[i - 2][j] == 'A' && grid[i - 3][j] == 'S') {
                count++;
            }
        }
        // Top-left to bottom-right
        if (i + 3 < rows && j + 3 < cols) {
            if (grid[i][j] == 'X' && grid[i + 1][j + 1] == 'M' && grid[i + 2][j + 2] == 'A' && grid[i + 3][j + 3] == 'S') {
                count++;
            }
        }
        // Top-right to bottom-left
        if (i + 3 < rows && j - 3 >= 0) {
            if (grid[i][j] == 'X' && grid[i + 1][j - 1] == 'M' && grid[i + 2][j - 2] == 'A' && grid[i + 3][j - 3] == 'S') {
                count++;
            }
        }
        // Bottom-left to top-right
        if (i - 3 >= 0 && j + 3 < cols) {
            if (grid[i][j] == 'X' && grid[i - 1][j + 1] == 'M' && grid[i - 2][j + 2] == 'A' && grid[i - 3][j +3] == 'S') {
                count++;
            }
        }
        // Bottom-right to top-left
        if (i - 3 >= 0 && j - 3 >= 0) {
            if (grid[i][j] == 'X' && grid[i - 1][j - 1] == 'M' && grid[i - 2][j - 2] == 'A' && grid[i - 3][j - 3] == 'S') {
                count++;
            }
        }

        return count;
    }

    private int isMatchPart2(Character[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;

        int count = 0;

        if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < rows && j + 1 < cols) {
            // M.S
            // .A.
            // M.S
            if (grid[i][j] == 'A' && grid[i - 1][j - 1] == 'M' && grid[i - 1][j + 1] == 'S' && grid[i + 1][j - 1] == 'M' && grid[i + 1][j + 1] == 'S') {
                count++;
            }

            // S.M
            // .A.
            // S.M
            if (grid[i][j] == 'A' && grid[i - 1][j - 1] == 'S' && grid[i - 1][j + 1] == 'M' && grid[i + 1][j - 1] == 'S' && grid[i + 1][j + 1] == 'M') {
                count++;
            }

            // M.M
            // .A.
            // S.S
            if (grid[i][j] == 'A' && grid[i - 1][j - 1] == 'M' && grid[i - 1][j + 1] == 'M' && grid[i + 1][j - 1] == 'S' && grid[i + 1][j + 1] == 'S') {
                count++;
            }

            // S.S
            // .A.
            // M.M
            if (grid[i][j] == 'A' && grid[i - 1][j - 1] == 'S' && grid[i - 1][j + 1] == 'S' && grid[i + 1][j - 1] == 'M' && grid[i + 1][j + 1] == 'M') {
                count++;
            }
        }

        return count;
    }
}