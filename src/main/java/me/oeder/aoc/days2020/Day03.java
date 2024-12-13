package me.oeder.aoc.days2020;

import java.util.ArrayList;
import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

public class Day03 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Coord> trees = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				if (line.substring(j, j + 1).equals("#")) {
					trees.add(new Coord(i, j));
				}
			}
		}
		
		List<Coord> slopes = new ArrayList<>();
		slopes.add(new Coord(1, 3));
		if (part == Part.TWO) {
			slopes.add(new Coord(1, 1));
			slopes.add(new Coord(1, 5));
			slopes.add(new Coord(1, 7));
			slopes.add(new Coord(2, 1));
		}
		
		long sum = 1;
		for (Coord slope : slopes) {
			long slopeSum = 0;
			Coord cur = new Coord(0, 0);
			while (cur.getRow() < lines.size() - 1) {
				cur = new Coord(cur.getRow() + slope.getRow(), (cur.getCol() + slope.getCol()) % lines.get(0).length());
				if (trees.contains(cur)) {
					slopeSum++;
				}
			}
			sum *= slopeSum;
		}
		
		return sum;
	}
}