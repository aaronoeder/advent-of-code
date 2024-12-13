package me.oeder.aoc.days2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day16 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Field> fields = new ArrayList<>();
		List<Integer> yourTicket = new ArrayList<>();
		List<List<Integer>> nearbyTickets = new ArrayList<>();
		
		int section = 1;
		for (String line : lines) {
			if (line.equals("")) {
				section++;
			} else if (section == 1) {
				String[] parts = line.split(": ");
				
				String id = parts[0];
				
				List<Range> ranges = new ArrayList<>();
				String[] rangeParts = parts[1].split(" or ");
				for (String rangePart : rangeParts) {
					String[] startEndParts = rangePart.split("-");
					ranges.add(new Range(Integer.parseInt(startEndParts[0]), Integer.parseInt(startEndParts[1])));
				}
				
				fields.add(new Field(id, ranges));
			} else if (section == 2) {
				if (!line.equals("your ticket:")) {
					yourTicket = Arrays.asList(line.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
				}
			} else if (section == 3) {
				if (!line.equals("nearby tickets:")) {
					nearbyTickets.add(Arrays.asList(line.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()));
				}
			}
		}
		
		int errorRate = 0;
		List<List<Integer>> validTickets = new ArrayList<>();
		for (List<Integer> ticket : nearbyTickets) {
			boolean isValidTicket = true;
			for (int i = 0; i < ticket.size(); i++) {
				int num = ticket.get(i);
				boolean isValidTicketNumber = false;
				for (Field field : fields) {
					for (Range range : field.getRanges()) {
						if (num >= range.getStart() && num <= range.getEnd()) {
							isValidTicketNumber = true;
						}
					}
				}
				if (!isValidTicketNumber) {
					errorRate += num;
					isValidTicket = false;
				}
			}
			if (isValidTicket) {
				validTickets.add(ticket);
			}
		}
		
		if (part == Part.ONE) { 
			return errorRate;
		} else {
			Map<Integer, List<Field>> possibleFieldMappings = new ConcurrentHashMap<>();
			for (int i = 0; i < validTickets.get(0).size(); i++) {
				List<Field> validFields = new ArrayList<>();
				for (Field field : fields) {
					boolean isValid = true;
					
					for (List<Integer> ticket : validTickets) {
						int num = ticket.get(i);
						
						boolean isInRange = false;
						for (Range range : field.getRanges()) {
							if (num >= range.getStart() && num <= range.getEnd()) {
								isInRange = true;
							}
						}
						if (!isInRange) {
							isValid = false;
						}
					}
					
					if (isValid) {
						validFields.add(field);
					}
				}
				possibleFieldMappings.put(i, validFields);
			}
			
			Map<Integer, Field> actualFieldMapping = new HashMap<>();
			
			while (actualFieldMapping.size() < possibleFieldMappings.size()) {
				for (Map.Entry<Integer, List<Field>> entry : possibleFieldMappings.entrySet()) {
					List<Field> validFields = entry.getValue();
					
					if (validFields.size() == 1) {
						Field validField = validFields.get(0);
						for (Map.Entry<Integer, List<Field>> innerEntry : possibleFieldMappings.entrySet()) {
							innerEntry.getValue().remove(validField);
						}
						actualFieldMapping.put(entry.getKey(), validField);
					} else {
						for (Field validField : validFields) {
							boolean isUnique = true;
							for (Map.Entry<Integer, List<Field>> innerEntry : possibleFieldMappings.entrySet()) {
								if (innerEntry.getKey().equals(entry.getKey())) {
									continue;
								}
								if (innerEntry.getValue().contains(validField)) {
									isUnique = false;
								}
							}
							if (isUnique) {
								for (Map.Entry<Integer, List<Field>> innerEntry : possibleFieldMappings.entrySet()) {
									innerEntry.getValue().remove(validField);
								}
								actualFieldMapping.put(entry.getKey(), validField);
								break;
							}
						}
					}
				}
			}
			
			long product = 1;
			for (int i = 0; i < yourTicket.size(); i++) {
				String fieldId = actualFieldMapping.get(i).getId();
				if (fieldId.startsWith("departure")) {
					product *= yourTicket.get(i);
				}
			}
			return product;
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Field {
		private String id;
		private List<Range> ranges;
	}
	
	@Data
	@AllArgsConstructor
	private class Range {
		private int start;
		private int end;
	}
}