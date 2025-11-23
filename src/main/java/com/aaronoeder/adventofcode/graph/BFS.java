package com.aaronoeder.adventofcode.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import com.aaronoeder.adventofcode.common.Coord;

public final class BFS {
	private BFS() {}
	
	public static <T> int getMinDistance(T[][] grid, Coord start, Coord end, boolean includeDiagonalNeighbors, BiPredicate<Coord, Coord> isValidNeighbor) {
		for (Map.Entry<Coord, Integer> entry : getDistances(grid, start, end, includeDiagonalNeighbors, isValidNeighbor).entrySet()) {
			if (entry.getKey().equals(end)) {
				return entry.getValue();
			}
		}
		return -1;
	}
	
	public static <T> Map<Coord, Integer> getDistances(T[][] grid, Coord start, Coord end, boolean includeDiagonalNeighbors, BiPredicate<Coord, Coord> isValidNeighbor) {
		List<Coord> queue = new ArrayList<>();
		queue.add(start);
		
		Map<Coord, Integer> distances = new HashMap<>();	
		distances.put(start, 0);
		
		while (!queue.isEmpty()) {
			Coord cur = queue.remove(0);
			
			if (end != null && cur.equals(end)) {
				break;
			}
			
			for (Coord neighbor : cur.getNeighbors(includeDiagonalNeighbors)) {
				if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
					continue;
				}
				if (distances.containsKey(neighbor)) {
					continue;
				}
				if (!isValidNeighbor.test(cur, neighbor)) {
					continue;
				}
				
				queue.add(neighbor);
				distances.put(neighbor, distances.get(cur) + 1);
			}
		}
		
		return distances;
	}
	
	public static <T> int getMinDistance(Graph graph, Node start, Node end, BiPredicate<Node, Node> isValidNeighbor) {
		for (Map.Entry<Node, Integer> entry : getDistances(graph, start, end, isValidNeighbor).entrySet()) {
			if (entry.getKey().equals(end)) {
				return entry.getValue();
			}
		}
		return -1;
	}
	
	public static <T> Map<Node, Integer> getDistances(Graph graph, Node start, Node end, BiPredicate<Node, Node> isValidNeighbor) {
		List<Node> queue = new ArrayList<>();
		queue.add(start);
		
		Map<Node, Integer> distances = new HashMap<>();	
		distances.put(start, 0);
		
		while (!queue.isEmpty()) {
			Node cur = queue.remove(0);
			
			if (end != null && cur.equals(end)) {
				break;
			}
			
			for (Node neighbor : cur.getAdjacentNodes().keySet()) {
				if (distances.containsKey(neighbor)) {
					continue;
				}
				if (!isValidNeighbor.test(cur, neighbor)) {
					continue;
				}
				
				queue.add(neighbor);
				distances.put(neighbor, distances.get(cur) + 1);
			}
		}
		
		return distances;
	}
}