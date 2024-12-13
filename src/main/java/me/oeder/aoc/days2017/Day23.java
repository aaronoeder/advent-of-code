package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Integer> values = new HashMap<>();
		values.put("a", part == Part.ONE ? 0 : 1);
		values.put("b", 0);
		values.put("c", 0);
		values.put("d", 0);
		values.put("e", 0);
		values.put("f", 0);
		values.put("g", 0);
		values.put("h", 0);

		int multCount = 0;
		int index = 0;
		while (index < lines.size()) {
			String line = lines.get(index);
			String[] parts = line.split(" ");

			String instruction = parts[0];
//			log(line);
			if (instruction.equals("set")) {
				String target = parts[1];
				String source = parts[2];
				if (Character.isLetter(source.charAt(0))) {
					values.put(target, values.get(source));
				} else {
					values.put(target, Integer.parseInt(source));
				}
				index++;
			} else if (instruction.equals("sub")) {
				String target = parts[1];
				String source = parts[2];
				if (Character.isLetter(source.charAt(0))) {
					values.put(target, values.get(target) - values.get(source));
				} else {
					values.put(target, values.get(target) - Integer.parseInt(source));
				}
				index++;
			} else if (instruction.equals("mul")) {
				String target = parts[1];
				String source = parts[2];
				if (Character.isLetter(source.charAt(0))) {
					values.put(target, values.get(target) * values.get(source));
				} else {
					values.put(target, values.get(target) * Integer.parseInt(source));
				}
				index++;
				multCount++;
			} else if (instruction.equals("jnz")) {
				String source = parts[1];
				int offset = Integer.parseInt(parts[2]);
				int sourceValue = 0;
				if (Character.isLetter(source.charAt(0))) {
					sourceValue = values.get(source);
				} else {
					sourceValue = Integer.parseInt(source);
				}
				if (sourceValue != 0) {
					index += offset;
				} else {
					index++;
				}
			}
		}

		if (part == Part.ONE) {
			return multCount;
		}
		return values.get("h");
	}
}