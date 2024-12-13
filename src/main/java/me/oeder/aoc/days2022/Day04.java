package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day04 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int count = 0;
		for (String line : lines) {
			String[] parts = line.split(",");
			
			String[] range1Parts = parts[0].split("-");
			int range1Min = Integer.parseInt(range1Parts[0]);
			int range1Max = Integer.parseInt(range1Parts[1]);
			
			String[] range2Parts = parts[1].split("-");
			int range2Min = Integer.parseInt(range2Parts[0]);
			int range2Max = Integer.parseInt(range2Parts[1]);
			
			if (part == Part.ONE) { 
				if (range1Min >= range2Min && range1Max <= range2Max) {
					count++;
				} else if (range2Min >= range1Min && range2Max <= range1Max) {
					count++;
				}
			} else {
				if (range1Min >= range2Min && range1Min <= range2Max) {
					count++;
				} else if (range2Min >= range1Min && range2Min <= range1Max) {
					count++;
				}
			}
		}
		return count;
	}
}