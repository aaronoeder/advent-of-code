package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day06 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String line = lines.get(0);
		
		int preMarkerCount = 0;
		int offset = (part == Part.ONE ? 3 : 13);
		for (int i = offset; i < line.length(); i++) {
			String substring = line.substring(i - offset, i + 1);
			if (doesStringHaveUniqueCharacters(substring)) {
				preMarkerCount = (i + 1);
				break;
			}
		}
		return preMarkerCount;
	}
	
	private boolean doesStringHaveUniqueCharacters(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			for (int j = i + 1; j < chars.length; j++) {
				if (chars[i] == chars[j]) {
					return false;
				}
			}
		}
		return true;
	}
}