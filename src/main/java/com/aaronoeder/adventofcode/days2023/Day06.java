package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.aaronoeder.adventofcode.AdventDay;

public class Day06 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			List<Long> ways = getWays(lines, Part.ONE);

			int sum = 1;
			for (long way : ways) {
				sum *= way;
			}
			return sum;
		}
		return getWays(lines, Part.TWO).get(0);
	}
	
	private List<Long> getWays(List<String> lines, Part part) {
		List<Long> times = getLongList(lines, 0, part);
		List<Long> distances = getLongList(lines, 1, part);
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
	
	private List<Long> getLongList(List<String> lines, int i, Part part) {
		String[] parts = null;
		
		if (part == Part.ONE) {
			parts = lines.get(i).split(":")[1].split(" ");
		} else if (part == Part.TWO) {
			parts = new String[] { lines.get(i).split(":")[1].replace(" ", "") };
		}
		
		return Arrays.asList(parts).stream()
				.filter(p -> !p.trim().equals(""))
				.map(s -> Long.parseLong(s))
				.collect(Collectors.toList());
	}
}