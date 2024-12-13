package me.oeder.aoc.days2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;

public class Day03 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		int val = Integer.parseInt(lines.get(0));
		
		if (part == Part.ONE) {
			Coord coord = getCoord(val);
			return Math.abs(coord.getRow()) + Math.abs(coord.getCol());
		} else {
			Map<Coord, Integer> evaluatedCoords = new HashMap<>();
			evaluatedCoords.put(new Coord(0, 0), 1);
			
			int index = 2;
			while (true) {
				Coord coord = getCoord(index);
				int sum = 0;
				for (int r = -1; r <= 1; r++) {
					for (int c = -1; c <= 1; c++) {
						if (r == 0 && c == 0) {
							continue;
						}
						Coord neighbor = new Coord(coord.getRow() + r, coord.getCol() + c);
						if (evaluatedCoords.containsKey(neighbor)) {
							sum += evaluatedCoords.get(neighbor);
						}
					}
				}
				evaluatedCoords.put(coord, sum);
				
				if (sum > val) {
					return sum;
				}
				
				index++;
			}
		}
	}
	
	private Coord getCoord(int val) {
		int horizontal = -1;
		int vertical = -1;
		
		int diag = (int)Math.sqrt(val);
		if (diag % 2 == 0) {
			diag--;
		}
		
		int diagSquared = diag * diag;
		
		if (diag > 1 && val == diagSquared) {
			horizontal = ((diag - 1) / 2);
			vertical = ((diag - 1) / 2);
		} else {
			int step = diag + 1;
			
			if (val <= diagSquared + step) {
				
				// Right side
				int middle = diagSquared + (step / 2);
				horizontal = (diag / 2) + 1;
				vertical = middle - val;
				
			} else if (val <= diagSquared + 2 * step) {
				
				// Top side
				int middle = ((diagSquared + step) + (diagSquared + 2 * step)) / 2;
				vertical = -((diag / 2) + 1);
				horizontal = middle - val;

			} else if (val <= diagSquared + 3 * step) {
				
				// Left side
				int middle = ((diagSquared + 2 * step) + (diagSquared + 3 * step)) / 2;
				vertical = val - middle;
				horizontal = -((diag / 2) + 1);
				
			} else if (val < diagSquared + 4 * step) {
				
				// Bottom side
				int middle = ((diagSquared + 3 * step) + (diagSquared + 4 * step)) / 2;
				vertical = (diag / 2) + 1;
				horizontal = val - middle;
			}
		}
		
		return new Coord(vertical, horizontal);
	}
}