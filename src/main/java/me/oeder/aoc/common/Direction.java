package me.oeder.aoc.common;

public enum Direction {
	N(-1, 0),
	W(0, -1),
	S(1, 0),
	E(0, 1);
	
	private int rowDiff;
	private int colDiff;
	
	private Direction(int rowDiff, int colDiff) {
		this.rowDiff = rowDiff;
		this.colDiff = colDiff;
	}
	
	public int getRowDiff() {
		return rowDiff;
	}
	
	public int getColDiff() {
		return colDiff;
	}
	
	public Direction getDirectionToLeft() {
		return Direction.values()[(ordinal() + 1) % Direction.values().length];
	}
	
	public Direction getDirectionToRight() {
		return Direction.values()[(ordinal() + 3) % Direction.values().length];
	}
}