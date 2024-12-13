package me.oeder.aoc.days2020;

import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.InputUtils;

public class Day11 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);

		int occupied = 0;
		while (true) {
			occupied = 0;
			Character[][] newGrid = new Character[grid.length][grid[0].length];
			
			int changes = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					char val = grid[i][j];
					if (val == 'L' && getOccupiedNeighborCount(grid, i, j, part) == 0) {
						newGrid[i][j] = '#';
						changes++;
					} else if (val == '#' && getOccupiedNeighborCount(grid, i, j, part) >= (part == Part.ONE ? 4 : 5)) {
						newGrid[i][j] = 'L';
						changes++;
					} else {
						newGrid[i][j] = val;
					}
					
					if (newGrid[i][j] == '#') {
						occupied++;
					}
				}
			}
			
			if (changes == 0) {
				break;
			}
			
			grid = newGrid;
		}
		
		return occupied;
	}
	
	private int getOccupiedNeighborCount(Character[][] grid, int i, int j, Part part) {
		int neighborCount = 0;
		for (int r = -1; r <= 1; r++) {
			for (int c = -1; c <= 1; c++) {
				if (r == 0 && c == 0) {
					continue;
				}
				int row = i + r;
				int col = j + c;
				if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
					continue;
				}
				
				char val = grid[row][col];
				if (part == Part.ONE) { 
					if (val == '#') {
						neighborCount++;
					}
				} else if (part == Part.TWO) {
					while (val == '.') {
						row += r;
						col += c;
						
						if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
							row -= r;
							col -= c;
							break;
						}
						
						val = grid[row][col];
					}
					
					if (val == '#') {
						neighborCount++;
					}
				}
			}
		}
		return neighborCount;
	}
}