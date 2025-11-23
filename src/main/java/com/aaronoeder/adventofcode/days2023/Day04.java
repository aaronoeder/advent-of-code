package com.aaronoeder.adventofcode.days2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.aaronoeder.adventofcode.AdventDay;

public class Day04 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		if (part == Part.ONE) {
			for (String line : lines) {
				int matches = getMatches(line);
				if (matches > 0) {
					sum += Math.pow(2, matches - 1);
				}
			}
		} else if (part == Part.TWO) {
			for (int i = 0; i < lines.size(); i++) {
				sum += getNumberOfCardsWon(lines, i);
			}
		}
		return sum;
	}
	
	private int getNumberOfCardsWon(List<String> lines, int i) {
		int matches = getCachedMatches(lines, i);
		int count = 1;
		for (int m = 1; m <= Math.min(matches, lines.size() - i - 1); m++) {
			count += getNumberOfCardsWon(lines, i + m);
		}
		return count;
	}
	
	private Map<Integer, Integer> cache = new HashMap<>();
	private int getCachedMatches(List<String> lines, int i) {
		String line = lines.get(i);
		if (cache.containsKey(i)) {
			return cache.get(i);
		}
		int matches = getMatches(line);
		cache.put(i, matches);
		return matches;
	}
	
	private int getMatches(String line) {
		String[] valuesParts = line.split(": ")[1].split("\\| ");
		
		String winningValues = valuesParts[0];
		String playerValues = valuesParts[1];

		List<Integer> winningNums = getIntegerList(winningValues.split(" "));
		List<Integer> nums = getIntegerList(playerValues.split(" "));
			
		int matches = 0;
		for (int num : nums) {
			if (winningNums.contains(num)) {
				matches++;
			}
		}
		
		return matches;
	}
	
	private List<Integer> getIntegerList(String...vals) {
		return Arrays.asList(vals).stream()
				.filter(s -> StringUtils.isNotBlank(s))
				.map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
	}
}