package me.oeder.aoc.days2021;
import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day02 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int x = 0;
		int y = 0;
		int aim = 0;
		for (String line : lines) {
			String[] dirParts = line.split(" ");
			String dir = dirParts[0];
			int amount = Integer.parseInt(dirParts[1]);
			switch (dir) {
				case "forward":
					x += amount;
					if (part == Part.TWO) {
						y += (amount * aim);
					}
					break;
				case "down":
					if (part == Part.ONE) {
						y += amount;
					} else {
						aim += amount;
					}
					break;
				case "up":
					if (part == Part.ONE) {
						y -= amount;
					} else {
						aim -= amount;	
					}
					break;
			}
		}
		
		return x * y;
	}
}