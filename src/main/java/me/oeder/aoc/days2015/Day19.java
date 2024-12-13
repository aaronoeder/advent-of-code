package me.oeder.aoc.days2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day19 extends AdventDay {
	
	@Data
	@AllArgsConstructor
	private class Replacement {
		private String start;
		private String end;
	}

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String initialString = "";
		List<Replacement> replacements = new ArrayList<>();
		boolean finishedReplacements = false;
		for (String line : lines) {
			if (finishedReplacements) {
				initialString = line;
			} else if (line.equals("")) { 
				finishedReplacements = true;
			} else {
				String[] parts = line.split(" => ");
				replacements.add(new Replacement(parts[0], parts[1]));
			}
		}
		
		if (part == Part.ONE) { 
			Set<String> results = new HashSet<>();
			for (Replacement replacement : replacements) {
				int index = 0;
				while (index < initialString.length()) {
					int foundIndexStart = initialString.indexOf(replacement.getStart(), index);
					if (foundIndexStart == -1) {
						break;
					} else {
						int foundIndexEnd = foundIndexStart + replacement.getStart().length();
						results.add(initialString.substring(0, foundIndexStart) + replacement.getEnd() + initialString.substring(foundIndexEnd));
						index = foundIndexEnd;
					}
				}
			}
			return results.size();
		} else {
			return getMinStepCount(0, "e", initialString, replacements);
		}
	}

	private Map<StepData, Integer> cache = new HashMap<>();
	private int getMinStepCount(int steps, String cur, String target, List<Replacement> replacements) {
		StepData sd = new StepData(steps, cur, target);
		if (cache.containsKey(sd)) {
			return cache.get(sd);
		}
		int min = Integer.MAX_VALUE;
		if (cur.equals(target)) {
			min = steps;
		} else {
			for (Replacement replacement : replacements) {
				int index = 0;
				while (index < cur.length()) {
					int foundIndexStart = cur.indexOf(replacement.getStart(), index);
					if (foundIndexStart == -1) {
						break;
					} else {
						int foundIndexEnd = foundIndexStart + replacement.getStart().length();
						String next = cur.substring(0, foundIndexStart) + replacement.getEnd() + cur.substring(foundIndexEnd);
						if (next.equals(target)) {
							return steps + 1;
						} else if (next.length() < target.length()) {
							int nextStepCount = getMinStepCount(steps + 1, next, target, replacements);
							if (nextStepCount < min) {
								min = nextStepCount;
							}
						}
						index = foundIndexEnd;
					}
				}
			}
		}
		cache.put(sd, min);
		return min;
	}

	@Data
	@AllArgsConstructor
	private static class StepData {
		private int steps;
		private String cur;
		private String target;
	}
}