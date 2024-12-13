package me.oeder.aoc.days2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.oeder.aoc.AdventDay;
import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Day14 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Program> programs = new ArrayList<>();
		
		boolean started = false;
		String mask = null;
		List<Memory> memories = new ArrayList<>();
		Pattern memoryPattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");
		for (String line : lines) {
			if (line.startsWith("mask")) {
				if (started) {
					programs.add(new Program(mask, memories));
				}
				mask = line.split(" = ")[1];
				memories = new ArrayList<>();
			} else {
				Matcher memoryMatcher = memoryPattern.matcher(line);
				if (memoryMatcher.find()) {
					memories.add(new Memory(Integer.parseInt(memoryMatcher.group(1)), Long.parseLong(memoryMatcher.group(2))));
				}
			}
			
			if (!started) {
				started = true;
			}
		}
		programs.add(new Program(mask, memories));
		
		Map<Long, Long> memoryValues = new HashMap<>();
		for (Program program : programs) {
			for (Memory memory : program.getMemories()) {
				if (part == Part.ONE) { 
					String binaryValue = Long.toBinaryString(memory.getValue());
					String maskedBinaryValue = getMaskedBinaryNumber(binaryValue, program.getMask(), Part.ONE);
					memoryValues.put(memory.getKey(), Long.valueOf(maskedBinaryValue, 2));
				} else if (part == Part.TWO) { 
					String binaryKey = Long.toBinaryString(memory.getKey());
					String maskedBinaryKey = getMaskedBinaryNumber(binaryKey, program.getMask(), Part.TWO);
					addToMemory(memoryValues, maskedBinaryKey, memory.getValue());
				}
			}
		}
		
		long sum = 0;
		for (Map.Entry<Long, Long> entry : memoryValues.entrySet()) {
			sum += entry.getValue();
		}

		return sum;
	}
	
	private void addToMemory(Map<Long, Long> memoryValues, String maskedKey, long val) {
		if (!maskedKey.contains("X")) {
			memoryValues.put(Long.valueOf(maskedKey, 2), val);
		} else {
			addToMemory(memoryValues, maskedKey.replaceFirst("X", "0"), val);
			addToMemory(memoryValues, maskedKey.replaceFirst("X", "1"), val);
		}
	}
	
	private String getMaskedBinaryNumber(String binaryNumber, String mask, Part part) {
		binaryNumber = StringUtils.leftPad(binaryNumber, mask.length(), "0");
		
		String maskedBinaryNumber = "";
		for (int i = 0; i < binaryNumber.length(); i++) {
			String maskVal = mask.substring(i, i + 1);
			String binVal = binaryNumber.substring(i, i + 1);
			if (maskVal.equals("X")) {
				maskedBinaryNumber += (part == Part.ONE ? binVal : "X");
			} else if (maskVal.equals("1")) {
				maskedBinaryNumber += maskVal;
			} else {
				maskedBinaryNumber += binVal;
			}
		}
		
		return maskedBinaryNumber;
	}
	
	@Data
	@AllArgsConstructor
	private class Program {
		private String mask;
		private List<Memory> memories;
	}
	
	@Data
	@AllArgsConstructor
	private class Memory {
		private long key;
		private long value;
	}
}