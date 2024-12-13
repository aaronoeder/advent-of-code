package me.oeder.aoc.days2015;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

public class Day06 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int[][] lights = new int[1000][1000];
		for (String line : lines) {
			List<Coord> coords = Arrays.asList(line.split(" ")).stream()
					.filter(s -> s.contains(","))
					.map(s -> {
						String[] parts = s.split(",");
						return new Coord(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
					})
					.collect(Collectors.toList());
			
			Coord start = coords.get(0);
			Coord end = coords.get(1);
			
			int minRow = Math.min(start.getRow(), end.getRow());
			int maxRow = Math.max(start.getRow(), end.getRow());
			int minCol = Math.min(start.getCol(), end.getCol());
			int maxCol = Math.max(start.getCol(), end.getCol());
			
			for (int i = minRow; i <= maxRow; i++) {
				for (int j = minCol; j <= maxCol; j++) {
					int val = lights[i][j];
					if (line.startsWith("turn on")) {
						val = (part == Part.ONE ? 1 : val + 1);
					} else if (line.startsWith("turn off")) {
						val = (part == Part.ONE ? 0 : Math.max(0, val - 1));
					} else if (line.startsWith("toggle")) {
						val = (part == Part.ONE ? (val == 0 ? 1 : 0) : val + 2);
					}
					lights[i][j] = val;
				}
			}
		}
		return getCount(lights);
	}
	
	private int getCount(int[][] lights) {
		int count = 0;
		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights[0].length; j++) {
				count += lights[i][j];
			}
		}
		return count;
	}
}