package com.aaronoeder.adventofcode.common;

import lombok.Getter;

@Getter
public enum Direction {
	N(-1, 0),
	W(0, -1),
	S(1, 0),
	E(0, 1);
	
	private final int rowDiff;
	private final int colDiff;
	
	Direction(int rowDiff, int colDiff) {
		this.rowDiff = rowDiff;
		this.colDiff = colDiff;
	}
	
	public Direction getDirectionToLeft() {
		return Direction.values()[(ordinal() + 1) % Direction.values().length];
	}
	
	public Direction getOppositeDirection() {
		return Direction.values()[(ordinal() + 2) % Direction.values().length];
	}
	
	public Direction getDirectionToRight() {
		return Direction.values()[(ordinal() + 3) % Direction.values().length];
	}
}