package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		
		int max = Integer.MIN_VALUE;
		Map<String, Integer> registerValues = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split(" if ");
			
			String[] registerParts = parts[0].split(" ");
			String register = registerParts[0];
			boolean inc = registerParts[1].equals("inc");
			int amount = Integer.parseInt(registerParts[2]);
			
			String[] conditionParts = parts[1].split(" ");
			String conditionRegister = conditionParts[0];
			String conditionOperator = conditionParts[1];
			int conditionValue = Integer.parseInt(conditionParts[2]);
			
			if (isConditionTrue(registerValues, conditionRegister, conditionOperator, conditionValue)) {
				int previousValue = registerValues.getOrDefault(register, 0);
				int newValue = inc ? (previousValue + amount) : (previousValue - amount);
				registerValues.put(register, newValue);
				
				if (newValue > max) {
					max = newValue;
				}
			}
		}
		
		for (Map.Entry<String, Integer> entry : registerValues.entrySet()) {
			log(entry.getKey() + " " + entry.getValue());
		}
		
		List<Integer> values = new ArrayList<>(registerValues.values());
		Collections.sort(values, (i1, i2) -> i2 - i1);
		
		if (part == Part.ONE) { 
			return values.get(0);
		} else {
			return max;
		}
	}
	
	private boolean isConditionTrue(Map<String, Integer> registerValues, String conditionRegister, String conditionOperator, int conditionValue) {
		int registerValue = registerValues.getOrDefault(conditionRegister, 0);
		switch (conditionOperator) {
			case "<":
				return registerValue < conditionValue;
			case "<=":
				return registerValue <= conditionValue;
			case ">":
				return registerValue > conditionValue;
			case ">=":
				return registerValue >= conditionValue;
			case "==":
				return registerValue == conditionValue;
			case "!=":
				return registerValue != conditionValue;
			default:
				throw new IllegalArgumentException("Unknown operator: " + conditionOperator);
		}
	}
}