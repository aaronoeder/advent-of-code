package me.oeder.aoc.days2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;

public class Day09 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int[][] grid = new int[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				grid[i][j] = Integer.parseInt(line.substring(j, j + 1));
			}
		}
		
		int total = 0;
		List<Integer> basinSizes = new ArrayList<>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				boolean lowPoint = true;
				for (Direction dir : Direction.values()) {
					int row = i + dir.getRowDiff();
					int col = j + dir.getColDiff();
					if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
						continue;
					}
					if (grid[row][col] <= grid[i][j]) {
						lowPoint = false;
						break;
					}
				}
				if (lowPoint) {
					if (part == Part.ONE) {
						total += grid[i][j] + 1;
					} else if (part == Part.TWO) {
						Set<Coord> visited = new HashSet<>();
						dfs(visited, new Coord(i, j), grid);
						basinSizes.add(visited.size());
						total *= visited.size();
					}
				}
			}
		}
		
		if (part == Part.TWO) {
			Collections.sort(basinSizes, (a, b) -> b - a);
			total = basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
		}
		
		return total;
	}

	private void dfs(Set<Coord> visited, Coord cur, int[][] grid) {
		visited.add(cur);
		for (Coord neighbor : cur.getNeighbors(false)) {
			if (visited.contains(neighbor)) {
				continue;
			}
			int neighborRow = neighbor.getRow();
			int neighborCol = neighbor.getCol();
			if (neighborRow < 0 || neighborRow >= grid.length || neighborCol < 0 || neighborCol >= grid[0].length) {
				continue;
			}
			int curVal = grid[cur.getRow()][cur.getCol()];
			int neighborVal = grid[neighborRow][neighborCol];
			if (neighborVal == 9 || neighborVal < curVal) {
				continue;
			}
			
			dfs(visited, neighbor, grid);
		}
	}
}