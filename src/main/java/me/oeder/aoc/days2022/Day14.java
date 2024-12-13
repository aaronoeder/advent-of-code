package me.oeder.aoc.days2022;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day14 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Set<Coord2D> rocks = new LinkedHashSet<>();
		Set<Coord2D> sand = new HashSet<>();
		
		for (String line : lines) {
			String[] coords = line.split(" -> ");
			for (int i = 0; i < coords.length - 1; i++) {
				Coord2D coord1 = new Coord2D(Integer.parseInt(coords[i].split(",")[0]), Integer.parseInt(coords[i].split(",")[1]));
				Coord2D coord2 = new Coord2D(Integer.parseInt(coords[i + 1].split(",")[0]), Integer.parseInt(coords[i + 1].split(",")[1]));
				rocks.addAll(getOccupiedCoords(coord1, coord2));
			}
		}
		
		while (true) {
			boolean success = dropSand(rocks, sand, part);
			if (!success) {
				return sand.size() + (part == Part.TWO ? 1 : 0);
			}
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Coord2D {
		private int x;
		private int y;
	}
	
	private Set<Coord2D> getOccupiedCoords(Coord2D coord1, Coord2D coord2) {
		Set<Coord2D> occupiedCoords = new LinkedHashSet<>();
		
		int minX = Math.min(coord1.getX(), coord2.getX());
		int maxX = Math.max(coord1.getX(), coord2.getX());
		int minY = Math.min(coord1.getY(), coord2.getY());
		int maxY = Math.max(coord1.getY(), coord2.getY());
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				occupiedCoords.add(new Coord2D(x, y));
			}
		}
		
		return occupiedCoords;
	}
	
	private boolean dropSand(Set<Coord2D> rocks, Set<Coord2D> sand, Part part) {
		Coord2D start = new Coord2D(500, 0);
		Coord2D cur = start;
		
		boolean done = false;
		while (!done) {
			Coord2D down = new Coord2D(cur.getX(), cur.getY() + 1);
			Coord2D downLeft = new Coord2D(cur.getX() - 1, cur.getY() + 1);
			Coord2D downRight = new Coord2D(cur.getX() + 1, cur.getY() + 1);
			
			if (part == Part.ONE && cur.getY() > getLowestRockY(rocks)) {
				return false;
			} else if (part == Part.TWO && cur.getY() == getLowestRockY(rocks) + 1) {
				break;
			}
			
			if (!sand.contains(down) && !rocks.contains(down)) {
				cur = down;
			} else if (!sand.contains(downLeft) && !rocks.contains(downLeft)) {
				cur = downLeft;
			} else if (!sand.contains(downRight) && !rocks.contains(downRight)) {
				cur = downRight;
			} else {
				done = true;
			}
		}
		
		if (cur.equals(start)) {
			return false;
		} else {
			sand.add(cur);
			return true;
		}
	}
	
	private int getLowestRockY(Set<Coord2D> rocks) {
		return rocks.stream()
				.sorted((c1, c2) -> c2.getY() - c1.getY())
				.map(c -> c.getY())
				.findFirst()
				.get();
	}
}