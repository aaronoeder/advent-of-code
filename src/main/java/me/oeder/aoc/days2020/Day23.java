package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day23 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> cups = Arrays.asList(lines.get(0).split("")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());

		if (part == Part.ONE) { 
			applyMoves(cups, 100);
			return getLabelOrder(cups);
		} else {
//			for (int i = 6; i < 1000; i++) {
//				cups.add(i);
//			}
			
			List<Integer> deepCopyOfCups = getDeepCopyOfCups(cups);
			int cycleLength = applyMoves(cups, 10000);
			log(cycleLength);
			
			applyMoves(deepCopyOfCups, 10000000 % cycleLength);
			
			return getLabelOrder(deepCopyOfCups);
		}
	}
	
	private int applyMoves(List<Integer> cups, int moves) {
		Map<Integer, Integer> history = new HashMap<>();
		int cycleLength = -1;
		
		int curCup = cups.get(0);
		for (int move = 1; move <= moves; move++) {
			List<Integer> selectedCups = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				int selectedCupIndex = cups.indexOf(curCup) + 1;
				if (selectedCupIndex == cups.size()) {
					selectedCupIndex = 0;
				}
				selectedCups.add(cups.remove(selectedCupIndex));
			}
			
			int destCup = curCup - 1;
			if (destCup == 0) {
				destCup = 9;
			}
			while (selectedCups.contains(destCup)) {
				destCup--;
				if (destCup == 0) {
					destCup = 9;
				}
			}

			int destIndex = cups.indexOf(destCup);
			
			cups.addAll(destIndex + 1, selectedCups);
			
			int curCupIndex = cups.indexOf(curCup);
			if (curCupIndex < cups.size() - 1) {
				curCup = cups.get(curCupIndex + 1);
			} else {
				curCup = cups.get(0);
			}
			
			int hashCode = cups.hashCode();
			if (history.containsKey(hashCode)) {
				cycleLength = move - history.get(hashCode);
			}
			
			history.put(hashCode, move);
		}
		
		return cycleLength;
	}
	
	private String getLabelOrder(List<Integer> cups) {
		String labelOrder = "";
		int oneIndex = cups.indexOf(1);
		for (int i = 1; i < cups.size(); i++) {			
			int index = (oneIndex + i) % cups.size();
			labelOrder += cups.get(index);
		}

		return labelOrder;
	}
	
	private List<Integer> getDeepCopyOfCups(List<Integer> cups) {
		List<Integer> deepCopy = new ArrayList<>();
		deepCopy.addAll(cups);
		return deepCopy;
	}
}