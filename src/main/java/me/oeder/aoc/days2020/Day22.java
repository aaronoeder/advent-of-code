package me.oeder.aoc.days2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day22 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		
		List<Integer> player1Cards = new ArrayList<>();
		List<Integer> player2Cards = new ArrayList<>();
		
		boolean arePlayer1CardsFilled = false;
		for (String line : lines) {
			if (line.contains("Player")) {
				continue;
			} else if (line.equals("")) {
				arePlayer1CardsFilled = true;
			} else if (!arePlayer1CardsFilled) {
				player1Cards.add(Integer.parseInt(line));
			} else {
				player2Cards.add(Integer.parseInt(line));
			}
		}
		
		if (part == Part.ONE) { 
			while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
				int player1Card = player1Cards.remove(0);
				int player2Card = player2Cards.remove(0);
				
				if (player1Card > player2Card) {
					player1Cards.add(player1Card);
					player1Cards.add(player2Card);
				} else {
					player2Cards.add(player2Card);
					player2Cards.add(player1Card);
				}
			}
			
			List<Integer> winningCards = (!player1Cards.isEmpty() ? player1Cards : player2Cards);
			
			int score = 0;
			for (int i = 0; i < winningCards.size(); i++) {
				score += winningCards.get(i) * (winningCards.size() - i);
			}
			return score;
		} else {
			boolean didPlayer1WinGame = didPlayer1WinGame(player1Cards, player2Cards);
			List<Integer> winningCards = (didPlayer1WinGame ? player1Cards : player2Cards);
			
			int score = 0;
			for (int i = 0; i < winningCards.size(); i++) {
				score += winningCards.get(i) * (winningCards.size() - i);
			}
			return score;
		}
	}
	
	private boolean didPlayer1WinGame(List<Integer> player1Cards, List<Integer> player2Cards) {
		Set<HistoryItem> history = new HashSet<>();
		while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
			HistoryItem historyItem = new HistoryItem(player1Cards, player2Cards);
			if (history.contains(historyItem)) {
				return true;
			}
			
			history.add(historyItem);
			
			int player1Card = player1Cards.remove(0);
			int player2Card = player2Cards.remove(0);
			
			boolean didPlayer1WinRound = false;
			if (player1Card <= player1Cards.size() && player2Card <= player2Cards.size()) {
				didPlayer1WinRound = didPlayer1WinGame(getDeepSublist(player1Cards, 0, player1Card), getDeepSublist(player2Cards, 0, player2Card));
			} else {
				didPlayer1WinRound = player1Card > player2Card;
			}
			
			if (didPlayer1WinRound) {
				player1Cards.add(player1Card);
				player1Cards.add(player2Card);
			} else {
				player2Cards.add(player2Card);
				player2Cards.add(player1Card);
			}
		}
		return !player1Cards.isEmpty();
	}
	
	@Data
	@AllArgsConstructor
	private class HistoryItem {
		private List<Integer> player1Cards;
		private List<Integer> player2Cards;
	}
	
	private List<Integer> getDeepSublist(List<Integer> nums, int start, int pastEnd) {
		List<Integer> deepSublist = new ArrayList<>();
		for (int i = start; i < pastEnd; i++) {
			deepSublist.add(nums.get(i));
		}
		return deepSublist;
	}
}