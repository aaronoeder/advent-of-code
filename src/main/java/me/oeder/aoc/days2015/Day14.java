package me.oeder.aoc.days2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day14 extends AdventDay {
	
	@Data
	@AllArgsConstructor
	private class Reindeer {
		private int flySpeed;
		private int flyTime;
		private int restTime;
		private int currentFlyTime;
		private int currentRestTime;
		private int totalFlyDistance;
		private int points;
	}

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Pattern pattern = Pattern.compile("(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.");
		
		List<Reindeer> reindeer = new ArrayList<>();
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				reindeer.add(new Reindeer(
					Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)),
					Integer.parseInt(matcher.group(4)),
					0, 0, 0, 0
				));
			}
		}
		
		int time = 1;
		while (time <= 2503) {
			for (Reindeer deer : reindeer) {
				if (deer.getCurrentFlyTime() < deer.getFlyTime()) {
					deer.setTotalFlyDistance(deer.getTotalFlyDistance() + deer.getFlySpeed());
					deer.setCurrentFlyTime(deer.getCurrentFlyTime() + 1);
				} else {
					deer.setCurrentRestTime(deer.getCurrentRestTime() + 1);
					if (deer.getCurrentRestTime() == deer.getRestTime()) {
						deer.setCurrentRestTime(0);
						deer.setCurrentFlyTime(0);
					}
				}
			}
			
			if (part == Part.TWO) {
				Collections.sort(reindeer, (r1, r2) -> r2.getTotalFlyDistance() - r1.getTotalFlyDistance());
				List<Reindeer> leaders = reindeer.stream().filter(deer -> deer.getTotalFlyDistance() == reindeer.get(0).getTotalFlyDistance()).collect(Collectors.toList());
				for (Reindeer leader : leaders) {
					leader.setPoints(leader.getPoints() + 1);
				}
			}
			
			time++;
		}
		
		if (part == Part.ONE) {
			Collections.sort(reindeer, (r1, r2) -> r2.getTotalFlyDistance() - r1.getTotalFlyDistance());
			return reindeer.get(0).getTotalFlyDistance();
		} else {
			Collections.sort(reindeer, (r1, r2) -> r2.getPoints() - r1.getPoints());
			return reindeer.get(0).getPoints();
		}
	}
}