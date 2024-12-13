package me.oeder.aoc.days2021;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.DoubleDirection;
import me.oeder.aoc.util.InputUtils;

public class Day11 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Integer[][] grid = InputUtils.loadLinesIntoIntegerGrid(lines);
		
		int flashCount = 0;
		int max = (part == Part.ONE ? 100 : 1000);
		for (int step = 1; step <= max; step++) {
			Queue<Coord> flashCoords = new LinkedList<>();
			
			// Increase energy level
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					grid[i][j] = grid[i][j] + 1;
					
				}
			}
			
			// Flash
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					if (grid[i][j] > 9) {
						flashCoords.add(new Coord(i, j));
					}
					
				}
			}
			
			Set<Coord> flashed = new HashSet<>();
			while (!flashCoords.isEmpty()) {
				Coord coord = flashCoords.poll();
				if (flashed.contains(coord)) {
					continue;
				}

				for (DoubleDirection dd : DoubleDirection.values()) {
					int row = coord.getRow() + dd.getRowDiff();
					int col = coord.getCol() + dd.getColDiff();
					if (row < 0 || row > grid.length - 1 || col < 0 || col > grid[0].length - 1) {
						continue;
					}
					
					int newEnergyLevel = grid[row][col] + 1;
					grid[row][col] = newEnergyLevel;
					
					if (newEnergyLevel > 9) {
						flashCoords.add(new Coord(row, col));
					}
				}
				
				flashed.add(coord);
				flashCount++;
			}
			
			for (Coord coord : flashed) {
				grid[coord.getRow()][coord.getCol()] = 0;
			}
			
			if (part == Part.TWO && flashed.size() == grid.length * grid[0].length) {
				return step;
			}
		}
		
		return flashCount;
	}
}