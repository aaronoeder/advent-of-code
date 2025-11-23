package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.util.InputUtils;

public class Day03 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;

		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		Map<Coord, List<Integer>> gears = new HashMap<>();
		for (int i = 0; i < grid.length; i++) {
			int colIndex = 0;
			while (colIndex < grid.length) {
				String num = "";
				boolean isPartNumber = false;
				Coord gearCoord = null;
				
				// Keep moving to the right until the full number is located
				// At each point in this process, we also check to see if a given digit is adjacent to a symbol
				boolean done = false;
				while (!done) {
					if (colIndex == grid.length) {
						done = true;
					} else {
						char ch = grid[i][colIndex];
						if (Character.isDigit(ch)) {
							num += ch;
							Coord adjacentSymbolCoord = getAdjacentSymbolCoord(grid, i, colIndex);
							if (adjacentSymbolCoord != null) {
								isPartNumber = true;
								if (grid[adjacentSymbolCoord.getRow()][adjacentSymbolCoord.getCol()] == '*') {
									gearCoord = adjacentSymbolCoord;
								}
							}
						} else {
							done = true;
						}
					}
					if (done && gearCoord != null) {
						if (!gears.containsKey(gearCoord)) {
							gears.put(gearCoord, new ArrayList<>());
						}
						gears.get(gearCoord).add(Integer.parseInt(num));
					}
					colIndex++;
				}
				
				if (part == Part.ONE && isPartNumber) {
					sum += Integer.parseInt(num);
				}
			}
		}
		
		if (part == Part.TWO) {
			for (Map.Entry<Coord, List<Integer>> entry : gears.entrySet()) {
				if (entry.getValue().size() == 2) {
					sum += (entry.getValue().get(0) * entry.getValue().get(1));
				}
			}
		}
		
		return sum;
	}
	
	private Coord getAdjacentSymbolCoord(Character[][] grid, int i, int j) {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x == 0 && y == 0) {
					continue;
				}
				if (i + x < 0 || i + x > grid.length - 1 || j + y < 0 || j + y > grid[i].length - 1) {
					continue;
				}
				if (isSymbol(grid[i + x][j + y])) {
					return new Coord(i + x, j + y);
				}
			}
		}
		return null;
	}
	
	private boolean isSymbol(char ch) {
		return !Character.isDigit(ch) && ch != '.';
	}
}