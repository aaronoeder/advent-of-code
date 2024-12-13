package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.List;

public class Day25 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		long cardPublicKey = Long.parseLong(lines.get(0));
		
		long doorPublicKey = Long.parseLong(lines.get(1));
		int doorLoopSize = getLoopSize(doorPublicKey);
		
		return transformSubjectNumber(cardPublicKey, doorLoopSize);
	}
	
	private int getLoopSize(long publicKey) {
		int n = 1;
		int i = 0;
		while (true) {
			n *= 7;
			n %= 20201227;
			i++;
			if (n == publicKey) {
				return i; 
			}
		}
	}
	
	private long transformSubjectNumber(long subjectNumber, int loopSize) {
		long n = 1;
		for (int i = 0; i < loopSize; i++) {
			n *= subjectNumber;
			n %= 20201227;
		}
		return n;
	}
}