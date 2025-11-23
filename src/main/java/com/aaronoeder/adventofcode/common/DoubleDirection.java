package com.aaronoeder.adventofcode.common;

import lombok.Getter;

@Getter
public enum DoubleDirection {
	N(-1, 0),
	NW(-1, -1),
	W(0, -1),
	SW(1, -1),
	S(1, 0),
	SE(1, 1),
	E(0, 1),
	NE(-1, 1);
	
	private final int rowDiff;
	private final int colDiff;
	
	DoubleDirection(int rowDiff, int colDiff) {
		this.rowDiff = rowDiff;
		this.colDiff = colDiff;
	}
}