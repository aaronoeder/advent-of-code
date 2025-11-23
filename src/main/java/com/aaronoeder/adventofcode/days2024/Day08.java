package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);

        Set<Character> chars = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                char ch = grid[i][j];
                if (ch != '.') {
                    chars.add(ch);
                }
            }
        }

        List<Pair> pairs = new ArrayList<>();
        for (char ch : chars) {
            pairs.addAll(getPairsHavingCharacter(grid, ch));
        }

        Set<Coord> antiNodes = new HashSet<>();
        for (Pair pair : pairs) {
            int rowDiff = pair.getFirst().getRow() - pair.getSecond().getRow();
            int colDiff = pair.getFirst().getCol() - pair.getSecond().getCol();

            Coord before = new Coord(pair.getFirst().getRow() + rowDiff, pair.getFirst().getCol() + colDiff);
            Coord after = new Coord(pair.getSecond().getRow() - rowDiff, pair.getSecond().getCol() - colDiff);

            if (part == Part.ONE) {
                if (isInBounds(grid, before)) {
                    antiNodes.add(before);
                }
                if (isInBounds(grid, after)) {
                    antiNodes.add(after);
                }
            } else {
                antiNodes.add(pair.getFirst());
                antiNodes.add(pair.getSecond());

                while (isInBounds(grid, before)) {
                    antiNodes.add(before);
                    before = new Coord(before.getRow() + rowDiff, before.getCol() + colDiff);
                }
                while (isInBounds(grid, after)) {
                    antiNodes.add(after);
                    after = new Coord(after.getRow() - rowDiff, after.getCol() - colDiff);
                }
            }
        }

        return antiNodes.size();
    }

    private List<Pair> getPairsHavingCharacter(Character[][] grid, char ch) {
        List<Coord> coords = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ch) {
                    coords.add(new Coord(i, j));
                }
            }
        }

        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < coords.size() - 1; i++) {
            for (int j = i + 1; j < coords.size(); j++) {
                pairs.add(new Pair(coords.get(i), coords.get(j)));
            }
        }
        return pairs;
    }

    private boolean isInBounds(Character[][] grid, Coord coord) {
        return coord.getRow() >= 0 && coord.getRow() < grid.length && coord.getCol() >= 0 && coord.getCol() < grid[0].length;
    }

    @Data
    @AllArgsConstructor
    private static class Pair {
        private Coord first;
        private Coord second;
    }
}