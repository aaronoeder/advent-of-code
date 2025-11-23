package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;
import com.aaronoeder.adventofcode.util.InputUtils;

public class Day21 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		
		Set<Coord> starts = new HashSet<>();
		starts.add(getStartCoord(grid));
		
		List<Long> reachablePlots = new ArrayList<>();
		
		int rows = grid.length;
		int steps = (part == Part.ONE ? 64 : (rows / 2) + 2 * rows);
		
		for (int i = 1; i <= steps; i++) {
			Set<Coord> newStarts = new HashSet<>();
			for (Coord start : starts) {
				for (Direction dir : Direction.values()) {
					Coord neighbor = new Coord(start.getRow() + dir.getRowDiff(), start.getCol() + dir.getColDiff());
					Coord neighborInBounds = getCoordInBounds(neighbor, grid);
					if (grid[neighborInBounds.getRow()][neighborInBounds.getCol()] != '#') {
						newStarts.add(neighbor);
					}
				}
			}
			starts = newStarts;
			
			if (part == Part.TWO && (i - (rows / 2)) % rows == 0) {
				reachablePlots.add(Long.valueOf(starts.size()));
			}
		}
		
		reachablePlots.add(Long.valueOf(starts.size()));
		
		if (part == Part.ONE) {
			return reachablePlots.get(0);
		} else {
			/*
			 * The input is a 131 x 131 grid with the starting tile at the very center
			 * We compute the number of reachable plots for three amounts of steps:
			 * 
			 * 65 (this brings us to the edge of the grid)
			 * 65 + 131 (this brings us to the edge of the immediately adjacent grid)
			 * 65 + 262 (this brings us to the edge of the next adjacent grid)
			 * 
			 * This is entirely dependent on the fact that the row and column of the
			 * starting tile is empty
			 *
			 * A quadratic regression line can then be plotted against these points to 
			 * extrapolate the number of reachable plots in 26501365 steps
			 * (since 26501365 = 65 + 131 * 202300)
			 */
			long first = reachablePlots.get(0);
			long second = reachablePlots.get(1);
			long third = reachablePlots.get(2);
			
			long n = 26501365 / rows;
			return first + (second - first) * n + (n * (n - 1) / 2) * ((third - second) - (second - first));
		}
	}
	
	private Coord getStartCoord(Character[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'S') {
					return new Coord(i, j);
				}
			}
		}
		return null;
	}
	
	private Coord getCoordInBounds(Coord coord, Character[][] grid) {
		int row = coord.getRow() % grid.length;
		int col = coord.getCol() % grid[0].length;
		if (row < 0) {
			row += grid.length;
		}
		if (col < 0) {
			col += grid[0].length;
		}
		return new Coord(row, col);
	}
}