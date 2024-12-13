package me.oeder.aoc.days2022;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;

public class Day09 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Motion> motions = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split(" ");
			Direction dir = null;
			switch (parts[0]) {
				case "U":
					dir = Direction.N;
					break;
				case "D":
					dir = Direction.S;
					break;
				case "L":
					dir = Direction.W;
					break;
				case "R":
					dir = Direction.E;
					break;
			}
			motions.add(new Motion(dir, Integer.parseInt(parts[1])));
		}
		
		Coord hPos = new Coord(0, 0);
		
		int totalTails = (part == Part.TWO ? 9 : 1);
		List<Coord> tPosList = new ArrayList<>();
		for (int i = 0; i < totalTails; i++) {
			tPosList.add(new Coord(0, 0));
		}
		
		Set<Coord> lastTailVisited = new LinkedHashSet<>();
		lastTailVisited.add(new Coord(tPosList.get(totalTails -1).getRow(), tPosList.get(totalTails - 1).getCol()));
		
		for (Motion m : motions) {
			for (int i = 0; i < m.getAmount(); i++) {
				hPos = new Coord(hPos.getRow() + m.getDir().getRowDiff(), hPos.getCol() + m.getDir().getColDiff());
				for (int j = 0; j < tPosList.size(); j++) {
					Coord refPos = (j == 0 ? hPos : tPosList.get(j - 1));
					Coord tPos = tPosList.get(j);
					if (refPos.equals(tPos)) {
						break;
					} else if (refPos.getRow() == tPos.getRow()) {
						if (Math.abs(refPos.getCol() - tPos.getCol()) > 1) {
							int diff = refPos.getCol() - tPos.getCol();
							int offset = diff > 0 ? -1 : 1;
							tPos.setCol(tPos.getCol() + diff + offset);
						}
					} else if (refPos.getCol() == tPos.getCol()) {
						if (Math.abs(refPos.getRow() - tPos.getRow()) > 1) {
							int diff = refPos.getRow() - tPos.getRow();
							int offset = diff > 0 ? -1 : 1;
							tPos.setRow(tPos.getRow() + diff + offset);
						}
					} else {
						int rowDiff = refPos.getRow() - tPos.getRow();
						int colDiff = refPos.getCol() - tPos.getCol();
						
						if (Math.abs(rowDiff) >= 1 && Math.abs(colDiff) > 1) {
							int rowOffset = Math.abs(rowDiff) > 1 ? (rowDiff > 0 ? -1 : 1) : 0;
							int colOffset = colDiff > 0 ? -1 : 1;
							tPos.setRow(refPos.getRow() + rowOffset);
							tPos.setCol(tPos.getCol() + colDiff + colOffset);
						} else if (Math.abs(colDiff) >= 1 && Math.abs(rowDiff) > 1) {
							int rowOffset = rowDiff > 0 ? -1 : 1;
							int colOffset = Math.abs(colDiff) > 1 ? (colDiff > 0 ? -1 : 1) : 0;
							tPos.setRow(tPos.getRow() + rowDiff + rowOffset);
							tPos.setCol(refPos.getCol() + colOffset);
						}
					}
					
					if (j == tPosList.size() - 1) {
						lastTailVisited.add(new Coord(tPos.getRow(), tPos.getCol()));
					}
				}
			}
		}
		
		return lastTailVisited.size();
	}
	
	@Data
	@AllArgsConstructor
	public class Motion {
		private Direction dir;
		private int amount;
	}
}