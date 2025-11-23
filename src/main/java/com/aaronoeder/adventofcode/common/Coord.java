package com.aaronoeder.adventofcode.common;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coord {
	private int row;
	private int col;
	
	public List<Coord> getNeighbors(boolean includeDiagonal) {
		List<Coord> neighbors = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				if (!includeDiagonal && (i * j != 0)) {
					continue;
				}
				neighbors.add(new Coord(row + i, col + j));
			}
		}
		return neighbors;
	}

	public List<Coord> getNeighbors(Character[][] grid, boolean includeDiagonal) {
		List<Coord> neighbors = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				if (!includeDiagonal && (i * j != 0)) {
					continue;
				}
				int newRow = row + i;
				int newCol = col + j;
				if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid.length) {
					continue;
				}
				neighbors.add(new Coord(newRow, newCol));
			}
		}
		return neighbors;
	}
}