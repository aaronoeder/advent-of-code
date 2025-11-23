package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.MathUtils;

public class Day20 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Map<String, Module> modules = new HashMap<>();
		modules.put("button", new ButtonModule());
		
		// Initialize modules
		for (String line : lines) {
			String[] moduleParts = line.split(" -> ");
			String moduleId = moduleParts[0];
			List<String> destinations = Arrays.asList(moduleParts[1].split(", "));
			
			if (moduleId.startsWith("broadcaster")) {
				modules.put(moduleId, new BroadcasterModule(destinations));
			} else if (moduleId.startsWith("%")) {
				modules.put(moduleId.substring(1), new FlipFlopModule(moduleId.substring(1), destinations));
			} else if (moduleId.startsWith("&")) {
				modules.put(moduleId.substring(1), new ConjunctionModule(moduleId.substring(1), destinations));
			}
		}
		modules.put("rx", new OutputModule("rx"));
		
		// For all conjunctions, initialize its map of recent pulses
		for (Module module : modules.values()) {
			if (module instanceof ConjunctionModule) {
				ConjunctionModule conjunctionModule = (ConjunctionModule)module;
				for (Module otherModule : modules.values()) {
					if (otherModule.getDestinations().contains(module.getName())) {
						conjunctionModule.getRecentPulses().put(otherModule.getName(), PulseType.LOW);
					}
				}
			}
		}
		
		Module conjunctionLeadingToOutput = modules.values().stream()
				.filter(m -> m.getDestinations().contains("rx"))
				.findFirst()
				.orElse(null);
		
		List<String> cycleNames = modules.values().stream()
				.filter(m -> m.getDestinations().contains(conjunctionLeadingToOutput.getName()))
				.map(m -> m.getName())
				.collect(Collectors.toList());
		
		Map<String, Long> cycleLengths = new HashMap<>();

		long lowPulseCount = 0;
		long highPulseCount = 0;
		long buttonPresses = 1;
		boolean loop = true;
		while (loop) {
			Queue<Module> queue = new LinkedList<>();
			queue.add(modules.get("button"));

			while (!queue.isEmpty()) {
				Module cur = queue.poll();
				
				PulseType pulseToSend = null;
				if (cur instanceof ButtonModule || cur instanceof BroadcasterModule) {
					pulseToSend = PulseType.LOW;
					
				} else if (cur instanceof FlipFlopModule) {
					FlipFlopModule flipFlopModule = (FlipFlopModule)cur;
					if (flipFlopModule.isOn()) {
						flipFlopModule.setOn(false);
						pulseToSend = PulseType.LOW;
					} else {
						flipFlopModule.setOn(true);
						pulseToSend = PulseType.HIGH;
					}
					
				} else if (cur instanceof ConjunctionModule) {
					ConjunctionModule conjunctionModule = (ConjunctionModule)cur;
					
					boolean allHighRecentPulses = conjunctionModule.getRecentPulses().values().stream()
							.filter(pulse -> pulse == PulseType.LOW)
							.count() == 0;
					
					pulseToSend = (allHighRecentPulses ? PulseType.LOW : PulseType.HIGH);
				}
				
				if (pulseToSend == PulseType.HIGH) {
					if (cycleNames.contains(cur.getName()) && !cycleLengths.containsKey(cur.getName())) {
						cycleLengths.put(cur.getName(), buttonPresses);
					}
				}
				
				for (String dest : cur.getDestinations()) {
					if (pulseToSend == PulseType.LOW) {
						lowPulseCount++;
					} else if (pulseToSend == PulseType.HIGH) {
						highPulseCount++;
					}
					
					Module destModule = modules.get(dest);
					if (destModule instanceof BroadcasterModule) {
						queue.add(destModule);
					} else if (destModule instanceof FlipFlopModule && pulseToSend == PulseType.LOW) {
						queue.add(destModule);
					} else if (destModule instanceof ConjunctionModule) {
						ConjunctionModule conjunctionModule = (ConjunctionModule)destModule;
						conjunctionModule.getRecentPulses().put(cur.getName(), pulseToSend);
						queue.add(destModule);
					}
				}
			}
			
			buttonPresses++;
			loop = (part == Part.ONE ? buttonPresses <= 1000 : cycleLengths.size() != cycleNames.size());
		}
		
		if (part == Part.ONE) {
			return lowPulseCount * highPulseCount;
		} else {
			return MathUtils.lcm(cycleLengths.values());
		}
	}
	
	@Data
	@AllArgsConstructor
	private abstract class Module {
		private String name;
		private List<String> destinations;
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	private class ButtonModule extends Module {
		public ButtonModule() {
			super("button", Arrays.asList("broadcaster"));
		}
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	private class BroadcasterModule extends Module {
		public BroadcasterModule(List<String> destinations) {
			super("broadcaster", destinations);
		}
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	private class FlipFlopModule extends Module {
		private boolean on;
		
		public FlipFlopModule(String name, List<String> destinations) {
			super(name, destinations);
		}
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	private class ConjunctionModule extends Module {
		private Map<String, PulseType> recentPulses;
		
		public ConjunctionModule(String name, List<String> destinations) {
			super(name, destinations);
			recentPulses = new HashMap<>();
		}
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	private class OutputModule extends Module {
		public OutputModule(String name) {
			super(name, new ArrayList<>());
		}
	}
	
	private enum PulseType {
		LOW, HIGH;
	}
}