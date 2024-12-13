package me.oeder.aoc.days2018;

import me.oeder.aoc.AdventDay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Day02 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			return getChecksum(lines);
		} else {
			return getCommonLetters(lines);
		}
	}
	
	private int getChecksum(List<String> lines) {
		int doubles = 0;
		int triples = 0;
		for (String line : lines) {
			Map<Character, Integer> counts = new HashMap<>();
			for (char c : line.toCharArray()) {
				counts.put(c, 1 + counts.getOrDefault(c, 0));
			}
			
			for (int count : new HashSet<>(counts.values())) {
				if (count == 2) {
					doubles++;
				} else if (count == 3) {
					triples++;
				}
			}
			
		}
		return doubles * triples;
	}
	
	private String getCommonLetters(List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			for (int j = i + 1; j < lines.size(); j++) {
				char[] first = lines.get(i).toCharArray();
				char[] second = lines.get(j).toCharArray();
				
				int differences = 0;
				String commonLetters = "";
				for (int k = 0; k < first.length; k++) {
					if (first[k] != second[k]) {
						differences++;
					} else {
						commonLetters += first[k];
					}
				}
				
				if (differences == 1) {
					return commonLetters;
				}
			}
		}
		return "";		
	}
}