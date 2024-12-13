package me.oeder.aoc.days2021;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day21 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {

		Player player1 = new Player(Integer.parseInt(lines.get(0).split(": ")[1]), 0);
		Player player2 = new Player(Integer.parseInt(lines.get(1).split(": ")[1]), 0);

		if (part == Part.ONE) {
			int lastDiceRoll = 0;
			int numberOfDiceRolls = 0;
			boolean isPlayer1Turn = true;

			while (player1.getScore() < 1000 && player2.getScore() < 1000) {
				int movement = 0;
				for (int i = 0; i < 3; i++) {
					lastDiceRoll = getNextDiceRoll(lastDiceRoll);
					movement += lastDiceRoll;
				}
				if (isPlayer1Turn) {
					int newPosition = player1.getPosition() + movement;
					newPosition = (newPosition % 10 > 0 ? newPosition % 10 : 10);
					player1.setPosition(newPosition);
					player1.setScore(player1.getScore() + newPosition);
				} else {
					int newPosition = player2.getPosition() + movement;
					newPosition = (newPosition % 10 > 0 ? newPosition % 10 : 10);
					player2.setPosition(newPosition);
					player2.setScore(player2.getScore() + newPosition);
				}
				numberOfDiceRolls += 3;
				isPlayer1Turn = !isPlayer1Turn;
			}

			int losingScore = Math.min(player1.getScore(), player2.getScore());
			return losingScore * numberOfDiceRolls;
		} else {
			Game game = new Game(player1, player2, true);
			return Math.max(getWinCount(game, true), getWinCount(game, false));
		}
	}

	private int getNextDiceRoll(int lastDiceRoll) {
		if (lastDiceRoll < 100) {
			return lastDiceRoll + 1;
		}
		return 1;
	}

	private Map<Game, Long> cache = new HashMap<>();
	public long getWinCount(Game game, boolean isPlayer1) {
		if (cache.containsKey(game)) {
			return cache.get(game);
		}

		long winCount = 0;
		if (game.getPlayer1().getScore() >= 21) {
			winCount = isPlayer1 ? 1 : 0;
		} else if (game.getPlayer2().getScore() >= 21) {
			winCount = isPlayer1 ? 0 : 1;
		} else {
			if (game.isPlayer1Turn()) {
				List<Integer> sums = getPossibleDiceSums();
				for (int sum : sums) {
					Game deepCopyOfGame = getDeepCopyOfGame(game);
					int newPosition = deepCopyOfGame.getPlayer1().getPosition() + sum;
					newPosition = (newPosition % 10 > 0 ? newPosition % 10 : 10);

					deepCopyOfGame.getPlayer1().setPosition(newPosition);
					deepCopyOfGame.getPlayer1().setScore(deepCopyOfGame.getPlayer1().getScore() + newPosition);
					deepCopyOfGame.setPlayer1Turn(!deepCopyOfGame.isPlayer1Turn());
					winCount += getWinCount(deepCopyOfGame, isPlayer1);
				}
			} else {
				List<Integer> sums = getPossibleDiceSums();
				for (int sum : sums) {
					Game deepCopyOfGame = getDeepCopyOfGame(game);
					int newPosition = deepCopyOfGame.getPlayer2().getPosition() + sum;
					newPosition = (newPosition % 10 > 0 ? newPosition % 10 : 10);

					deepCopyOfGame.getPlayer2().setPosition(newPosition);
					deepCopyOfGame.getPlayer2().setScore(deepCopyOfGame.getPlayer2().getScore() + newPosition);
					deepCopyOfGame.setPlayer1Turn(!deepCopyOfGame.isPlayer1Turn());
					winCount += getWinCount(deepCopyOfGame, isPlayer1);
				}
			}
		}
		cache.put(game, winCount);

		return winCount;
	}

	private List<Integer> getPossibleDiceSums() {
		List<Integer> possibleDiceSums = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				for (int k = 1; k <= 3; k++) {
					possibleDiceSums.add(i + j + k);
				}
			}
		}
		return possibleDiceSums;
	}

	private Game getDeepCopyOfGame(Game game) {
		return new Game(
				new Player(game.getPlayer1().getPosition(), game.getPlayer1().getScore()),
				new Player(game.getPlayer2().getPosition(), game.getPlayer2().getScore()),
				game.isPlayer1Turn
		);
	}

	@Data
	@AllArgsConstructor
	public static class Game {
		private Player player1;
		private Player player2;
		private boolean isPlayer1Turn;
	}

	@Data
	@AllArgsConstructor
	public static class Player {
		private int position;
		private int score;
	}
}