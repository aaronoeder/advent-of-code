package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Day18 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		long sum = 0;
		for (String line : lines) {
			sum += getValue(line, part);
		}
		return sum;
	}
	
	private long getValue(String line, Part part) {		
		// Recurse with parens
		String flattenedLine = "";
		int openParenCount = 0;
		String recurseLine = "";
		for (int i = 0; i < line.length(); i++) {
			String val = line.substring(i, i + 1);
			if (val.equals("(")) {
				if (openParenCount > 0) {
					recurseLine += val;
				}
				openParenCount++;
			} else if (val.equals(")")) {
				if (openParenCount > 0) {
					recurseLine += val;
				}
				openParenCount--;
				if (openParenCount == 0) {
					flattenedLine += getValue(recurseLine, part);
					recurseLine = "";
				}
			} else {
				if (openParenCount > 0) {
					recurseLine += val;
				} else {
					flattenedLine += val;
				}
			}
		}

		String[] elems = flattenedLine.split(" ");		
		List<String> newElems = Arrays.asList(elems);
		
		if (part == Part.TWO) {
			newElems = new ArrayList<>();
			boolean isFutureAdd = false;
			for (String elem : elems) {
				if (elem.equals("+")) {
					isFutureAdd = true;
				} else {
					if (isFutureAdd) {
						long prev = Long.parseLong(newElems.get(newElems.size() - 1));
						newElems.set(newElems.size() - 1, "" + (prev + Long.parseLong(elem)));
						isFutureAdd = false;
					} else {
						newElems.add(elem);
					}
				}
			}
		}
		
		// Reverse and put on stack
		Stack<String> stack = new Stack<>();
		for (int i = newElems.size() - 1; i >= 0; i--) {
			stack.push(newElems.get(i));
		}
		
		long val = 0;
		String op = null;
		while (!stack.isEmpty()) {
			String elem = stack.pop();
			if (isInteger(elem)) {
				long elemVal = Long.parseLong(elem);
				if (op == null || op.equals("+")) {
					val += elemVal;
				} else if (op.equals("*")) {
					val *= elemVal;
				}
			} else {
				op = elem;
			}
		}
		return val;
	}
	
	private boolean isInteger(String s) {
		for (char ch : s.toCharArray()) {
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		return true;
	}
}