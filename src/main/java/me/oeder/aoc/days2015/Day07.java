package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day07 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Integer> values = new HashMap<>();
		int valueA = getValueA(lines, values);
		if (part == Part.ONE) {
			return valueA;
		}
		values.clear();
		values.put("b", valueA);
		return getValueA(lines, values);
	}
	
	private int getValueA(List<String> lines, Map<String, Integer> values) {
		int index = 0;
		while (values.size() < lines.size()) {
			String line = lines.get(index);
			String[] parts = line.split(" -> ");
			String expression = parts[0];
			String wire = parts[1];
			if (!values.containsKey(wire)) {
				if (expression.contains("LSHIFT")) {
					String[] expressionParts = expression.split(" LSHIFT ");
					String var = expressionParts[0];
					int amount = Integer.parseInt(expressionParts[1]);
					if (values.containsKey(var)) {
						int varValue = values.get(var);
						values.put(wire, varValue << amount);
					}
					
				} else if (expression.contains("RSHIFT")) {
					String[] expressionParts = expression.split(" RSHIFT ");
					String var = expressionParts[0];
					int amount = Integer.parseInt(expressionParts[1]);
					if (values.containsKey(var)) {
						int varValue = values.get(var);
						values.put(wire,  varValue >> amount);
					}
					
				} else if (expression.contains("AND")) {
					String[] expressionParts = expression.split(" AND ");
					String var1 = expressionParts[0];
					String var2 = expressionParts[1];
					if ((isInteger(var1) || values.containsKey(var1)) && (isInteger(var2) || values.containsKey(var2))) {
						int var1Value = isInteger(var1) ? Integer.parseInt(var1) : values.get(var1);
						int var2Value = isInteger(var2) ? Integer.parseInt(var2) : values.get(var2);
						values.put(wire, var1Value & var2Value);
					}
					
				} else if (expression.contains("OR")) {
					String[] expressionParts = expression.split(" OR ");
					String var1 = expressionParts[0];
					String var2 = expressionParts[1];
					if ((isInteger(var1) || values.containsKey(var1)) && (isInteger(var2) || values.containsKey(var2))) {
						int var1Value = isInteger(var1) ? Integer.parseInt(var1) : values.get(var1);
						int var2Value = isInteger(var2) ? Integer.parseInt(var2) : values.get(var2);
						values.put(wire, var1Value | var2Value);
					}
					
				} else if (expression.contains("NOT")) {
					String var = expression.replace("NOT ", "");
					if (values.containsKey(var)) {
						values.put(wire, 65536 + ~values.get(var));
					}
					
				} else {
					if (isInteger(expression)) {
						values.put(wire, Integer.parseInt(expression));
					} else if (values.containsKey(expression)) {
						values.put(wire, values.get(expression));
					}
				}
			}
			
			if (index < lines.size() - 1) {
				index++;
			} else {
				index = 0;
			}
		}
		return values.get("a");
	}
	
	private boolean isInteger(String str) {
		for (char ch : str.toCharArray()) {
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		return true;
	}
}