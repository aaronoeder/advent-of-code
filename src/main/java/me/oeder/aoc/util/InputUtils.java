package me.oeder.aoc.util;

import java.util.List;

public final class InputUtils {
	private InputUtils() {}
	
	public static Character[][] loadLinesIntoGrid(List<String> lines) {
		Character[][] grid = new Character[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				grid[i][j] = line.charAt(j);
			}
		}
		return grid;
	}
	
	public static Integer[][] loadLinesIntoIntegerGrid(List<String> lines) {
		Integer[][] grid = new Integer[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				grid[i][j] = Integer.parseInt(line.substring(j, j + 1));
			}
		}
		return grid;
	}
	
	public static <T> void printGrid(T[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}