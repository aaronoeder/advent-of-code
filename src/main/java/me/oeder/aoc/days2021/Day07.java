package me.oeder.aoc.days2021;

import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day07 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> nums = Arrays.asList(lines.get(0).split(",")).stream()
				.map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		
		int min = Integer.MAX_VALUE;
		int minValueInList = getMinValueInList(nums);
		int maxValueInList = getMaxValueInList(nums);
		for (int i = minValueInList; i <= maxValueInList; i++) {
			int sum = 0;
			for (int j = 0; j < nums.size(); j++) {
				sum += getCost(Math.abs(nums.get(j) - i), part);
			}
			if (sum < min) {
				min = sum;
			}
		}
		return min;
	}
	
	private int getMinValueInList(List<Integer> nums) {
		int min = Integer.MAX_VALUE;
		for (int num : nums) {
			if (num < min) {
				min = num;
			}
		}
		return min;
	}
	
	private int getMaxValueInList(List<Integer> nums) {
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			if (num > max) {
				max = num;
			}
		}
		return max;
	}
	
	private int getCost(int n, Part part) {
		if (part == Part.ONE) { 
			return n;
		}
		return n * (n + 1) / 2;
	}
}