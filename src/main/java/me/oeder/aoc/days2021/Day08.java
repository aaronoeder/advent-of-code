package me.oeder.aoc.days2021;

import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.List;

public class Day08 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int count = 0;
		for (String line : lines) {
			String[] parts = line.split(" \\| ");
			List<String> digits = Arrays.asList(parts[0].split(" "));
			List<String> numbers = Arrays.asList(parts[1].split(" "));
			for (String number : numbers) {
				if (number.length() == 2 || number.length() == 3 || number.length() == 4 || number.length() == 7) {
					count++;
				}
			}
		}
		return count;
	}
}