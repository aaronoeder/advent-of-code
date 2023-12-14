package me.oeder.aoc.common;

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
}