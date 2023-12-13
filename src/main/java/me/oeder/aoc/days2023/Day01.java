package me.oeder.aoc.days2023;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day01 extends AdventDay2023 {

	public Day01() {
		super(1);
	}

	@Override
	public Object solvePart1(List<String> lines) {
		return getSum(lines, false);
	}
	
	@Override
	public Object solvePart2(List<String> lines) {
		return getSum(lines, true);
	}
	
	private int getSum(List<String> lines, boolean part2) {
		Map<String, Integer> digitMap = new LinkedHashMap<>();
		if (part2) {
			digitMap.put("one", 1);
			digitMap.put("two", 2);
			digitMap.put("three", 3);
			digitMap.put("four", 4);
			digitMap.put("five", 5);
			digitMap.put("six", 6);
			digitMap.put("seven", 7);
			digitMap.put("eight", 8);
			digitMap.put("nine", 9);
		}
		
		int sum = 0;
		
		for (String line : lines) {
			String firstDigit = null;
			String lastDigit = null;
			
			for (int i = 0; i < line.length(); i++) {
				String digit = null;
				if (Character.isDigit(line.charAt(i))) {
					digit = String.valueOf(line.charAt(i));
				} else {
					for (Map.Entry<String, Integer> entry : digitMap.entrySet()) {
						if (line.substring(i).startsWith(entry.getKey())) {
							digit = String.valueOf(entry.getValue());
						}
					}
				}
				
				if (digit != null) {
					if (firstDigit == null) {
						firstDigit = digit;
						lastDigit = digit;
					} else {
						lastDigit = digit;
					}
				}
			}
			
			sum += Integer.valueOf(firstDigit + lastDigit);
		}
		return sum;
	}
}