package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day01 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> nums = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			nums.add(Integer.parseInt(lines.get(i)));
		}
		
		int amount = (part == Part.ONE ? 2 : 3);
		for (int i = 0; i < nums.size(); i++) {
			for (int j = i + 1; j < nums.size(); j++) {
				for (int k = j + 1; k < nums.size(); k++) {
					List<Integer> selections = (amount == 2 ? Arrays.asList(nums.get(i), nums.get(j)) : Arrays.asList(nums.get(i), nums.get(j), nums.get(k)));
					if (selections.size() != amount) {
						continue;
					}
					int sum = selections.stream().reduce((a, b) -> a + b).get();
					if (sum == 2020) {
						return selections.stream().reduce((a, b) -> a * b).get();
					}
				}
			}
		}
		
		return -1;
	}
}