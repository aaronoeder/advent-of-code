package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day01 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		char[] chars = lines.get(0).toCharArray();
		
		int sum = 0;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			sum += (c == '(' ? 1 : -1);
			if (part == Part.TWO && sum == -1) {
				return (i + 1);
			}
		}
		return sum;
	}
}