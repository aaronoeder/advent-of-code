package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.graph.BFS;
import com.aaronoeder.adventofcode.util.DistanceUtils;
import com.aaronoeder.adventofcode.util.GridUtils;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day20 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
        Coord start = GridUtils.getCoordInGridHaving(grid, ch -> ch == 'S');
        Coord end = GridUtils.getCoordInGridHaving(grid, ch -> ch == 'E');

        int sum = 0;

        Map<Coord, Integer> bfsFromStart = BFS.getDistances(grid, start, null, false, (cur, next) -> grid[next.getRow()][next.getCol()] != '#');
        Map<Coord, Integer> bfsFromEnd = BFS.getDistances(grid, end, null, false, (cur, next) -> grid[next.getRow()][next.getCol()] != '#');
        int nonCheatedDistance = bfsFromStart.get(end);

        for (Coord cheatStartCoord : bfsFromStart.keySet()) {
            Set<Coord> cheatEndCoords = new HashSet<>();
            cheatEndCoords.add(new Coord(cheatStartCoord.getRow(), cheatStartCoord.getCol()));
            for (int i = 0; i < (part == Part.ONE ? 2 : 20); i++) {
                cheatEndCoords.addAll(
                        cheatEndCoords.stream()
                                .map(cheatEndCoord -> cheatEndCoord.getNeighbors(grid, false))
                                .flatMap(Collection::stream)
                                .collect(Collectors.toSet())
                );
            }

            for (Coord cheatEndCoord : cheatEndCoords) {
                if (grid[cheatEndCoord.getRow()][cheatEndCoord.getCol()] == '#') {
                    continue;
                }
                int distance = bfsFromStart.get(cheatStartCoord) + DistanceUtils.getTaxiCabDistance(cheatStartCoord, cheatEndCoord) + bfsFromEnd.get(cheatEndCoord);
                if (distance <= nonCheatedDistance - 100) {
                    sum++;
                }
            }
        }
        return sum;
    }
}