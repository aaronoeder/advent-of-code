package me.oeder.aoc.days2022;

import java.util.List;
import java.util.Map;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.graph.BFS;

public class Day12 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Integer[][] grid = new Integer[lines.size()][lines.get(0).length()];
		
		Coord start = null;
		Coord end = null;
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				char ch = line.charAt(j);
				if (ch == 'S') {
					start = new Coord(i, j);
					ch = 'a';
				} else if (ch == 'E') {
					end = new Coord(i, j);
					ch = 'z';
				}
				int value = ch - 'a';
				grid[i][j] = value;
			}
		}
		
		if (part == Part.TWO) {
			Map<Coord, Integer> distances = BFS.getDistances(grid, end, null, false, 
					(cur, neighbor) -> grid[neighbor.getRow()][neighbor.getCol()] >= grid[cur.getRow()][cur.getCol()] - 1);
			
			int min = Integer.MAX_VALUE;
			for (Map.Entry<Coord, Integer> entry : distances.entrySet()) {
				if (grid[entry.getKey().getRow()][entry.getKey().getCol()] == 0 && entry.getValue() < min) {
					min = entry.getValue();
				}
			}
			return min;
		} else {
			return BFS.getMinDistance(grid, start, end, false, 
					(cur, neighbor) -> grid[neighbor.getRow()][neighbor.getCol()] <= grid[cur.getRow()][cur.getCol()] + 1);
		}
	}
}