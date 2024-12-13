package me.oeder.aoc.days2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day05 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<LineSegment> lineSegments = new ArrayList<>();
		for (String line : lines) {
			String[] coordParts = line.split(" -> ");
			lineSegments.add(new LineSegment(buildCoord2D(coordParts[0].split(",")), buildCoord2D(coordParts[1].split(","))));
		}
		
		Map<Coord2D, Integer> coordCounts = new HashMap<>();
		for (LineSegment lineSegment : lineSegments) {
			if (part == Part.ONE && lineSegment.isDiagonal()) {
				continue;
			}
			
			for (Coord2D coord : lineSegment.getOccupiedCoords()) {
				coordCounts.put(coord, 1 + coordCounts.getOrDefault(coord, 0));
			}
		}
		
		return coordCounts.values().stream()
				.filter(i -> i >= 2)
				.count();
	}
	
	@Data
	@AllArgsConstructor
	private class LineSegment {
		private Coord2D start;
		private Coord2D end;
		
		private boolean isDiagonal() {
			return start.getX() != end.getX() && start.getY() != end.getY();
		}
		
		private Set<Coord2D> getOccupiedCoords() {
			Set<Coord2D> occupiedCoords = new LinkedHashSet<>();
			
			int dx = end.getX() - start.getX();
			int dy = end.getY() - start.getY();
			
			int xInc = dx != 0 ? dx / (Math.abs(dx)) : 0;
			int yInc = dy != 0 ? dy / Math.abs(dy) : 0;
			
			occupiedCoords.add(start);
			Coord2D cur = start;
			while (cur.getX() + xInc != end.getX() || cur.getY() + yInc != end.getY()) {
				Coord2D next = new Coord2D(cur.getX() + xInc, cur.getY() + yInc);
				occupiedCoords.add(next);
				cur = next;
			}
			occupiedCoords.add(end);
			
			return occupiedCoords;
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Coord2D {
		private int x;
		private int y;
	}
	
	private Coord2D buildCoord2D(String[] coordElements) {
		return new Coord2D(Integer.parseInt(coordElements[0]), Integer.parseInt(coordElements[1]));
	}
}