package me.oeder.aoc.days2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

public class Day22 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int passwordIndex = 0;
		String password = lines.get(lines.size() - 1);
		List<Instruction> instructions = new ArrayList<>();
		
		String amount = "";
		while (passwordIndex < password.length()) {
			char ch = password.charAt(passwordIndex);
			if (Character.isDigit(ch)) {
				amount += String.valueOf(ch);
				if (passwordIndex == password.length() - 1) {
					instructions.add(new Instruction(Integer.parseInt(amount), null));
				}
			} else {
				instructions.add(new Instruction(Integer.parseInt(amount), null));
				instructions.add(new Instruction(null, String.valueOf(ch)));
				amount = "";
			}
			passwordIndex++;
		}
		
		Map<Coord, Direction> path = new HashMap<>();
		
		Direction dir = Direction.E;
		Coord cur = null;
		
		int cols = lines.stream().map(s -> s.length()).max((a, b) -> a - b).get();
		String[][] grid = new String[lines.size() - 2][cols];
		for (int i = 0; i < lines.size() - 2; i++) {
			String line = lines.get(i);
			for (int j = 0; j < cols; j++) {
				if (j < line.length()) {
					grid[i][j] = line.substring(j, j + 1);
					if (cur == null && i == 0 && grid[i][j].equals(".")) {
						cur = new Coord(i, j);
					}
				} else {
					grid[i][j] = "";
				}
			}
		}
		
		path.put(new Coord(cur.getRow(), cur.getCol()), dir);
		
		for (Instruction instruction : instructions) {
			if (instruction.getAmount() != null) {
				for (int i = 0; i < instruction.getAmount(); i++) {
					int newRow = getWrappedCoordinateElement(cur.getRow() + dir.getDeltaRow(), grid.length);
					int newCol = getWrappedCoordinateElement(cur.getCol() + dir.getDeltaCol(), grid[0].length);
					String newLoc = grid[newRow][newCol];
						
					// Out of bounds of board
					while (newLoc.trim().equals("")) {
						newRow = getWrappedCoordinateElement(newRow + dir.getDeltaRow(), grid.length);
						newCol = getWrappedCoordinateElement(newCol + dir.getDeltaCol(), grid[0].length);
						newLoc = grid[newRow][newCol];
					}
					
					if (newLoc.equals("#")) {
						break;
					}
					
					cur = new Coord(newRow, newCol);
					path.put(new Coord(cur.getRow(), cur.getCol()), dir);
				}
			} else if (instruction.getLeftOrRight() != null) {
				dir = dir.applyLeftOrRight(instruction.getLeftOrRight());
				path.put(new Coord(cur.getRow(), cur.getCol()), dir);
			}
		}
		return (cur.getRow() + 1) * 1000 + (cur.getCol() + 1) * 4 + dir.getFacingValue();
	}
	
	@Data
	@AllArgsConstructor
	private class Instruction {
		private Integer amount;
		private String leftOrRight;
	}
	
	private enum Direction {
		N(-1, 0, 3),
		E(0, 1, 0),
		S(1, 0, 1),
		W(0, -1, 2);
		
		private int deltaRow;
		private int deltaCol;
		private int facingValue;
		
		private Direction(int deltaRow, int deltaCol, int facingValue) {
			this.deltaRow = deltaRow;
			this.deltaCol = deltaCol;
			this.facingValue = facingValue;
		}
		
		public int getDeltaRow() {
			return deltaRow;
		}
		
		public int getDeltaCol() {
			return deltaCol;
		}
		
		public int getFacingValue() {
			return facingValue;
		}
		
		public Direction applyLeftOrRight(String leftOrRight) {
			int newIndex = ordinal();
			if (leftOrRight.equals("L")) {
				newIndex = newIndex > 0 ? newIndex - 1 : Direction.values().length - 1;
			} else {
				newIndex = newIndex < Direction.values().length - 1 ? newIndex + 1 : 0;
			}
			return Direction.values()[newIndex];
		}
	}
	
	private int getWrappedCoordinateElement(int coordinateElementValue, int maxCoordinateElementValue) {
		if (coordinateElementValue == -1) {
			return maxCoordinateElementValue -1;
		} else if (coordinateElementValue == maxCoordinateElementValue) {
			return 0;
		} else {
			return coordinateElementValue;
		}
	}
}