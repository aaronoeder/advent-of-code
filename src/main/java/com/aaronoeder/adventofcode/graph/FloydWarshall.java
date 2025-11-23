package com.aaronoeder.adventofcode.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class FloydWarshall {
	private FloydWarshall() {}
	
	private static final int INF = 99999;
	
	public static void traverseGraph(Graph graph) {
		List<Node> nodes = new ArrayList<>(graph.getNodes());
		
		double[][] dist = new double[nodes.size()][nodes.size()];
		
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			Set<Node> adjacentNodes = node.getAdjacentNodes().keySet();
			for (int j = 0; j < nodes.size(); j++) {
				if (i == j) { // Self
					dist[i][j] = 0;
				} else if (adjacentNodes.contains(nodes.get(j))) { // Adjacent node
					dist[i][j] = node.getAdjacentNodes().get(nodes.get(j));
				} else { // Some other node (uncalculated distance for now)
					dist[i][j] = INF;
				}
			}
		}
		
		for (int k = 0; k < dist.length; k++) {
			for (int i = 0; i < dist.length; i++) {
				for (int j = 0; j < dist.length; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}
			}
		}
		
		evaluate(nodes, dist);
		
		System.out.println("Floyd-Warshall Output");
		Comparator<Node> nodeComparator = (n1, n2) -> n1.getName().compareTo(n2.getName());
		for (Node node : convertSetToSortedList(graph.getNodes(), nodeComparator)) {
			for (Node adjacentNode : convertSetToSortedList(node.getAdjacentNodes().keySet(), nodeComparator)) {
				System.out.println(node.getName() + " -> " + adjacentNode.getName() + " " + node.getAdjacentNodes().get(adjacentNode));
			}
		}
	}
	
	private static <T> List<T> convertSetToSortedList(Set<T> items, Comparator<T> comparator) {
		List<T> list = new ArrayList<>(items);
		Collections.sort(list, comparator);
		return list;
	}
	
	private static void evaluate(List<Node> nodes, double[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (i == j) {
					continue;
				}
				double val = grid[i][j];
				if (val == INF) {
					continue;
				}
				Node start = nodes.get(i);
				Node end = nodes.get(j);
				start.getAdjacentNodes().put(end, val);
			}
		}
	}
}