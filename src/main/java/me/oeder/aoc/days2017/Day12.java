package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day12 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<Integer, List<Integer>> nodes = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split(" <-> ");
			int val = Integer.parseInt(parts[0]);
			List<Integer> neighbors = Arrays.asList(parts[1].split(", ")).stream().map((s -> Integer.parseInt(s))).collect(Collectors.toList());
			nodes.put(val, neighbors);
		}

		if (part == Part.ONE) {
			List<Integer> groupMembers = new ArrayList<>();
			populateGroupMembers(nodes, 0, groupMembers);
			return groupMembers.size();
		}

		int groupCount = 0;
		List<Integer> vals = new ArrayList<>(nodes.keySet());
		while (!vals.isEmpty()) {
			List<Integer> groupMembers = new ArrayList<>();
			populateGroupMembers(nodes, vals.get(0), groupMembers);
			vals.removeAll(groupMembers);
			groupCount++;
		}

		return groupCount;
	}

	private void populateGroupMembers(Map<Integer, List<Integer>> nodes, int val, List<Integer> groupMembers) {
		groupMembers.add(val);
		List<Integer> neighbors = nodes.get(val);
		for (int neighbor : neighbors) {
			if (groupMembers.contains(neighbor)) {
				continue;
			}
			populateGroupMembers(nodes, neighbor, groupMembers);
		}
	}
}