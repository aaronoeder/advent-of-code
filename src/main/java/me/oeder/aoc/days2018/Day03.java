package me.oeder.aoc.days2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

public class Day03 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Claim> claims = new ArrayList<>();
		
		Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				claims.add(new Claim(Integer.parseInt(matcher.group(1)),
						new Coord(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(2))),
						new Coord(Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(4)))));
			}
		}
		
		Map<Coord, Integer> coordCounts = new HashMap<>();
		for (Claim claim : claims) {
			for (Coord coord : claim.getOccupiedCoords()) {
				coordCounts.put(coord, 1 + coordCounts.getOrDefault(coord, 0));
			}
		}
		
		if (part == Part.ONE) { 
			int count = 0;
			for (Map.Entry<Coord, Integer> entry : coordCounts.entrySet()) {
				if (entry.getValue() > 1) {
					count++;
				}
			}
			return count;
		} else {
			int id = 0;
			for (Claim claim : claims) {
				boolean overlapsOthers = false;
				for (Coord coord : claim.getOccupiedCoords()) {
					if (coordCounts.get(coord) > 1) {
						overlapsOthers = true;
					}
				}
				
				if (!overlapsOthers) {
					id = claim.getId();
					break;
				}
			}
			return id;
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Claim {
		private int id;
		private Coord start;
		private Coord length;
		
		public List<Coord> getOccupiedCoords() {
			List<Coord> occupiedCoords = new ArrayList<>();
			for (int i = start.getRow(); i < start.getRow() + length.getRow(); i++) {
				for (int j = start.getCol(); j < start.getCol() + length.getCol(); j++) {
					occupiedCoords.add(new Coord(i, j));
				}
			}
			return occupiedCoords;
		}
	}
}