package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        long total = 0;
        for (String line : lines) {
            List<Integer> indices = new ArrayList<>();
            findDigits(line, indices, (part == Part.ONE ? 2 : 12));

            String largestNumber = "";
            for (int index : indices) {
                largestNumber += line.substring(index, index + 1);
            }
            total += Long.parseLong(largestNumber);
        }
        return total;
    }

    private void findDigits(String line, List<Integer> indices, int digitsNeeded) {
        if (indices.size() == digitsNeeded) {
            return;
        }
        int bestIndex = -1;
        for (int i = 9; i >= 0; i--) {
            int index = findAvailableIndex(line, i, indices);
            if (index == -1) {
                continue;
            }
            int digitsToTheRight = line.length() - index - 1;
            int digitsLeftToFind = digitsNeeded - indices.size() - 1;
            if (digitsToTheRight >= digitsLeftToFind) {
                bestIndex = index;
                break;
            }
        }
        indices.add(bestIndex);
        findDigits(line, indices, digitsNeeded);
    }

    private int findAvailableIndex(String line, int value, List<Integer> indices) {
        for (int i = 0; i < line.length(); i++) {
            if (Integer.parseInt(line.substring(i, i + 1)) == value && (indices.isEmpty() || indices.getLast() < i)) {
                return i;
            }
        }
        return -1;
    }
}