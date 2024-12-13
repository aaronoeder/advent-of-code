package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.List;
import java.util.regex.Pattern;

public class Day23 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {

		int a = 1;
		int b = 0;
		int index = 0;
		while (index < lines.size()) {
			String line = lines.get(index).replace(",", "");
			log(line);
			String[] parts = line.split(" " );
			if (line.startsWith("hlf")) {
				if (parts[1].equals("a")) {
					a /= 2;
				} else {
					b /= 2;
				}
				index++;
			} else if (line.startsWith("tpl")) {
				if (parts[1].equals("a")) {
					a *= 3;
				} else {
					b *= 3;
				}
				index++;
			} else if (line.startsWith("inc")) {
				if (parts[1].equals("a")) {
					a++;
				} else {
					b++;
				}
				index++;
			} else if (line.startsWith("jmp")) {
				index += Integer.parseInt(parts[1]);
			} else if (line.startsWith("jie")) {
				int offset = Integer.parseInt(parts[2]);
				if (parts[1].equals("a") && a % 2 == 0) {
					index += offset;
				} else if (parts[1].equals("b") && b % 2 == 0) {
					index += offset;
				} else {
					index++;
				}
			} else if (line.startsWith("jio")) {
				int offset = Integer.parseInt(parts[2]);
				if (parts[1].equals("a") && a == 1) {
					index += offset;
				} else if (parts[1].equals("b") && b == 1) {
					index += offset;
				} else {
					index++;
				}
			}
		}

		return b;
	}
}