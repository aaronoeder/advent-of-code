package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.List;
import java.util.stream.Collectors;

public class Day05 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> nums = lines.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		
		int steps = 0;
		int index = 0;
		while (true) {
			steps++;
			int val = nums.get(index);
			int nextIndex = index + val;
			if (nextIndex < 0 || nextIndex >= lines.size()) {
				break;
			}
			
			int offset = 1;
			if (part == Part.TWO && val >= 3) {
				offset = -1;
			}

			nums.set(index, val + offset);
			index = nextIndex;
		}
		return steps;
	}
}