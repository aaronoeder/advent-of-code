package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.util.GridUtils;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day07 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
        Coord start = GridUtils.getCoordInGridHaving(grid, ch -> ch == 'S');

        Set<Coord> splittersUsed = new HashSet<>();
        long pathCount = getPathCount(grid, start, splittersUsed);

        if (part == Part.ONE) {
            return splittersUsed.size();
        }
        return pathCount;
    }

    private static final Map<Coord, Long> CACHE = new HashMap<>();
    private long getPathCount(Character[][] grid, Coord start, Set<Coord> splittersUsed) {
        if (CACHE.containsKey(start)) {
            return CACHE.get(start);
        }

        long pathCount = 0;

        Coord south = new Coord(start.getRow() + 1, start.getCol());
        if (!isCoordValid(south, grid.length, grid[0].length)) {
            return 1;
        }

        if (grid[south.getRow()][south.getCol()] == '^') {
            splittersUsed.add(south);

            Coord west = new Coord(south.getRow(), south.getCol() - 1);
            if (isCoordValid(west, grid.length, grid[0].length)) {
                pathCount += getPathCount(grid, west, splittersUsed);
            }

            Coord east = new Coord(south.getRow(), south.getCol() + 1);
            if (isCoordValid(east, grid.length, grid[0].length)) {
                pathCount += getPathCount(grid, east, splittersUsed);
            }
        } else {
            return getPathCount(grid, south, splittersUsed);
        }
        CACHE.put(start, pathCount);
        return pathCount;
    }

    private boolean isCoordValid(Coord coord, int rows, int cols) {
        return coord.getRow() >= 0 && coord.getRow() < rows && coord.getCol() >= 0 && coord.getCol() < cols;
    }
}