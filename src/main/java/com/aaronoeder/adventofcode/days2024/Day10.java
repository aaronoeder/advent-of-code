package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.graph.DFS;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);

        int sum = 0;
        for (Coord start : getCoordsHavingValue(grid, '0')) {
            for (Coord end : getCoordsHavingValue(grid, '9')) {
                Set<Coord> visited = new HashSet<>();
                List<Set<Coord>> uniquePaths = new ArrayList<>();
                DFS.traverseGraph(grid, visited, uniquePaths, start, end, false,
                        (cur, next) -> Character.getNumericValue(grid[next.getRow()][next.getCol()])
                                == Character.getNumericValue(grid[cur.getRow()][cur.getCol()]) + 1);

                if (part == Part.ONE && !uniquePaths.isEmpty()) {
                    sum++;
                } else {
                    sum += uniquePaths.size();
                }
            }
        }
        return sum;
    }

    private List<Coord> getCoordsHavingValue(Character[][] grid, char ch) {
        List<Coord> coords = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == ch) {
                    coords.add(new Coord(i, j));
                }
            }
        }
        return coords;
    }
}