package me.oeder.aoc.days2022;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.DoubleDirection;
import me.oeder.aoc.util.InputUtils;

public class Day23 extends AdventDay {
	
	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		
		Set<Coord> coords = new HashSet<>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == '#') {
					coords.add(new Coord(i, j));
				}
			}
		}
		
		List<List<DoubleDirection>> dirs = buildList(
			buildList(DoubleDirection.N, DoubleDirection.NE, DoubleDirection.NW),
			buildList(DoubleDirection.S, DoubleDirection.SE, DoubleDirection.SW),
			buildList(DoubleDirection.W, DoubleDirection.NW, DoubleDirection.SW),
			buildList(DoubleDirection.E, DoubleDirection.NE, DoubleDirection.SE)
		);
		
		int max = (part == Part.ONE ? 10 : 10000);
		for (int round = 1; round <= max; round++) {
			
			// First Half
			Map<Coord, List<Coord>> propositions = new HashMap<>();
			for (Coord coord : coords) {
				boolean foundOtherElf = false;
				for (DoubleDirection dd : DoubleDirection.values()) {
					int row = coord.getRow() + dd.getRowDiff();
					int col = coord.getCol() + dd.getColDiff();
					Coord other = new Coord(row, col);
					if (coords.contains(other)) {
						foundOtherElf = true;
					}
				}
				
				if (!foundOtherElf) {
					continue;
				}
				
				for (List<DoubleDirection> d : dirs) {
					boolean valid = true;
					for (DoubleDirection dd : d) {
						int row = coord.getRow() + dd.getRowDiff();
						int col = coord.getCol() + dd.getColDiff();
						Coord other = new Coord(row, col);
						if (coords.contains(other)) {
							valid = false;
						}
					}
					if (valid) {
						Coord next = new Coord(coord.getRow() + d.get(0).getRowDiff(), coord.getCol() + d.get(0).getColDiff());
						if (!propositions.containsKey(next)) {
							propositions.put(next, new ArrayList<>());
						}
						propositions.get(next).add(coord);
						break;
					}
				}
			}
			
			// Second half
			boolean movement = false;
			for (Map.Entry<Coord, List<Coord>> entry : propositions.entrySet()) {
				Coord dest = entry.getKey();
				List<Coord> curs = entry.getValue();
				if (curs.size() > 1) {
					continue;
				}
				Coord cur = curs.get(0);
				coords.remove(cur);
				coords.add(dest);
				
				if (!cur.equals(dest)) {
					movement = true;
				}
			}
			
			if (part == Part.TWO && !movement) {
				return round;
			}
			
			// Change directions
			dirs.add(dirs.remove((0)));
		}
		
		return getEmptyTiles(coords);
	}

	private <T> List<T> buildList(T...elems) {
		List<T> list = new ArrayList<>();
		for (T elem : elems) {
			list.add(elem);
		}
		return list;
	}
	
	private int getEmptyTiles(Set<Coord> coords) {
		int emptyTiles = 0;
		
		int minRow = getFirst(coords, (c1, c2) -> c1.getRow() - c2.getRow(), c -> c.getRow());
		int maxRow = getFirst(coords, (c1, c2) -> c2.getRow() - c1.getRow(), c -> c.getRow());
		int minCol = getFirst(coords, (c1, c2) -> c1.getCol() - c2.getCol(), c -> c.getCol());
		int maxCol = getFirst(coords, (c1, c2) -> c2.getCol() - c1.getCol(), c -> c.getCol());
		for (int i = minRow; i <= maxRow; i++) {
			for (int j = minCol; j <= maxCol; j++) {
				Coord coord = new Coord(i, j);
				if (!coords.contains(coord)) {
					emptyTiles++;
				}
			}
		}
		return emptyTiles;
	}
	
	private int getFirst(Set<Coord> coords, Comparator<Coord> sorter, Function<Coord, Integer> mapper) {
		List<Coord> coordsList = new ArrayList<>(coords);
		Collections.sort(coordsList, sorter);
		return mapper.apply(coordsList.get(0));
	}
}