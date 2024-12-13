package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> numbers = new ArrayList<>();
		findNumbers(lines.get(0), numbers, part);
		return numbers.stream().reduce(0, (acc, cur) -> acc + cur);
	}
	
	private void findNumbers(String expression, List<Integer> numbers, Part part) {
		if (expression.startsWith("[") || expression.startsWith("{")) {
			for (String expressionPart : getExpressionParts(expression, part)) {
				findNumbers(expressionPart, numbers, part);
			}
		} else if (expression.startsWith("\"")) {
			if (expression.contains(":")) {
				findNumbers(expression.substring(expression.indexOf(":") + 1), numbers, part);
			}
		} else {
			numbers.add(Integer.parseInt(expression));
		}
	}
	
	private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("^\"(\\w+)\":\"(\\w+)\"$");
	
	private List<String> getExpressionParts(String expression, Part part) {
		boolean isObject = expression.startsWith("{");
		
		List<String> expressionParts = new ArrayList<>();
		char[] expressionChars = expression.substring(1, expression.length() - 1).toCharArray();
		int openCurlyBrackets = 0;
		int openSquareBrackets = 0;
		String expressionPart = "";
		for (char ch : expressionChars) {
			if (ch == ',' && openCurlyBrackets == 0 && openSquareBrackets == 0) {
				expressionParts.add(expressionPart);
				expressionPart = "";
			} else {
				if (ch == '{') {
					openCurlyBrackets++;
				} else if (ch == '}') {
					openCurlyBrackets--;
				} else if (ch == '[') {
					openSquareBrackets++;
				} else if (ch == ']') {
					openSquareBrackets--;
				}
				expressionPart += ch;
			}
		}
		expressionParts.add(expressionPart);
		
		if (isObject && part == Part.TWO) {
			for (String ePart : expressionParts) {
				Matcher ePartMatcher = KEY_VALUE_PATTERN.matcher(ePart);
				if (ePartMatcher.find() && ePartMatcher.group(2).equals("red")) {
					return new ArrayList<>();
				}
			}
		}
		
		return expressionParts;
	}
}