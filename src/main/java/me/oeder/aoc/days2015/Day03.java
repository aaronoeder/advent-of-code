package me.oeder.aoc.days2015;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;

public class Day03 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		char[] chars = lines.get(0).toCharArray();
		
		char[] santaChars = part == Part.ONE ? chars : getSantaChars(chars, i -> i % 2 == 0);
		char[] roboSantaChars = part == Part.ONE ? new char[0] : getSantaChars(chars, i -> i % 2 == 1);
		
		Set<Coord> visited = new HashSet<>();
		List<char[]> trips = Arrays.asList(santaChars, roboSantaChars);
		for (char[] trip : trips) {
			Coord cur = new Coord(0, 0);
			visited.add(cur);
			for (char c : trip) {
				Direction dir = getDirection(c);
				cur = new Coord(cur.getRow() + dir.getRowDiff(), cur.getCol() + dir.getColDiff());
				visited.add(cur);
			}
		}
		return visited.size();
	}
	
	private char[] getSantaChars(char[] chars, Predicate<Integer> cond) {
		char[] santaChars = new char[chars.length / 2];
		
		int index = 0;
		for (int i = 0; i < chars.length; i++) {
			if (cond.test(i)) {
				santaChars[index++] = chars[i];
			}
		}
		return santaChars;
	}
	
	private Direction getDirection(char c) {
		switch (c) {
			case '^':
				return Direction.N;
			case '<':
				return Direction.W;
			case 'v':
				return Direction.S;
			case '>':
				return Direction.E;
			default:
				return null;
		}
	}
}