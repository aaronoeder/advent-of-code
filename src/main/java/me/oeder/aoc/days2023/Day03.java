package me.oeder.aoc.days2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.oeder.aoc.graph.Coord;

public class Day03 extends AdventDay2023 {
	
	public Day03() {
		super(3);
	}

	@Override
	public Object solvePart1(List<String> lines) {
		return getSum(lines, false);
	}

	@Override
	public Object solvePart2(List<String> lines) {
		return getSum(lines, true);
	}
	
	private int getSum(List<String> lines, boolean part2) {
		int sum = 0;
		
		// Load input file into 2D array
		String[][] arr = new String[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				arr[i][j] = line.substring(j, j + 1);
			}
		}
		
		Map<Coord, List<Integer>> gears = new HashMap<>();
		
		for (int i = 0; i < arr.length; i++) {
			int colIndex = 0;
			while (colIndex < arr.length) {
				String num = "";
				boolean isPartNumber = false;
				Coord gearCoord = null;
				
				// Keep moving to the right until the full number is located
				// At each point in this process, we also check to see if a given digit is adjacent to a symbol
				boolean done = false;
				while (!done) {
					if (colIndex == arr.length) {
						done = true;
					} else {
						char ch = arr[i][colIndex].charAt(0);
						if (Character.isDigit(ch)) {
							num += ch;
							Coord adjacentSymbolCoord = getAdjacentSymbolCoord(arr, i, colIndex);
							if (adjacentSymbolCoord != null) {
								isPartNumber = true;
								if (arr[adjacentSymbolCoord.getRow()][adjacentSymbolCoord.getCol()].equals("*")) {
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
				
				if (!part2 && isPartNumber) {
					sum += Integer.parseInt(num);
				}
			}
		}
		
		if (part2) {
			for (Map.Entry<Coord, List<Integer>> entry : gears.entrySet()) {
				if (entry.getValue().size() == 2) {
					sum += (entry.getValue().get(0) * entry.getValue().get(1));
				}
			}
		}
		
		return sum;
	}
	
	private Coord getAdjacentSymbolCoord(String[][] arr, int i, int j) {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x == 0 && y == 0) {
					continue;
				}
				if (i + x < 0 || i + x > arr.length - 1 || j + y < 0 || j + y > arr[i].length - 1) {
					continue;
				}
				if (isSymbol(arr[i + x][j + y])) {
					return new Coord(i + x, j + y);
				}
			}
		}
		return null;
	}
	
	private boolean isSymbol(String str) {
		char c = str.charAt(0);
		return !Character.isDigit(c) && c != '.';
	}
}