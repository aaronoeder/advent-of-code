package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;
import me.oeder.aoc.util.InputUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Day19 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(getPaddedLines(lines));

		Coord coord = getStartCoord(grid);
		Direction dir = Direction.S;

		String letters = "";
		int steps = 1;
		while (true) {
			coord = getCoordInDirection(coord, dir);
			char val = grid[coord.getRow()][coord.getCol()];
			if (val == ' ') {
				break;
			} else if (val == '+') {
				for (Direction otherDir : Direction.values()) {
					if (otherDir == dir.getOppositeDirection()) {
						continue;
					}
					Coord otherCoord = getCoordInDirection(coord, otherDir);
					if (isInBounds(grid, otherCoord) && grid[otherCoord.getRow()][otherCoord.getCol()] != ' ') {
						dir = otherDir;
						break;
					}
				}
			} else if (val != '|' && val != '-') {
				letters += val;
			}
			steps++;
		}

		if (part == Part.ONE) {
			return letters;
		}
		return steps;
	}

	private List<String> getPaddedLines(List<String> lines) {
		List<String> paddedLines = new ArrayList<>();
		int maxLength = Integer.MIN_VALUE;
		for (String line : lines) {
			maxLength = Math.max(maxLength, line.length());
		}
		for (String line : lines) {
			paddedLines.add(StringUtils.rightPad(line, maxLength));
		}
		return paddedLines;
	}

	private Coord getStartCoord(Character[][] grid) {
		for (int j = 0; j < grid[0].length; j++) {
			if (grid[0][j] == '|') {
				return new Coord(0, j);
			}
		}
		return null;
	}

	private Coord getCoordInDirection(Coord coord, Direction dir) {
		return new Coord(coord.getRow() + dir.getRowDiff(), coord.getCol() + dir.getColDiff());
	}

	private boolean isInBounds(Character[][] grid, Coord coord) {
		return coord.getRow() >= 0 && coord.getRow() < grid.length &&
				coord.getCol() >= 0 && coord.getCol() < grid[0].length;
	}
}