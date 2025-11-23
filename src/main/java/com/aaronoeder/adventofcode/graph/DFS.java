package com.aaronoeder.adventofcode.graph;

import com.aaronoeder.adventofcode.common.Coord;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

public final class DFS {
	private DFS() {}

	public static <T> void traverseGraph(T[][] grid, Set<Coord> visited, List<Set<Coord>> uniquePaths, Coord cur, Coord end, boolean includeDiagonalNeighbors, BiPredicate<Coord, Coord> isValidNeighbor) {
		visited.add(cur);
		if (cur.equals(end)) {
			uniquePaths.add(visited);
			return;
		}

		for (Coord neighbor : cur.getNeighbors(includeDiagonalNeighbors)) {
			if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
				continue;
			}
			if (visited.contains(neighbor)) {
				continue;
			}
			if (!isValidNeighbor.test(cur, neighbor)) {
				continue;
			}
			traverseGraph(grid, new HashSet<>(visited), uniquePaths, neighbor, end, includeDiagonalNeighbors, isValidNeighbor);
		}
	}

	public static <T> void traverseGraph(T[][] grid, Set<Coord> visited, Coord cur, Coord end, boolean includeDiagonalNeighbors, BiPredicate<Coord, Coord> isValidNeighbor) {
		visited.add(cur);
		if (cur.equals(end)) {
			return;
		}

		for (Coord neighbor : cur.getNeighbors(includeDiagonalNeighbors)) {
			if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
				continue;
			}
			if (visited.contains(neighbor)) {
				continue;
			}
			if (!isValidNeighbor.test(cur, neighbor)) {
				continue;
			}
			traverseGraph(grid, visited, neighbor, end, includeDiagonalNeighbors, isValidNeighbor);
		}
	}

	public static <T> void traverseGraph(Graph graph, Set<Node> visited, Node cur, Node end, BiPredicate<Node, Node> isValidNeighbor) {
		visited.add(cur);

		if (end != null && cur.equals(end)) {
			return;
		}

		for (Node neighbor : cur.getAdjacentNodes().keySet()) {
			if (visited.contains(neighbor)) {
				continue;
			}
			if (!isValidNeighbor.test(cur, neighbor)) {
				continue;
			}
			traverseGraph(graph, visited, neighbor, end, isValidNeighbor);
		}
	}
}