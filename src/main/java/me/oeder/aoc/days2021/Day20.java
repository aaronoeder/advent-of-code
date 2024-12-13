package me.oeder.aoc.days2021;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.graph.AStar;
import me.oeder.aoc.graph.Graph;
import me.oeder.aoc.graph.Node;
import me.oeder.aoc.util.DistanceUtils;
import me.oeder.aoc.util.InputUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day20 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String algo = null;
		Map<Coord, Character> coords = new HashMap<>();

		boolean hitBlankLine = false;
		int gridLineIndex = 0;
		for (String line : lines) {
			if (!hitBlankLine) {
				if (line.isEmpty()) {
					hitBlankLine = true;
				} else {
					algo = line;
				}
			} else {
				for (int i = 0; i < line.length(); i++) {
					char ch = line.charAt(i);
					coords.put(new Coord(gridLineIndex, i), ch);
				}
				gridLineIndex++;
			}
		}

		int minRow = Integer.MAX_VALUE;
		int minCol = Integer.MAX_VALUE;
		int maxRow = Integer.MIN_VALUE;
		int maxCol = Integer.MIN_VALUE;
		int delta = 12;
		for (int iter = 0; iter < (part == Part.ONE ? 2 : 50); iter++) {
			log(iter + " " + coords.size());
			minRow = Integer.MAX_VALUE;
			minCol = Integer.MAX_VALUE;
			maxRow = Integer.MIN_VALUE;
			maxCol = Integer.MIN_VALUE;

			for (Coord coord : coords.keySet()) {
				minRow = Math.min(minRow, coord.getRow());
				minCol = Math.min(minCol, coord.getCol());
				maxRow = Math.max(maxRow, coord.getRow());
				maxCol = Math.max(maxCol, coord.getCol());
			}

			Map<Coord, Character> updatedCoords = new HashMap<>();
			for (int i = minRow - delta; i <= maxRow + delta; i++) {
				for (int j = minCol - delta; j <= maxCol + delta; j++) {
					Coord coord = new Coord(i, j);
					char updatedPixel = getUpdatedPixel(coords, coord, algo);
					updatedCoords.put(coord, updatedPixel);
				}
			}
			coords = updatedCoords;
		}

		int sum = 0;
		for (int i = minRow - delta; i <= maxRow + delta; i++) {
			if (i < minRow || i >= maxRow) {
				continue;
			}
			for (int j = minCol - delta; j <= maxCol + delta; j++) {
				char ch = coords.get(new Coord(i, j));
				if (j < minCol || j > maxCol) {
					continue;
				}
				if (ch == '#') {
					sum++;
				}
			}
		}

		return sum;
	}

	private char getUpdatedPixel(Map<Coord, Character> coords, Coord coord, String algo) {
		String pixelString = "";
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				Coord adjacent = new Coord(coord.getRow() + i, coord.getCol() + j);
				pixelString += coords.getOrDefault(adjacent, '.');
			}
		}

		int value = 0;
		char[] bits = pixelString.replace("#", "1").replace(".", "0").toCharArray();
		for (int i = 0; i < bits.length; i++) {
			value += Math.pow(2, i) * Character.getNumericValue(bits[bits.length - 1 - i]);
		}
		return algo.charAt(value);
	}
}