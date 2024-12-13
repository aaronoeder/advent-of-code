package me.oeder.aoc.days2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day17 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		CACHE.clear();
		
		List<Integer> options = new ArrayList<>();
		for (String line : lines) {
			options.add(Integer.parseInt(line));
		}
		
		List<Integer> availableIndices = IntStream.range(0, options.size()).boxed().collect(Collectors.toList());
		
		Collections.sort(options, (o1, o2) -> o2 - o1);
		
		Set<List<Integer>> combinations = new HashSet<>();
		generateCombinations(150, options, availableIndices, new ArrayList<>(), combinations);
		
		if (part == Part.ONE) { 
			return combinations.size();
		} else {
			List<List<Integer>> combinationsList = new ArrayList<>(combinations);
			Collections.sort(combinationsList, (c1, c2) -> c1.size() - c2.size());
			
			return combinationsList.stream().filter(c -> c.size() == combinationsList.get(0).size()).count();
		}
	}
	
	@Data
	@AllArgsConstructor
	private class CachedItem {
		private int capacity;
		private List<Integer> availableIndices;
	}
	
	private static final Set<CachedItem> CACHE = new HashSet<>();
	
	private void generateCombinations(int capacity, List<Integer> options, List<Integer> availableIndices, List<Integer> selectedIndices, Set<List<Integer>> combinations) {
		CachedItem item = new CachedItem(capacity, availableIndices);
		if (CACHE.contains(item)) {
			return;
		}
		
		if (capacity < 0) {
			return;
		}
		
		if (capacity == 0) {
			combinations.add(selectedIndices);
		} else {
			for (int availableIndex : availableIndices) {
				List<Integer> newAvailableIndices = availableIndices.stream().filter(i -> i != availableIndex).collect(Collectors.toList());
				
				List<Integer> newSelectedIndices = new ArrayList<>(selectedIndices);
				newSelectedIndices.add(availableIndex);
				
				generateCombinations(capacity - options.get(availableIndex), options, newAvailableIndices, newSelectedIndices, combinations);
			}
		}
		
		CACHE.add(item);
	}
}