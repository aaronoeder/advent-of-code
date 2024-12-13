package me.oeder.aoc.days2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

public class Day10 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Point> points = new ArrayList<>();
		
		Pattern pattern = Pattern.compile("position=<([ ]*-?\\d+), ([ ]*-?\\d+)> velocity=<([ ]*-?\\d+), ([ ]*-?\\d+)>");
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				points.add(new Point(
							new Coord(Integer.parseInt(matcher.group(2).trim()), Integer.parseInt(matcher.group(1).trim())), 
							new Coord(Integer.parseInt(matcher.group(4).trim()), Integer.parseInt(matcher.group(3).trim()))));
			}
		}
		
		for (int t = 1; t <= 1000000; t++) {
			for (Point point : points) {
				point.getPosition().setRow(point.getPosition().getRow() + point.getVelocity().getRow());
				point.getPosition().setCol(point.getPosition().getCol() + point.getVelocity().getCol());
			}
		
			printPoints(points, t);
		}
		
		return 0;
	}
	
	@Data
	@AllArgsConstructor
	private class Point {
		private Coord position;
		private Coord velocity;
	}
	
	private void printPoints(List<Point> points, int t) {
		int minRow = getFirst(points, (p1, p2) -> p1.getPosition().getRow() - p2.getPosition().getRow(), p -> p.getPosition().getRow());
		int maxRow = getFirst(points, (p1, p2) -> p2.getPosition().getRow() - p1.getPosition().getRow(), p -> p.getPosition().getRow());
		int minCol = getFirst(points, (p1, p2) -> p1.getPosition().getCol() - p2.getPosition().getCol(), p -> p.getPosition().getCol());
		int maxCol = getFirst(points, (p1, p2) -> p2.getPosition().getCol() - p1.getPosition().getCol(), p -> p.getPosition().getCol());
		
		if (Math.abs(minRow - maxRow) > 10) {
			return;
		}
		
		log("Time: " + t);
		
		for (int i = minRow - 1; i <= maxRow + 1; i++) {
			for (int j = minCol - 1; j <= maxCol + 1; j++) {
				String val = ".";
				for (Point point : points) {
					if (point.getPosition().getRow() == i && point.getPosition().getCol() == j) {
						val = "#";
					}
				}
				System.out.print(val);
			}
			System.out.println();
		}
	}
	
	private int getFirst(List<Point> points, Comparator<Point> sorter, Function<Point, Integer> mapper) {
		Collections.sort(points, sorter);
		return mapper.apply(points.get(0));
	}
}