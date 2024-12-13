package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Pattern pattern = Pattern.compile("(\\w+) would (\\w+) (\\d+) happiness units by sitting next to (\\w+).");
		
		Map<String, Map<String, Integer>> happinessMap = new HashMap<>();
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String source = matcher.group(1);
				boolean negativeHappiness = matcher.group(2).equals("lose");
				int happiness = Integer.parseInt(matcher.group(3));
				String target = matcher.group(4);
				
				if (!happinessMap.containsKey(source)) {
					happinessMap.put(source, new HashMap<>());
				}
				happinessMap.get(source).put(target, (negativeHappiness ? -1 : 1) * happiness);
			}
		}
		
		if (part == Part.TWO) {
			happinessMap.put("You", new HashMap<>());
			for (String source : happinessMap.keySet()) {
				happinessMap.get(source).put("You", 0);
				happinessMap.get("You").put(source, 0);
			}
		}
		
		List<String> sources = new ArrayList<>(happinessMap.keySet());
		List<List<String>> permutations = new ArrayList<>();
		calculatePermutations(sources, new ArrayList<>(), permutations);
		
		int maxHappiness = Integer.MIN_VALUE;
		for (List<String> permutation : permutations) {
			int totalHappiness = getTotalHappiness(permutation, happinessMap);
			if (totalHappiness > maxHappiness) {
				maxHappiness = totalHappiness;
			}
		}
		
		return maxHappiness;
	}
	
	private void calculatePermutations(List<String> remainingOptions, List<String> partialPermutation, List<List<String>> permutations) {
		if (remainingOptions.isEmpty()) {
			permutations.add(partialPermutation);
		} else {
			for (int i = 0; i < remainingOptions.size(); i++) {
				String selectedOption = remainingOptions.get(i);
				
				List<String> newRemainingOptions = new ArrayList<>();
				for (int j = 0; j < remainingOptions.size(); j++) {
					if (i != j) {
						newRemainingOptions.add(remainingOptions.get(j));
					}
				}
				
				List<String> newPartialPermutation = new ArrayList<>(partialPermutation);
				newPartialPermutation.add(selectedOption);
				calculatePermutations(newRemainingOptions, newPartialPermutation, permutations);
			}
		}
	}
	
	private int getTotalHappiness(List<String> permutation, Map<String, Map<String, Integer>> happinessMap) {
		int totalHappiness = 0;
		for (int i = 0; i < permutation.size(); i++) {
			String source = permutation.get(i);
			String left = permutation.get(i > 0 ? i - 1 : permutation.size() - 1);
			String right = permutation.get(i < permutation.size() - 1 ? i + 1 : 0);
			totalHappiness += (happinessMap.get(source).get(left) + happinessMap.get(source).get(right));
		}
		return totalHappiness;
	}
}