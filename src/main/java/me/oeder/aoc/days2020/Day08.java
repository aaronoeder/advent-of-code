package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) { 
			int index = 0;
			int acc = 0;
			Set<Integer> visitedIndices = new HashSet<>();
			while (!visitedIndices.contains(index)) {
				visitedIndices.add(index);
				
				String line = lines.get(index);
				String[] parts = line.split(" ");
				
				String instruction = parts[0];
				int value = Integer.parseInt(parts[1]);
				
				if (instruction.equals("nop")) {
					index++;
				} else if (instruction.equals("acc")) {
					acc += value;
					index++;
				} else if (instruction.equals("jmp")) {
					index += value;
				}
			}
			return acc;
		} else {
			
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).startsWith("acc")) {
					continue;
				}
				
				int modIndex = i;
				
				int index = 0;
				int acc = 0;
				Set<Integer> visitedIndices = new HashSet<>();
				while (index < lines.size() && !visitedIndices.contains(index)) {
					visitedIndices.add(index);
					
					String line = lines.get(index);
					String[] parts = line.split(" ");
					
					String instruction = parts[0];
					
					if (index == modIndex) {
						instruction = (instruction.equals("nop") ? "jmp" : "nop");
					}
					
					int value = Integer.parseInt(parts[1]);
					
					if (instruction.equals("nop")) {
						index++;
					} else if (instruction.equals("acc")) {
						acc += value;
						index++;
					} else if (instruction.equals("jmp")) {
						index += value;
					}
				}
				
				if (!visitedIndices.contains(index)) {
					return acc;
				}
			}
			
			return -1;
		}
	}
}