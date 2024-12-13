package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			List<Integer> dimensions = Arrays.asList(line.split("x")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			Collections.sort(dimensions);
			List<Integer> sideAreas = Arrays.asList(dimensions.get(0) * dimensions.get(1), dimensions.get(1) * dimensions.get(2), dimensions.get(0) * dimensions.get(2));
			Collections.sort(sideAreas);
			if (part == Part.ONE) { 
				sum += sideAreas.get(0) + 2 * sideAreas.stream().reduce(0, (acc, elem) -> acc + elem);		
			} else {
				sum += 2 * (dimensions.get(0) + dimensions.get(1)) + dimensions.stream().reduce(1, (acc, elem) -> acc * elem);
			}
		}
		return sum;
	}
}