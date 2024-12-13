package me.oeder.aoc.days2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day07 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Program> programs = new HashMap<>();
		for (String line : lines) {
			String[] parts = line.split(" -> " );
			String[] frontParts = parts[0].split(" ");
			
			String name = frontParts[0];
			int weight = Integer.parseInt(frontParts[1].replace("(", "").replace(")", ""));
			
			List<String> children = new ArrayList<>();
			if (parts.length > 1) {
				children.addAll(Arrays.asList(parts[1].split(", ")));
			}
			
			programs.put(name, new Program(weight, children));
		}
		
		Set<String> childProgramNames = new HashSet<>();
		for (Program program : programs.values()) {
			childProgramNames.addAll(program.getChildren());
		}
		
		String rootProgramName = "";
		for (String programName : programs.keySet()) {
			if (!childProgramNames.contains(programName)) {
				rootProgramName = programName;
			}
		}
		
		return rootProgramName;
	}
	
	@Data
	@AllArgsConstructor
	private class Program {
		private int weight;
		private List<String> children;
	}
}