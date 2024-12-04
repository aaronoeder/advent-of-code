package me.oeder.aoc.days2024;

import me.oeder.aoc.util.InputUtils;

import java.util.List;

public class Day04 extends AdventDay2024 {

    public Day04() {
        super(4);
    }

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        String[][] grid = InputUtils.loadLinesIntoGrid(lines);
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

    private int isMatchPart1(String[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[i].length;

        int count = 0;

        // Left to right
        if (j + 3 < cols) {
            if (grid[i][j].equals("X") && grid[i][j + 1].equals("M") && grid[i][j + 2].equals("A") && grid[i][j + 3].equals("S")) {
                count++;
            }
        }
        // Right to left
        if (j - 3 >= 0) {
            if (grid[i][j].equals("X") && grid[i][j - 1].equals("M") && grid[i][j - 2].equals("A") && grid[i][j - 3].equals("S")) {
                count++;
            }
        }
        // Top to bottom
        if (i + 3 < rows) {
            if (grid[i][j].equals("X") && grid[i + 1][j].equals("M") && grid[i + 2][j].equals("A") && grid[i + 3][j].equals("S")) {
                count++;
            }
        }
        // Bottom to top
        if (i - 3 >= 0) {
            if (grid[i][j].equals("X") && grid[i - 1][j].equals("M") && grid[i - 2][j].equals("A") && grid[i - 3][j].equals("S")) {
                count++;
            }
        }
        // Top-left to bottom-right
        if (i + 3 < rows && j + 3 < cols) {
            if (grid[i][j].equals("X") && grid[i + 1][j + 1].equals("M") && grid[i + 2][j + 2].equals("A") && grid[i + 3][j + 3].equals("S")) {
                count++;
            }
        }
        // Top-right to bottom-left
        if (i + 3 < rows && j - 3 >= 0) {
            if (grid[i][j].equals("X") && grid[i + 1][j - 1].equals("M") && grid[i + 2][j - 2].equals("A") && grid[i + 3][j - 3].equals("S")) {
                count++;
            }
        }
        // Bottom-left to top-right
        if (i - 3 >= 0 && j + 3 < cols) {
            if (grid[i][j].equals("X") && grid[i - 1][j + 1].equals("M") && grid[i - 2][j + 2].equals("A") && grid[i - 3][j +3].equals("S")) {
                count++;
            }
        }
        // Bottom-right to top-left
        if (i - 3 >= 0 && j - 3 >= 0) {
            if (grid[i][j].equals("X") && grid[i - 1][j - 1].equals("M") && grid[i - 2][j - 2].equals("A") && grid[i - 3][j - 3].equals("S")) {
                count++;
            }
        }

        return count;
    }

    private int isMatchPart2(String[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;

        int count = 0;

        if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < rows && j + 1 < cols) {
            // M.S
            // .A.
            // M.S
            if (grid[i][j].equals("A") && grid[i - 1][j - 1].equals("M") && grid[i - 1][j + 1].equals("S") && grid[i + 1][j - 1].equals("M") && grid[i + 1][j + 1].equals("S")) {
                count++;
            }

            // S.M
            // .A.
            // S.M
            if (grid[i][j].equals("A") && grid[i - 1][j - 1].equals("S") && grid[i - 1][j + 1].equals("M") && grid[i + 1][j - 1].equals("S") && grid[i + 1][j + 1].equals("M")) {
                count++;
            }

            // M.M
            // .A.
            // S.S
            if (grid[i][j].equals("A") && grid[i - 1][j - 1].equals("M") && grid[i - 1][j + 1].equals("M") && grid[i + 1][j - 1].equals("S") && grid[i + 1][j + 1].equals("S")) {
                count++;
            }

            // S.S
            // .A.
            // M.M
            if (grid[i][j].equals("A") && grid[i - 1][j - 1].equals("S") && grid[i - 1][j + 1].equals("S") && grid[i + 1][j - 1].equals("M") && grid[i + 1][j + 1].equals("M")) {
                count++;
            }
        }

        return count;
    }
}