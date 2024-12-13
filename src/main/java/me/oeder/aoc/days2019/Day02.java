package me.oeder.aoc.days2019;

import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			return getResult(lines, 12, 2);
		}
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				int result = getResult(lines, i, j);
				if (result == 19690720) {
					return 100 * i + j;
				}
			}
		}
		return -1;
	}

	private int getResult(List<String> lines, int first, int second) {
		List<Integer> nums = Arrays.asList(lines.get(0).split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		nums.set(1, first);
		nums.set(2, second);

		int index = 0;
		while (true) {
			int val = nums.get(index);
			int opCode = val;
			int input1 = nums.get(nums.get(index + 1));
			int input2 = nums.get(nums.get(index + 2));
			int outputIndex = nums.get(index + 3);
			int outputValue = -1;
			if (opCode == 1) {
				outputValue = input1 + input2;
			} else if (opCode == 2) {
				outputValue = input1 * input2;
			}
			nums.set(outputIndex, outputValue);
			index += 4;
			if (nums.get(index) == 99) {
				break;
			}
		}
		return nums.get(0);
	}
}