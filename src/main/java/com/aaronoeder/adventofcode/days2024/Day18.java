package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.graph.BFS;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.List;

public class Day18 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        if (part == Part.ONE) {
            Character[][] grid = buildGrid();
            return getMinDistance(1024, lines, grid);
        }

        int index = lines.size() - 1;
        while (true) {
            Character[][] grid = buildGrid();
            int min = getMinDistance(index, lines, grid);
            if (min != -1) {
                return lines.get(index + 1);
            }
            index--;
        }
    }

    private Character[][] buildGrid() {
        Character[][] grid = new Character[71][71];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = '.';
            }
        }
        return grid;
    }

    private int getMinDistance(int endIndex, List<String> lines, Character[][] grid) {
        for (int i = 0; i <= endIndex; i++) {
            List<Integer> nums = InputUtils.getNums(lines.get(i));
            int x = nums.get(0);
            int y = nums.get(1);
            grid[y][x] = '#';
        }

        return BFS.getMinDistance(grid, new Coord(0, 0), new Coord(70, 70), false,
                (cur, next) -> grid[next.getRow()][next.getCol()] == '.' );
    }
}