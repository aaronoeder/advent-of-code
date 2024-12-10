package me.oeder.aoc.graph;

import me.oeder.aoc.common.Coord;

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
}