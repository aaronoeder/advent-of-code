package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;
import com.aaronoeder.adventofcode.graph.DFS;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);

        List<List<Coord>> regions = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Coord coord = new Coord(i, j);
                Set<Coord> visited = new HashSet<>();
                DFS.traverseGraph(grid, visited, coord, null, false, (cur, next) -> grid[cur.getRow()][cur.getCol()] == grid[next.getRow()][next.getCol()]);

                boolean isNewRegion = !regions.stream()
                        .flatMap(region -> region.stream())
                        .filter(regionCoord -> regionCoord.equals(coord))
                        .findFirst()
                        .isPresent();
                if (isNewRegion) {
                    regions.add(new ArrayList<>(visited));
                }
            }
        }

        int sum = 0;
        for (List<Coord> coordsInRegion : regions) {
            int area = coordsInRegion.size();
            if (part == Part.ONE) {
                int perimeter = 0;
                for (Coord coord : coordsInRegion) {
                    perimeter += getDirectionsWithWall(coord, grid).size();
                }
                sum += area * perimeter;
            } else {
                int sides = 0;
                for (Direction dir : Direction.values()) {
                    sides += getSidesFacingDirection(coordsInRegion, grid, dir);
                }
                sum += area * sides;
            }
        }

        return sum;
    }

    private List<Direction> getDirectionsWithWall(Coord coord, Character[][] grid) {
        List<Direction> dirs = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Coord adjacentCoord = new Coord(coord.getRow() + dir.getRowDiff(), coord.getCol() + dir.getColDiff());
            if (adjacentCoord.getRow() < 0 || adjacentCoord.getRow() >= grid.length || adjacentCoord.getCol() < 0 || adjacentCoord.getCol() >= grid[0].length) {
                dirs.add(dir);
            } else if (grid[adjacentCoord.getRow()][adjacentCoord.getCol()] != grid[coord.getRow()][coord.getCol()]) {
                dirs.add(dir);
            }
        }
        return dirs;
    }

    private int getSidesFacingDirection(List<Coord> coordsInRegion, Character[][] grid, Direction dir) {
        int sides = 0;
        if (dir == Direction.N || dir == Direction.S) {
            Collections.sort(coordsInRegion, (c1, c2) -> {
                if (c1.getRow() != c2.getRow()) {
                    return c1.getRow() - c2.getRow();
                }
                return c1.getCol() - c2.getCol();
            });

            int curRow = -1;
            int prevCol = 0;
            for (Coord coord : coordsInRegion) {
                List<Direction> dirs = getDirectionsWithWall(coord, grid);
                if (dirs.contains(dir)) {
                    if (curRow != coord.getRow()) {
                        sides++;
                        curRow = coord.getRow();
                    } else {
                        if (prevCol + 1 != coord.getCol()) {
                            sides++;
                        }
                    }
                    prevCol = coord.getCol();
                }
            }
        } else {
            Collections.sort(coordsInRegion, (c1, c2) -> {
                if (c1.getCol() != c2.getCol()) {
                    return c1.getCol() - c2.getCol();
                }
                return c1.getRow() - c2.getRow();
            });

            int curCol = -1;
            int prevRow = 0;
            for (Coord coord : coordsInRegion) {
                List<Direction> dirs = getDirectionsWithWall(coord, grid);
                if (dirs.contains(dir)) {
                    if (curCol != coord.getCol()) {
                        sides++;
                        curCol = coord.getCol();
                    } else {
                        if (prevRow + 1 != coord.getRow()) {
                            sides++;
                        }
                    }
                    prevRow = coord.getRow();
                }
            }
        }
        return sides;
    }
}