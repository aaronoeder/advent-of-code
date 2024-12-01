package me.oeder.aoc.days2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.oeder.aoc.common.Direction;
import me.oeder.aoc.util.InputUtils;

public class Day14 extends AdventDay2023 {
	
	public Day14() {
		super(14);
	}

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String[][] grid = InputUtils.loadLinesIntoGrid(lines);
		if (part == Part.ONE) {
			tiltGrid(grid, Direction.N);
		} else if (part == Part.TWO) {
			String[][] copy = getDeepCopyOfGrid(grid);
			Map<String, Long> seen = new HashMap<>();
			long currentCycle = 1;
			long cycleLength = -1;
			while (cycleLength == -1) {
				for (Direction d : Direction.values()) {
					tiltGrid(copy, d);
				}
				
				String stringifiedGrid = stringifyGrid(copy);
				if (seen.containsKey(stringifiedGrid)) {
					cycleLength = currentCycle - seen.get(stringifiedGrid);
				} else {
					seen.put(stringifiedGrid, currentCycle);
					currentCycle++;
				}
			}
			
			long finalPartialCycleLength = (1000000000 % cycleLength);
			for (long i = 0; i < cycleLength + finalPartialCycleLength; i++) {
				for (Direction d : Direction.values()) {
					tiltGrid(grid, d);
				}
			}
		}
		
		return getLoadOfGrid(grid);
	}
	
	private String[][] getDeepCopyOfGrid(String[][] grid) {
		String[][] deepCopy = new String[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				deepCopy[i][j] = grid[i][j];
			}
		}
		return deepCopy;
	}
	
	private String stringifyGrid(String[][] grid) {
		String s = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				s += grid[i][j];
			}
		}
		return s;
	}
	
	private void tiltGrid(String[][] grid, Direction dir) {
		if (dir == Direction.N || dir == Direction.W) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					tiltElementInGrid(grid, i, j, dir);
				}
			}
		} else {
			for (int i = grid.length - 1; i >= 0; i--) {
				for (int j = grid[0].length - 1; j >= 0; j--) {
					tiltElementInGrid(grid, i, j, dir);
				}
			}
		}
	}
	
	private void tiltElementInGrid(String[][] grid, int i, int j, Direction dir) {
		String val = grid[i][j];
		if (val.equals("O")) {
			int r = i;
			int c = j;
			int lastValidRow = r;
			int lastValidCol = c;
			
			if (r + dir.getRowDiff() < 0 || r + dir.getRowDiff() == grid.length || c + dir.getColDiff() < 0 || c + dir.getColDiff() == grid[0].length) {
				return;
			} else {
				r += dir.getRowDiff();
				c += dir.getColDiff();
				while (true) {
					if (grid[r][c].equals(".")) {
						lastValidRow = r;
						lastValidCol = c;
					} else if (grid[r][c].equals("O") || grid[r][c].equals("#")) {
						break;
					}
					
					if (r + dir.getRowDiff() < 0 || r + dir.getRowDiff() == grid.length || c + dir.getColDiff() < 0 || c + dir.getColDiff() == grid[0].length) {
						break;
					}
					
					r += dir.getRowDiff();
					c += dir.getColDiff();
				}
			}
			
			if (lastValidRow != i || lastValidCol != j) {
				grid[lastValidRow][lastValidCol] = "O";
				grid[i][j] = ".";
			}
		}
	}
	
	private int getLoadOfGrid(String[][] grid) {
		int load = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].equals("O")) {
					load += (grid.length - i);
				}
			}
		}
		return load;
	}
}