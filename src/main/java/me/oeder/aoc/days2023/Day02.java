package me.oeder.aoc.days2023;

import java.util.List;

public class Day02 extends AdventDay2023 {

	public Day02() {
		super(2);
	}

	@Override
	public Object solvePart1(List<String> lines) {
		return getSum(lines, false);
	}

	@Override
	public Object solvePart2(List<String> lines) {
		return getSum(lines, true);
	}
	
	private int getSum(List<String> lines, boolean part2) {
		int sum = 0;
		
		for (String line : lines) {
			boolean valid = true;
			
			int maxRed = Integer.MIN_VALUE;
			int maxGreen = Integer.MIN_VALUE;
			int maxBlue = Integer.MIN_VALUE;
			
			String[] parts = line.split(":");
			
			int gameNumber = Integer.parseInt(parts[0].split(" ")[1]);
			
			String[] rounds = parts[1].split(";");
			for (String round : rounds) {
				String[] elements = round.trim().split(",");
				for (String element : elements) {
					String trimmedElement = element.trim();
					String[] trimmedElementParts = trimmedElement.split(" ");
					int count = Integer.parseInt(trimmedElementParts[0]);
					String color = trimmedElementParts[1];
					
					if (color.equals("red") && count > 12) {
						valid = false;
					} else if (color.equals("green") && count > 13) {
						valid = false;
					} else if (color.equals("blue") && count > 14) {
						valid = false;
					}
					
					if (color.equals("red") && count > maxRed) {
						maxRed = count;
					} else if (color.equals("green") && count > maxGreen) {
						maxGreen = count;
					} else if (color.equals("blue") && count > maxBlue) {
						maxBlue = count;
					}
				}
			}
			
			if (!part2 && valid) {
				sum += gameNumber;
			} else if (part2) {
				sum += (maxRed * maxGreen * maxBlue);
			}
		}
		return sum;
	}
}