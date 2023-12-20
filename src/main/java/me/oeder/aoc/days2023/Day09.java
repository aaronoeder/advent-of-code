package me.oeder.aoc.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 extends AdventDay2023 {
	
	public Day09() {
		super(9);
	}
	
	@Override
	public Object solvePart1(List<String> lines) {
		return getSum(lines, Part.ONE);
	}
	
	@Override
	public Object solvePart2(List<String> lines) {
		return getSum(lines, Part.TWO);
	}
	
	private int getSum(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			List<Integer> nums = Arrays.asList(line.split(" ")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			if (part == Part.TWO) {
				Collections.reverse(nums);
			}
			sum += getNextNumber(nums);
		}
		return sum;
	}
	
	private int getNextNumber(List<Integer> nums) {
		boolean allZero = true;
		List<Integer> nextRow = new ArrayList<>();
		for (int i = 0; i < nums.size() - 1; i++) {
			int nextRowNum = nums.get(i + 1) - nums.get(i);
			nextRow.add(nextRowNum);
			if (nextRowNum != 0) {
				allZero = false;
			}
		}
		
		if (allZero) {
			return nums.get(nums.size() - 1);
		} else {
			return nums.get(nums.size() - 1) + getNextNumber(nextRow);
		}
	}
}