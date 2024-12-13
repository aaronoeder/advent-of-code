package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Map<String, Integer>> sues = new ArrayList<>();
		for (String line : lines) {
			Map<String, Integer> sue = new HashMap<>();
			String[] things = line.substring(line.indexOf(":") + 2).split(", ");
			for (String thing : things) {
				String[] thingParts = thing.split(": ");
				sue.put(thingParts[0], Integer.parseInt(thingParts[1]));
			}
			sues.add(sue);
		}
		
		for (int i = 0; i < sues.size(); i++) {
			int sueNumber = (i + 1);
			Map<String, Integer> sue = sues.get(i);
			if (
				matchesThingCriteria(sue, "children", 3, part) &&
				matchesThingCriteria(sue, "cats", 7, part) &&
				matchesThingCriteria(sue, "samoyeds", 2, part) &&
				matchesThingCriteria(sue, "pomeranians", 3, part) &&
				matchesThingCriteria(sue, "akitas", 0, part) &&
				matchesThingCriteria(sue, "vizslas", 0, part) &&
				matchesThingCriteria(sue, "goldfish", 5, part) &&
				matchesThingCriteria(sue, "trees", 3, part) &&
				matchesThingCriteria(sue, "cars", 2, part) &&
				matchesThingCriteria(sue, "perfumes", 1, part)
			) {
				return sueNumber;
			}
		}
		return -1;
	}
	
	private boolean matchesThingCriteria(Map<String, Integer> sue, String thing, int amount, Part part) {
		int sueAmount = sue.getOrDefault(thing, -1);
		if (sueAmount == -1) {
			return true;
		}
		if (part == Part.TWO) {
			if (thing.equals("cats") || thing.equals("trees")) {
				return sueAmount > amount;
			}
			if (thing.equals("pomeranians") || thing.equals("goldfish")) {
				return sueAmount < amount;
			}
		}
		return sueAmount == amount;
	}
}