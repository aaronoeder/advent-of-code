package me.oeder.aoc.days2015;

import java.util.List;

import me.oeder.aoc.AdventDay;
import org.apache.commons.codec.digest.DigestUtils;

public class Day04 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int num = 0;
		while (true) {
			String hash = DigestUtils.md5Hex(lines.get(0) + num);
			if (hash.startsWith(part == Part.ONE ? "00000" : "000000")) {
				return num;
			}
			num++;
		}
	}
}