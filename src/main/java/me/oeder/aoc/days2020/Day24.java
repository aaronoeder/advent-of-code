package me.oeder.aoc.days2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day24 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<List<HexDirection>> instructions = new ArrayList<>();
		for (String line : lines) {
			List<HexDirection> instruction = new ArrayList<>();
			
			while (line.length() > 0) {
				for (HexDirection dir : HexDirection.values()) {
					if (line.startsWith(dir.toString().toLowerCase())) {
						instruction.add(dir);
						line = line.replaceFirst(dir.toString().toLowerCase(), "");
					}
				}
			}
			instructions.add(instruction);
		}
		
		HexCoord referenceTile = new HexCoord(0, 0);
		
		Set<HexCoord> blackTiles = new HashSet<>(); 
		for (List<HexDirection> instruction : instructions) {
			HexCoord tile = referenceTile;
			for (HexDirection dir : instruction) {
				tile = new HexCoord(tile.getRow() + dir.getRowDiff(), tile.getCol() + dir.getColDiff());
			}
			
			if (blackTiles.contains(tile)) {
				blackTiles.remove(tile);
			} else {
				blackTiles.add(tile);
			}
		}
		
		if (part == Part.TWO) { 
			for (int day = 1; day <= 100; day++) {
				Set<HexCoord> newBlackTiles = new HashSet<>();
				
				double minRow = getMin(blackTiles, c -> c.getRow());
				double maxRow = getMax(blackTiles, c -> c.getRow());
				double minCol = getMin(blackTiles, c -> c.getCol());
				double maxCol = getMax(blackTiles, c -> c.getCol());
				
				for (double row = minRow - 10; row <= maxRow + 10; row++) {
					for (double col = minCol - 10; col <= maxCol + 10; col += 0.5) {
						HexCoord tile = new HexCoord(row, col);
						int neighbors = getNeighbors(blackTiles, tile);
						
						if (blackTiles.contains(tile)) {
							if (neighbors == 1 || neighbors == 2) {
								newBlackTiles.add(tile);
							}
						} else {
							if (neighbors == 2) {
								newBlackTiles.add(tile);
							}
						}
					}
				}
				
				blackTiles = newBlackTiles;
			}
		}
		
		return blackTiles.size();
	}
	
	@Data
	@AllArgsConstructor
	private class HexCoord {
		private double row;
		private double col;
	}
	
	private enum HexDirection {

		NE(-1, 0.5),
		SE(1, 0.5),

		NW(-1, -0.5),
		SW(1, -0.5),
		
		W(0, -1),
		E(0, 1);
		
		private double rowDiff;
		private double colDiff;
		
		private HexDirection(double rowDiff, double colDiff) {
			this.rowDiff = rowDiff;
			this.colDiff = colDiff;
		}
		
		public double getRowDiff() {
			return rowDiff;
		}
		
		public double getColDiff() {
			return colDiff;
		}
	}
	
	private double getMin(Set<HexCoord> blackTiles, Function<HexCoord, Double> mapper) {
		double min = Integer.MAX_VALUE;
		for (HexCoord blackTile : blackTiles) {
			double val = mapper.apply(blackTile);
			if (val < min) {
				min = val;
			}
		}
		return min;
	}
	
	private double getMax(Set<HexCoord> blackTiles, Function<HexCoord, Double> mapper) {
		double max = Integer.MIN_VALUE;
		for (HexCoord blackTile : blackTiles) {
			double val = mapper.apply(blackTile);
			if (val > max) {
				max = val;
			}
		}
		return max;
	}
	
	private int getNeighbors(Set<HexCoord> blackTiles, HexCoord tile) {
		int neighbors = 0;
		for (HexDirection dir : HexDirection.values()) {
			HexCoord neighbor = new HexCoord(tile.getRow() + dir.getRowDiff(), tile.getCol() + dir.getColDiff());
			if (blackTiles.contains(neighbor)) {
				neighbors++;
			}
		}
		return neighbors;
	}
}