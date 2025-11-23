package com.aaronoeder.adventofcode.util;

import com.aaronoeder.adventofcode.common.Coord;

public final class DistanceUtils {
	private DistanceUtils() {}
	
	public static int getTaxiCabDistance(Coord c1, Coord c2) {
		return Math.abs(c2.getRow() - c1.getRow()) + Math.abs(c2.getCol() - c1.getCol());
	}
}