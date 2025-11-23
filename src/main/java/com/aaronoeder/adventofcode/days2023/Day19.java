package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

public class Day19 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Rule> rulesMap = new LinkedHashMap<>();
		List<Input> inputs = new ArrayList<>();
		
		boolean foundBlank = false;
		for (String line : lines) {
			if (line.equals("")) {
				foundBlank = true;
			} else if (!foundBlank) {
				String[] workflowParts = line.split("\\{");
				
				String id = workflowParts[0];
				List<Condition> conditions = new ArrayList<>();
				String fallbackResult = null;
				
				String[] conditionParts = workflowParts[1].split(",");
				for (int i = 0; i < conditionParts.length; i++) {
					String conditionPart = conditionParts[i];
					if (i < conditionParts.length - 1) {
						boolean lessThan = conditionPart.contains("<");
						conditionPart = conditionPart.replace("<", ":").replace(">", ":");
						
						String[] conditionPartElements = conditionPart.split(":");
						conditions.add(new Condition(conditionPartElements[0], lessThan, Integer.parseInt(conditionPartElements[1]), conditionPartElements[2]));
					} else {
						fallbackResult = conditionPart.substring(0, conditionPart.length() - 1);
					}
				}
				
				rulesMap.put(id, new Rule(conditions, fallbackResult));
			} else {
				String[] inputParts = line.split(",");
				inputs.add(new Input(getInt(inputParts, 0), getInt(inputParts, 1), getInt(inputParts, 2), getInt(inputParts, 3)));
			}
		}
		
		if (part == Part.ONE) {
			return getSumOfRatingNumbers(rulesMap, inputs);
		} else {
			Map<String, Range> rangesMap = new HashMap<>();
			rangesMap.put("x", new Range(1, 4000));
			rangesMap.put("m", new Range(1, 4000));
			rangesMap.put("a", new Range(1, 4000));
			rangesMap.put("s", new Range(1, 4000));
			return getCombinationCount("in", rulesMap, rangesMap);
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Rule {
		private List<Condition> conditions;
		private String fallbackResult;
	}
	
	@Data
	@AllArgsConstructor
	private class Condition {
		private String var;
		private boolean lessThan;
		private int amount;
		private String result;
	}
	
	@Data
	@AllArgsConstructor
	private class Input {
		private int x;
		private int m;
		private int a;
		private int s;
	}

	@Data
	@AllArgsConstructor
	private class Range {
		private int min;
		private int max;
	}
	
	private int getInt(String[] parts, int i) {
		return Integer.parseInt(parts[i].split("=")[1].replace("}", ""));
	}
	
	private int getSumOfRatingNumbers(Map<String, Rule> rulesMap, List<Input> inputs) {
		int sum = 0;
		for (Input input : inputs) {
			int x = input.getX();
			int m = input.getM();
			int a = input.getA();
			int s = input.getS();
			
			String result = "in";
			boolean loop = true;
			while (loop) {
				boolean passedCond = false;
				Rule rule = rulesMap.get(result);
				for (Condition cond : rule.getConditions()) {
					int val = -1;
					if (cond.getVar().equals("x")) {
						val = x;
					} else if (cond.getVar().equals("m")) {
						val = m;
					} else if (cond.getVar().equals("a")) {
						val = a;
					} else if (cond.getVar().equals("s")) {
						val = s;
					}
					
					if (cond.isLessThan()) {
						if (val < cond.getAmount()) {
							passedCond = true;
							result = cond.getResult();
							break;
						}
					} else {
						if (val > cond.getAmount()) {
							passedCond = true;
							result = cond.getResult();
							break;
						}
					}
				}
				
				if (!passedCond) {
					result = rule.getFallbackResult();
				}
				
				if (result.equals("R") || result.equals("A")) {
					loop = false;
				}
			}
			
			if (result.equals("A")) {
				sum += (x + m + a + s);
			}
		}
		return sum;
	}
	
	private long getCombinationCount(String result, Map<String, Rule> rulesMap, Map<String, Range> rangesMap) {
		if (result.equals("R")) {
			return 0;
		} else if (result.equals("A")) {
			long combinations = 1;
			for (Map.Entry<String, Range> entry : rangesMap.entrySet()) {
				Range range = entry.getValue();
				long diff = range.getMax() - range.getMin() + 1;
				combinations *= diff;
			}
			return combinations;
		} else {
			long total = 0;
			Rule rule = rulesMap.get(result);
			for (Condition cond : rule.getConditions()) {
				if (cond.isLessThan()) {
					Range range = rangesMap.get(cond.getVar());
					if (range.getMin() < cond.getAmount()) {
						Map<String, Range> rangesMapCopy = getDeepCopyOfRangesMap(rangesMap);
						rangesMapCopy.put(cond.getVar(), new Range(range.getMin(), cond.getAmount() - 1));
						total += getCombinationCount(cond.getResult(), rulesMap, rangesMapCopy);
					}
					if (range.getMax() >= cond.getAmount()) {
						rangesMap.put(cond.getVar(), new Range(cond.getAmount(), range.getMax()));
					} else {
						break;
					}
				} else {
					Range range = rangesMap.get(cond.getVar());
					if (range.getMax() > cond.getAmount()) {
						Map<String, Range> rangesMapCopy = getDeepCopyOfRangesMap(rangesMap);
						rangesMapCopy.put(cond.getVar(), new Range(cond.getAmount() + 1, range.getMax()));
						total += getCombinationCount(cond.getResult(), rulesMap, rangesMapCopy);
					}
					if (range.getMin() <= cond.getAmount()) {
						rangesMap.put(cond.getVar(), new Range(range.getMin(), cond.getAmount()));
					} else {
						break;
					}
				}
			}
			
			total += getCombinationCount(rule.getFallbackResult(), rulesMap, getDeepCopyOfRangesMap(rangesMap));
			return total;
		}
	}
	
	private Map<String, Range> getDeepCopyOfRangesMap(Map<String, Range> rangesMap) {
		Map<String, Range> deepCopy = new HashMap<>();
		for (Map.Entry<String, Range> entry : rangesMap.entrySet()) {
			deepCopy.put(entry.getKey(), new Range(entry.getValue().getMin(), entry.getValue().getMax()));
		}
		return deepCopy;
	}
}