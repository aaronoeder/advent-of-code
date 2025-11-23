package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day06 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);

        Coord start = null;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '^') {
                    start = new Coord(i, j);
                }
            }
        }

        if (part == Part.ONE) {
            return getVisitedCount(grid, start);
        }

        int loopCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char ch = grid[i][j];
                grid[i][j] = '#';
                if (getVisitedCount(grid, start) == -1) {
                    loopCount++;
                }
                grid[i][j] = ch;
            }
        }
        return loopCount;
    }

    private int getVisitedCount(Character[][] grid, Coord start) {
        Coord pos = start;
        Direction dir = Direction.N;

        Set<VisitedData> visited = new HashSet<>();

        while (true) {
            VisitedData visitedData = new VisitedData(pos, dir);
            if (visited.contains(visitedData)) {
                return -1;
            }
            visited.add(visitedData);

            Coord newPos = new Coord(pos.getRow() + dir.getRowDiff(), pos.getCol() + dir.getColDiff());
            if (isOutOfBounds(grid, newPos)) {
                break;
            }

            char ch = grid[newPos.getRow()][newPos.getCol()];
            if (ch == '#') {
                dir = dir.getDirectionToRight();
            } else {
                pos = newPos;
            }
        }
        return visited.stream().map(md -> md.getCoord()).collect(Collectors.toSet()).size();
    }

    private boolean isOutOfBounds(Character[][] grid, Coord coord) {
        return coord.getRow() < 0 || coord.getRow() >= grid.length || coord.getCol() < 0 || coord.getCol() >= grid[0].length;
    }

    @Data
    @AllArgsConstructor
    private static class VisitedData {
        private Coord coord;
        private Direction dir;
    }
}