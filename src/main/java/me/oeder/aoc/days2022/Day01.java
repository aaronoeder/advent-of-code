package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> calories = new ArrayList<>();
		int cur = 0;
		for (String line : lines) {
			if (line.equals("")) {
				calories.add(cur);
				cur = 0;
			} else {
				cur += Integer.parseInt(line);
			}
		}
		Collections.sort(calories, (c1, c2) -> c2 - c1);
		
		if (part == Part.ONE) {
			return calories.get(0);
		} else {
			return calories.get(0) + calories.get(1) + calories.get(2);
		}
	}
}