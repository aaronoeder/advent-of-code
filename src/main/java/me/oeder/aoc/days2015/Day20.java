package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day20 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int min = Integer.parseInt(lines.get(0));
		int house = 600000;
		while (true) {
			int presents = 0;
			for (int i = 1; i <= house; i++) {
				if (house > 50 * i) {
					continue;
				}
				if (house % i == 0) {
					presents += 11 * i;
				}
			}
			
			if (presents > min) {
				return house;
			}
			
			house++;
		}
	}
}