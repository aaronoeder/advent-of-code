package me.oeder.aoc.days2018;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day05 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String originalLine = lines.get(0);
		
		List<String> replacements = new ArrayList<>();
		replacements.add("");
		if (part == Part.TWO) {
			replacements.addAll(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", 
					"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
					"w", "x", "y", "z"));
		}
		
		int min = Integer.MAX_VALUE;
		for (String replacement : replacements) {
			String line = originalLine;
			line = line.replace(replacement, "");
			line = line.replace(replacement.toUpperCase(), "");
			int index = 0;
			while (index < line.length() - 1) {
				String left = line.substring(index, index + 1);
				String right = line.substring(index + 1, index + 2);
				if (!left.equals(right) && left.toUpperCase().equals(right.toUpperCase())) {
					line = collapse(line, index);
					index = Math.max(0, index - 1);
				} else {
					index++;
				}
			}
			if (line.length() < min) {
				min = line.length();
			}
		}
		
		return min;
	}
	
	private String collapse(String s, int index) {
		return s.substring(0, index) + s.substring(index + 2);
	}
}