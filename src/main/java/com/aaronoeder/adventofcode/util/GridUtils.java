package com.aaronoeder.adventofcode.util;

import com.aaronoeder.adventofcode.common.Coord;

import java.util.function.Predicate;

public final class GridUtils {
    private GridUtils() {}

    public static Coord getCoordInGridHaving(Character[][] grid, Predicate<Character> evaluator) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char ch = grid[i][j];
                if (evaluator.test(ch)) {
                    return new Coord(i, j);
                }
            }
        }
        return null;
    }
}