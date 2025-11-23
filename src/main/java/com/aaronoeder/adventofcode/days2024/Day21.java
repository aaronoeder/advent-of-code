package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;
import com.aaronoeder.adventofcode.util.GridUtils;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Day21 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] numGrid = {
            { '7', '8', '9' },
            { '4', '5', '6' },
            { '1', '2', '3' },
            { ' ', '0', 'A' }
        };

        Character[][] dirGrid = {
            { ' ', '^', 'A' },
            { '<', 'v', '>' }
        };

        long sum = 0;
        for (String line : lines) {
            int code = InputUtils.getNums(line).get(0);
            long minLength = getMinLength(numGrid, dirGrid, line, (part == Part.ONE ? 2 : 25), 0);
            sum += code * minLength;
        }
        return sum;
    }

    @Data
    @AllArgsConstructor
    private static class CacheObject {
        private String line;
        private int robotCount;
        private int robotIndex;
    }

    private Map<CacheObject, Long> cache = new HashMap<>();
    private long getMinLength(Character[][] numGrid, Character[][] dirGrid, String line, int robotCount, int robotIndex) {
        CacheObject cacheObject = new CacheObject(line, robotCount, robotIndex);
        if (cache.containsKey(cacheObject)) {
            return cache.get(cacheObject);
        }

        Coord emptyCoord = (robotIndex == 0 ? new Coord(3, 0) : new Coord(0, 0));
        Coord curCoord = (robotIndex == 0 ? new Coord(3, 2) : new Coord(0, 2));
        Character[][] grid = (robotIndex == 0 ? numGrid : dirGrid);

        long minLength = 0;
        for (char element : line.toCharArray()) {
            Coord nextCoord = GridUtils.getCoordInGridHaving(grid, ch -> ch == element);
            List<String> minimumLengthPaths = getMinimumLengthPaths(grid, curCoord, nextCoord, emptyCoord);
            if (robotIndex == robotCount) {
                minLength += minimumLengthPaths.get(0).length();
            } else {
                long min = Long.MAX_VALUE;
                for (String minimumLengthPath : minimumLengthPaths) {
                    min = Math.min(min, getMinLength(numGrid, dirGrid, minimumLengthPath, robotCount, robotIndex + 1));
                }
                minLength += min;
            }
            curCoord = nextCoord;
        }

        cache.put(cacheObject, minLength);
        return minLength;
    }

    private List<String> getMinimumLengthPaths(Character[][] grid, Coord start, Coord end, Coord emptyCoord) {
        List<String> uniquePaths = new ArrayList<>();
        traverseGraph(grid, new HashSet<>(), uniquePaths, "", start, end, (cur, next) -> !next.equals(emptyCoord));

        int minLength = uniquePaths.stream()
                .map(String::length)
                .min(Integer::compareTo)
                .get();

        return uniquePaths.stream()
                .filter(s -> s.length() == minLength)
                .map(s -> s + "A")
                .collect(Collectors.toList());
    }

    private void traverseGraph(Character[][] grid, Set<Coord> visited, List<String> uniquePaths, String path, Coord cur, Coord end, BiPredicate<Coord, Coord> isValidNeighbor) {
        visited.add(cur);
        if (cur.equals(end)) {
            uniquePaths.add(path);
            return;
        }

        for (Direction dir : Direction.values()) {
            Coord neighbor = new Coord(cur.getRow() + dir.getRowDiff(), cur.getCol() + dir.getColDiff());
            if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
                continue;
            }
            if (visited.contains(neighbor)) {
                continue;
            }
            if (!isValidNeighbor.test(cur, neighbor)) {
                continue;
            }
            traverseGraph(grid, new HashSet<>(visited), uniquePaths, path + getSymbolForDirection(dir), neighbor, end, isValidNeighbor);
        }
    }

    private String getSymbolForDirection(Direction dir) {
        switch (dir) {
            case N:
                return "^";
            case S:
                return "v";
            case W:
                return "<";
            case E:
                return ">";
        }
        return null;
    }
}
