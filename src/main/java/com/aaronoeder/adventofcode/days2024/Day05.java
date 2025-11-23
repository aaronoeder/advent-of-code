package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<SortOrderData> sortOrder = new ArrayList<>();
        List<List<Integer>> lists = new ArrayList<>();

        boolean hitEmptyLine = false;
        for (String line : lines) {
            if (!hitEmptyLine) {
                if (line.isEmpty()) {
                    hitEmptyLine = true;
                } else {
                    String[] parts = line.split("\\|");
                    sortOrder.add(new SortOrderData(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                }
            } else {
                lists.add(Arrays.asList(line.split(",")).stream()
                    .map(s -> Integer.parseInt(s))
                    .collect(Collectors.toList()));
            }
        }

        int sum = 0;
        for (List<Integer> list : lists) {
            List<Integer> sortedList = getSortedList(list, sortOrder);
            if (list.equals(sortedList) && part == Part.ONE) {
                sum += getMiddleElement(sortedList);
            } else if (!list.equals(sortedList) && part == Part.TWO) {
                sum += getMiddleElement(sortedList);
            }
        }
        return sum;
    }

    private List<Integer> getSortedList(List<Integer> list, List<SortOrderData> sortOrder) {
        List<Integer> deepCopyOfList = new ArrayList<>(list);
        Collections.sort(deepCopyOfList, (n1, n2) -> {
            for (SortOrderData sortOrderData : sortOrder) {
                if (n1 == sortOrderData.getFirst() && n2 == sortOrderData.getSecond()) {
                    return -1;
                } else if (n1 == sortOrderData.getSecond() && n2 == sortOrderData.getFirst()) {
                    return 1;
                }
            }
            return 0;
        });
        return deepCopyOfList;
    }

    private int getMiddleElement(List<Integer> list) {
        return list.get(list.size() / 2);
    }

    @Data
    @AllArgsConstructor
    private static class SortOrderData {
        private int first;
        private int second;
    }
}