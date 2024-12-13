package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day09 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			line = getCleanedLine(line);
			sum += getGroups(line, 1);
			log(line + " " + getGroups(line, 1));
		}
		return sum;
	}

	private String getCleanedLine(String line) {
		String cleanedLine = "";
		int index = 0;
		while (index < line.length()) {
			char ch = line.charAt(index);
			if (ch != '!') {
				cleanedLine += ch;
			} else {
				index++;
			}
			index++;
		}

		String moreCleanedLine = "";
		boolean isGarbageActive = false;
		for (int i = 0; i < cleanedLine.length(); i++) {
			char ch = cleanedLine.charAt(i);
			if (ch == '<') {
				isGarbageActive = true;
				moreCleanedLine += ch;
			} else if (ch == '>') {
				isGarbageActive = false;
				moreCleanedLine += ch;
			} else {
				moreCleanedLine += (isGarbageActive ? "." : ch);
			}
		}
		return moreCleanedLine;
	}

	private int getGroups(String s, int depth) {
		if (s.equals("{}")) {
			return depth;
		}
		String rem = s.substring(1, s.length() - 1);
		if (rem.startsWith("<") && rem.endsWith(">")) {
			return depth;
		}
		if (rem.startsWith("{{")) {
			return depth + getGroups(rem, depth + 1);
		}
		int groups = depth;
		String[] parts = s.substring(1, s.length() - 1).split(",");
		for (String part: parts) {
			groups += getGroups(part, depth + 1);
		}
		return groups;
	}
}