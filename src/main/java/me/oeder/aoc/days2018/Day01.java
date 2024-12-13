package me.oeder.aoc.days2018;

import me.oeder.aoc.AdventDay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day01 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			int sum = 0;
			for (String line : lines) {
				if (line.startsWith("+")) {
					sum += Integer.parseInt(line.substring(1));
				} else {
					sum -= Integer.parseInt(line.substring(1));
				}
			}
			return sum;
		} else {
			int index = 0;
			int sum = 0;
			Set<Integer> prevs = new HashSet<>();
			while (true) {
				String line = lines.get(index % lines.size());
				if (line.startsWith("+")) {
					sum += Integer.parseInt(line.substring(1));
				} else {
					sum -= Integer.parseInt(line.substring(1));
				}
				
				if (prevs.contains(sum)) {
					return sum;
				}
				
				prevs.add(sum);
				index++;
			}
		}
	}
}