package com.aaronoeder.adventofcode.days2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.aaronoeder.adventofcode.AdventDay;

public class Day07 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        long sum = 0;
        for (String line : lines) {
            String[] parts = line.split(": ");
            long val = Long.parseLong(parts[0]);
            List<Long> nums = Arrays.asList(parts[1].split(" ")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());

            List<Long> possibleResults = new ArrayList<>();
            populatePossibleResults(possibleResults, nums, 0, 0, part);
            if (possibleResults.contains(val)) {
                sum += val;
            }
        }
        return sum;
    }

    private void populatePossibleResults(List<Long> possibleResults, List<Long> nums, long result, int index, Part part) {
        if (index == nums.size()) {
            possibleResults.add(result);
        } else {
            populatePossibleResults(possibleResults, nums, result + nums.get(index), index + 1, part);
            populatePossibleResults(possibleResults, nums, result * nums.get(index), index + 1, part);
            if (part == Part.TWO) {
                populatePossibleResults(possibleResults, nums, Long.parseLong("" + result + nums.get(index)), index + 1, part);
            }
        }
    }
}