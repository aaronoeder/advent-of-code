package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Stack<String>> stacks = new ArrayList<>();
		
		int dividerIndex = -1;
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).trim().equals("")) {
				dividerIndex = i;
			}
		}
		
		for (int i = dividerIndex - 2; i >= 0; i--) {
			String line = lines.get(i);
			
			for (int j = 0; j < line.length(); j += 4) {
				
				String elem = line.substring(j, j + 3).trim().replace("[", "").replace("]", "");
				if ("".equals(elem)) {
					continue;
				}
				
				int stackIndex = (j / 4);
				if (stacks.size() <= stackIndex) {
					stacks.add(new Stack<String>());
				}
				
				stacks.get(stackIndex).push(elem);
			}
		}
		
		for (int i = dividerIndex + 1; i < lines.size(); i++) {
			Pattern numberPattern = Pattern.compile("\\d+");
			Matcher numberMatcher = numberPattern.matcher(lines.get(i));
			
			numberMatcher.find();
			int quantity = Integer.parseInt(numberMatcher.group());
			numberMatcher.find();
			int originIndex = Integer.parseInt(numberMatcher.group()) - 1;
			numberMatcher.find();
			int destIndex = Integer.parseInt(numberMatcher.group()) - 1;
			
			if (part == Part.ONE) { 
				for (int j = 0; j < quantity; j++) {
					String elem = stacks.get(originIndex).pop();
					stacks.get(destIndex).push(elem);
				}
			} else {
				Stack<String> temp = new Stack<>();
				for (int j = 0; j < quantity; j++) {
					String elem = stacks.get(originIndex).pop();
					temp.push(elem);
				}
				
				while (!temp.isEmpty()) {
					stacks.get(destIndex).push(temp.pop());
				}
			}
		}
		
		String result = "";
		for (Stack<String> stack : stacks) {
			result += stack.pop();
		}
		
		return result;
	}
}