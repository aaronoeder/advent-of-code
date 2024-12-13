package me.oeder.aoc.days2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.InputUtils;

public class Day17 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Set<Coord4D> activeCubes = new HashSet<>();
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '#') {
					activeCubes.add(new Coord4D(i, j, 0, 0));
				}
			}
		}
		
		int minX = getMin(activeCubes, c -> c.getX());
		int minY = getMin(activeCubes, c -> c.getY());
		int minZ = getMin(activeCubes, c -> c.getZ());
		int minW = getMin(activeCubes, c -> c.getW());
		
		int maxX = getMax(activeCubes, c -> c.getX());
		int maxY = getMax(activeCubes, c -> c.getY());
		int maxZ = getMax(activeCubes, c -> c.getZ());
		int maxW = getMax(activeCubes, c -> c.getW());
		
		for (int cycle = 0; cycle < 6; cycle++) {
			Set<Coord4D> newActiveCubes = new HashSet<>();
			for (int x = minX - 10; x <= maxX + 10; x++) {
				for (int y = minY - 10; y <= maxY + 10; y++) {
					for (int z = minZ - 10; z <= maxZ + 10; z++) {
						for (int w = minW - 10; w <= maxW + 10; w++) {
							Coord4D coord = new Coord4D(x, y, z, (part == Part.TWO ? w : 0));
							int neighbors = getActiveNeighbors(coord, activeCubes, part);
							if (activeCubes.contains(coord)) {
								if (neighbors == 2 || neighbors == 3) {
									newActiveCubes.add(coord);
								}
							} else {
								if (neighbors == 3) {
									newActiveCubes.add(coord);
								}
							}
						}
					}
				}
			}
			activeCubes = newActiveCubes;
		}
		
		return activeCubes.size();
	}
	
	@Data
	@AllArgsConstructor
	private class Coord4D {
		private int x;
		private int y;
		private int z;
		private int w;
	}
	
	private int getMin(Set<Coord4D> activeCubes, Function<Coord4D, Integer> mapper) {
		int min = Integer.MAX_VALUE;
		for (Coord4D activeCube : activeCubes) {
			int val = mapper.apply(activeCube);
			if (val < min) {
				min = val;
			}
		}
		return min;
	}
	
	private int getMax(Set<Coord4D> activeCubes, Function<Coord4D, Integer> mapper) {
		int max = Integer.MIN_VALUE;
		for (Coord4D activeCube : activeCubes) {
			int val = mapper.apply(activeCube);
			if (val > max) {
				max = val;
			}
		}
		return max;
	}
	
	private int getActiveNeighbors(Coord4D coord, Set<Coord4D> activeCubes, Part part) {
		int activeNeighbors = 0;
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					for (int w = -1; w <= 1; w++) {
						if (x == 0 && y == 0 && z == 0 && (part == Part.TWO ? w == 0 : true)) {
							continue;
						}
						Coord4D neighbor = new Coord4D(coord.getX() + x, coord.getY() + y, coord.getZ() + z, coord.getW() + w);
						if (activeCubes.contains(neighbor)) {
							activeNeighbors++;
						}
					}
				}
			}
		}
		return activeNeighbors;
	}
}