package me.oeder.aoc.days2021;

import me.oeder.aoc.AdventDay;

import java.util.List;
import java.util.stream.Collectors;

public class Day03 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			String mostCommon = "";
			String leastCommon = "";
			for (int j = 0; j < lines.get(0).length(); j++) {
				mostCommon += getMostCommon(lines, j);
				leastCommon += getLeastCommon(lines, j);
			}
			return Integer.parseInt(mostCommon, 2) * Integer.parseInt(leastCommon, 2);
		} else {
			int oxygenIndex = 0;
			List<String> oxygenNumbers = lines;
			while (oxygenNumbers.size() > 1) {
				String mostCommon = getMostCommon(oxygenNumbers, oxygenIndex);
				
				final int temp = oxygenIndex;
				oxygenNumbers = oxygenNumbers.stream()
						.filter(num -> num.substring(temp, temp + 1).equals(mostCommon))
						.collect(Collectors.toList());
				
				oxygenIndex++;
			}
			
			int co2Index = 0;
			List<String> co2Numbers = lines;
			while (co2Numbers.size() > 1) {
				String leastCommon = getLeastCommon(co2Numbers, co2Index);
				
				final int temp = co2Index;
				co2Numbers = co2Numbers.stream()
						.filter(num -> num.substring(temp, temp + 1).equals(leastCommon))
						.collect(Collectors.toList());
				
				co2Index++;
			}
			
			return Integer.parseInt(oxygenNumbers.get(0), 2) * Integer.parseInt(co2Numbers.get(0), 2);
		}
	}
	
	private String getMostCommon(List<String> numbers, int index) {
		int zeroCount = 0;
		int oneCount = 0;
		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i).substring(index, index + 1).equals("0")) {
				zeroCount++;
			} else {
				oneCount++;
			}
		}
		if (zeroCount > oneCount) {
			return "0"; 
		} else {
			return "1";
		}
	}
	
	private String getLeastCommon(List<String> numbers, int index) {
		int zeroCount = 0;
		int oneCount = 0;
		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i).substring(index, index + 1).equals("0")) {
				zeroCount++;
			} else {
				oneCount++;
			}
		}
		if (oneCount < zeroCount) {
			return "1"; 
		} else {
			return "0";
		}
	}
}