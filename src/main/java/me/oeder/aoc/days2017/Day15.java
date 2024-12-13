package me.oeder.aoc.days2017;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.List;

public class Day15 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Generator a = new Generator(
			Long.parseLong(lines.get(0).replace("Generator A starts with ", "")),
			16807,
			4
		);

		Generator b = new Generator(
			Long.parseLong(lines.get(1).replace("Generator B starts with ", "")),
			48271,
			8
		);

		List<Generator> generators = Arrays.asList(a, b);

		int matches = 0;
		for (int i = 0; i < (part == Part.ONE ? 40_000_000 : 5_000_000); i++) {
			for (Generator generator : generators) {
				generator.setValue((generator.getValue() * generator.getFactor()) % 2147483647);
				if (part == Part.TWO) {
					while (generator.getValue() % generator.getModulo() != 0) {
						generator.setValue((generator.getValue() * generator.getFactor()) % 2147483647);
					}
				}
			}

			if (generators.get(0).getLast16Bits() == generators.get(1).getLast16Bits()) {
				matches++;
			}
		}

		return matches;
	}

	@Data
	@AllArgsConstructor
	private class Generator {
		private long value;
		private int factor;
		private int modulo;

		private long getLast16Bits() {
			return value % (long)Math.pow(2, 16);
		}
	}
}