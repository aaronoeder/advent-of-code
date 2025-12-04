package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class Day04 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        long total = 0;

        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);

        long previousTotal = -1;
        while (true) {
            List<Coord> coordsToRemove = new ArrayList<>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    char ch = grid[i][j];
                    if (ch != '@') {
                        continue;
                    }

                    Coord coord = new Coord(i, j);
                    List<Coord> neighbors = coord.getNeighbors(true);

                    int adjacentCount = 0;
                    for (Coord neighbor : neighbors) {
                        if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
                            continue;
                        }
                        if (grid[neighbor.getRow()][neighbor.getCol()] == '@') {
                            adjacentCount++;
                        }
                    }
                    if (adjacentCount < 4) {
                        total++;
                        coordsToRemove.add(coord);
                    }
                }
            }
            if (part == Part.ONE) {
                return total;
            }
            for (Coord coord : coordsToRemove) {
                grid[coord.getRow()][coord.getCol()] = '.';
            }
            if (total == previousTotal) {
                return total;
            }
            previousTotal = total;
        }
    }
}