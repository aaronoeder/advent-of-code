package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day08 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int startCount = 0;
		int endCount = 0;
		for (String line : lines) {
			startCount += line.length();
			
			if (part == Part.ONE) { 
				int lineMemoryCharCount = 0;
				char[] lineChars = line.toCharArray();
				int index = 1;
				while (index < lineChars.length - 1) {
					char ch = lineChars[index];
					if (ch == '\\') {
						char nextCh = lineChars[index + 1];
						if (nextCh == '\\' || nextCh == '"') {
							lineMemoryCharCount++;
							index += 2;
						} else {
							lineMemoryCharCount++;
							index += 4;
						}
					} else {
						lineMemoryCharCount++;
						index++;
					}
				}
				endCount += lineMemoryCharCount;
			} else {
				endCount += encodeString(line).length();
			}
		}
		if (part == Part.ONE) { 
			return startCount - endCount;
		} else {
			return endCount - startCount;
		}
	}
	
	private String encodeString(String str) {
		return "\"" + str.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
	}
}