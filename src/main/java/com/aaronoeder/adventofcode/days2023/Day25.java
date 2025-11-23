package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

public class Day25 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			Set<String> vertices = new HashSet<>();
			List<Edge> edges = new ArrayList<>();

			for (String line : lines) {
				String[] parts = line.split(": ");

				String component = parts[0];
				vertices.add(component);

				List<String> connections = Arrays.asList(parts[1].split(" " ));
				for (String connection : connections) {
					vertices.add(connection);
					edges.add(new Edge(connection, component));
				}
			}

			List<List<String>> groups = getGroups(vertices, edges);

			int product = 1;
			for (List<String> group : groups) {
				product *= group.size();
			}
			return product;
		}
		return "Merry Christmas!";
	}

	@Data
	@AllArgsConstructor
	private class Edge {
		private String from;
		private String to;
	}
	
	private List<List<String>> getGroups(Set<String> vertices, List<Edge> edges) {
		List<List<String>> groups = new ArrayList<>();
		do {
			groups = new ArrayList<>();
			for (String vertex : vertices) {
				List<String> group = new ArrayList<>();
				group.add(vertex);
				groups.add(group);
			}
			
			while (groups.size() > 2) {
				Edge randomEdge = edges.get(new Random().nextInt(edges.size()));
				
				List<String> group1 = groups.stream().filter(group -> group.contains(randomEdge.getFrom())).findFirst().get();
				List<String> group2 = groups.stream().filter(group -> group.contains(randomEdge.getTo())).findFirst().get();
				
				if (group1.equals(group2)) {
					continue;
				}
				
				groups.remove(group2);
				group1.addAll(group2);
			}
			
		} while (getDisconnectionCount(groups, edges) != 3);
		
		return groups;
	}
	
	private int getDisconnectionCount(List<List<String>> groups, List<Edge> edges) {
		int disconnectionCount = 0;
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			List<String> group1 = groups.stream().filter(group -> group.contains(edge.getFrom())).findFirst().get();
			List<String> group2 = groups.stream().filter(group -> group.contains(edge.getTo())).findFirst().get();
			if (!group1.equals(group2)) {
				disconnectionCount++;
			}
		}
		return disconnectionCount;
	}
}