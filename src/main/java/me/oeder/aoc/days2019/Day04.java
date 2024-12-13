package me.oeder.aoc.days2019;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day04 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String[] parts = lines.get(0).split("-");
		int count = 0;

		int min = Integer.parseInt(parts[0]);
		int max = Integer.parseInt(parts[1]);
		for (int i = min; i <= max; i++) {
			if (isValid(i, part)) {
				count++;
			}
		}
		return count;
	}

	private boolean isValid(int num, Part part) {
		String numStr = String.valueOf(num);

		List<String> groups = new ArrayList<>();
		String group = "";

		for (int i = 0; i < numStr.length(); i++) {
			int digit = Integer.parseInt(numStr.substring(i, i + 1));
			int prevDigit = i > 0 ? Integer.parseInt(numStr.substring(i - 1, i)) : -1;

			if (digit < prevDigit) {
				return false;
			}
			if (digit == prevDigit) {
				group += String.valueOf(digit);
			} else {
				groups.add(group);
				group = String.valueOf(digit);
			}
		}
		groups.add(group);

		if (part == Part.ONE) {
			return groups.stream().filter(str -> str.length() > 1).count() > 0;
		} else {
			return groups.stream().filter(str -> str.length() == 2).count() > 0;
		}
	}
}