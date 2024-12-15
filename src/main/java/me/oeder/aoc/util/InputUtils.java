package me.oeder.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputUtils {
	private InputUtils() {}

	public static List<Integer> getNums(String str) {
		List<Integer> nums = new ArrayList<>();
		Pattern pattern = Pattern.compile("-?\\d+");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			nums.add(Integer.parseInt(matcher.group()));
		}
		return nums;
	}
	
	public static Character[][] loadLinesIntoGrid(List<String> lines) {
		int maxLineLength = lines.stream().map(line -> line.length()).max(Integer::compareTo).get();
		Character[][] grid = new Character[lines.size()][maxLineLength];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < maxLineLength; j++) {
				grid[i][j] = j < line.length() ? line.charAt(j) : ' ';
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