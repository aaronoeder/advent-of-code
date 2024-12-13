package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> nums = lines.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		nums.add(0);
		
		Collections.sort(nums);
		nums.add(nums.get(nums.size() - 1) + 3);
		
		if (part == Part.ONE) { 
			int singles = 0;
			int triples = 0;
			for (int i = 0; i < nums.size() - 1; i++) {
				int diff = nums.get(i + 1) - nums.get(i);
				if (diff == 1) {
					singles++;
				} else if (diff == 3) {
					triples++;
				}
			}
			
			return singles * triples;
		} else {
			List<Integer> selectedNums = new ArrayList<>();
			selectedNums.add(nums.get(0));
			
			int lastNum = nums.get(nums.size() - 1);
			
			return getArrangementCount(nums, selectedNums, lastNum);
		}
	}
	
	private Map<Integer, Long> cache = new HashMap<>();
	
	private long getArrangementCount(List<Integer> nums, List<Integer> selectedNums, int lastNum) {
		int curNum = selectedNums.get(selectedNums.size() - 1);
		
		if (cache.containsKey(curNum)) {
			return cache.get(curNum);
		}
		
		if (curNum == lastNum) {
			return 1;
		}
		
		long count = 0;
		for (int i = 1; i <= 3; i++) {
			int val = curNum + i;
			if (val > lastNum) {
				continue;
			}
			int index = nums.indexOf(val);
			if (index == -1) {
				continue;
			}
			List<Integer> deepCopy = getDeepCopyOfSelectedNums(selectedNums);
			deepCopy.add(val);
			count += getArrangementCount(nums, deepCopy, lastNum);
		}
		
		cache.put(curNum, count);
		
		return count;
	}
	
	private List<Integer> getDeepCopyOfSelectedNums(List<Integer> selectedNums) {
		List<Integer> deepCopy = new ArrayList<>();
		deepCopy.addAll(selectedNums);
		return deepCopy;
	}
}