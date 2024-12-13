package me.oeder.aoc.days2022;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day21 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Monkey> monkeyMap = new HashMap<>();
		Map<String, Monkey> calcMonkeyMap = new HashMap<>();
		for (String line : lines) {
			String name = line.split(": ")[0];
			Long number = null;
			String leftMonkey = null;
			String operation = null;
			String rightMonkey = null;
			
			if (hasMathOperation(line)) {
				String parts[] = line.split(": ")[1].split(" ");
				leftMonkey = parts[0];
				operation = parts[1];
				rightMonkey = parts[2];
			} else {
				number = Long.parseLong(line.split(": ")[1]);
			}
			
			Monkey monkey = new Monkey(name, number, leftMonkey, operation, rightMonkey);
			monkeyMap.put(name, monkey);
			if (leftMonkey != null) {
				calcMonkeyMap.put(leftMonkey, monkey);
			}
			if (rightMonkey != null) {
				calcMonkeyMap.put(rightMonkey, monkey);
			}
		}
		
		if (part == Part.ONE) {
			return getNumber(monkeyMap.get("root"), monkeyMap);
		} else {
			Monkey root = monkeyMap.get("root");
			Monkey rootLeft = monkeyMap.get(root.getLeftMonkey());
			Monkey rootRight = monkeyMap.get(root.getRightMonkey());
			
			List<Monkey> ancestors = Arrays.asList(rootLeft, rootRight);
			
			Monkey humanAncestor = null;
			long target = 0;
			for (Monkey ancestor : ancestors) {
				if (hasHuman(ancestor, monkeyMap)) {
					humanAncestor = ancestor;
				} else {
					target = getNumber(ancestor, monkeyMap);
				}
			}
			
			return getHumanValue(monkeyMap.get("humn"), humanAncestor, monkeyMap, calcMonkeyMap, target);
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Monkey {
		private String name;
		private Long number;
		private String leftMonkey;
		private String operation;
		private String rightMonkey;
	}
	
	private boolean hasMathOperation(String s) {
		for (String op : Arrays.asList("+", "-", "*", "/")) {
			if (s.contains(op)) {
				return true;
			}
		}
		return false;
	}
	
	private long getNumber(Monkey monkey, Map<String, Monkey> monkeyMap) {
		if (monkey.getNumber() != null) {
			return monkey.getNumber();
		}
		long leftNumber = getNumber(monkeyMap.get(monkey.getLeftMonkey()), monkeyMap);
		long rightNumber = getNumber(monkeyMap.get(monkey.getRightMonkey()), monkeyMap);
		
		switch (monkey.getOperation()) {
			case "+":
				return leftNumber + rightNumber;
			case "-":
				return leftNumber - rightNumber;			
			case "*":
				return leftNumber * rightNumber;		
			case "/":
				return leftNumber / rightNumber;			
			default:
				return -1;
		}
	}
	
	private boolean hasHuman(Monkey monkey, Map<String, Monkey> monkeyMap) {
		if (monkey.getName().equals("humn")) {
			return true;
		}
		boolean hasHumanLeft = monkey.getLeftMonkey() != null ? hasHuman(monkeyMap.get(monkey.getLeftMonkey()), monkeyMap) : false;
		boolean hasHumanRight = monkey.getRightMonkey() != null ? hasHuman(monkeyMap.get(monkey.getRightMonkey()), monkeyMap) : false;
		return hasHumanLeft || hasHumanRight;
	}
	
	private long getHumanValue(Monkey human, Monkey humanAncestor, Map<String, Monkey> monkeyMap, Map<String, Monkey> calcMonkeyMap, long target) {
		long humanValue = target;
		
		Stack<String> stack = new Stack<>();
		
		Monkey cur = human;
		while (!cur.equals(humanAncestor)) {
			Monkey next = calcMonkeyMap.get(cur.getName());
			
			boolean reverse = false;
			
			Monkey otherMonkeyInOperation = null;
			if (next.getLeftMonkey().equals(cur.getName())) {
				otherMonkeyInOperation = monkeyMap.get(next.getRightMonkey());

			} else if (next.getRightMonkey().equals(cur.getName())) {
				otherMonkeyInOperation = monkeyMap.get(next.getLeftMonkey());
				if (next.getOperation().equals("-") || next.getOperation().equals("/")) {
					reverse = true;
				}
			}
			
			long number = otherMonkeyInOperation.getNumber() != null ? otherMonkeyInOperation.getNumber() : getNumber(otherMonkeyInOperation, monkeyMap);			
			
			stack.push(String.valueOf(number));
			stack.push(next.getOperation() + (reverse ? "R" : ""));
			cur = next;
		}
		
		while (!stack.isEmpty()) {
			boolean reverse = false;
			String operator = stack.pop();
			if (operator.endsWith("R")) {
				reverse = true;
				operator = operator.replace("R", "");
			}
			Long operand = Long.parseLong(stack.pop());
			if (reverse && operator.equals("-")) {
				humanValue *= -1;
			} else if (reverse && operator.equals("/")) {
				humanValue = 1 / humanValue;
			}
			humanValue = performInverseOperation(humanValue, operator, operand);
		}
		
		return humanValue;
	}
	
	private long performInverseOperation(long num, String operator, long operand) {
		switch (operator) {
			case "+":
				return num -= operand;
			case "-":
				return num += operand;
			case "*":
				return num /= operand;
			case "/":
				return num *= operand;
			default:
				return -1;
		}
	}
}