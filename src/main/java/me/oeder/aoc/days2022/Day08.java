package me.oeder.aoc.days2022;

import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Direction;

public class Day08 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int[][] grid = new int[lines.size()][lines.get(0).length()];
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				grid[i][j] = Integer.parseInt(line.substring(j, j + 1));
			}
		}
		
		int sum = 0;
		int topScore = Integer.MIN_VALUE;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				boolean isVisible = false;
				int score = 1;
				for (Direction dir : Direction.values()) {
					boolean isVisibleDir = true;
					int countDir = 0;
					int x = i + dir.getRowDiff();
					int y = j + dir.getColDiff();
					while (x >= 0 && x < grid.length && y >= 0 && y < grid[i].length) {
						countDir++;
						if (grid[x][y] >= grid[i][j]) {
							isVisibleDir = false;
							break;
						}
						x += dir.getRowDiff();
						y += dir.getColDiff();
					}
					if (isVisibleDir) {
						isVisible = true;
					}
					score *= countDir;
				}
				if (isVisible) {
					sum++;
				}
				if (score > topScore) {
					topScore = score;
				}
			}
		}
		if (part == Part.ONE) {
			return sum;
		} else {
			return topScore;
		}
	}
}