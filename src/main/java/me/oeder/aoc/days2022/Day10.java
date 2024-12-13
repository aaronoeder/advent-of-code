package me.oeder.aoc.days2022;

import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.InputUtils;

public class Day10 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int spriteMiddle = 1;
		String[][] imageArr = new String[6][40];
		
		int sum = 0;
		int x = 1;
		int cycle = 1;
		int index = 0;
		boolean midCycle = false;
		while (cycle <= 240) {
			int i = (cycle - 1) / 40;
			int j = (cycle - 1) % 40;
			imageArr[i][j] = (j >= spriteMiddle - 1 && j <= spriteMiddle + 1) ? "#": ".";
			
			String line = lines.get(index);
			if (line.equals("noop")) {
				index++;
			} else if (line.startsWith("addx ")) {
				int number = Integer.parseInt(line.split(" ")[1]);
				if (!midCycle) {
					midCycle = true;
				} else {
					x += number;
					spriteMiddle = x;
					midCycle = false;
					index++;
				}
			}
			
			cycle++;
			
			if ((cycle + 20) % 40 == 0) {
				sum += cycle * x;
			}
		}
		
		if (part == Part.TWO) {
			InputUtils.printGrid(imageArr);
		}
		
		return sum;
	}
}