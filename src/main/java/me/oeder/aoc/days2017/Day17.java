package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day17 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int step = Integer.parseInt(lines.get(0));

		int currentPos = 0;

		if (part == Part.ONE) {
			List<Integer> vals = new ArrayList<>();
			vals.add(0);
			while (vals.size() <= 2017) {
				currentPos = (currentPos + step) % vals.size();

				List<Integer> before = new ArrayList<>(vals.subList(0, currentPos + 1));
				List<Integer> after = new ArrayList<>(vals.subList(currentPos + 1, vals.size()));

				List<Integer> newVals = new ArrayList<>();
				newVals.addAll(before);
				newVals.add(vals.size());
				newVals.addAll(after);
				vals = newVals;

				currentPos++;
			}

			int index = vals.indexOf(2017);
			return vals.get(index + 1);
		}

		int lastVal = 0;
		int size = 1;
		while (size <= 50_000_000) {
			currentPos = (currentPos + step) % size;
			if (currentPos == 0) {
				lastVal = size;
			}
			currentPos++;
			size++;
		}
		return lastVal;
	}
}