package me.oeder.aoc.days2019;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;
import me.oeder.aoc.util.DistanceUtils;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<CoordWithSteps> firstWireCoords = getCoords(lines.get(0));
		List<CoordWithSteps> secondWireCoords = getCoords(lines.get(1));

		CoordWithSteps start = new CoordWithSteps(0, 0, 0);
		if (part == Part.ONE) {
			int minDistance = Integer.MAX_VALUE;
			for (CoordWithSteps firstCoord : firstWireCoords) {
				for (CoordWithSteps secondCoord : secondWireCoords) {
					if (firstCoord.getRow() == secondCoord.getRow() && firstCoord.getCol() == secondCoord.getCol()) {
						int distance = DistanceUtils.getTaxiCabDistance(start, (Coord)firstCoord);
						if (distance > 0 && distance < minDistance) {
							minDistance = distance;
						}
					}
				}
			}
			return minDistance;
		} else {
			int minSteps = Integer.MAX_VALUE;
			for (CoordWithSteps firstCoord : firstWireCoords) {
				for (CoordWithSteps secondCoord : secondWireCoords) {
					if (firstCoord.getRow() == secondCoord.getRow() && firstCoord.getCol() == secondCoord.getCol()) {
						int steps = firstCoord.getSteps() + secondCoord.getSteps();
						if (steps < minSteps) {
							minSteps = steps;
						}
					}
				}
			}
			return minSteps;
		}
	}

	private List<CoordWithSteps> getCoords(String line) {
		List<CoordWithSteps> coords = new ArrayList<>();

		int curRow = 0;
		int curCol = 0;
		int steps = 0;

		String[] instructions = line.split(",");
		for (String instruction : instructions) {
			Direction dir = getDirection(instruction.substring(0, 1));
			int amount = Integer.parseInt(instruction.substring(1));
			if (dir.getRowDiff() != 0) {
				for (int i = 1; i <= amount; i++) {
					curRow += dir.getRowDiff();
					coords.add(new CoordWithSteps(curRow, curCol, ++steps));
				}
			} else if (dir.getColDiff() != 0) {
				for (int j = 1; j <= amount; j++) {
					curCol += dir.getColDiff();
					coords.add(new CoordWithSteps(curRow, curCol, ++steps));
				}
			}
		}
		return coords;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	private class CoordWithSteps extends Coord {
		private int steps;

		public CoordWithSteps(int row, int col, int steps) {
			super(row, col);
			this.steps = steps;
		}
	}

	private Direction getDirection(String rawDir) {
		switch (rawDir) {
			case "U":
				return Direction.N;
			case "L":
				return Direction.W;
			case "D":
				return Direction.S;
			case "R":
				return Direction.E;
			default:
				return null;
		}
	}
}