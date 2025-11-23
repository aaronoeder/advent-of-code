package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;
import com.aaronoeder.adventofcode.util.InputUtils;

public class Day16 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		
		int max = Integer.MIN_VALUE;
		
		List<Beam> startBeams = new ArrayList<>();
		if (part == Part.ONE) {
			startBeams.add(new Beam(new Coord(0, 0), getInitialDirection(grid[0][0], Direction.E)));
		} else if (part == Part.TWO) {
			for (int i = 0; i < grid.length; i++) {
				startBeams.add(new Beam(new Coord(i, 0), getInitialDirection(grid[i][0], Direction.W)));
				startBeams.add(new Beam(new Coord(i, grid[0].length - 1), getInitialDirection(grid[i][grid[0].length - 1], Direction.E)));
			}
			
			for (int j = 0; j < grid[0].length; j++) {
				startBeams.add(new Beam(new Coord(0, j), getInitialDirection(grid[0][j], Direction.S)));
				startBeams.add(new Beam(new Coord(grid.length - 1, j), getInitialDirection(grid[grid.length - 1][j], Direction.N)));
			}
		}
		
		for (Beam startBeam : startBeams) {
			List<Beam> beams = new ArrayList<>();
			beams.add(startBeam);
			
			Set<Coord> visitedSplitters = new HashSet<>();
			
			while (areBeamsActive(beams)) {
				List<Beam> newBeams = new ArrayList<>();
				for (Beam beam : beams) {
					Coord cur = beam.getCur();
					Direction dir = beam.getDir();
					
					Coord next = new Coord(cur.getRow() + dir.getRowDiff(), cur.getCol() + dir.getColDiff());
					
					if (next.getRow() < 0 || next.getRow() >= grid.length || next.getCol() < 0 || next.getCol() >= grid[0].length) {
						beam.setActive(false);
						continue;
					} else {
						char val = grid[next.getRow()][next.getCol()];
						if (val == '.') {
							// Do nothing
						} else if (val == '/') {
							if (dir == Direction.N) {
								dir = Direction.E;
							} else if (dir == Direction.W) {
								dir = Direction.S;
							} else if (dir == Direction.S) {
								dir = Direction.W;
							} else if (dir == Direction.E) {
								dir = Direction.N;
							}
						} else if (val == '\\') {
							if (dir == Direction.N) {
								dir = Direction.W;
							} else if (dir == Direction.W) {
								dir = Direction.N;
							} else if (dir == Direction.S) {
								dir = Direction.E;
							} else if (dir == Direction.E) {
								dir = Direction.S;
							}
						} else if (val == '|') {
							if (dir == Direction.N) {
								// do nothing
							} else if (dir == Direction.W) {
								dir = Direction.N;
								if (visitedSplitters.contains(next)) {
									beam.setActive(false);
								} else {
									visitedSplitters.add(next);
									newBeams.add(new Beam(next, Direction.S));
								}
							} else if (dir == Direction.S) {
								// do nothing
							} else if (dir == Direction.E) {
								dir = Direction.N;
								if (visitedSplitters.contains(next)) {
									beam.setActive(false);
								} else {
									visitedSplitters.add(next);
									newBeams.add(new Beam(next, Direction.S));
								}
							}
						} else if (val == '-') {
							if (dir == Direction.N) {
								dir = Direction.W;
								if (visitedSplitters.contains(next)) {
									beam.setActive(false);
								} else {
									visitedSplitters.add(next);
									newBeams.add(new Beam(next, Direction.E));
								}
							} else if (dir == Direction.W) {
								// do nothing
							} else if (dir == Direction.S) {
								dir = Direction.W;
								if (visitedSplitters.contains(next)) {
									beam.setActive(false);
								} else {
									visitedSplitters.add(next);
									newBeams.add(new Beam(next, Direction.E));
								}
							} else if (dir == Direction.E) {
								// do nothing
							}
						}
						beam.setCur(next);
						beam.setDir(dir);
						beam.getVisited().add(next);
					}
				}
				beams.addAll(newBeams);
			}
			
			int visitedCoordCount = getVisitedCoordCount(beams);
			if (visitedCoordCount > max) {
				max = visitedCoordCount;
			}
		}
		
		return max;
	}
	
	@Data
	private class Beam {		
		private Coord cur;
		private Direction dir;
		private List<Coord> visited;
		private boolean active;
		
		public Beam(Coord cur, Direction dir) {
			this.cur = cur;
			this.dir = dir;
			this.visited = new ArrayList<>();
			this.visited.add(cur);
			this.active = true;
		}
	}
	
	private Direction getInitialDirection(char val, Direction entranceDirection) {
		Direction dir = entranceDirection;
		if (val == '.') {
			// Do nothing
		} else if (val == '/') {
			if (dir == Direction.N) {
				dir = Direction.E;
			} else if (dir == Direction.W) {
				dir = Direction.S;
			} else if (dir == Direction.S) {
				dir = Direction.W;
			} else if (dir == Direction.E) {
				dir = Direction.N;
			}
		} else if (val == '\\') {
			if (dir == Direction.N) {
				dir = Direction.W;
			} else if (dir == Direction.W) {
				dir = Direction.N;
			} else if (dir == Direction.S) {
				dir = Direction.E;
			} else if (dir == Direction.E) {
				dir = Direction.S;
			}
		} else if (val == '|') {
			if (dir == Direction.N) {
				// do nothing
			} else if (dir == Direction.W) {
				dir = Direction.N;
			} else if (dir == Direction.S) {
				// do nothing
			} else if (dir == Direction.E) {
				dir = Direction.N;
			}
		} else if (val == '-') {
			if (dir == Direction.N) {
				dir = Direction.W;
			} else if (dir == Direction.W) {
				// do nothing
			} else if (dir == Direction.S) {
				dir = Direction.W;
			} else if (dir == Direction.E) {
				// do nothing
			}
		}
		return dir;
	}
	
	private boolean areBeamsActive(List<Beam> beams) {
		return beams.stream().filter(b -> b.isActive()).count() > 0;
	}
	
	private int getVisitedCoordCount(List<Beam> beams) {
		Set<Coord> visitedCoords = new HashSet<>();
		for (Beam beam : beams) {
			visitedCoords.addAll(beam.getVisited());
		}
		return visitedCoords.size();
	}
}