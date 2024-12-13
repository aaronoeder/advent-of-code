package me.oeder.aoc.days2015;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day10 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String seq = lines.get(0);
		for (int i = 0; i < (part == Part.ONE ? 40 : 50); i++) {
			log(i);
			String newSeq = "";
			char[] seqChars = seq.toCharArray();
			int seqIndex = 0;
			while (seqIndex < seqChars.length) {
				int count = 1;
				char ch = seqChars[seqIndex];
				if (seqIndex < seqChars.length - 1) {
					char nextCh = seqChars[seqIndex + 1];
					int nextSeqIndex = seqIndex + 1;
					while (nextCh == ch) {
						count++;
						nextSeqIndex++;
						if (nextSeqIndex == seqChars.length) {
							break;
						}
						nextCh = seqChars[nextSeqIndex];
					}
					seqIndex = nextSeqIndex;
				} else {
					seqIndex++;
				}
				newSeq += (String.valueOf(count) + ch);
			}
			seq = newSeq;
		}
		return seq.length();
	}	
}