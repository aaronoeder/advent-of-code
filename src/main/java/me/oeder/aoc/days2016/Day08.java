package me.oeder.aoc.days2016;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.InputUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        String[][] grid = new String[6][50];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = ".";
            }
        }

        Pattern rectPattern = Pattern.compile("rect (\\d+)x(\\d+)");
        Pattern rotateRowPattern = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
        Pattern rotateColPattern = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
        for (String line : lines) {
            if (rectPattern.matcher(line).matches()) {
                Matcher matcher = rectPattern.matcher(line);
                if (matcher.find()) {
                    int rows = Integer.parseInt(matcher.group(2));
                    int cols = Integer.parseInt(matcher.group(1));
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            grid[i][j] = "#";
                        }
                    }
                }
            } else if (rotateRowPattern.matcher(line).matches()) {
                Matcher matcher = rotateRowPattern.matcher(line);
                if (matcher.find()) {
                    int row = Integer.parseInt(matcher.group(1));
                    int offset = Integer.parseInt(matcher.group(2));
                    String[][] gridCopy = getGridCopy(grid);
                    for (int j = 0; j < grid[0].length; j++) {
                        int offsetIndex = j - offset;
                        if (offsetIndex < 0) {
                            offsetIndex = grid[0].length + offsetIndex;
                        }
                        gridCopy[row][j] = grid[row][offsetIndex];
                    }
                    grid = gridCopy;
                }
            } else if (rotateColPattern.matcher(line).matches()) {
                Matcher matcher = rotateColPattern.matcher(line);
                if (matcher.find()) {
                    int col = Integer.parseInt(matcher.group(1));
                    int offset = Integer.parseInt(matcher.group(2));
                    String[][] gridCopy = getGridCopy(grid);
                    for (int i = 0; i < grid.length; i++) {
                        int offsetIndex = i - offset;
                        if (offsetIndex < 0) {
                            offsetIndex = grid.length + offsetIndex;
                        }
                        gridCopy[i][col] = grid[offsetIndex][col];
                    }
                    grid = gridCopy;
                }
            }
        }
        if (part == Part.TWO) {
            InputUtils.printGrid(grid);
        }
        return getLitCount(grid);
    }

    private String[][] getGridCopy(String[][] grid) {
        String[][] gridCopy = new String[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                gridCopy[i][j] = grid[i][j];
            }
        }
        return gridCopy;
    }

    private int getLitCount(String[][] grid) {
        int litCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].equals("#")) {
                    litCount++;
                }
            }
        }
        return litCount;
    }
}