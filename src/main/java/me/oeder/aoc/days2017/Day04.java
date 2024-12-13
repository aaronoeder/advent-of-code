package me.oeder.aoc.days2017;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day04 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			List<String> words = Arrays.asList(line.split(" "));
			if (part == Part.ONE) { 
				Set<String> uniqueWords = new HashSet<>(words);
				if (words.size() == uniqueWords.size()) {
					sum++;
				}
			} else {
				boolean valid = true;
				for (int i = 0; i < words.size(); i++) {
					for (int j = i + 1; j < words.size(); j++) {
						String word1 = words.get(i);
						String word2 = words.get(j);
						if (getAlphabetizedWord(word1).equals(getAlphabetizedWord(word2))) {
							valid = false;
						}
					}
				}
				if (valid) {
					sum++;
				}
			}
		}
		return sum;
	}
	
	private String getAlphabetizedWord(String word) {
		List<String> characters = new ArrayList<>();
		for (char ch : word.toCharArray()) {
			characters.add(String.valueOf(ch));
		}
		Collections.sort(characters);
		return String.join("", characters);
	}
}