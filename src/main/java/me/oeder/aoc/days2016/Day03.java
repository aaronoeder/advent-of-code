package me.oeder.aoc.days2016;

import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int[][] nums = new int[lines.size()][3];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			
			int j = 0;
			for (String entry : line.split(" ")) {
				if (entry.equals("")) {
					continue;
				}
				nums[i][j] = Integer.parseInt(entry);
				j++;
			}
		}
		
		int sum = 0;
		if (part == Part.ONE) { 
			for (int i = 0; i < nums.length; i++) {
				List<Integer> sides = Arrays.stream(nums[i]).boxed().collect(Collectors.toList());
				if (isTriangle(sides)) {
					sum++;
				}
			}
		} else {
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < lines.size(); i += 3) {
					List<Integer> sides = Arrays.stream(new int[] { nums[i][j], nums[i + 1][j], nums[i + 2][j] }).boxed().collect(Collectors.toList());
					if (isTriangle(sides)) {
						sum++;
					}
				}
			}
		}
		return sum;
	}
	
	private boolean isTriangle(List<Integer> sides) {
		Collections.sort(sides);
		if (sides.get(0) + sides.get(1) > sides.get(2)) {
			return true;
		}
		return false;
	}
}