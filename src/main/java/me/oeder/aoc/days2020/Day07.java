package me.oeder.aoc.days2020;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day07 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Rule> rules = new LinkedHashMap<>();
		for (String line : lines) {
			String[] parts = line.split(" contain ");
			String bagId = parts[0].replace(" bags", "");
			
			Map<String, Integer> nestedBags = new LinkedHashMap<>();
			if (!parts[1].contains("no other bags")) {
				String[] nestedParts = parts[1].replace(".", "").split(", ");
				for (String nestedPart : nestedParts) {
					String nestedBagId = nestedPart.substring(nestedPart.indexOf(" ") + 1, nestedPart.lastIndexOf(" "));
					int nestedCount = Integer.parseInt(nestedPart.substring(0, nestedPart.indexOf(" ")));
					nestedBags.put(nestedBagId, nestedCount);
				}
			}
			
			rules.put(bagId, new Rule(bagId, nestedBags));
		}
		
		String shinyGoldBagId = "shiny gold";
		
		int sum = 0;
		if (part == Part.ONE) { 
			for (String bagId : rules.keySet()) {
				if (bagId.equals(shinyGoldBagId)) {
					continue;
				}
				if (holdsBag(rules, bagId, shinyGoldBagId)) {
					sum++;
				}
			}
		} else if (part == Part.TWO) {
			return getBagCount(rules, shinyGoldBagId);
		}
	
		return sum;
	}
	
	private boolean holdsBag(Map<String, Rule> rules, String bagId, String endingBagId) {
		if (bagId.equals(endingBagId)) {
			return true;
		}
		
		Rule rule = rules.get(bagId);
		for (Map.Entry<String, Integer> entry : rule.getNestedBags().entrySet()) {
			String nestedBagId = entry.getKey();
			if (holdsBag(rules, nestedBagId, endingBagId)) {
				return true;
			}
		}
		return false;
	}
	
	private int getBagCount(Map<String, Rule> rules, String bagId) {
		Rule rule = rules.get(bagId);
		if (rule.getNestedBags().size() == 0) {
			return 0;
		}
		int count = 0;
		for (Map.Entry<String, Integer> entry : rule.getNestedBags().entrySet()) {
			count += entry.getValue() * (1 + getBagCount(rules, entry.getKey()));
		}
		return count;
	}
	
	@Data
	@AllArgsConstructor
	private class Rule {
		private String bagId;
		private Map<String, Integer> nestedBags;
	}
}