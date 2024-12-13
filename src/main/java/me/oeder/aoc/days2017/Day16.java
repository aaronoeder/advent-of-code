package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Character> group = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			group.add((char)('a' + i));
		}
		
		if (part == Part.ONE) {
			transform(group, lines);
		} else {
			int cycleLength = -1;
			List<Character> deepCopyOfGroup = new ArrayList<>(group);
			Map<String, Integer> history = new HashMap<>();
			for (int i = 0; i < 100; i++) {
				transform(deepCopyOfGroup, lines);
				String str = stringify(deepCopyOfGroup);
				if (history.containsKey(str)) {
					cycleLength = i - history.get(str);
					break;
				} else {
					history.put(str, i);
				}
			}
			
			for (int i = 0; i < (1000000000 % cycleLength); i++) {
				transform(group, lines);
			}
		}
		
		return stringify(group);
	}
	
	private void transform(List<Character> group, List<String> lines) {
		for (String line : lines.get(0).split(",")) {
			if (line.startsWith("s")) {
				int size = Integer.parseInt(line.substring(1));
				List<Character> partialGroup = new ArrayList<>(group.subList(group.size() - size, group.size()));
				group.removeAll(partialGroup);
				group.addAll(0, partialGroup);
			} else if (line.startsWith("x")) {
				String[] parts = line.substring(1).split("/");
				int index1 = Integer.parseInt(parts[0]);
				int index2 = Integer.parseInt(parts[1]);
				swap(group, index1, index2);
			} else if (line.startsWith("p")) {
				String[] parts = line.substring(1).split("/");
				char value1 = parts[0].charAt(0);
				char value2 = parts[1].charAt(0);
				swap(group, group.indexOf(value1), group.indexOf(value2));
			}
		}
	}
	
	private void swap(List<Character> group, int index1, int index2) {
		char temp = group.get(index2);
		group.set(index2, group.get(index1));
		group.set(index1, temp);
	}
	
	private String stringify(List<Character> group) {
		String str = "";
		for (char ch : group) {
			str += ch;
		}
		return str;
	}
}