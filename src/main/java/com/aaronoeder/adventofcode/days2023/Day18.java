package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;

public class Day18 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Direction> directionMapping = new HashMap<>();
		directionMapping.put("R", Direction.E);
		directionMapping.put("D", Direction.S);
		directionMapping.put("L", Direction.W);
		directionMapping.put("U", Direction.N);
		directionMapping.put("0", Direction.E);
		directionMapping.put("1", Direction.S);
		directionMapping.put("2", Direction.W);
		directionMapping.put("3", Direction.N);
		
		List<Coord> boundaryPoints = new ArrayList<>();
		boundaryPoints.add(new Coord(0, 0));
		
		long boundaryPerimeterLength = 0;
		
		for (String line : lines) {
			String[] parts = line.split(" ");
			Direction dir = null;
			int amount = 0;
			
			if (part == Part.ONE) {
				dir = directionMapping.get(parts[0]);
				amount = Integer.parseInt(parts[1]);
			} else if (part == Part.TWO) {
				String hex = parts[2].substring(2, parts[2].length() - 1);
				String firstFiveHexDigits = hex.substring(0, hex.length() - 1);
				String lastHexDigit = hex.substring(hex.length() - 1);
				
				dir = directionMapping.get(lastHexDigit);
				amount = Integer.parseInt(firstFiveHexDigits, 16);
			}
			
			Coord lastBoundaryPoint = boundaryPoints.get(boundaryPoints.size() - 1);
			
			int totalRowDiff = amount * dir.getRowDiff();
			int totalColDiff = amount * dir.getColDiff();
			
			boundaryPoints.add(new Coord(lastBoundaryPoint.getRow() + totalRowDiff, lastBoundaryPoint.getCol() + totalColDiff));
			boundaryPerimeterLength += Math.abs(totalRowDiff) + Math.abs(totalColDiff);
		}
		
		// Find total area using the Shoelace formula (this requires the boundary points to be arranged in counter-clockwise order first)
		Collections.reverse(boundaryPoints);
		double totalArea = 0;
		for (int i = 0; i < boundaryPoints.size(); i++) {
			Coord c1 = boundaryPoints.get(i);
			Coord c2 = boundaryPoints.get(i == boundaryPoints.size() - 1 ? 0 : i + 1);
			totalArea += 0.5 * (Long.valueOf(c1.getRow()) * Long.valueOf(c2.getCol()) - Long.valueOf(c2.getRow()) * Long.valueOf(c1.getCol()));
		}
		
		// Find interior area using Pick's theorem
		// Pick's theorem states: A = i + b/2 - 1, where A is the total area, b is the # of boundary points and i is the # of interior points
		// We need to solve for i: i = A + 1 - b/2
		double interiorArea = totalArea + 1 - (boundaryPerimeterLength / 2);
		
		return (long)(interiorArea + boundaryPerimeterLength);
	}
}