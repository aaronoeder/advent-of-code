package me.oeder.aoc.days2016;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

public class Day13 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        int num = Integer.parseInt(lines.get(0));
        if (part == Part.ONE) {
            return getMinDistance(new Coord(1, 1), new Coord(39, 31), false, (cur, next) -> isOpen(next, num));
        }
        return getDistances(new Coord(1, 1), null, false, (cur, next) -> isOpen(next, num), part).size();
    }

    private boolean isOpen(Coord coord, int num) {
        int result = coord.getCol() * coord.getCol() + 3 * coord.getCol() +
                2 * coord.getRow() * coord.getCol() + coord.getRow() + coord.getRow() * coord.getRow();
        result += num;
        String binaryString = Integer.toBinaryString(result);

        int oneBits = 0;
        for (char ch : binaryString.toCharArray()) {
            if (ch == '1') {
                oneBits++;
            }
        }
        return oneBits % 2 == 0;
    }

    public static int getMinDistance(Coord start, Coord end, boolean includeDiagonalNeighbors, BiPredicate<Coord, Coord> isValidNeighbor) {
        for (Map.Entry<Coord, Integer> entry : getDistances(start, end, includeDiagonalNeighbors, isValidNeighbor, Part.ONE).entrySet()) {
            if (entry.getKey().equals(end)) {
                return entry.getValue();
            }
        }
        return -1;
    }

    public static Map<Coord, Integer> getDistances(Coord start, Coord end, boolean includeDiagonalNeighbors, BiPredicate<Coord, Coord> isValidNeighbor, Part part) {
        List<Coord> queue = new ArrayList<>();
        queue.add(start);

        Map<Coord, Integer> distances = new HashMap<>();
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Coord cur = queue.remove(0);

            if (cur.equals(end)) {
                break;
            }

            for (Coord neighbor : cur.getNeighbors(includeDiagonalNeighbors)) {
                if (neighbor.getRow() < 0 || neighbor.getCol() < 0) {
                    continue;
                }
                if (distances.containsKey(neighbor)) {
                    continue;
                }
                if (!isValidNeighbor.test(cur, neighbor)) {
                    continue;
                }

                if (part == Part.TWO && distances.get(cur) == 50) {
                    continue;
                }

                queue.add(neighbor);
                distances.put(neighbor, distances.get(cur) + 1);
            }
        }

        return distances;
    }
}