package me.oeder.aoc.days2021;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day01 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int count = 0;
		int offset = (part == Part.ONE ? 1 : 3);
		for (int i = offset; i < lines.size(); i++) {
			int cur = Integer.parseInt(lines.get(i));
			int prev = Integer.parseInt(lines.get(i - offset));
			
			if (cur > prev) {
				count++;
			}
		}
		return count;
	}
}