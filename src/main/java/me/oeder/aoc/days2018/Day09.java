package me.oeder.aoc.days2018;

import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Pattern pattern = Pattern.compile("(\\d+) players; last marble is worth (\\d+) points");
		Matcher matcher = pattern.matcher(lines.get(0));
		if (matcher.find()) {
			int players = Integer.parseInt(matcher.group(1));
			int marbles = Integer.parseInt(matcher.group(2));
			
			List<Integer> circle = new ArrayList<>();
			circle.add(0);
			
			int currentMarbleIndex = 1;
			int nextMarble = 1;
			int player = 1;
			
			Map<Integer, Integer> scoresMap = new HashMap<>();
			
			while (nextMarble <= marbles) {
				log(nextMarble);
				if (nextMarble % 23 == 0) {
					int grabbedMarbleIndex = currentMarbleIndex - 7;
					
					if (grabbedMarbleIndex < 0) {
						grabbedMarbleIndex = circle.size() + grabbedMarbleIndex;
					}
					
					int playerScoreIncrease = nextMarble + circle.get(grabbedMarbleIndex);
					scoresMap.put(player, playerScoreIncrease + scoresMap.getOrDefault(player, 0));
					
					circle.remove(grabbedMarbleIndex);
					
					currentMarbleIndex = grabbedMarbleIndex;
					
				} else {
					currentMarbleIndex = (currentMarbleIndex < circle.size() - 1 ? currentMarbleIndex + 2 : 1);
					circle.add(currentMarbleIndex, nextMarble);
				}
				
				nextMarble++;
				player = (player < players ? player + 1 : 1);
			}
			
			List<Integer> scores = new ArrayList<>(scoresMap.values());
			Collections.sort(scores, (s1, s2) -> s2 - s1);
			return scores.get(0);
		}
		
		return 0;
	}
}
