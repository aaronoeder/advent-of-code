package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day06 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> nums = Arrays.asList(lines.get(0).split("\t")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		
		Map<List<Integer>, List<Integer>> numsHistory = new HashMap<>(); // (nums) -> (cy
		int cycle = 0;
		while (true) {
			if (!numsHistory.containsKey(nums)) {
				numsHistory.put(nums, new ArrayList<>());
			}
			numsHistory.get(nums).add(cycle);
			
			List<Integer> indicesWithMax = getIndicesWithMax(nums);
			
			int index = indicesWithMax.get(0);
			int amount = nums.get(index);
			
			nums.set(index, 0);
			while (true) {
				for (int i = 1; i <= nums.size(); i++) {
					int redistributeIndex = (index + i) % nums.size();
					nums.set(redistributeIndex, 1 + nums.get(redistributeIndex));
					amount--;
					
					if (amount == 0) {
						break;
					}
				}
				if (amount == 0) {
					break;
				}
			}
			
			cycle++;
			
			if (numsHistory.containsKey(nums)) {
				int count = numsHistory.get(nums).size();
				if (part == Part.ONE && count == 1) {
					return cycle;
				} else if (part == Part.TWO && count == 2) {
					return numsHistory.get(nums).get(1) - numsHistory.get(nums).get(0);
				}
			}
		}
	}
	
	private List<Integer> getIndicesWithMax(List<Integer> nums) {
		List<Integer> indices = new ArrayList<>();
		int max = Integer.MIN_VALUE;
		
		for (int i = 0; i < nums.size(); i++) {
			int num = nums.get(i);
			if (num > max) {
				indices.clear();
				indices.add(i);
				max = num;
			} else if (num == max) {
				indices.add(i);
			}
		}
		
		Collections.sort(indices);
		
		return indices;
	}
}