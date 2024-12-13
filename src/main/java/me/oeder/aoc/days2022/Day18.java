package me.oeder.aoc.days2022;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day18 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Coord3D> coords = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split(",");
			coords.add(new Coord3D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
		}
		
		int sum = 0;
		for (int i = 0; i < coords.size(); i++) {
			Coord3D coord = coords.get(i);
			int neighbors = 0;
			for (int j = 0; j < coords.size(); j++) {
				if (i == j) {
					continue;
				}
				Coord3D other = coords.get(j);
				if (coord.isAdjacentTo(other)) {
					neighbors++;
				}
			}
			sum += (6 - neighbors);
		}
		
		return sum;
	}
	
	@Data
	@AllArgsConstructor
	private class Coord3D {
		private int x;
		private int y;
		private int z;
		
		public boolean isAdjacentTo(Coord3D other) {
			if (Math.abs(this.x - other.x) == 1) {
				return this.y == other.y && this.z == other.z;
			} else if (Math.abs(this.y - other.y) == 1) {
				return this.x == other.x && this.z == other.z;
			} else if (Math.abs(this.z - other.z) == 1) {
				return this.x == other.x && this.y == other.y;
			}
			return false;
		}
	}
}