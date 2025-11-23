package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.MathUtils;

public class Day08 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String instructions = lines.get(0);
		
		Map<String, Node> nodeMap = new HashMap<>();
		Pattern nodePattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
		for (int i = 2; i < lines.size(); i++) {
			Matcher nodeMatcher = nodePattern.matcher(lines.get(i));
			if (nodeMatcher.matches()) {
				String name = nodeMatcher.group(1);
				String left = nodeMatcher.group(2);
				String right = nodeMatcher.group(3);
				nodeMap.put(name, new Node(name, left, right));
			}
		}
		
		String ending = (part == Part.ONE ? "AAA": "A");
		List<String> startingNodeNames = nodeMap.keySet().stream().filter(nodeName -> nodeName.endsWith(ending)).collect(Collectors.toList());
		
		Map<String, String> currentNodes = new HashMap<>();
		Map<String, Long> nodeCycleLengths = new HashMap<>();
		int instructionIndex = 0;
		long steps = 0;
		
		for (String startingNodeName : startingNodeNames) {
			currentNodes.put(startingNodeName, startingNodeName);
		}
		
		while (nodeCycleLengths.size() < currentNodes.size()) {
			for (String startingNode : startingNodeNames) {
				Node currentNode = nodeMap.get(currentNodes.get(startingNode));
				
				boolean left = instructions.substring(instructionIndex, instructionIndex + 1).equals("L");
				String next = left ? currentNode.getLeft() : currentNode.getRight();
				if (next.endsWith("Z")) {
					nodeCycleLengths.put(startingNode, steps + 1);
				}
				
				currentNodes.put(startingNode, next);
			}
			
			if (instructionIndex < instructions.length() - 1) {
				instructionIndex++;
			} else {
				instructionIndex = 0;
			}
			steps++;
		}
		return MathUtils.lcm(new ArrayList<>(nodeCycleLengths.values()));
	}
	
	@Data
	@AllArgsConstructor
	private class Node {
		private String name;
		private String left;
		private String right;
	}
}