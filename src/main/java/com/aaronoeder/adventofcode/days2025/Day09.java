package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day09 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Coord> reds = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            reds.add(new Coord(Integer.parseInt(parts[1]), Integer.parseInt(parts[0])));
        }

        List<Coord> greens = new ArrayList<>();
        for (int a = 0; a < reds.size(); a++) {
            Coord cur = reds.get(a);
            Coord next = (a < reds.size() - 1 ? reds.get(a + 1) : reds.getFirst());

            if (cur.getRow() == next.getRow()) {
                int startCol = Math.min(cur.getCol(), next.getCol());
                int endCol = Math.max(cur.getCol(), next.getCol());
                for (int j = startCol + 1; j < endCol; j++) {
                    greens.add(new Coord(cur.getRow(), j));
                }
            } else {
                int startRow = Math.min(cur.getRow(), next.getRow());
                int endRow = Math.max(cur.getRow(), next.getRow());
                for (int i = startRow + 1; i < endRow; i++) {
                    greens.add(new Coord(i, cur.getCol()));
                }
            }
        }

        List<Coord> boundaryCoords = new ArrayList<>();
        boundaryCoords.addAll(reds);
        boundaryCoords.addAll(greens);

        List<Box> boxes = new ArrayList<>();
        for (int i = 0; i < reds.size() - 1; i++) {
            for (int j = 1; j < reds.size(); j++) {
                Coord first = reds.get(i);
                Coord second = reds.get(j);

                long dr = Math.abs(first.getRow() - second.getRow()) + 1;
                long dc = Math.abs(first.getCol() - second.getCol()) + 1;

                boxes.add(new Box(first, second, dr * dc));
            }
        }
        boxes = boxes.stream()
                    .sorted(Comparator.comparingLong(Box::getArea))
                    .toList().reversed();

        if (part == Part.ONE) {
            return boxes.getFirst().getArea();
        }

        for (Box box : boxes) {
            if (!doesBoxIntersectBoundary(box, boundaryCoords)) {
                return box.getArea();
            }
        }
        return -1;
    }

    private static boolean doesBoxIntersectBoundary(Box box, List<Coord> boundaryCoords) {
        int minRow = Math.min(box.getFirst().getRow(), box.getSecond().getRow());
        int maxRow = Math.max(box.getFirst().getRow(), box.getSecond().getRow());

        int minCol = Math.min(box.getFirst().getCol(), box.getSecond().getCol());
        int maxCol = Math.max(box.getFirst().getCol(), box.getSecond().getCol());

        for (Coord coord : boundaryCoords) {
            if (coord.getRow() > minRow && coord.getRow() < maxRow && coord.getCol() > minCol && coord.getCol() < maxCol) {
                return true;
            }
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    private static class Box {
        private Coord first;
        private Coord second;
        private long area;
    }
}