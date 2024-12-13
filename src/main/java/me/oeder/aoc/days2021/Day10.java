package me.oeder.aoc.days2021;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day10 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<Character, Character> pairings = new HashMap<>();
		pairings.put('(', ')');
		pairings.put('[', ']');
		pairings.put('{', '}');
		pairings.put('<', '>');
		
		Map<Character, Integer> points = new HashMap<>();
		points.put('(', 1);
		points.put('[', 2);
		points.put('{', 3);
		points.put('<', 4);
		points.put(')', 3);
		points.put(']', 57);
		points.put('}', 1197);
		points.put('>', 25137);
		
		long score = 0;
		List<Long> lineScores = new ArrayList<>();
		for (String line : lines) {
			boolean corrupted = false;
			Stack<Character> stack = new Stack<>();
			for (char c : line.toCharArray()) {
				if (pairings.containsKey(c)) {
					stack.push(c);
				} else {
					char last = stack.pop();
					if (pairings.get(last) != c) {
						corrupted = true;
						if (part == Part.ONE) { 
							score += points.get(c);
						}
					}
				}
			}
			
			if (!corrupted && part == Part.TWO) {
				long lineScore = 0;
				while (!stack.isEmpty()) {
					lineScore *= 5;
					lineScore += points.get(stack.pop());
				}
				lineScores.add(lineScore);
			}
		}
		
		if (part == Part.TWO) {
			Collections.sort(lineScores);
			score = lineScores.get(lineScores.size() / 2);
		}
		
		return score;
	}
}