package me.oeder.aoc.days2018;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day04 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Event> events = new ArrayList<>();
		
		Pattern pattern = Pattern.compile("\\[(.+)\\] (.+)");
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				int id = -1;
				EventType type = null;
				
				String remaining = matcher.group(2);
				if (remaining.equals("falls asleep")) {
					type = EventType.SLEEP;
				} else if (remaining.equals("wakes up")) {
					type = EventType.WAKE;
				} else {
					Pattern beginShiftPattern = Pattern.compile("Guard #(\\d+) begins shift");
					Matcher beginShiftMatcher = beginShiftPattern.matcher(remaining);
					if (beginShiftMatcher.find()) {
						id = Integer.parseInt(beginShiftMatcher.group(1));
					}
					type = EventType.BEGIN;
				}
				events.add(new Event(dateTime, id, type));
			}
		}
		
		Collections.sort(events, (e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()));
		
		Map<Integer, LocalDateTime> sleepTimeMap = new HashMap<>();
		Map<Integer, Long> minuteMap = new HashMap<>();
		Map<Integer, Map<Integer, Integer>> minuteCountsMap = new HashMap<>();
		
		int lastId = -1;
		for (Event event : events) {
			if (event.getType() == EventType.BEGIN) {
				lastId = event.getId();
			} else {
				event.setId(lastId);
			}
		}
		
		for (Event event : events) {
			if (event.getType() == EventType.SLEEP) {
				sleepTimeMap.put(event.getId(), event.getDateTime());
			
			} else if (event.getType() == EventType.WAKE) {
				LocalDateTime start = sleepTimeMap.get(event.getId());
				LocalDateTime end = event.getDateTime();
				
				long minutes = ChronoUnit.MINUTES.between(start, end);
				minuteMap.put(event.getId(), minutes + minuteMap.getOrDefault(event.getId(), 0L));

				if (!minuteCountsMap.containsKey(event.getId())) {
					minuteCountsMap.put(event.getId(), new HashMap<>());
				}
				
				MinuteRange range = new MinuteRange(start.getMinute(), end.getMinute());
				List<Integer> minuteValues = range.getMinuteValuesInRange();
				for (int minuteValue : minuteValues) {
					minuteCountsMap.get(event.getId()).put(minuteValue, 1 + minuteCountsMap.get(event.getId()).getOrDefault(minuteValue, 0));
				}
			}
		}
		
		int guardId = -1;
		int minuteId = -1;
		
		if (part == Part.ONE) {
			long maxSleepTime = Integer.MIN_VALUE;
			for (Map.Entry<Integer, Long> entry : minuteMap.entrySet()) {
				if (entry.getValue() > maxSleepTime) {
					maxSleepTime = entry.getValue();
					guardId = entry.getKey();
				}
			}
			
			long maxMinuteCount = Integer.MIN_VALUE;
			for (Map.Entry<Integer, Integer> entry : minuteCountsMap.get(guardId).entrySet()) {
				if (entry.getValue() > maxMinuteCount) {
					maxMinuteCount = entry.getValue();
					minuteId = entry.getKey();
				}
			}
		} else if (part == Part.TWO) { 
			int maxMinuteCount = Integer.MIN_VALUE;
			for (Map.Entry<Integer, Map<Integer, Integer>> entry : minuteCountsMap.entrySet()) {
				for (Map.Entry<Integer, Integer> innerEntry : entry.getValue().entrySet()) {
					if (innerEntry.getValue() > maxMinuteCount) {
						maxMinuteCount = innerEntry.getValue();
						minuteId = innerEntry.getKey();
						guardId = entry.getKey();
					}
				}
			}
		}
		
		return guardId * minuteId;
	}
	
	@Data
	@AllArgsConstructor
	private class MinuteRange {
		private int start;
		private int end;
		
		public List<Integer> getMinuteValuesInRange() {
			List<Integer> minuteValuesInRange = new ArrayList<>();
			if (start < end) {
				for (int t = start; t < end; t++) {
					minuteValuesInRange.add(t);
				}
			} else {
				for (int t = start; t <= 60; t++) {
					minuteValuesInRange.add(t);
				}
				for (int t = 0; t < end; t++) {
					minuteValuesInRange.add(t);
				}
			}
			return minuteValuesInRange;
		}
	}
	
	@Data
	@AllArgsConstructor
	private class Event {
		private LocalDateTime dateTime;
		private int id;
		private EventType type;
	}
	
	private enum EventType {
		BEGIN, SLEEP, WAKE;
	}
}