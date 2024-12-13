package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<List<String>> groups = new ArrayList<>();
		List<String> group = new ArrayList<>();
		for (String line : lines) {
			if (line.equals("")) {
				groups.add(group);
				group = new ArrayList<>();
			} else {
				group.add(line);
			}
		}
		groups.add(group);
		
		int sum = 0;
		for (List<String> g : groups) {
			Map<Character, Integer> letterCounts = new HashMap<>();
			for (String person : g) {
				for (char ch : person.toCharArray()) {
					letterCounts.put(ch, 1 + letterCounts.getOrDefault(ch, 0));
				}
			}
			
			if (part == Part.ONE) {
				sum += letterCounts.size();
			} else {
				for (Map.Entry<Character, Integer> entry : letterCounts.entrySet()) {
					if (entry.getValue() == g.size()) {
						sum++;
					}
				}
			}
		}
		
		return sum;
	}
}