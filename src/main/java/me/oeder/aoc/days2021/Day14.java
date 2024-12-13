package me.oeder.aoc.days2021;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends AdventDay {
	
	private Map<String, String> insertionMapping = new HashMap<>();
	private Map<String, Long> counts = new HashMap<>();
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String polymer = lines.get(0);
		String finalCharacter = polymer.substring(polymer.length() - 1);
		
		for (int i = 2; i < lines.size(); i++) {
			String[] parts = lines.get(i).split(" -> ");
			insertionMapping.put(parts[0], parts[1]);
		}
		
		// Setup
		for (int j = 0; j < polymer.length() - 1; j++) {
			String pair = polymer.substring(j, j + 2);
			counts.put(pair, 1 + counts.getOrDefault(pair, 0L));
		}
		
		// Passes
		for (int i = 0; i < (part == Part.TWO ? 40 : 10); i++) {
			Map<String, Long> newCounts = new HashMap<>();
			for (Map.Entry<String, Long> entry : counts.entrySet()) {
				String pair = entry.getKey();
				long count = entry.getValue();
				if (insertionMapping.containsKey(pair)) {
					String insertion = insertionMapping.get(pair);
					
					String newPair1 = pair.substring(0, 1) + insertion;
					String newPair2 = insertion + pair.substring(1, 2);
					
					newCounts.put(newPair1, count + newCounts.getOrDefault(newPair1, 0L));
					newCounts.put(newPair2, count + newCounts.getOrDefault(newPair2, 0L));
				} else {
					newCounts.put(pair, count);
				}
			}
			
			counts = newCounts;
		}
		
		// Counts
		Map<String, Long> charCounts = new HashMap<>();
		for (Map.Entry<String, Long> entry : counts.entrySet()) {
			String firstCharacter = entry.getKey().substring(0, 1);
			long count = entry.getValue();
			charCounts.put(firstCharacter, count + charCounts.getOrDefault(firstCharacter, 0L));
		}
		charCounts.put(finalCharacter, 1 + charCounts.getOrDefault(finalCharacter, 0L));
		
		return getMostCommonCharacterCount(charCounts) - 
				getLeastCommonCharacterCount(charCounts);
	}
	
	private long getMostCommonCharacterCount(Map<String, Long> charCounts) {
		return new ArrayList<>(charCounts.values()).stream().sorted((a, b) -> b.compareTo(a)).findFirst().get();
	}
	
	private long getLeastCommonCharacterCount(Map<String, Long> charCounts) {
		return new ArrayList<>(charCounts.values()).stream().sorted((a, b) -> a.compareTo(b)).findFirst().get();
	}
}