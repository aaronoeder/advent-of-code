package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day05 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int maxSeatId = 0;
		int seatId = 0;
		Set<Integer> seatIds = new HashSet<>();
		for (String line : lines) {
			String[] rowInstructions = line.substring(0, 7).split("");
			String[] colInstructions = line.substring(7).split("");
			
			int row = getValue(rowInstructions, 0, 127, "F", "B");
			int col = getValue(colInstructions, 0, 7, "L", "R");
			
			int id = 8 * row + col;
			if (id > maxSeatId) {
				maxSeatId = id;
			}
			
			seatIds.add(id);
		}
		
		for (int i = 0; i < 128 * 8; i++) {
			if (!seatIds.contains(i) && seatIds.contains(i - 1) && seatIds.contains(i + 1)) {
				seatId = i;
			}
		}
		
		if (part == Part.ONE) { 
			return maxSeatId;
		} else {
			return seatId;
		}
	}
	
	private int getValue(String[] instructions, int lower, int upper, String lowerChar, String upperChar) {
		int index = 0;
		while (index < instructions.length) {
			String val = instructions[index];
			if (val.equals(lowerChar)) {
				upper = upper - (upper - lower + 1) / 2;
			} else {
				lower = lower + + (upper - lower + 1) / 2;
			}
			index++;
		}
		return lower;
	}
}