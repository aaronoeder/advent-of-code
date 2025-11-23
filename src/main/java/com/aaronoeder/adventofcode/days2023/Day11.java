package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.List;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;

public class Day11 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> emptyRowIndices = getEmptyRowIndices(lines);
		List<Integer> emptyColIndices = getEmptyColIndices(lines);
		List<Coord> coords = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				String val = line.substring(j, j + 1);
				if (val.equals("#")) {
					int additionalEmptyCount = (part == Part.ONE ? 1 : 999999);
					int row = i;
					int col = j;
					for (int emptyRowIndex : emptyRowIndices) {
						if (i > emptyRowIndex) {
							row += additionalEmptyCount;
						}
					}
					for (int emptyColIndex : emptyColIndices) {
						if (j > emptyColIndex) {
							col += additionalEmptyCount;
						}
					}
					coords.add(new Coord(row, col));
				}
			}
		}
		
		long sum = 0;
		for (int i = 0; i < coords.size(); i++) {
			for (int j = i + 1; j < coords.size(); j++) {
				sum += getMinDistance(coords.get(i), coords.get(j));
			}
		}
		
		return sum;
	}
	
	private List<Integer> getEmptyRowIndices(List<String> lines) {
		List<Integer> emptyRowIndices = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			if (!lines.get(i).contains("#")) {
				emptyRowIndices.add(i);
			}
		}
		return emptyRowIndices;
	}
	
	private List<Integer> getEmptyColIndices(List<String> lines) {
		List<Integer> emptyColIndices = new ArrayList<>();
		for (int i = 0; i < lines.get(0).length(); i++) {
			boolean isColEmpty = true;
			for (String line : lines) {
				if (line.substring(i, i + 1).equals("#")) {
					isColEmpty = false;
				}
			}
			if (isColEmpty) {
				emptyColIndices.add(i);
			}
		}
		return emptyColIndices;
	}
	
	private long getMinDistance(Coord start, Coord end) {
		return Math.abs(end.getCol() - start.getCol()) + Math.abs(end.getRow() - start.getRow());
	}
}