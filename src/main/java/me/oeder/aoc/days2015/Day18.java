package me.oeder.aoc.days2015;

import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.InputUtils;

public class Day18 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		if (part == Part.TWO) { 
			setStuckLights(grid);
		}
		
		for (int i = 0; i < 100; i++) {
			grid = getNewGrid(grid, part);
		}

		return getOnCount(grid);
	}
	
	private void setStuckLights(Character[][] grid) {
		grid[0][0] = '#';
		grid[0][grid[0].length - 1] = '#';
		grid[grid.length - 1][0] = '#';
		grid[grid.length - 1][grid[0].length - 1] = '#';
	}
	
	private Character[][] getNewGrid(Character[][] grid, Part part) {
		Character[][] newGrid = new Character[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				newGrid[i][j] = shouldBeOn(grid, i, j) ? '#': '.';
			}
		}
		if (part == Part.TWO) { 
			setStuckLights(newGrid);
		}
		return newGrid;
	}
	
	private boolean shouldBeOn(Character[][] grid, int i, int j) {
		boolean isOn = grid[i][j] == '#';
		
		int onNeighbors = 0;
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
				if (grid[row][col] == '#') {
					onNeighbors++;
				}
			}
		}
		
		if (isOn) {
			return onNeighbors == 2 || onNeighbors == 3;
		} else {
			return onNeighbors == 3;
		}
	}
	
	private int getOnCount(Character[][] grid) {
		int onCount = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '#') {
					onCount++;
				}
			}
		}
		return onCount;
	}
}