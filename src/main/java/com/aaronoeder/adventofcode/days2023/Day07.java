package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

public class Day07 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Hand> hands = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String[] parts = lines.get(i).split(" ");
			hands.add(new Hand(parts[0], Integer.parseInt(parts[1])));
		}
		
		int sum = 0;
		Collections.sort(hands, (h1, h2) -> compareHands(h1.getCards(), h2.getCards(), part));
		for (int i = 0; i < hands.size(); i++) {
			int score = hands.size() - i;
			int bet = hands.get(i).getBet();
			sum += (score * bet);
		}
		
		return sum;
	}
	
	@Data
	@AllArgsConstructor
	private class Hand {
		private String cards;
		private int bet;
	}
	
	private int compareHands(String hand1, String hand2, Part part) {
		boolean includeJokers = (part == Part.TWO);
		int hs1 = getHandStrength(hand1, includeJokers);
		int hs2 = getHandStrength(hand2, includeJokers);
		if (hs1 != hs2) {
			return hs2 - hs1;
		} else {
			for (int i = 0; i < hand1.length(); i++) {
				int cs1 = getCardStrength(hand1.substring(i, i + 1), includeJokers);
				int cs2 = getCardStrength(hand2.substring(i, i + 1), includeJokers);
				if (cs1 != cs2) {
					return cs2 - cs1;
				}
			}
			return 0;
		}
	}
	
	private enum CardType {
		FIVE_OF_A_KIND,
		FOUR_OF_A_KIND,
		FULL_HOUSE,
		THREE_OF_A_KIND,
		TWO_PAIRS,
		ONE_PAIR,
		HIGH_CARD;
		
		public int getStrength() {
			return CardType.values().length - ordinal();
		}
	}
	
	private int getHandStrength(String hand, boolean includeJokers) {
		Map<String, Integer> counts = new HashMap<>();
		for (int i = 0; i < hand.length(); i++) {
			String s = hand.substring(i, i + 1);
			counts.put(s, 1 + counts.getOrDefault(s, 0));
		}
		
		if (counts.size() == 1) {
			return CardType.FIVE_OF_A_KIND.getStrength();
		}
		
		int fourCount = 0;
		int threeCount = 0;
		int twoCount = 0;
		int jokerCount = 0;
		for (Map.Entry<String, Integer> entry : counts.entrySet()) {
			if (includeJokers && entry.getKey().equals("J")) {
				jokerCount += entry.getValue();
			} else if (entry.getValue() == 4) {
				fourCount++;
			} else if (entry.getValue() == 3) {
				threeCount++;
			} else if (entry.getValue() == 2) {
				twoCount++;
			}
		}
		
		if (fourCount == 1) {
			if (jokerCount == 1) {
				return CardType.FIVE_OF_A_KIND.getStrength();
			} else {
				return CardType.FOUR_OF_A_KIND.getStrength();
			}
		}
		
		if (threeCount == 1 && twoCount == 1) {
			return CardType.FULL_HOUSE.getStrength();
		}
		
		if (threeCount == 1) {
			if (jokerCount == 1) {
				return CardType.FOUR_OF_A_KIND.getStrength();
			} else if (jokerCount == 2) {
				return CardType.FIVE_OF_A_KIND.getStrength();
			} else {
				return CardType.THREE_OF_A_KIND.getStrength();
			}
		}
		
		if (twoCount == 2) {
			if (jokerCount == 1) {
				return CardType.FULL_HOUSE.getStrength();
			} else {
				return CardType.TWO_PAIRS.getStrength();
			}
		}
		
		if (twoCount  == 1) {
			if (jokerCount == 1) {
				return CardType.THREE_OF_A_KIND.getStrength();
			} else if (jokerCount == 2) {
				return CardType.FOUR_OF_A_KIND.getStrength();
			} else if (jokerCount == 3) {
				return CardType.FIVE_OF_A_KIND.getStrength();
			} else {
				return CardType.ONE_PAIR.getStrength();
			}
		}
		
		if (jokerCount == 1) {
			return CardType.ONE_PAIR.getStrength();
		} else if (jokerCount == 2) {
			return CardType.THREE_OF_A_KIND.getStrength();
		} else if (jokerCount == 3) {
			return CardType.FOUR_OF_A_KIND.getStrength();
		} else if (jokerCount == 4) {
			return CardType.FIVE_OF_A_KIND.getStrength();
		} else {
			return CardType.HIGH_CARD.getStrength();
		}
	}
	
	private int getCardStrength(String card, boolean includeJokers) {
		List<String> strengthList = includeJokers ? 
				Arrays.asList("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J") : 
				Arrays.asList("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2");
				
		Map<String, Integer> strengthMap = new HashMap<>();
		for (int i = 0; i < strengthList.size(); i++) {
			strengthMap.put(strengthList.get(i), strengthList.size() - i);
		}
		return strengthMap.get(card);
	}
}