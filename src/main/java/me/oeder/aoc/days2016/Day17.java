package me.oeder.aoc.days2016;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = new Character[4][4];
        String passcode = lines.get(0);
        Coord start = new Coord(0, 0);
        Coord end = new Coord(3, 3);

        List<VisitedData> visited = new ArrayList<>();
        List<List<Direction>> paths = new ArrayList<>();
        traverseGraph(grid, visited, paths, new ArrayList<>(), passcode, start, end);

        int minPathLength = Integer.MAX_VALUE;
        int maxPathLength = Integer.MIN_VALUE;
        List<Direction> minPath = null;
        for (List<Direction> path : paths) {
            if (path.size() < minPathLength) {
                minPathLength = path.size();
                minPath = path;
            } else if (path.size() > maxPathLength) {
                maxPathLength = path.size();
            }
        }
        if (part == Part.ONE) {
            return getPathString(minPath);
        }
        return maxPathLength;
    }

    public void traverseGraph(Character[][] grid, List<VisitedData> visited, List<List<Direction>> paths, List<Direction> curPath, String passcode, Coord start, Coord end) {
        visited.add(new VisitedData(start, passcode));
        if (start.equals(end)) {
            paths.add(curPath);
            return;
        }

        for (Direction dir : Direction.values()) {
            Coord neighbor = new Coord(start.getRow() + dir.getRowDiff(), start.getCol() + dir.getColDiff());
            if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
                continue;
            }

            VisitedData neighborVisitedData = new VisitedData(neighbor, passcode);
            if (visited.contains(neighborVisitedData)) {
                continue;
            }

            String hash = DigestUtils.md5Hex(passcode).substring(0, 4);
            if (!isDirOpen(hash, dir)) {
                continue;
            }

            List<VisitedData> newVisited = new ArrayList<>(visited);
            List<Direction> newCurPath = new ArrayList<>(curPath);
            newCurPath.add(dir);

            traverseGraph(grid, newVisited, paths, newCurPath, passcode + getDirectionLetter(dir), neighbor, end);
        }
    }

    List<Character> openChars = Arrays.asList('b', 'c', 'd', 'e', 'f');
    private boolean isDirOpen(String hash, Direction dir) {
        int index = -1;
        if (dir == Direction.N) {
            index = 0;
        } else if (dir == Direction.S) {
            index = 1;
        } else if (dir == Direction.W) {
            index = 2;
        } else if (dir == Direction.E) {
            index = 3;
        }
        return openChars.contains(hash.charAt(index));
    }

    private String getDirectionLetter(Direction dir) {
        if (dir == Direction.N) {
            return "U";
        } else if (dir == Direction.S) {
            return "D";
        } else if (dir == Direction.W) {
            return "L";
        } else {
            return "R";
        }
    }

    private String getPathString(List<Direction> path) {
        String pathString = "";
        for (Direction dir : path) {
            pathString += getDirectionLetter(dir);
        }
        return pathString;
    }

    @Data
    @AllArgsConstructor
    private static class VisitedData {
        private Coord coord;
        private String passcode;
    }
}