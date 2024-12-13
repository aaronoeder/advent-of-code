package me.oeder.aoc.days2020;

import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Direction;

public class Day12 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		
		int rowTravel = 0;
		int colTravel = 0;
		
		if (part == Part.ONE) { 
			Direction facingDir = Direction.E;
			for (String line : lines) {
				String action = line.substring(0, 1);
				int amount = Integer.parseInt(line.substring(1));
				
				if (action.equals("L")) {
					for (int i = 0; i < amount / 90; i++) {
						facingDir = facingDir.getDirectionToLeft();
					}
				} else if (action.equals("R")) {
					for (int i = 0; i < amount / 90; i++) {
						facingDir = facingDir.getDirectionToRight();
					}
				} else if (action.equals("F")){
					rowTravel += amount * facingDir.getRowDiff();
					colTravel += amount * facingDir.getColDiff();
				} else {
					Direction travelDir = Direction.valueOf(action);
					rowTravel += amount * travelDir.getRowDiff();
					colTravel += amount * travelDir.getColDiff();
				}
			}
		} else if (part == Part.TWO) {
			Direction waypointRowDir = Direction.N;
			int waypointRowAmount = 1;
			Direction waypointColDir = Direction.E;
			int waypointColAmount = 10;
			
			for (String line : lines) {
				String action = line.substring(0, 1);
				int amount = Integer.parseInt(line.substring(1));
				
				if (action.equals("L")) {
					for (int i = 0; i < amount / 90; i++) {
						Direction tempDir = waypointRowDir;
						int tempAmount = waypointRowAmount;
						
						waypointRowDir = waypointColDir.getDirectionToLeft();
						waypointColDir = tempDir.getDirectionToLeft();
						
						waypointRowAmount = waypointColAmount;
						waypointColAmount = tempAmount;
					}
				} else if (action.equals("R")) {
					for (int i = 0; i < amount / 90; i++) {
						Direction tempDir = waypointRowDir;
						int tempAmount = waypointRowAmount;
						
						waypointRowDir = waypointColDir.getDirectionToRight();
						waypointColDir = tempDir.getDirectionToRight();
						
						waypointRowAmount = waypointColAmount;
						waypointColAmount = tempAmount;
					}
				} else if (action.equals("F")){
					rowTravel += amount * waypointRowAmount * waypointRowDir.getRowDiff();
					colTravel += amount * waypointColAmount * waypointColDir.getColDiff();
				} else {
					Direction travelDir = Direction.valueOf(action);
					if (travelDir == Direction.W || travelDir == Direction.E) {
						if (travelDir == waypointColDir) {
							waypointColAmount += amount;
						} else {
							waypointColAmount -= amount;
							if (waypointColAmount < 0) {
								waypointColAmount *= -1;
								waypointColDir = waypointColDir.getOppositeDirection();
							}
						}
					} else if (travelDir == Direction.N || travelDir == Direction.S) {
						if (travelDir == waypointRowDir) {
							waypointRowAmount += amount;
						} else {
							waypointRowAmount -= amount;
							if (waypointRowAmount < 0) {
								waypointRowAmount *= -1;
								waypointRowDir = waypointRowDir.getOppositeDirection();
							}
						}
					}
				}
			}
		}
		
		return Math.abs(rowTravel) + Math.abs(colTravel);
	}
}