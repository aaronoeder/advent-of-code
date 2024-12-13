package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			List<Integer> nums = Arrays.asList(line.split("\t")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			sum += (part == Part.ONE ? getChecksum(nums) : getDivision(nums));
		}
		return sum;
	}
	
	private int getChecksum(List<Integer> nums) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int num : nums) {
			if (num < min) {
				min = num;
			}
			if (num > max) {
				max = num;
			}
		}
		return max - min;
	}
	
	private int getDivision(List<Integer> nums) {
		Collections.sort(nums, (n1, n2) -> n2 - n1);
		for (int i = 0; i < nums.size(); i++) {
			for (int j = i + 1; j < nums.size(); j++) {
				int firstNum = nums.get(i);
				int secondNum = nums.get(j);
				if (firstNum % secondNum == 0) {
					return firstNum / secondNum;
				}
			}
		}
		return -1;
	}
}