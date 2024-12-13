package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day02 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			String[] parts = line.split(" ");
			String[] rangeParts = parts[0].split("-");
			
			int min = Integer.parseInt(rangeParts[0]);
			int max = Integer.parseInt(rangeParts[1]);
			
			String ch = parts[1].substring(0, 1);
			
			String str = parts[2];
			
			if (part == Part.ONE) { 
				int len = str.length();
				int adjustedLen = str.replace(ch, "").length();
				
				int diff = len - adjustedLen;
				if (diff >= min && diff <= max) {
					sum++;
				}
			} else {
				int matches = 0;
				if (str.substring(min - 1, min).equals(ch)) {
					matches++;
				}
				if (str.substring(max - 1, max).equals(ch)) {
					matches++;
				}
				if (matches == 1) {
					sum++;
				}
			}
		}
		
		return sum;
	}
}