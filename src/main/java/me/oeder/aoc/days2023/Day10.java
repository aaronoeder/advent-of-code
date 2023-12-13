package me.oeder.aoc.days2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.oeder.aoc.graph.Coord;

public class Day10 extends AdventDay2023 {
	
	public Day10() {
		super(10);
	}

	@Override
	public Object solvePart1(List<String> lines) {
		Tile[][] grid = getGrid(lines);
		return getPath(grid).size() / 2;
	}
	
	@Override
	public Object solvePart2(List<String> lines) {
		Tile[][] grid = getGrid(lines);
		List<TileCoord> path = getPath(grid);
		return getEnclosedTileCount(grid, path);
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
		for (TileCoord TileCoord : cur.getNeighbors(grid)) {
			if (visited.contains(TileCoord)) {
				continue;
			}
			return TileCoord;
		}
		return null;
	}
	
	private int getEnclosedTileCount(Tile[][] grid, List<TileCoord> path) {
		int enclosedTileCount = 0;
		for (int i = 0; i < grid.length; i++) {
			int crossedVerticalTiles = 0;
			Tile cornerTile = null;
			for (int j = 0; j < grid[0].length; j++) {
				if (path.contains(new TileCoord(i, j, grid))) {
					Tile curTile = grid[i][j];
					if (curTile == Tile.V) {
						crossedVerticalTiles++;
					} else if (curTile != Tile.H) {
						if (cornerTile != null) {
							if (cornerTile == Tile.L && curTile == Tile.SEVEN) {
								crossedVerticalTiles++;
							} else if (cornerTile == Tile.F && curTile == Tile.J) {
								crossedVerticalTiles++;
							}
							cornerTile = null;
						} else {
							cornerTile = curTile;
						}
					} 
				} else if (crossedVerticalTiles % 2 == 1) { // If we've crossed an odd number of vertical tiles, the current tile must be enclosed by the loop
					enclosedTileCount++;
				}
			}
		}
		return enclosedTileCount;
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