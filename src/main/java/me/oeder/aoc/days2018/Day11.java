package me.oeder.aoc.days2018;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day11 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int serial = Integer.parseInt(lines.get(0));

		int[][] grid = new int[300][300];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int x = j;
				int y = i;
				grid[i][j] = getPower(x, y, serial);
			}
		}
		
		int minSquareSize = (part == Part.ONE ? 3 : 1);
		int maxSquareSize = (part == Part.ONE ? 3 : 300);
		
		int maxPower = Integer.MIN_VALUE;
		int x = -1;
		int y = -1;
		int size = -1;
		for (int s = minSquareSize; s <= maxSquareSize; s++) {
			for (int i = 0; i < grid.length - s; i++) {
				for (int j = 0; j < grid[0].length - s; j++) {
					
					int power = 0;
					for (int row = i; row < i + s; row++) {
						for (int col = j; col < j + s; col++) {
							power += grid[row][col];
						}
					}
					
					if (power > maxPower) {
						maxPower = power;
						x = j;
						y = i;
						size = s;
					}
				}
			}
		}
		
		if (part == Part.ONE) { 
			return x + "," + y;
		} else {
			return x + "," + y + "," + size;
		}
	}
	
	private int getPower(int x, int y, int serial) {
		int rackId = x + 10;
		int power = rackId * y;
		power += serial;
		power *= rackId;
		power = ((power / 100) % 10);
		power -= 5;
		return power;
	}
}