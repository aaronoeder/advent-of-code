package me.oeder.aoc.days2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day07 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String order = "";
		Map<String, List<String>> dependencies = new HashMap<>();
		
		List<Instruction> instructions = new ArrayList<>();
		Pattern pattern = Pattern.compile("Step (\\w+) must be finished before step (\\w+) can begin.");
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				instructions.add(new Instruction(matcher.group(1), matcher.group(2)));
			}
		}
		
		Set<String> steps = instructions.stream().map(i -> Arrays.asList(i.getStep(), i.getDependency())).flatMap(o -> o.stream()).collect(Collectors.toSet());
		for (String step : steps) {
			dependencies.put(step, new ArrayList<>());
		}
		
		for (Instruction instruction : instructions) {
			dependencies.get(instruction.getStep()).add(instruction.getDependency());
		}
		
		int seconds = 0;
		int workerCount = 5;
		List<Worker> workers = new ArrayList<>();
		
		while (order.length() < steps.size()) {
			if (part == Part.TWO) {
				for (Worker worker : workers) {
					if (worker.isActive()) {
						worker.setSecondsRemaining(worker.getSecondsRemaining() - 1);
					}
					if (worker.getSecondsRemaining() == 0) {
						order += worker.getStep();
						worker.setStep("");
						worker.setActive(false);
					}
				}
			}
			
			List<String> candidates = new ArrayList<>();
			for (Map.Entry<String, List<String>> entry : dependencies.entrySet()) {
				String step = entry.getKey();
				List<String> dependentSteps = entry.getValue();
				
				if (order.contains(step)) {
					continue;
				}
				
				boolean valid = true;
				for (String dependentStep : dependentSteps) {
					if (!order.contains(dependentStep)) {
						valid = false;
					}
				}
				
				if (valid) {
					candidates.add(step);
				}
			}
			
			Collections.sort(candidates);
			
			if (part == Part.ONE) { 
				order += candidates.get(0);
			} else if (part == Part.TWO) {
				for (String candidate : candidates) {
					Worker worker = findAvailableWorker(candidate, workers, workerCount);
					if (worker != null) {
						worker.setStep(candidate);
						worker.setSecondsRemaining(60 + 1 + (candidate.charAt(0) - 'A'));
						worker.setActive(true);
					}
				}
			}
			
			seconds++;
		}
		
		if (part == Part.ONE) { 
			return order;
		} else {
			return seconds - 1;
		}
	}
	
	private Worker findAvailableWorker(String candidate, List<Worker> workers, int workerCount) {
		for (Worker worker : workers) {
			if (worker.isActive() && worker.getStep().equals(candidate)) {
				return null;
			}
		}
		
		if (workers.size() < workerCount) {
			Worker worker = new Worker("", 0, false);
			workers.add(worker);
			return worker;
		} else {
			for (Worker worker : workers) {
				if (!worker.isActive()) {
					return worker;
				}
			}
		}
		return null;
	}
	
	@Data
	@AllArgsConstructor
	private class Worker {
		private String step;
		private int secondsRemaining;
		private boolean active;
	}
	
	@Data
	@AllArgsConstructor
	private class Instruction {
		private String dependency;
		private String step;
	}
}