package me.oeder.aoc.days2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day11 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Monkey> monkeys = new ArrayList<>();
		for (int i = 0; i < lines.size(); i += 7) {
			List<Long> items = Arrays.asList(lines.get(i + 1).split("  Starting items: ")[1].split(", ")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
			
			Function<Long, Long> operation = null;
			String[] parts = lines.get(i + 2).split("  Operation: new = old ")[1].split(" ");
			String operator = parts[0];
			String operand = parts[1];
			
			if (operator.equals("+")) {
				if (operand.equals("old")) {
					operation = old -> old + old;
				} else {
					operation = old -> old + Integer.parseInt(operand);
				}
			} else if (operator.equals("-")) {
				if (operand.equals("old")) {
					operation = old -> old - old;
				} else {
					operation = old -> old - Integer.parseInt(operand);
				}				
			} else if (operator.equals("*")) {
				if (operand.equals("old")) {
					operation = old -> old * old;
				} else {
					operation = old -> old * Integer.parseInt(operand);
				}				
			} else if (operator.equals("/")) {
				if (operand.equals("old")) {
					operation = old -> old / old;
				} else {
					operation = old -> old / Integer.parseInt(operand);
				}				
			}
			
			int divisibility = Integer.parseInt(lines.get(i + 3).split("  Test: divisible by ")[1]);
			int trueMonkey = Integer.parseInt(lines.get(i + 4).split("    If true: throw to monkey ")[1]);
			int falseMonkey = Integer.parseInt(lines.get(i + 5).split("    If false: throw to monkey ")[1]);
			
			monkeys.add(new Monkey(items, operation, divisibility, trueMonkey, falseMonkey, 0));
		}
		
		if (part == Part.ONE) { 
			for (int i = 0; i < 20; i++) {
				for (Monkey monkey : monkeys) {
					for (long item : monkey.getItems()) {
						long worry = monkey.getOperation().apply(item);
						worry /= 3;
						if (worry % monkey.getDivisibility() == 0) {
							monkeys.get(monkey.getTrueMonkey()).getItems().add(worry);
						} else {
							monkeys.get(monkey.getFalseMonkey()).getItems().add(worry);
						}
						monkey.setInspectionCount(monkey.getInspectionCount() + 1);
					}
					monkey.getItems().clear();
				}
			}
		} else {
			int combinedModulo = lcm(monkeys.stream().map(m -> m.getDivisibility()).collect(Collectors.toList()));
			
			for (int i = 0; i < 10000; i++) {
				for (Monkey monkey : monkeys) {
					for (long item : monkey.getItems()) {
						long worry = monkey.getOperation().apply(item);
						if (worry % monkey.getDivisibility() == 0) {
							worry = worry % combinedModulo;
							monkeys.get(monkey.getTrueMonkey()).getItems().add(worry);
						} else {
							worry = worry % combinedModulo;
							monkeys.get(monkey.getFalseMonkey()).getItems().add(worry);
						}
						monkey.setInspectionCount(monkey.getInspectionCount() + 1);
					}
					monkey.getItems().clear();
				}
			}
		}
		
		List<Integer> monkeyInspectionCounts = monkeys.stream().map(m -> m.getInspectionCount()).sorted((a, b) -> b - a).collect(Collectors.toList());
		
		return (long)monkeyInspectionCounts.get(0) * (long)monkeyInspectionCounts.get(1);
	}
	
	@Data
	@AllArgsConstructor
	private class Monkey {
		private List<Long> items;
		private Function<Long, Long> operation;
		private int divisibility;
		private int trueMonkey;
		private int falseMonkey;
		private int inspectionCount;
	}
	
	private int lcm(List<Integer> numbers) {
	    return numbers.stream().reduce(1, (x, y) -> x * (y / gcd(x, y)));
	}
	
	private int gcd(int x, int y) {
	    return (y == 0) ? x : gcd(y, x % y);
	}
}