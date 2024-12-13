package me.oeder.aoc.days2022;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day25 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		long sum = 0;
		for (String line : lines) {
			sum += convertSnafuToDecimal(line);
		}
		return convertDecimalToSnafu(sum);
	}
	
	private long convertSnafuToDecimal(String snafu) {
		long decimal = 0;
		int pow = 0;
		for (int i = snafu.length() - 1; i >= 0; i--) {
			String s = snafu.substring(i, i + 1);
			int val = 0;
			if (s.equals("-")) {
				val = -1;
			} else if (s.equals("=")) {
				val = -2;
			} else {
				val = Integer.parseInt(s);
			}
			decimal += val * Math.pow(5, pow);
			pow++;
		}
		return decimal;
	}
	
	private String convertDecimalToSnafu(long decimal) {
		long mod = decimal % 5;
		long div = decimal / 5;
		
		if (decimal == 0) {
			return "";
		}
		
		if (mod == 3) {
			return convertDecimalToSnafu(div + 1) + "=";
		} else if (mod == 4) {
			return convertDecimalToSnafu(div + 1) + "-";
		} else {
			return convertDecimalToSnafu(div) + mod;
		}
	}
}