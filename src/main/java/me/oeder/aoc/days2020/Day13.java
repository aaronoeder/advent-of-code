package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int start = Integer.parseInt(lines.get(0));
		
		List<String> busValues = Arrays.asList(lines.get(1).split(","));
		List<Integer> buses = busValues.stream()
				.filter(s -> !s.equals("x"))
				.map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		
		if (part == Part.ONE) { 
			int minBus = -1;
			int min = Integer.MAX_VALUE;
			for (int bus : buses) {
				int div = start / bus;
				int time = bus * div + bus;
				if (time < min) {
					minBus = bus;
					min = time;
				}
			}
			
			return minBus * (min - start);
		} else {
			List<MathUtils.Congruence> congruences = new ArrayList<>();
			for (int bus : buses) {
				congruences.add(new MathUtils.Congruence(bus, (bus - busValues.indexOf(String.valueOf(bus)) % bus)));
			}
			return MathUtils.chineseRemainder(congruences);
		}
	}
}