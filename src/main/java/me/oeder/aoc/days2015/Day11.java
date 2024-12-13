package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day11 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String cur = lines.get(0);
		String next = getNextValidPassword(cur);
		if (part == Part.ONE) { 
			return next;
		} else {
			return getNextValidPassword(next);
		}
	}
	
	private String getNextValidPassword(String cur) {
		String next = getNextPassword(cur);
		while (!isPasswordValid(next)) {
			next = getNextPassword(next);
		}
		
		return next;
	}
	
	private String getNextPassword(String cur) {
		int pos = cur.length() - 1;
		String next = "";
		boolean incr = false;
		while (next.length() < cur.length()) {
			char ch = cur.charAt(pos);
			String nextCh = "";
			if (!incr) {
				if (ch < 'z') {
					nextCh = String.valueOf((char)(ch + 1));
					incr = true;
				} else {
					nextCh = "a";
				}
			} else {
				nextCh = String.valueOf(ch);
			}
			next = nextCh + next;
			pos--;
		}
		return next;
	}
	
	private boolean isPasswordValid(String pw) {
		if (pw.contains("i") || pw.contains("o") || pw.contains("l")) {
			return false;
		}
		
		// Straight of three
		boolean hasStraightOfThree = false;
		char[] chars = pw.toCharArray();
		for (int i = 0; i < chars.length - 2; i++) {
			char first = chars[i];
			char second = chars[i + 1];
			char third = chars[i + 2];
			if (first + 1 == second && second + 1 == third) {
				hasStraightOfThree = true;
				break;
			}
		}
		if (!hasStraightOfThree) {
			return false;
		}
		
		// Pair count
		int pairCount = 0;
		int index = 0;
		while (index < chars.length - 1) {
			char first = chars[index];
			char second = chars[index + 1];
			if (first == second) {
				pairCount++;
				index += 2;
			} else {
				index++;
			}
		}
		
		return pairCount >= 2;
	}
}