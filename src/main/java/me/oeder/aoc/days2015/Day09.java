package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day09 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Map<String, Integer>> distances = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split(" = ");
			String[] locations = parts[0].split(" to ");
			String leftLocation = locations[0];
			String rightLocation = locations[1];
			int distance = Integer.parseInt(parts[1]);
			
			if (!distances.containsKey(leftLocation)) {
				distances.put(leftLocation, new HashMap<>());
			}
			distances.get(leftLocation).put(rightLocation, distance);
			if (!distances.containsKey(rightLocation)) {
				distances.put(rightLocation, new HashMap<>());
			}
			distances.get(rightLocation).put(leftLocation, distance);
		}
		
		int shortestDistance = Integer.MAX_VALUE;
		int longestDistance = Integer.MIN_VALUE;
		Set<String> locations = distances.keySet();
		for (String location : locations) {
			List<String> visitedLocations = new ArrayList<>();
			visitedLocations.add(location);
			List<Integer> totalDistances = new ArrayList<>();
			recursiveTravel(location, 0, visitedLocations, totalDistances, distances);
			Collections.sort(totalDistances);
			if (totalDistances.size() > 0) {
				if (totalDistances.get(0) < shortestDistance) {
					shortestDistance = totalDistances.get(0);
				}
				if (totalDistances.get(totalDistances.size() - 1) > longestDistance) {
					longestDistance = totalDistances.get(totalDistances.size() - 1);
				}
			}
		}
		
		if (part == Part.ONE) {
			return shortestDistance;
		}
		return longestDistance;
	}
	
	private void recursiveTravel(String location, int distance, List<String> visitedLocations, List<Integer> totalDistances, Map<String, Map<String, Integer>> distances) {
		if (visitedLocations.size() == distances.size()) {
			totalDistances.add(distance);
		} else {
			Map<String, Integer> options = distances.get(location);
			for (Map.Entry<String, Integer> entry : options.entrySet()) {
				if (!visitedLocations.contains(entry.getKey())) {
					List<String> newVisitedLocations = new ArrayList<>(visitedLocations);
					newVisitedLocations.add(entry.getKey());
					recursiveTravel(entry.getKey(), distance + entry.getValue(), newVisitedLocations, totalDistances, distances);
				}
			}
		}
	}
}