package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day05 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			if (isNice(line, part)) {
				sum++;
			}
		}
		return sum;
	}
	
	private boolean isNice(String line, Part part) {
		char[] chars = line.toCharArray();
		if (part == Part.ONE) { 
			if (hasBadSubstring(line)) {
				return false;
			}
			return getVowelCount(chars) >= 3 && getTwiceInRowCount(chars) >= 1;
		} else {
			return hasPairTwiceWithoutOverlapping(chars) && hasRepetitionWithOneLetterBetween(chars);
		}
	}
	
	private boolean hasBadSubstring(String line) {
		return line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy");
	}
	
	private int getVowelCount(char[] chars) {
		int vowelCount = 0;
		for (char ch : chars) {
			if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
				vowelCount++;
			}
		}
		return vowelCount;
	}
	
	private int getTwiceInRowCount(char[] chars) {
		int count = 0;
		for (int i = 0; i < chars.length - 1; i++) {
			if (chars[i] == chars[i + 1]) {
				count++;
			}
		}
		return count;
	}
	
	private boolean hasPairTwiceWithoutOverlapping(char[] chars) {
		for (int i = 0; i < chars.length - 3; i++) {
			String firstPair = chars[i] + "" + chars[i + 1];
			for (int j = i + 2; j < chars.length - 1; j++) {
				String secondPair = chars[j] + "" + chars[j + 1];
				if (firstPair.equals(secondPair)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasRepetitionWithOneLetterBetween(char[] chars) {
		for (int i = 0; i < chars.length - 2; i++) {
			if (chars[i] == chars[i + 2]) {
				return true;
			}
		}
		return false;
	}
}