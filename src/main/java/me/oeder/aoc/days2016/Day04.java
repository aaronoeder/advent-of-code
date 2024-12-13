package me.oeder.aoc.days2016;

import me.oeder.aoc.AdventDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day04 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int sum = 0;
		for (String line : lines) {
			String letterString = line.substring(0, line.lastIndexOf("-"));
			List<String> letters = Arrays.asList(letterString.split("")).stream().filter(s -> !s.equals("-")).collect(Collectors.toList());
			int sectorId = Integer.parseInt(line.substring(line.lastIndexOf("-") + 1, line.indexOf("[")));
			String checksum = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
			
			if (doesMatchChecksum(letters, checksum)) {
				sum += sectorId;
				
				if (part == Part.TWO) {
					String decryptedLetterString = getDecryptedLetterString(letterString, sectorId);
					if (decryptedLetterString.equals("northpole object storage")) {
						return sectorId;
					}
				}
			}
		}
		return sum;
	}
	
	private boolean doesMatchChecksum(List<String> letters, String checksum) {
		Map<String, Integer> counts = new HashMap<>();
		for (String str : letters) {
			counts.put(str, 1 + counts.getOrDefault(str, 0));
		}
		
		while (checksum.length() > 0) {
			String maxLetter = "";
			int maxCount = Integer.MIN_VALUE;
			for (Map.Entry<String, Integer> entry : counts.entrySet()) {
				if (entry.getValue() > maxCount || (entry.getValue() == maxCount && entry.getKey().compareTo(maxLetter) < 0)) {
					maxLetter = entry.getKey();
					maxCount = entry.getValue();
				}
			}
			
			if (!checksum.startsWith(maxLetter)) {
				return false;
			}
			
			checksum = checksum.substring(1);
			counts.remove(maxLetter);
		}
		
		return true;
	}
	
	private String getDecryptedLetterString(String str, int sectorId) {
		String decryptedString = "";
		for (char ch : str.toCharArray()) {
			if (ch == '-') {
				decryptedString += " ";
			} else {
				int rotateCount = sectorId % 26;
				int result = (int)(ch + rotateCount);
				if (result <= (int)('z')) {
					decryptedString += (char)(ch + rotateCount);
				} else {
					int diff = result - (int)('z');
					decryptedString += (char)('a' + diff - 1);
				}
			}
		}
		return decryptedString;
	}
}