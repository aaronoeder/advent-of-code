package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

public class Day22 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Brick> bricks = new ArrayList<>();
		for (String line : lines) {
			String[] coordParts = line.split("~");
			bricks.add(new Brick(buildCoord3D(coordParts[0].split(",")), buildCoord3D(coordParts[1].split(","))));
		}
		
		descend(bricks);
		
		int sum = 0;
		for (int i = 0; i < bricks.size(); i++) {
			List<Brick> bricksCopy = getDeepCopyOfBricks(bricks, i);
			int descend = descend(bricksCopy);
			if (part == Part.ONE && descend == 0) {
				sum++;
			} else if (part == Part.TWO) {
				sum += descend;
			}
		}
		
		return sum;
	}
	
	@Data
	@AllArgsConstructor
	private class Brick {
		private Coord3D start;
		private Coord3D end;
		
		public void descend() {
			start.setZ(start.getZ() - 1);
			end.setZ(end.getZ() - 1);
		}
		
		public int getMinZ() {
			return Math.min(start.getZ(), end.getZ());
		}
		
		public int getMaxZ() {
			return Math.max(start.getZ(), end.getZ());
		}
		
		public boolean doesIntersect2D(Brick otherBrick) {
			Set<Coord3D> occupiedCoords = getOccupiedCoords();
			Set<Coord3D> otherBrickOccupiedCoords = otherBrick.getOccupiedCoords();
			
			for (Coord3D coord : occupiedCoords) {
				for (Coord3D otherCoord : otherBrickOccupiedCoords) {
					if (otherCoord.getX() == coord.getX() && otherCoord.getY() == coord.getY()) {
						return true;
					}
				}
			}
			return false;
		}
		
		private Set<Coord3D> getOccupiedCoords() {
			Set<Coord3D> occupiedCoords = new HashSet<>();
			
			Coord3D min = new Coord3D(
					Math.min(start.getX(), end.getX()),
					Math.min(start.getY(), end.getY()),
					Math.min(start.getZ(), end.getZ()));
			
			Coord3D max = new Coord3D(
					Math.max(start.getX(), end.getX()),
					Math.max(start.getY(), end.getY()),
					Math.max(start.getZ(), end.getZ()));
			
			for (int x = min.getX(); x <= max.getX(); x++) {
				occupiedCoords.add(new Coord3D(x, min.getY(), min.getZ()));
			}
			
			for (int y = min.getY(); y <= max.getY(); y++) {
				occupiedCoords.add(new Coord3D(min.getX(), y, min.getZ()));
			}
			
			for (int z = min.getZ(); z <= max.getZ(); z++) {
				occupiedCoords.add(new Coord3D(min.getX(), min.getY(), z));
			}
			
			return occupiedCoords;
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Coord3D {
		private int x;
		private int y;
		private int z;
	}
	
	private Coord3D buildCoord3D(String[] coordComponents) {
		return new Coord3D(Integer.parseInt(coordComponents[0]), Integer.parseInt(coordComponents[1]), Integer.parseInt(coordComponents[2]));
	}
	
	private List<Brick> getDeepCopyOfBricks(List<Brick> bricks, int exclusionIndex) {
		List<Brick> deepCopy = new ArrayList<>();
		for (int i = 0; i < bricks.size(); i++) {
			if (i == exclusionIndex) {
				continue;
			}
			Brick brick = bricks.get(i);
			deepCopy.add(new Brick(new Coord3D(brick.getStart().getX(), brick.getStart().getY(), brick.getStart().getZ()),
					new Coord3D(brick.getEnd().getX(), brick.getEnd().getY(), brick.getEnd().getZ())));
		}
		return deepCopy;
	}
	
	private int descend(List<Brick> bricks) {
		Collections.sort(bricks, (b1, b2) -> b1.getMinZ() - b2.getMinZ());
		int descendCount = 0;
		for (Brick brick : bricks) {
			boolean hasBrickDescended = false;
			while (canBrickDescend(brick, bricks)) {
				brick.descend();
				hasBrickDescended = true;
			}
			if (hasBrickDescended) {
				descendCount++;
			}
		}
		return descendCount;
	}
	
	private boolean canBrickDescend(Brick brick, List<Brick> bricks) {
		if (brick.getMinZ() == 1) {
			return false;
		}
		
		for (Brick otherBrick : bricks) {
			if (otherBrick.equals(brick)) {
				continue;
			}
			if (otherBrick.getMaxZ() == brick.getMinZ() - 1) {
				if (brick.doesIntersect2D(otherBrick)) {
					return false;
				}
			}
		}
		return true;
	}
}