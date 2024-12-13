package me.oeder.aoc.days2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.InputUtils;

public class Day06 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		
		String message = "";
		for (int j = 0; j < grid[0].length; j++) {
			List<Character> chars = new ArrayList<>();
			for (int i = 0; i < grid.length; i++) {
				chars.add(grid[i][j]);
			}
			message += getMessageCharacter(chars, part);
		}
		return message;
	}
	
	private Character getMessageCharacter(List<Character> chars, Part part) {
		Map<Character, Integer> charCounts = new HashMap<>();
		for (char ch : chars) {
			charCounts.put(ch, 1 + charCounts.getOrDefault(ch, 0));
		}
		
		int maxCount = charCounts.values().stream().sorted((i1, i2) -> (part == Part.ONE ? i2 - i1 : i1 - i2)).collect(Collectors.toList()).get(0);
		return charCounts.keySet().stream().filter(key -> charCounts.get(key) == maxCount).findFirst().orElse(null);
	}
}