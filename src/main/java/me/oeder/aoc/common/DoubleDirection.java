package me.oeder.aoc.common;

public enum DoubleDirection {
	N(-1, 0),
	NW(-1, -1),
	W(0, -1),
	SW(1, -1),
	S(1, 0),
	SE(1, 1),
	E(0, 1),
	NE(-1, 1);
	
	private int rowDiff;
	private int colDiff;
	
	private DoubleDirection(int rowDiff, int colDiff) {
		this.rowDiff = rowDiff;
		this.colDiff = colDiff;
	}
	
	public int getRowDiff() {
		return rowDiff;
	}
	
	public int getColDiff() {
		return colDiff;
	}
}