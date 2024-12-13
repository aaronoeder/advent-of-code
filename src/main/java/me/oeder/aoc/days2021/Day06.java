package me.oeder.aoc.days2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day06 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int n = (part == Part.ONE ? 80 : 256);
		
		List<Fish> fishes = Arrays.asList(lines.get(0).split(",")).stream()
				.map(s -> new Fish(Integer.parseInt(s), 0, n))
				.collect(Collectors.toList());
		
		long amt = fishes.size();
		for (Fish fish : fishes) {
			amt += getFishGenerated(fish);
		}
		
		return amt;
	}
	
	@Data
	@AllArgsConstructor
	private class Fish {
		private int val;
		private int day;
		private int total;
	}
	
	private Map<Fish, Long> cache = new HashMap<>();
	private long getFishGenerated(Fish fish) {
		if (cache.containsKey(fish)) {
			return cache.get(fish);
		}
		
		long total = 0;
		
		int next = fish.getVal() + fish.getDay() + 1;
		if (next <= fish.getTotal()) {
			int cycles = (fish.getTotal() - next) / 7;
			for (int i = 0; i <= cycles; i++) {
				total += 1 + getFishGenerated(new Fish(8, next + i * 7, fish.getTotal()));
			}
		}
		
		cache.put(fish,  total);
		
		return total;
	}
}