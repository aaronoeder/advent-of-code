package me.oeder.aoc.days2022;

import java.util.Arrays;
import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.days2022.Day02.Selection.Outcome;

public class Day02 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		if (part == Part.ONE) {
			int score = 0;
			for (String line : lines) {
				String[] parts = line.split(" ");
				Selection opponentSelection = Selection.fromOpponentCode(parts[0]);
				Selection playerSelection = Selection.fromPlayerCode(parts[1]);
				score += playerSelection.getPoints() + playerSelection.getOutcomeWith(opponentSelection).getPoints();
			}
			return score;
		} else {
			int score = 0;
			for (String line : lines) {
				String[] parts = line.split(" ");
				Selection opponentSelection = Selection.fromOpponentCode(parts[0]);
				Outcome neededOutcome = Outcome.fromCode(parts[1]);
				Selection playerSelection = opponentSelection.getPlayerSelectionNeededToReachOutcome(neededOutcome);
				score += playerSelection.getPoints() + playerSelection.getOutcomeWith(opponentSelection).getPoints();
			}
			return score;
		}
	}
	
	public enum Selection {
		ROCK("A", "X", 1), 
		PAPER("B", "Y", 2),
		SCISSORS("C", "Z", 3);
		
		public enum Outcome {
			WIN("Z", 6), 
			LOSE("X", 0), 
			DRAW("Y", 3);
			
			private String code;
			private int points;
			
			private Outcome(String code, int points) {
				this.code = code;
				this.points = points;
			}
			
			public static Outcome fromCode(String code) {
				return Arrays.asList(Outcome.values()).stream()
						.filter(sel -> sel.code.equals(code))
						.findFirst()
						.orElse(null);			
			}
			
			public int getPoints() {
				return points;
			}
		}
		
		private String opponentCode;
		private String playerCode;
		private int points;
		
		private Selection(String opponentCode, String playerCode, int points) {
			this.opponentCode = opponentCode;
			this.playerCode = playerCode;
			this.points = points;
		}
		
		public static Selection fromOpponentCode(String opponentCode) {
			return Arrays.asList(Selection.values()).stream()
					.filter(sel -> sel.opponentCode.equals(opponentCode))
					.findFirst()
					.orElse(null);
		}
		
		public static Selection fromPlayerCode(String playerCode) {
			return Arrays.asList(Selection.values()).stream()
					.filter(sel -> sel.playerCode.equals(playerCode))
					.findFirst()
					.orElse(null);			
		}
		
		public Outcome getOutcomeWith(Selection otherSelection) {
			if (this == otherSelection) {
				return Outcome.DRAW;
			}
			if (this == ROCK) {
				return otherSelection == SCISSORS ? Outcome.WIN : Outcome.LOSE;
			} else if (this == PAPER) {
				return otherSelection == ROCK ? Outcome.WIN : Outcome.LOSE;
			} else {
				return otherSelection == PAPER ? Outcome.WIN : Outcome.LOSE;
			}
		}
		
		public Selection getPlayerSelectionNeededToReachOutcome(Outcome outcome) {
			if (outcome == Outcome.DRAW) {
				return this;
			}
			if (this == ROCK) {
				return outcome == Outcome.WIN ? Selection.PAPER : Selection.SCISSORS;
			} else if (this == PAPER) {
				return outcome == Outcome.WIN ? Selection.SCISSORS : Selection.ROCK;
			} else {
				return outcome == Outcome.WIN ? Selection.ROCK : Selection.PAPER;
			}
		}
		
		public int getPoints() {
			return points;
		}
	}
}