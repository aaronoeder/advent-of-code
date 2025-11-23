package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aaronoeder.adventofcode.AdventDay;

public class Day15 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		Map<Integer, List<String>> boxes = new HashMap<>();
		String[] steps = lines.get(0).split(",");
		for (String step : steps) {
			String valueToHash = step;
			if (part == Part.TWO) {
				if (step.contains("-")) {
					valueToHash = step.split("-")[0];
				} else if (step.contains("=")) {
					valueToHash = step.split("=")[0];
				}
			}
			
			int hash = 0;
			for (char c : valueToHash.toCharArray()) {
				hash += (int)c;
				hash *= 17;
				hash %= 256;
			}
			
			if (part == Part.ONE) {
				sum += hash;
			} else if (part == Part.TWO) {
				if (step.contains("-")) {
					if (!boxes.containsKey(hash)) {
						continue;
					}
					List<String> boxContents = boxes.get(hash);
					for (String content : boxContents) {
						if (content.startsWith(valueToHash)) {
							boxContents.remove(content);
							break;
						}
					}
				} else if (step.contains("=")) {
					if (!boxes.containsKey(hash)) {
						boxes.put(hash, new ArrayList<>());
					}
					boolean found = false;
					List<String> boxContents = boxes.get(hash);
					for (int i = 0; i < boxContents.size(); i++) {
						String content = boxContents.get(i);
						if (content.startsWith(valueToHash)) {
							found = true;
							boxContents.set(i, step);
						}
					}
					if (!found) {
						boxContents.add(step);
					}
				}				
			}
		}
		
		if (part == Part.TWO) {
			for (Map.Entry<Integer, List<String>> entry : boxes.entrySet()) {
				int boxId = entry.getKey() + 1;
				for (int i = 0; i < entry.getValue().size(); i++) {
					int slotId = i + 1;
					int focalLength = Integer.parseInt(entry.getValue().get(i).split("=")[1]);
					sum += boxId * slotId * focalLength;
				}
			}
		}
		return sum;
	}
}