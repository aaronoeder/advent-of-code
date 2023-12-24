package me.oeder.aoc.days2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;
import me.oeder.aoc.util.InputUtils;

public class Day23 extends AdventDay2023 {
	
	public Day23() {
		super(23);
	}

	@Override
	public Object solvePart1(List<String> lines) {
		return getStepCount(lines, Part.ONE);
	}
	
	@Override
	public Object solvePart2(List<String> lines) {
		return getStepCount(lines, Part.TWO);
	}
	
	private int getStepCount(List<String> lines, Part part) {
		String[][] grid = InputUtils.loadLinesIntoGrid(lines);
		
		Coord start = null;
		Coord end = null;
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (!grid[i][j].equals("#")) {
					Coord coord = new Coord(i, j);
					if (i == 0) {
						start = coord;
					} else if (i == grid.length - 1) {
						end = coord;
					}
				}
			}
		}
		
		Map<Coord, Integer> path = new HashMap<>();
		return getMostSteps(buildGraph(grid, part), start, end, path);
	}
	
	private Map<Coord, Map<Coord, Integer>> buildGraph(String[][] grid, Part part) {
		Map<Coord, Map<Coord, Integer>> graph = new ConcurrentHashMap<>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (!grid[i][j].equals("#")) {
					Coord coord = new Coord(i, j);
					Map<Coord, Integer> neighbors = new HashMap<>();
					for (Coord neighbor : getNeighbors(coord, grid, part)) {
						neighbors.put(neighbor, 1);
					}
					graph.put(coord, neighbors);
				}
			}
		}
		
		for (Map.Entry<Coord, Map<Coord, Integer>> entry : graph.entrySet()) {
			Coord coord = entry.getKey();
			Map<Coord, Integer> neighbors = entry.getValue();
			if (neighbors.size() == 2) {
				List<Coord> firstLast = new ArrayList<>(neighbors.keySet());
				Coord first = firstLast.get(0);
				Coord last = firstLast.get(1);
				int totalSteps = neighbors.get(first) + neighbors.get(last);
				
				graph.get(first).put(last, Math.max(graph.get(first).getOrDefault(last, 0), totalSteps));
				graph.get(last).put(first, Math.max(graph.get(last).getOrDefault(first, 0), totalSteps));
				
				graph.get(first).remove(coord);
				graph.get(last).remove(coord);
				graph.remove(coord);
			}
		}
		
		return graph;
	}
	
	private int getMostSteps(Map<Coord, Map<Coord, Integer>> graph, Coord cur, Coord end, Map<Coord, Integer> path) {
		if (cur.equals(end)) {
			int steps = 0;
			for (int val : path.values()) {
				steps += val;
			}
			return steps;
		}
		
		int mostSteps = Integer.MIN_VALUE;
		for (Map.Entry<Coord, Integer> entry : graph.get(cur).entrySet()) {
			Coord neighbor = entry.getKey();
			int steps = entry.getValue();
			if (!path.containsKey(neighbor)) {
				path.put(neighbor, steps);
				int totalSteps = getMostSteps(graph, neighbor, end, path);
				if (totalSteps > mostSteps) {
					mostSteps = totalSteps;
				}
				path.remove(neighbor);
			}
		}
		return mostSteps;
	}
	
	private List<Coord> getNeighbors(Coord coord, String[][] grid, Part part) {
		List<Coord> neighbors = new ArrayList<>();
		for (Direction dir : Direction.values()) {
			if (!isDirectionAllowed(coord, dir, grid, part)) {
				continue;
			}
			Coord neighbor = new Coord(coord.getRow() + dir.getRowDiff(), coord.getCol() + dir.getColDiff());
			if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
				continue;
			}
			if (grid[neighbor.getRow()][neighbor.getCol()].equals("#")) {
				continue;
			}
			neighbors.add(neighbor);
		}
		return neighbors;
	}
	
	private boolean isDirectionAllowed(Coord coord, Direction dir, String[][] grid, Part part) {
		if (part == Part.TWO) {
			return true;
		}
		
		String val = grid[coord.getRow()][coord.getCol()];
		switch (dir) {
			case N:
				return val.equals(".") || val.equals("^");
			case E:
				return val.equals(".") || val.equals(">");
			case S:
				return val.equals(".") || val.equals("v");
			case W:
				return val.equals(".") || val.equals("<");
			default:
				return false;
		}
	}
}