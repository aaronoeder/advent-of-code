package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Character> commonCharacters = new ArrayList<>();
		if (part == Part.ONE) {
			for (String line : lines) {
				String firstHalf = line.substring(0, line.length() / 2);
				String secondHalf = line.substring(line.length() / 2);
				commonCharacters.add(getCommonCharacter(firstHalf, secondHalf));
			}
		} else {
			for (int i = 0; i < lines.size(); i += 3) {
				String line1 = lines.get(i);
				String line2 = lines.get(i + 1);
				String line3 = lines.get(i + 2);
				commonCharacters.add(getCommonCharacter(line1, line2, line3));
			}
		}
		return getPriority(commonCharacters);
	}
	
	private char getCommonCharacter(String...strings) {
		for (char c : strings[0].toCharArray()) {
			
			boolean common = true;
			for (int i = 1; i < strings.length; i++) {
				if (!strings[i].contains(String.valueOf(c))) {
					common = false;
				}
			}
			
			if (common) {
				return c;
			}
		}
		throw new IllegalArgumentException("Couldn't find common character");
	}
	
	private int getPriority(List<Character> commonCharacters) {
		int priority = 0;
		for (Character c : commonCharacters) {
			if (Character.isUpperCase(c)) {
				priority += 26 + (Character.getNumericValue(c) - Character.getNumericValue('A') + 1);
			} else {
				priority += (Character.getNumericValue(c) - Character.getNumericValue('a') + 1);
			}
		}
		return priority;
	}
}