package me.oeder.aoc.util;

import me.oeder.aoc.common.Coord;

public final class DistanceUtils {
	private DistanceUtils() {}
	
	public static int getTaxiCabDistance(Coord c1, Coord c2) {
		return Math.abs(c2.getRow() - c1.getRow()) + Math.abs(c2.getCol() - c1.getCol());
	}
	
	public static int getChessboardDistance(Coord c1, Coord c2) {
		int count = 0;
		int col = c1.getCol();
		int row = c1.getRow();
		if (row != c2.getRow()) { // Move diagonally until rows are the same
			int times = c2.getRow() - row;
			
			int deltaCol = c2.getCol() - c1.getCol() < 0 ? -1 : 1;
			int deltaRow = c2.getRow() - c1.getRow() < 0 ? -1 : 1;
			
			col += times * deltaCol;
			row += times * deltaRow;
			count += times;
		}
		count += Math.abs(c2.getCol() - col);
		return count;
	}
}