package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.HexagonalDirection;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String line = lines.get(0);
		List<HexagonalDirection> directions = Arrays.asList(line.split(",")).stream()
				.map(s -> HexagonalDirection.valueOf(s.toUpperCase()))
				.collect(Collectors.toList());

		int maxDistance = Integer.MIN_VALUE;
		double rowDiff = 0;
		double colDiff = 0;
		for (HexagonalDirection direction : directions) {
			rowDiff += direction.getRowDiff();
			colDiff += direction.getColDiff();
			maxDistance = Math.max(maxDistance, getMoves(rowDiff, colDiff));
		}

		if (part == Part.ONE) {
			return getMoves(rowDiff, colDiff);
		}
		return maxDistance;
	}

	private int getMoves(double rowDiff, double colDiff) {
		if (colDiff == 0) { // Moving vertically
			return (int)Math.abs(rowDiff);
		}
		if (rowDiff == 0) { // Moving horizontally
			return (int)Math.abs(colDiff);
		}

		HexagonalDirection nextDirection = null;
		if (rowDiff < 0) { // Moving upward
			if (colDiff < 0) { // Moving up-left (NW)
				nextDirection = HexagonalDirection.NW;
			} else { // Moving up-right (NE)
				nextDirection = HexagonalDirection.NE;
			}
		} else { // Moving downward
			if (colDiff < 0) { // Moving down-left (SW)
				nextDirection = HexagonalDirection.SW;
			} else { // Moving down-right (SE)
				nextDirection = HexagonalDirection.SE;
			}
		}

		return 1 + getMoves(rowDiff - nextDirection.getRowDiff(), colDiff - nextDirection.getColDiff());
	}
}