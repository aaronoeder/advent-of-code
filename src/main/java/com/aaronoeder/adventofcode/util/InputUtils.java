package com.aaronoeder.adventofcode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputUtils {
	private InputUtils() {}

	public static List<Long> getLongNums(String str) {
		List<Long> nums = new ArrayList<>();
		Pattern pattern = Pattern.compile("-?\\d+");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			nums.add(Long.parseLong(matcher.group()));
		}
		return nums;
	}

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
		int maxLineLength = lines.stream().map(String::length).max(Integer::compareTo).get();
		Character[][] grid = new Character[lines.size()][maxLineLength];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < maxLineLength; j++) {
				grid[i][j] = j < line.length() ? line.charAt(j) : ' ';
			}
		}
		return grid;
	}
}