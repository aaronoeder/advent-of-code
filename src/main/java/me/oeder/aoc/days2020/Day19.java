package me.oeder.aoc.days2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day19 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<Integer, Rule> rules = new LinkedHashMap<>();
		List<String> messages = new ArrayList<>();
		
		boolean hasAddedAllRules = false;
		for (String line : lines) {
			
			if (part == Part.TWO) {
				if (line.startsWith("8:")) {
					line = "8: 42 | 42 8";
				} else if (line.startsWith("11:")) {
					line = "11: 42 31 | 42 11 31";
				}
			}
			
			if (line.equals("")) {
				hasAddedAllRules = true;
			} else if (!hasAddedAllRules) {
				String[] ruleParts = line.split(": ");
				
				int ruleId = Integer.parseInt(ruleParts[0]);
				
				String value = "";
				List<Integer> leftRules = new ArrayList<>();
				List<Integer> rightRules = new ArrayList<>();
				
				if (ruleParts[1].contains("\"")) {
					value = ruleParts[1].replace("\"", "");
				} else {
					String[] leftRightParts = ruleParts[1].split(" \\| ");
					
					leftRules = Arrays.asList(leftRightParts[0].split(" ")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
					if (leftRightParts.length > 1) {
						rightRules = Arrays.asList(leftRightParts[1].split(" ")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
					}
				}
				
				rules.put(ruleId, new Rule(value, leftRules, rightRules));
			} else {
				messages.add(line);
			}
		}
		
		int sum = 0;
		Set<String> validMessages = getValidMessages(rules, 0);
		for (String message : messages) {
			if (validMessages.contains(message)) {
				sum++;
			}
		}
		
		return sum;
	}
	
	private Set<String> getValidMessages(Map<Integer, Rule> rules, int ruleId) {
		Rule rule = rules.get(ruleId);
		if (!rule.getValue().equals("")) {
			Set<String> combos = new HashSet<>();
			combos.add(rule.getValue());
			return combos;
		} else {
			Set<String> combos = new HashSet<>();
			Set<String> leftCombos = new HashSet<>();
			if (!rule.getLeftRules().isEmpty()) {
				for (int leftRule : rule.getLeftRules()) {
					if (leftCombos.size() == 0) {
						leftCombos.addAll(getValidMessages(rules, leftRule));
					} else {
						Set<String> newLeftCombos = new HashSet<>();
						for (String leftCombo : leftCombos) {
							Set<String> nextLeftCombos = getValidMessages(rules, leftRule);
							for (String nextLeftCombo : nextLeftCombos) {
								newLeftCombos.add(leftCombo + nextLeftCombo);
							}
						}
						leftCombos = newLeftCombos;
					}
				}
			}
			Set<String> rightCombos = new HashSet<>();
			if (!rule.getRightRules().isEmpty()) {
				for (int rightRule : rule.getRightRules()) {
					if (rightCombos.size() == 0) {
						rightCombos.addAll(getValidMessages(rules, rightRule));
					} else {
						Set<String> newRightCombos = new HashSet<>();
						for (String rightCombo : rightCombos) {
							Set<String> nextRightCombos = getValidMessages(rules, rightRule);
							for (String nextRightCombo : nextRightCombos) {
								newRightCombos.add(rightCombo + nextRightCombo);
							}
						}
						rightCombos = newRightCombos;
					}
				}
			}
			
			combos.addAll(leftCombos);
			combos.addAll(rightCombos);
			return combos;
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Rule {
		private String value;
		private List<Integer> leftRules;
		private List<Integer> rightRules;
	}
}