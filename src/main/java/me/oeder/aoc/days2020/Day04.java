package me.oeder.aoc.days2020;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day04 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Map<String, String>> passports = new ArrayList<>();
		Map<String, String> passport = new HashMap<>();
		
		int index = 0;
		while (index < lines.size()) {
			String line = lines.get(index);
			if (line.equals("")) {

				passports.add(passport);
				passport = new HashMap<>();
			} else {
				String[] fields = line.split(" ");
				for (String field : fields) {
					String[] keyValue = field.split(":");
					passport.put(keyValue[0], keyValue[1]);
				}
			}
			index++;
		}
		
		int validCount = 0;
		for (Map<String, String> pass : passports) {
			if (pass.size() == 8 || (pass.size() == 7 && !pass.containsKey("cid"))) {
				if (part == Part.ONE) { 
					validCount++;
				} else if (part == Part.TWO && isValid(pass)) {
					validCount++;
				}
			}
		}
		
		return validCount;
	}
	
	private boolean isValid(Map<String, String> pass) {
		int birthYear = getInt(pass, "byr");
		if (!isInRange(birthYear, 1920, 2002)) { 
			return false;
		}
		
		int issueYear = getInt(pass, "iyr");
		if (!isInRange(issueYear, 2010, 2020)) {
			return false;
		}
		
		int expirationYear = getInt(pass, "eyr");
		if (!isInRange(expirationYear, 2020, 2030)) {
			return false;
		}
		
		String height = pass.get("hgt");
		if (!height.endsWith("cm") && !height.endsWith("in")) {
			return false;
		}
		
		if (height.endsWith("cm")) {
			int heightCm = Integer.parseInt(height.replace("cm", ""));
			if (!isInRange(heightCm, 150, 193)) {
				return false;
			}
		} else {
			int heightIn = Integer.parseInt(height.replace("in", ""));
			if (!isInRange(heightIn, 59, 76)) {
				return false;
			}
		}
		
		String hairColor = pass.get("hcl");
		if (hairColor.length() != 7) {
			return false;
		}
		if (!hairColor.startsWith("#")) {
			return false;
		}
		for (char ch : hairColor.substring(1).toCharArray()) {
			if (!Character.isDigit(ch) && !Arrays.asList('a', 'b', 'c', 'd', 'e', 'f').contains(ch)) {
				return false;
			}
		}
		
		String eyeColor = pass.get("ecl");
		if (!Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(eyeColor)) {
			return false;
		}
		
		String passportId = pass.get("pid");
		if (passportId.length() != 9) {
			return false;
		}
		for (char ch : passportId.toCharArray()) {
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		
		return true;
	}
	
	private int getInt(Map<String, String> pass, String key) {
		return Integer.parseInt(pass.get(key));
	}
	
	private boolean isInRange(int val, int min, int max) {
		return val >= min && val <= max;
	}
}