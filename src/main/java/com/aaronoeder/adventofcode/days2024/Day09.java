package com.aaronoeder.adventofcode.days2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.aaronoeder.adventofcode.AdventDay;

public class Day09 extends AdventDay {

    private static final int SPACE = -1;

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        String line = lines.get(0);

        List<Integer> positions = new ArrayList<>();
        boolean isSpace = false;
        int fileId = 0;
        for (char ch : line.toCharArray()) {
            int length = Character.getNumericValue(ch);
            if (isSpace) {
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        positions.add(SPACE);
                    }
                }
            } else {
                for (int i = 0; i < length; i++) {
                    positions.add(fileId);
                }
                fileId++;
            }
            isSpace = !isSpace;
        }

        if (part == Part.ONE) {
            while (!isCompact(positions)) {
                int i = 0;
                int j = positions.size() - 1;
                while (i < j) {
                    if (positions.get(i) == SPACE && positions.get(j) != SPACE) {
                        positions.set(i, positions.get(j));
                        positions.set(j, SPACE);
                        i++;
                        j--;
                    } else {
                        if (positions.get(i) != SPACE) {
                            i++;
                        }
                        if (positions.get(j) == SPACE) {
                            j--;
                        }
                    }
                }
            }
        } else {
            Map<Integer, Integer> spaces = new TreeMap<>(); // key = start index of space, value = end index of space
            Integer startIndex = null;
            for (int i = 0; i < positions.size(); i++) {
                if (startIndex == null && positions.get(i) == SPACE) {
                    startIndex = i;
                } else if (startIndex != null && positions.get(i) != SPACE) {
                    spaces.put(startIndex, i - 1);
                    startIndex = null;
                }
            }

            int currentFileId = fileId - 1;
            while (currentFileId >= 0) {
                int currentFileStartIndex = positions.indexOf(currentFileId);
                int currentFileEndIndex = positions.lastIndexOf(currentFileId);
                int currentFileLength = currentFileEndIndex - currentFileStartIndex + 1;

                for (Map.Entry<Integer, Integer> entry : spaces.entrySet()) {
                    int spaceStartIndex = entry.getKey();
                    int spaceEndIndex = entry.getValue();
                    int spaceLength = spaceEndIndex - spaceStartIndex + 1;

                    if (spaceStartIndex > currentFileEndIndex) {
                        break;
                    }
                    if (spaceLength >= currentFileLength) {
                        for (int i = spaceStartIndex; i < spaceStartIndex + currentFileLength; i++) {
                            positions.set(i, currentFileId);
                        }
                        for (int i = currentFileStartIndex; i <= currentFileEndIndex; i++) {
                            positions.set(i, SPACE);
                        }
                        spaces.remove(spaceStartIndex);
                        if (spaceLength > currentFileLength) {
                            spaces.put(spaceStartIndex + currentFileLength, spaceEndIndex);
                        }
                        break;
                    }
                }
                currentFileId--;
            }
        }

        long sum = 0;
        for (int i = 0; i < positions.size(); i++) {
            int val = positions.get(i);
            if (val != SPACE) {
                sum += i * val;
            }
        }
        return sum;
    }

    private boolean isCompact(List<Integer> positions) {
        boolean foundSpace = false;
        for (int pos : positions) {
            if (!foundSpace && pos == SPACE) {
                foundSpace = true;
            } else if (foundSpace && pos != SPACE) {
                return false;
            }
        }
        return true;
    }
}