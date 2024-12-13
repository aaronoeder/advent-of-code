package me.oeder.aoc.days2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import me.oeder.aoc.AdventDay;

public class Day04 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Integer> nums = Arrays.asList(lines.get(0).split(",")).stream()
				.map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		
		List<Card> cards = new ArrayList<>();
		for (int i = 2; i < lines.size(); i += 6) {
			cards.add(new Card(lines.subList(i, i + 5)));
		}
		
		if (part == Part.ONE) {
			for (int num : nums) {
				for (Card card : cards) {
					card.markNumber(num);
					if (card.hasWon()) {
						return card.getScore();
					}
				}
			}
		} else {
			for (int num : nums) {
				long cardsWon = cards.stream().filter(c -> c.hasWon()).count();
				for (Card card : cards) {
					if (card.hasWon()) {
						continue;
					}
					card.markNumber(num);
					if (cardsWon == cards.size() - 1 && card.hasWon()) {
						return card.getScore();
					}
				}
			}
		}
		
		return 0;
	}
	
	@Data
	private class Card {
		private Square[][] squares;
		private int lastNum;
		
		public Card(List<String> lines) {
			this.squares = new Square[lines.size()][lines.size()];
			for (int i = 0; i < lines.size(); i++) {
				List<Integer> nums = Arrays.asList(lines.get(i).split(" ")).stream()
						.filter(s -> !s.equals(""))
						.map(s -> Integer.parseInt(s))
						.collect(Collectors.toList());
				for (int j = 0; j < nums.size(); j++) {
					squares[i][j] = new Square(nums.get(j));
				}
			}
		}
		
		public void markNumber(int num) {
			lastNum = num;
			for (int i = 0; i < squares.length; i++) {
				for (int j = 0; j < squares[0].length; j++) {
					Square square = squares[i][j];
					if (square.getNum() == num) {
						square.setCalled(true);
					}
				}
			}
		}
		
		public boolean hasWon() {
			
			// Row
			for (int i = 0; i < squares.length; i++) {
				boolean wonRow = true;
				for (int j = 0; j < squares[0].length; j++) {
					if (!squares[i][j].isCalled()) {
						wonRow = false;
					}
				}
				if (wonRow) {
					return true;
				}
			}
			
			// Column
			for (int j = 0; j < squares[0].length; j++) {
				boolean wonCol = true;
				for (int i = 0; i < squares.length; i++) {
					if (!squares[i][j].isCalled()) {
						wonCol = false;
					}
				}
				if (wonCol) {
					return true;
				}
			}
			
			return false;
		}
		
		public int getScore() {
			int unmarkedSum = 0;
			for (int i = 0; i < squares.length; i++) {
				for (int j = 0; j < squares[0].length; j++) {
					Square square = squares[i][j];
					if (!square.isCalled()) {
						unmarkedSum += square.getNum();
					}
				}
			}
			return unmarkedSum * lastNum;
		}
	}
	
	@Data
	private class Square {
		private int num;
		private boolean called;
		
		public Square(int num) {
			this.num = num;
		}
	}
}