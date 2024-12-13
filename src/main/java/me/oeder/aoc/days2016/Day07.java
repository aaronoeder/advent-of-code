package me.oeder.aoc.days2016;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day07 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			List<String> outside = new ArrayList<>();
			List<String> inside = new ArrayList<>();
			String cur = "";
			for (int i = 0; i < line.length(); i++) {
				String ch = line.substring(i, i + 1);
				if (ch.equals("[")) {
					outside.add(cur);
					cur = "";
				} else if (ch.equals("]")) {
					inside.add(cur);
					cur = "";
				} else {
					cur += ch;
				}
			}
			outside.add(cur);
			
			boolean isTls = false;
			if (part == Part.ONE) { 
				for (String out : outside) {
					if (hasAbba(out)) {
						isTls = true;
						break;
					}
				}
				if (isTls) {
					for (String in : inside) {
						if (hasAbba(in)) {
							isTls = false;
							break;
						}
					}
				}
			} else {
				for (String out : outside) {
					List<String> abas = getAbas(out);
					for (String aba : abas) {
						String bab = getBab(aba);
						for (String in : inside) {
							if (in.contains(bab)) {
								isTls = true;
							}
						}
					}
				}
			}
			
			if (isTls) {
				sum++;
			}
		}
		return sum;
	}
	
	private boolean hasAbba(String str) {
		for (int i = 0; i <= str.length() - 4; i++) {
			String c1 = str.substring(i, i + 1);
			String c2 = str.substring(i + 1, i + 2);
			String c3 = str.substring(i + 2, i + 3);
			String c4 = str.substring(i + 3, i + 4);
			if (c1.equals(c4) && c2.equals(c3) && !c1.equals(c2)) {
				return true;
			}
		}
		return false;
	}
	
	private List<String> getAbas(String str) {
		List<String> abas = new ArrayList<>();
		for (int i = 0; i <= str.length() - 3; i++) {
			String c1 = str.substring(i, i + 1);
			String c2 = str.substring(i + 1, i + 2);
			String c3 = str.substring(i + 2, i + 3);
			if (c1.equals(c3) && !c1.equals(c2)) {
				abas.add(c1 + c2 + c3); 
			}
		}
		return abas;
	}
	
	private String getBab(String aba) {
		return aba.substring(1, 2) + aba.substring(0, 1) + aba.substring(1, 2);
	}
}