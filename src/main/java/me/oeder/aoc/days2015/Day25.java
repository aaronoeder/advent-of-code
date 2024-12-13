package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day25 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {

		List<Coord> nums = new ArrayList<>();
		Map<Coord, Long> numsMap = new HashMap<>();

		long val = 20151125;
		int req = 1;
		int rep = 0;
		int row = 1;
		int col = 1;
		while (true) {
			if (row == 2981 && col == 3075) {
				return val;
			}

			nums.add(new Coord(row, col));
			numsMap.put(new Coord(row, col), val);
			val *= 252533;
			val %= 33554393;
			rep++;
			if (rep == req) {
				rep = 0;
				req++;
				row = req;
				col = 1;
			} else {
				row--;
				col++;
			}
		}
	}
}