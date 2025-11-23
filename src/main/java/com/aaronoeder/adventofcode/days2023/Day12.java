package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

public class Day12 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int replications = (part == Part.ONE ? 1 : 5);
		long sum = 0;
		for (String line : lines) {
			String[] parts = line.split(" ");
			String record = String.join("?", Collections.nCopies(replications, parts[0]));
			List<Integer> nums = getIntegerList(replications, parts[1].split(","));
			sum += getArrangementCount(record, nums);
		}
		return sum;
	}
	
	@Data
	@AllArgsConstructor
	private class CachedItem {
		private String record;
		private List<Integer> nums;
	}
	
	private Map<CachedItem, Long> cache = new HashMap<>();
	
	private long getArrangementCount(String record, List<Integer> nums) {
		CachedItem cachedItem = new CachedItem(record, nums);
		
		if (cache.containsKey(cachedItem)) {
			return cache.get(cachedItem);
		}
		
		long count = 0;
		if (record.length() == 0) {
			if (nums.size() == 0) {
				count = 1;
			} else {
				count = 0;
			}
		} else if (record.startsWith(".")) {
			count = getArrangementCount(record.substring(1), nums);
		} else if (record.startsWith("?")) {
			count = getArrangementCount("." + record.substring(1), nums) + getArrangementCount("#" + record.substring(1), nums);
		} else if (record.startsWith("#")) {
			if (nums.size() == 0) {
				count = 0;
			} else {
				int num = nums.get(0);
				if (record.length() < num) {
					count = 0;
				} else {
					boolean foundDot = false;
					for (int i = 0; i < num; i++) {
						if (record.substring(i, i + 1).equals(".")) {
							foundDot = true;
							count = 0;
						}
					}
					if (!foundDot) {
						if (nums.size() > 1) {
							if (record.length() < num + 1 || record.substring(num, num + 1).equals("#")) {
								count = 0;
							} else {
								count = getArrangementCount(record.substring(num + 1), nums.subList(1, nums.size()));
							}
						} else {
							count = getArrangementCount(record.substring(num), nums.subList(1, nums.size()));
						}
					}
				}
			}
		}
		cache.put(cachedItem, count);
		
		return count;
	}
	
	private List<Integer> getIntegerList(int repetitions, String[] arr) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < repetitions; i++) {
			list.addAll(Arrays.asList(arr).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
		}
		return list;
	}
}