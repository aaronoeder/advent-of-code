package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.aaronoeder.adventofcode.AdventDay;

public class Day09 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
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