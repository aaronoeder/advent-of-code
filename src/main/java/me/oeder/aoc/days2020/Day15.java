package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> nums = Arrays.asList(lines.get(0).split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		
		int lastSpokenNum = -1;
		Map<Integer, List<Integer>> spokenNums = new HashMap<>();
		
		for (int round = 0; round < (part == Part.ONE ? 2020 : 30000000); round++) {
			int num = -1;
			if (round < nums.size()) {
				num = nums.get(round);
			} else {
				List<Integer> lastSpokenNumHistory = spokenNums.get(lastSpokenNum);
				if (lastSpokenNumHistory.size() == 1) {
					num = 0;
				} else {
					num = lastSpokenNumHistory.get(lastSpokenNumHistory.size() - 1) - lastSpokenNumHistory.get(lastSpokenNumHistory.size() - 2);
				}
			}
			
			lastSpokenNum = num;
			if (!spokenNums.containsKey(num)) {
				spokenNums.put(num, new ArrayList<>());
			}
			spokenNums.get(num).add(round);
		}
		
		return lastSpokenNum;
	}
}