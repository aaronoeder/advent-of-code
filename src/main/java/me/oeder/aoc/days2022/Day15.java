package me.oeder.aoc.days2022;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day15 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<Coord2D, Coord2D> sensorBeacons = new LinkedHashMap<>();
		
		Pattern p = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
		for (String line : lines) {
			Matcher m = p.matcher(line);
			if (m.find()) {
				sensorBeacons.put(buildCoord2D(m.group(1), m.group(2)), buildCoord2D(m.group(3), m.group(4)));
			}
		}
		
		if (part == Part.ONE) { 
			int y = 2000000;
			Set<Integer> impossible = new HashSet<>();
			for (Map.Entry<Coord2D, Coord2D> entry : sensorBeacons.entrySet()) {
				Coord2D sensor = entry.getKey();
				Coord2D beacon = entry.getValue();
				int dist = dist(sensor, beacon);
				
				int travelY = y - sensor.getY();
				
				int remaining = dist - Math.abs(travelY);
				
				for (int i = -remaining; i <= remaining; i++) {
					
					boolean found = false;
					for (Coord2D b : sensorBeacons.values()) {
						if (b.getX() == sensor.getX() + i && b.getY() == y) {
							found = true;
						}
					}
					
					if (!found) {
						impossible.add(sensor.getX() + i);
					}
				}
			}
			
			return impossible.size();
		} else {
			Set<Coord2D> visited = new HashSet<>();
			int index = 0;
			for (Map.Entry<Coord2D, Coord2D> entry : sensorBeacons.entrySet()) {
				Coord2D sensor = entry.getKey();
				Coord2D beacon = entry.getValue();
				int dist = dist(sensor, beacon);
				log(dist);
				for (int x = sensor.getX() - dist; x <= sensor.getX() + dist; x++) {
					for (int y = sensor.getY() - dist; y <= sensor.getY() + dist; y++) {
						int dx = Math.abs(x - sensor.getX());
						int dy = Math.abs(y - sensor.getY());
						if (dx + dy > dist) {
							continue;
						}
						visited.add(new Coord2D(x, y));
					}
				}
				log(++index);
			}
			
			for (int x = 0; x <= 4000000; x++) {
				for (int y = 0; y <= 4000000; y++) {
					if (!visited.contains(new Coord2D(x, y))) {
						return x * 4000000 + y;
					}
				}
			}
			return -1;
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Coord2D {
		private int x;
		private int y;
	}
	
	private Coord2D buildCoord2D(String x, String y) {
		return new Coord2D(Integer.parseInt(x), Integer.parseInt(y));
	}
	
	private int dist(Coord2D c1, Coord2D c2) {
		return Math.abs(c1.getX() - c2.getX()) + Math.abs(c1.getY() - c2.getY());
	}
}