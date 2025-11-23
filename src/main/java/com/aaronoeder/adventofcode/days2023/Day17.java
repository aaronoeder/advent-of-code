package com.aaronoeder.adventofcode.days2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;

public class Day17 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int[][] grid = new int[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				grid[i][j] = Integer.parseInt(line.substring(j, j + 1));
			}
		}
		
		int minMovementInSameDirection = (part == Part.ONE ? 0 : 4);
		int maxMovementInSameDirection = (part == Part.ONE ? 3 : 10);
		
		// We don't want to count the heat loss of the starting block (hence the "0" as the third argument)
		// We also have to consider going both south and east from this starting block
		Block startingBlockSouth = new Block(new Coord(0, 0), Direction.S, 0);
		Block startingBlockEast = new Block(new Coord(0, 0), Direction.E, 0);
		
		Map<Block, Integer> minHeatLosses = new HashMap<>();
		minHeatLosses.put(startingBlockSouth, 0);
		minHeatLosses.put(startingBlockEast, 0);
		
		// We are using Dijkstra's algorithm, so a priority queue is needed
		Queue<Block> queue = new PriorityQueue<>((block1, block2) -> minHeatLosses.get(block1) - minHeatLosses.get(block2));
		queue.add(startingBlockSouth);
		queue.add(startingBlockEast);
		
		while (!queue.isEmpty()) {
			Block block = queue.poll();
			
			if (block.getMovementInSameDirection() < maxMovementInSameDirection) {
				Coord straight = new Coord(block.getCoord().getRow() + block.getDir().getRowDiff(), block.getCoord().getCol() + block.getDir().getColDiff());
				if (isInGrid(grid, straight)) {
					Block straightBlock = new Block(straight, block.getDir(), block.getMovementInSameDirection() + 1);
					if (!minHeatLosses.containsKey(straightBlock)) {
						int straightHeatLoss = minHeatLosses.get(block) + grid[straight.getRow()][straight.getCol()];
						minHeatLosses.put(straightBlock, straightHeatLoss);
						queue.add(straightBlock);
					}
				}
			}
			
			if (block.getMovementInSameDirection() >= minMovementInSameDirection) {
				Direction leftDir = block.getDir().getDirectionToLeft();
				Coord left = new Coord(block.getCoord().getRow() + leftDir.getRowDiff(), block.getCoord().getCol() + leftDir.getColDiff());
				if (isInGrid(grid, left)) {
					Block leftBlock = new Block(left, leftDir, 1);
					if (!minHeatLosses.containsKey(leftBlock)) {
						int leftHeatLoss = minHeatLosses.get(block) + grid[left.getRow()][left.getCol()];
						minHeatLosses.put(leftBlock, leftHeatLoss);
						queue.add(leftBlock);
					}
				}
				
				Direction rightDir = block.getDir().getDirectionToRight();
				Coord right = new Coord(block.getCoord().getRow() + rightDir.getRowDiff(), block.getCoord().getCol() + rightDir.getColDiff());
				if (isInGrid(grid, right)) {
					Block rightBlock = new Block(right, rightDir, 1);
					if (!minHeatLosses.containsKey(rightBlock)) {
						int rightHeatLoss = minHeatLosses.get(block) + grid[right.getRow()][right.getCol()];
						minHeatLosses.put(rightBlock, rightHeatLoss);
						queue.add(rightBlock);
					}
				}
			}
		}
		
		// Find length of shortest path that was found to the end
		int minDistanceToEnd = Integer.MAX_VALUE;
		for (Map.Entry<Block, Integer> entry : minHeatLosses.entrySet()) {
			Coord coord = entry.getKey().getCoord();
			if (coord.getRow() == grid.length - 1 && coord.getCol() == grid[0].length - 1) {
				if (entry.getValue() < minDistanceToEnd && entry.getKey().getMovementInSameDirection() >= minMovementInSameDirection) {
					minDistanceToEnd = entry.getValue();
				}
			}
		}
		return minDistanceToEnd;
	}
	
	@Data
	@AllArgsConstructor
	private class Block {
		private Coord coord;
		private Direction dir;
		private int movementInSameDirection;
	}
	
	private boolean isInGrid(int[][] grid, Coord coord) {
		return coord.getRow() >= 0 && coord.getRow() < grid.length && coord.getCol() >= 0 && coord.getCol() < grid[0].length;
	}
}