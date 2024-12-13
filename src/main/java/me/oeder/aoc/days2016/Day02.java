package me.oeder.aoc.days2016;

import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Direction;

public class Day02 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String[][] keypad = null;
		
		if (part == Part.ONE) {
			keypad = new String[][] {
				{ "1", "2", "3" },
				{ "4", "5", "6" },
				{ "7", "8", "9" }
			};
		} else {
			keypad = new String[][] {
				{ null, null, "1", null, null },
				{ null, "2", "3", "4", null },
				{ "5", "6", "7", "8", "9" },
				{ null, "A", "B", "C", null },
				{ null, null, "D", null, null }
			};
		}
		
		String code = "";
		
		int row = -1;
		int col = -1;
		for (int i = 0; i < keypad.length; i++) {
			for (int j = 0; j < keypad[0].length; j++) {
				if ("5".equals(keypad[i][j])) {
					row = i;
					col = j;
				}
			}
		}

		for (String line : lines) {
			for (String instruction : line.split("")) {
				Direction dir = null;
				switch (instruction) {
					case "U":
						dir = Direction.N;
						break;
					case "D":
						dir = Direction.S;
						break;
					case "L":
						dir = Direction.W;
						break;
					case "R":
						dir = Direction.E;
						break;
				}
				int newRow = row + dir.getRowDiff();
				int newCol = col + dir.getColDiff();
				
				if (newRow < 0 || newRow >= keypad.length || newCol < 0 || newCol >= keypad[0].length) {
					continue;
				}
				
				if (keypad[newRow][newCol] == null) {
					continue;
				}
				
				row = newRow;
				col = newCol;
			}
			
			code += keypad[row][col];
		}
		return code;
	}
}