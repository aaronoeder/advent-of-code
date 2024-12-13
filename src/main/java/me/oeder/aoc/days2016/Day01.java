package me.oeder.aoc.days2016;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;

public class Day01 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		
		Set<Coord> visited = new HashSet<>();
		
		Direction dir = Direction.N;
		Coord coord = new Coord(0, 0);
		for (String line : lines.get(0).split(", ")) {
			String leftRight = line.substring(0, 1);
			int amount = Integer.parseInt(line.substring(1));
			
			dir = (leftRight.equals("L") ? dir.getDirectionToLeft() : dir.getDirectionToRight());
			Coord newCoord = new Coord(coord.getRow() + dir.getRowDiff() * amount, coord.getCol() + dir.getColDiff() * amount);
			
			int minRow = Math.min(coord.getRow(), newCoord.getRow());
			int maxRow = Math.max(coord.getRow(), newCoord.getRow());
			int minCol = Math.min(coord.getCol(), newCoord.getCol());
			int maxCol = Math.max(coord.getCol(), newCoord.getCol());
			
			if (part == Part.TWO) {
				for (int row = minRow; row <= maxRow; row++) {
					for (int col = minCol; col <= maxCol; col++) {
						if (row == coord.getRow() && col == coord.getCol()) {
							continue;
						}
						Coord c = new Coord(row, col);
						if (visited.contains(c)) {
							return getDistance(c);
						}
						visited.add(c);
					}
				}
			}			
			coord = newCoord;
		}
		return getDistance(coord);
	}
	
	private int getDistance(Coord coord) {
		return Math.abs(coord.getRow()) + Math.abs(coord.getCol());
	}
}