package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Long> nums = lines.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
		
		long invalidNum = -1;
		int len = 25;
		for (int i = len; i < nums.size(); i++) {
			long num = nums.get(i);
			boolean valid = false;
			for (int j = i - len; j < i; j++) {
				for (int k = j + 1; k < i; k++) {
					if (nums.get(j) + nums.get(k) == num) {
						valid = true;
					}
				}
			}
			if (!valid) {
				invalidNum = num;
				break;
			}
		}
		
		if (part == Part.ONE) { 
			return invalidNum;
		} else {
			for (int i = 0; i < nums.size(); i++) {
				int sum = 0;
				List<Long> numsInSum = new ArrayList<>();
				int index = i;

				while (index < nums.size() && sum < invalidNum) {
					long num = nums.get(index);
					sum += num;
					numsInSum.add(num);
					index++;
				}
				if (sum == invalidNum) {
					Collections.sort(numsInSum);
					return numsInSum.get(0) + numsInSum.get(numsInSum.size() - 1);
				}
			}
			return -1;
		}
	}
}