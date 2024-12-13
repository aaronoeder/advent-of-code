package me.oeder.aoc.days2019;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day01 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			int num = Integer.parseInt(line);
			sum += getFuel(0, num, part);
		}
		return sum;
	}
	
	public int getFuel(int total, int num, Part part) {
		int result = Math.floorDiv(num, 3) - 2;
		if (part == Part.ONE) {
			return result;
		}
		if (result > 0) {
			return getFuel(total + result, result, part);
		}
		return total;
	}
}