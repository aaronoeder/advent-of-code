package me.oeder.aoc.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 extends AdventDay2023 {
	
	public Day06() {
		super(6);
	}

	@Override
	public Object solvePart1(List<String> lines) {
		List<Long> ways = getWays(lines, false);
		
		int sum = 1;
		for (long way : ways) {
			sum *= way;
		}
		return sum;
	}

	@Override
	public Object solvePart2(List<String> lines) {
		return getWays(lines, true).get(0);
	}
	
	private List<Long> getWays(List<String> lines, boolean part2) {
		List<Long> times = getLongList(lines, 0, part2);
		List<Long> distances = getLongList(lines, 1, part2);
		List<Long> ways = new ArrayList<>();
	
		for (int i = 0; i < times.size(); i++) {
			long w = 0;
			long time = times.get(i);
			for (int j = 1; j < time; j++) {
				long timeLeft = time - j;
				long totalDistance = timeLeft * j;
				if (totalDistance > distances.get(i)) {
					w++;
				}
			}
			ways.add(w);
		}
		
		return ways;
	}
	
	private List<Long> getLongList(List<String> lines, int i, boolean part2) {
		String[] parts = null;
		
		if (!part2) {
			parts = lines.get(i).split(":")[1].split(" ");
		} else {
			parts = new String[] { lines.get(i).split(":")[1].replace(" ", "") };
		}
		
		return Arrays.asList(parts).stream()
				.filter(part -> !part.trim().equals(""))
				.map(s -> Long.parseLong(s))
				.collect(Collectors.toList());
	}
}