package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

public class Day05 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		String seedLine = lines.get(0);
		String[] seedLineParts = seedLine.split(": ");
		String[] seedStrings = seedLineParts[1].split(" ");
		List<Long> seeds = Arrays.asList(seedStrings).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
		
		Map<String, List<Range>> data = new LinkedHashMap<>();
		
		List<String> titles = Arrays.asList(
			"seed-to-soil map:",
			"soil-to-fertilizer map:",
			"fertilizer-to-water map:",
			"water-to-light map:",
			"light-to-temperature map:",
			"temperature-to-humidity map:",
			"humidity-to-location map:"
		);
		
		String currentTitle = "";
		int index = 1;
		while (index < lines.size()) {
			String line = lines.get(index);
			if (!line.trim().equals("")) {
				if (titles.contains(line.trim())) {
					currentTitle = line.trim();
					data.put(currentTitle, new ArrayList<>());
				} else {
					List<Range> curData = data.get(currentTitle);
					String[] parts = line.split(" ");
					curData.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2])));
					data.put(currentTitle, curData);
				}
			}
			index++;
		}
		
		long min = Integer.MAX_VALUE;
		if (part == Part.ONE) {
			for (long seed : seeds) {
				long val = seed;
				for (String title : titles) {
					val = getDestinationMapping(data.get(title), val);
				}
				if (val < min) {
					min = val;
				}
			}
		} else if (part == Part.TWO) {
			long i = 0;
			while (true) {
				long val = i;
				for (int j = titles.size() - 1; j >= 0; j--) {
					String title = titles.get(j);
					val = getSourceMapping(data.get(title), val);
				}
				if (isInSeedRanges(seeds, val)) {
					min = i;
					break;
				}
				i++;
			}
		}
		
		return min;
	}
	
	@Data
	@AllArgsConstructor
	private class Range {
		private long destination;
		private long source;
		private long length;
		
		public long getDestinationFromSource(long x) {
			long sourceMax = source + (length - 1);
			if (x >= source && x <= sourceMax) {
				long delta = x - source;
				return destination + delta;
			}
			return -1;
		}
		
		public long getSourceFromDestination(long x) {
			long destinationMax = destination + (length - 1);
			if (x >= destination && x <= destinationMax) {
				long delta = x - destination;
				return source + delta;
			}
			return -1;
		}
	}
	
	private long getDestinationMapping(List<Range> ranges, long x) {
		for (Range range : ranges) {
			long result = range.getDestinationFromSource(x);
			if (result != -1) {
				return result;
			}
		}
		return x;
	}
	
	private long getSourceMapping(List<Range> ranges, long x) {
		for (Range range : ranges) {
			long result = range.getSourceFromDestination(x);
			if (result != -1) {
				return result;
			}
		}
		return x;
	}
	
	private boolean isInSeedRanges(List<Long> seedRanges, long x) {
		for (int s = 0; s < seedRanges.size(); s += 2) {
			if (x >= seedRanges.get(s) && x <= seedRanges.get(s) + seedRanges.get(s + 1)) {
				return true;
			}
		}
		return false;
	}
}