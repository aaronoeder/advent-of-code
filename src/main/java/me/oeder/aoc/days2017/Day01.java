package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day01 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String line = lines.get(0);
		
		int sum = 0;
		for (int i = 0; i < line.length(); i++) {
			int firstIndex = i;
			int secondIndex = (part == Part.ONE ? i + 1 : i + (line.length() / 2));
			
			if (secondIndex >= line.length()) {
				secondIndex %= line.length();
			}
			
			String first = line.substring(firstIndex, firstIndex + 1);
			String second = line.substring(secondIndex, secondIndex + 1);
			
			if (first.equals(second)) {
				sum += Integer.parseInt(first);
			}
		}
		
		return sum;
	}
}