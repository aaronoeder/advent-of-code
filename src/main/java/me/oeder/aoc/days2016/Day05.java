package me.oeder.aoc.days2016;

import java.util.List;

import me.oeder.aoc.AdventDay;
import org.apache.commons.codec.digest.DigestUtils;

public class Day05 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String doorId = lines.get(0);
		int index = 0;
		String password = "********";
		while (password.contains("*")) {
			String hash = DigestUtils.md5Hex(doorId + index);
			if (hash.startsWith("00000")) {
				if (part == Part.ONE) {
					password = password.replaceFirst("\\*", hash.substring(5, 6));
				} else {
					char posChar = hash.charAt(5);
					if (Character.isDigit(posChar)) {
						int pos = Integer.parseInt(hash.substring(5, 6));
						String ch = hash.substring(6, 7);
						if (pos < password.length() && password.substring(pos, pos + 1).equals("*")) {
							password = password.substring(0, pos) + ch + password.substring(pos + 1);
						}
					}
				}
			}
			index++;
		}
		return password;
	}
}