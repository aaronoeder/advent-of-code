package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;

public class Day10 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Tile[][] grid = getGrid(lines);
		List<TileCoord> path = getPath(grid);
		if (part == Part.ONE) {
			return path.size() / 2;
		} else {
			return getEnclosedTileCount(grid, path);
		}
	}
	
	private Tile[][] getGrid(List<String> lines) {
		Tile[][] grid = new Tile[lines.size()][lines.get(0).length()];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				String val = line.substring(j, j + 1);
				grid[i][j] = Tile.fromString(val);
			}
		}
		return grid;
	}
	
	private List<TileCoord> getPath(Tile[][] grid) {
		TileCoord start = null;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == Tile.S) { // Find start tile and swap it out with what it should be
					start = new TileCoord(i, j, grid);
					for (Tile tile : Tile.values()) {
						TileCoord TileCoord = new TileCoord(start.getRow(), start.getCol(), tile);
						if (TileCoord.getNeighbors(grid).size() == 2) {
							start.setTile(tile);
							grid[start.getRow()][start.getCol()] = tile;
						}
					}
				}
			}
		}
		
		List<TileCoord> path = new ArrayList<>();
		path.add(start);
		
		TileCoord cur = getNextStepInPath(path, grid, start);
		while (cur != null) {
			path.add(cur);
			cur = getNextStepInPath(path, grid, cur);
		}
		
		return path;
	}
	
	private TileCoord getNextStepInPath(List<TileCoord> visited, Tile[][] grid, TileCoord cur) {
		for (TileCoord tileCoord : cur.getNeighbors(grid)) {
			if (visited.contains(tileCoord)) {
				continue;
			}
			return tileCoord;
		}
		return null;
	}
	
	private int getEnclosedTileCount(Tile[][] grid, List<TileCoord> path) {
		// Find total area using the Shoelace formula (this requires the boundary points to be arranged in counter-clockwise order first)
		Collections.reverse(path);
		double totalArea = 0;
		for (int i = 0; i < path.size(); i++) {
			Coord c1 = path.get(i);
			Coord c2 = path.get(i == path.size() - 1 ? 0 : i + 1);
			totalArea += 0.5 * (c1.getRow() * c2.getCol() - c2.getRow() * c1.getCol());
		}
		
		// Find interior area using Pick's theorem
		// Pick's theorem states: A = i + b/2 - 1, where A is the total area, b is the # of boundary points and i is the # of interior points
		// We need to solve for i: i = A + 1 - b/2
		return (int)(totalArea + 1 - (path.size() / 2));
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	private class TileCoord extends Coord {
		private Tile tile;
		
		public TileCoord(int row, int col, Tile tile) {
			super(row, col);
			this.tile = tile;
		}
		
		public TileCoord(int row, int col, Tile[][] grid) {
			super(row, col);
			if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
				this.tile = null;
			} else {
				this.tile = (grid[row][col] != null ? grid[row][col] : null);
			}
		}
		
		public List<TileCoord> getNeighbors(Tile[][] grid) {
			List<TileCoord> neighbors = new ArrayList<>();
			
			TileCoord north = new TileCoord(getRow() - 1, getCol(), grid);
			TileCoord south = new TileCoord(getRow() + 1, getCol(), grid);
			TileCoord west = new TileCoord(getRow(), getCol() - 1, grid);
			TileCoord east = new TileCoord(getRow(), getCol() + 1, grid);
			
			switch (tile) {
				case V:
					if (Arrays.asList(Tile.V, Tile.F, Tile.SEVEN).contains(north.getTile())) {
						neighbors.add(north);
					}
					if (Arrays.asList(Tile.V, Tile.L, Tile.J).contains(south.getTile())) {
						neighbors.add(south);
					}
					break;
				case H:
					if (west.getTile() != Tile.V) {
						neighbors.add(west);
					}
					if (east.getTile() != Tile.V) {
						neighbors.add(east);
					}
					break;					
				case L:
					if (Arrays.asList(Tile.V, Tile.F, Tile.SEVEN).contains(north.getTile())) {
						neighbors.add(north);
					}
					if (Arrays.asList(Tile.H, Tile.J, Tile.SEVEN).contains(east.getTile())) {
						neighbors.add(east);
					}
					break;
				case J:
					if (Arrays.asList(Tile.V, Tile.F, Tile.SEVEN).contains(north.getTile())) {
						neighbors.add(north);
					}
					if (Arrays.asList(Tile.H, Tile.L, Tile.F).contains(west.getTile())) {
						neighbors.add(west);
					}
					break;
				case SEVEN:
					if (Arrays.asList(Tile.V, Tile.J, Tile.L).contains(south.getTile())) {
						neighbors.add(south);
					}
					if (Arrays.asList(Tile.H, Tile.F, Tile.L).contains(west.getTile())) {
						neighbors.add(west);
					}
					break;
				case F:
					if (Arrays.asList(Tile.V, Tile.J, Tile.L).contains(south.getTile())) {
						neighbors.add(south);
					}
					if (Arrays.asList(Tile.H, Tile.SEVEN, Tile.J).contains(east.getTile())) {
						neighbors.add(east);
					}
					break;
				case S:
					break; // Should never happen
			}
			
			return neighbors;
		}
	}
	
	private enum Tile {
		S("S"),
		V("|"),
		H("-"),
		L("L"),
		J("J"),
		SEVEN("7"),
		F("F");
		
		private String symbol;
		
		private Tile(String symbol) {
			this.symbol = symbol;
		}
		
		public String getSymbol() {
			return symbol;
		}
		
		public static Tile fromString(String s) {
			for (Tile tile : Tile.values()) {
				if (tile.getSymbol().equals(s)) {
					return tile;
				}
			}
			return null;
		}
	}
}