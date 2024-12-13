package me.oeder.aoc.days2021;

import java.util.*;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.util.InputUtils;

public class Day13 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Coord> coords = new ArrayList<>();
        List<Fold> folds = new ArrayList<>();

        boolean hitEmptyLine = false;
        for (String line : lines) {
            if (line.equals("")) {
                hitEmptyLine = true;
            } else if (!hitEmptyLine) {
                String[] coordInfo = line.split(",");
                coords.add(new Coord(Integer.parseInt(coordInfo[1]), Integer.parseInt(coordInfo[0])));
            } else {
                String[] foldInfo = line.replace("fold along ", "").split("=");
                boolean isX = foldInfo[0].equals("x");
                folds.add(new Fold(foldInfo[0].equals("y"), Integer.parseInt(foldInfo[1])));
            }
        }

        int maxRow = getMax(coords, (c1, c2) -> c2.getRow() - c1.getRow(), c -> c.getRow());
        int maxCol = getMax(coords, (c1, c2) -> c2.getCol() - c1.getCol(), c -> c.getCol());

        String[][] grid = new String[maxRow+ 1][maxCol + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = ".";
            }
        }
        for (Coord coord : coords) {
            grid[coord.getRow()][coord.getCol()] = "#";
        }

        for (int foldIndex = 0; foldIndex < (part == Part.ONE ? 1 : folds.size()); foldIndex++) {
            Fold fold = folds.get(foldIndex);
            String[][] foldedGrid = new String[fold.isRow() ? fold.getValue() : grid.length][fold.isRow() ? grid[0].length : fold.getValue()];
            for (int i = 0; i < foldedGrid.length; i++) {
                for (int j = 0; j < foldedGrid[0].length; j++) {
                    String result = grid[i][j];
                    if (fold.isRow()) {
                        int amountBeneath = grid.length - fold.getValue() - 1;
                        int reflectionStart = foldedGrid.length - amountBeneath;
                        if (i >= reflectionStart) {
                            int reflectionDiff = i - reflectionStart;
                            String top = grid[i][j];
                            String bottom = grid[grid.length - 1 - reflectionDiff][j];
                            result = (top.equals("#") || bottom.equals("#")) ? "#" : ".";
                        }
                    } else {
                        int amountToRight = grid[0].length - fold.getValue() - 1;
                        int reflectionStart = foldedGrid[0].length - amountToRight;
                        if (j >= reflectionStart) {
                            int reflectionDiff = j - reflectionStart;
                            String left = grid[i][j];
                            String right = grid[i][grid[0].length - 1 - reflectionDiff];
                            result = (left.equals("#") || right.equals("#")) ? "#" : ".";
                        }

                    }
                    foldedGrid[i][j] = result;
                }
            }
            grid = foldedGrid;
        }
        if (part == Part.TWO) {
            InputUtils.printGrid(grid);
        }
        return getDotCount(grid);
    }

    @Data
    @AllArgsConstructor
    private class Fold {
        private boolean isRow;
        private int value;
    }

    private int getMax(List<Coord> coords, Comparator<Coord> sorter, Function<Coord, Integer> mapper) {
        Collections.sort(coords, sorter);
        return mapper.apply(coords.get(0));
    }

    private int getDotCount(String[][] grid) {
        int dotCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].equals("#")) {
                    dotCount++;
                }
            }
        }
        return dotCount;
    }
}