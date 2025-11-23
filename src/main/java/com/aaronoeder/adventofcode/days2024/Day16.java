package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;
import com.aaronoeder.adventofcode.util.GridUtils;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Day16 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Character[][] grid = InputUtils.loadLinesIntoGrid(lines);

        Coord start = GridUtils.getCoordInGridHaving(grid, ch -> ch == 'S');
        Coord end = GridUtils.getCoordInGridHaving(grid, ch -> ch == 'E');

        int minDistance = getAnswer(grid, start, end, Part.ONE, Integer.MAX_VALUE);
        if (part == Part.ONE) {
            return minDistance;
        }
        return getAnswer(grid, start, end, part, minDistance);
    }

    private int getAnswer(Character[][] grid, Coord start, Coord end, Part part, int lowestScore) {
        Queue<QueueElement> queue = new LinkedList<>();
        queue.add(new QueueElement(start.getRow(), start.getCol(), Direction.E, 0, Arrays.asList(start)));

        Map<CostElement, Integer> costs = new HashMap<>();
        List<List<Coord>> paths = new ArrayList<>();

        while (!queue.isEmpty()) {
            QueueElement queueElement = queue.poll();

            CostElement costElement = new CostElement(queueElement.getRow(), queueElement.getCol(), queueElement.getDir());
            if (costs.containsKey(costElement) && costs.get(costElement) < queueElement.getScore()) {
                continue;
            }
            costs.put(costElement, queueElement.getScore());

            if (queueElement.getScore() > lowestScore) {
                continue;
            }

            if (queueElement.getRow() == end.getRow() && queueElement.getCol() == end.getCol() && queueElement.getScore() == lowestScore) {
                paths.add(queueElement.getPath());
                continue;
            }

            int newRow = queueElement.getRow() + queueElement.getDir().getRowDiff();
            int newCol = queueElement.getCol() + queueElement.getDir().getColDiff();

            if (grid[newRow][newCol] != '#') {
                List<Coord> newPath = new ArrayList<>(queueElement.getPath());
                newPath.add(new Coord(newRow, newCol));
                queue.add(new QueueElement(newRow, newCol, queueElement.getDir(), queueElement.getScore() + 1, newPath));
            }
            queue.add(new QueueElement(queueElement.getRow(), queueElement.getCol(), queueElement.getDir().getDirectionToLeft(), queueElement.getScore() + 1000, new ArrayList<>(queueElement.getPath())));
            queue.add(new QueueElement(queueElement.getRow(), queueElement.getCol(), queueElement.getDir().getDirectionToRight(), queueElement.getScore() + 1000, new ArrayList<>(queueElement.getPath())));
        }

        if (part == Part.ONE) {
            int minDistanceToEnd = Integer.MAX_VALUE;
            for (Map.Entry<CostElement, Integer> entry : costs.entrySet()) {
                Coord coord = new Coord(entry.getKey().getRow(), entry.getKey().getCol());
                if (coord.equals(end) && entry.getValue() < minDistanceToEnd) {
                    minDistanceToEnd = entry.getValue();
                }
            }
            return minDistanceToEnd;
        } else {
            Set<Coord> uniqueTiles = new HashSet<>();
            for (List<Coord> path : paths) {
                uniqueTiles.addAll(path);
            }
            return uniqueTiles.size();
        }
    }

    @Data
    @AllArgsConstructor
    private static class QueueElement {
        private int row;
        private int col;
        private Direction dir;
        private int score;
        private List<Coord> path;
    }

    @Data
    @AllArgsConstructor
    private static class CostElement {
        private int row;
        private int col;
        private Direction dir;
    }
}