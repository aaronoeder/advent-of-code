package com.aaronoeder.adventofcode.days2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.aaronoeder.adventofcode.AdventDay;

public class Day02 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        int count = 0;
        for (String line : lines) {
            List<Integer> nums = Arrays.asList(line.split(" ")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

            boolean isSafe = isSafe(nums);
            if (isSafe) {
                count++;
            }

            if (!isSafe && part == Part.TWO) {
                List<List<Integer>> permutations = getNumsPermutationsWithOneRemoved(nums);
                for (List<Integer> permutation : permutations) {
                    if (isSafe(permutation)) {
                        count++;
                        break;
                    }
                }
            }
        }

        return count;
    }

    private boolean isSafe(List<Integer> nums) {
        boolean isSafe = true;
        boolean isIncreasing = true;
        boolean isDecreasing = true;
        int prev = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            int num = nums.get(i);
            if (num < prev) {
                isIncreasing = false;
            }
            if (num > prev) {
                isDecreasing = false;
            }
            if (!isIncreasing && !isDecreasing) {
                isSafe = false;
            }
            int diff = Math.abs(num - prev);
            if (diff < 1 || diff > 3) {
                isSafe = false;
            }
            prev = num;
        }
        return isSafe;
    }

    private List<List<Integer>> getNumsPermutationsWithOneRemoved(List<Integer> nums) {
        List<List<Integer>> permutations = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> permutation = new ArrayList<>();
            for (int j = 0; j < nums.size(); j++) {
                if (i != j) {
                    permutation.add(nums.get(j));
                }
            }
            permutations.add(permutation);
        }
        return permutations;
    }
}