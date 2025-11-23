package com.aaronoeder.adventofcode.days2023;

import java.util.ArrayList;
import java.util.List;

import com.aaronoeder.adventofcode.AdventDay;

public class Day13 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<List<String>> mirrors = new ArrayList<>();
		int mirrorsIndex = 0;
		for (String line : lines) {
			if (line.equals("")) {
				mirrorsIndex++;
			} else {
				if (mirrorsIndex > mirrors.size() - 1) {
					mirrors.add(new ArrayList<>());
				}
				mirrors.get(mirrorsIndex).add(line);
			}
		}
		
		int sum = 0;
		for (List<String> mirror : mirrors) {
			String[][] arr = new String[mirror.size()][mirror.get(0).length()];
			
			for (int i = 0; i < mirror.size(); i++) {
				String line = mirror.get(i);
				for (int j = 0; j < line.length(); j++) {
					arr[i][j] = line.substring(j, j + 1);
				}
			}
			
			List<Integer> verticalReflections = getVerticalReflections(arr);
			List<Integer> horizontalReflections = getHorizontalReflections(arr);
			
			int vertical = verticalReflections.size() > 0 ? verticalReflections.get(0) : -1;
			int horizontal = horizontalReflections.size() > 0 ? horizontalReflections.get(0) : -1;
			
			int newV = vertical;
			int newH = horizontal;
			
			if (part == Part.TWO) {
				for (int a = 0; a < arr.length; a++) {
					for (int b = 0; b < arr[0].length; b++) {
						String val = arr[a][b];
						String opp = val.equals("#") ? "." : "#";
						arr[a][b] = opp;
						
						List<Integer> verticalReflectionsSmudge = getVerticalReflections(arr);
						List<Integer> horizontalReflectionsSmudge = getHorizontalReflections(arr);
						
						if (verticalReflectionsSmudge.contains(vertical)) {
							verticalReflectionsSmudge.remove(verticalReflectionsSmudge.indexOf(vertical));
						}
						if (horizontalReflectionsSmudge.contains(horizontal)) {
							horizontalReflectionsSmudge.remove(horizontalReflectionsSmudge.indexOf(horizontal));
						}
						if (verticalReflectionsSmudge.size() > 0) {
							newV = verticalReflectionsSmudge.get(0);
						}
						if (horizontalReflectionsSmudge.size() > 0) {
							newH = horizontalReflectionsSmudge.get(0);
						}

						arr[a][b] = val;
					}
				}
			}
			
			if (part == Part.ONE) {
				if (newV != -1) {
					sum += newV;
				}
				if (newH != -1) {
					sum += newH * 100;
				}
			} else if (part == Part.TWO) {
				if (newV != -1 && newV != vertical) {
					sum += newV;
				}
				if (newH != -1 && newH != horizontal) {
					sum += newH * 100;
				}
			}
		}
		
		return sum;
	}

	private List<Integer> getVerticalReflections(String[][] arr) {
		List<Integer> verticalReflections = new ArrayList<>();
		for (int j = 1; j < arr[0].length; j++) {
			int leftIndex = j - 1;
			int rightIndex = j;
			if (!areColsEqual(arr, leftIndex, rightIndex)) {
				continue;
			}
			
			leftIndex--;
			rightIndex++;
			while (leftIndex >= 0 && rightIndex < arr[0].length) {
				if (!areColsEqual(arr, leftIndex, rightIndex)) {
					break;
				}
				leftIndex--;
				rightIndex++;
			}
			
			if (rightIndex == arr[0].length) {
				verticalReflections.add(j);
			}
		}
		for (int j = 0; j < arr[0].length - 1; j++) {
			int leftIndex = j;
			int rightIndex = j + 1;
			if (!areColsEqual(arr, leftIndex, rightIndex)) {
				continue;
			}
			
			leftIndex--;
			rightIndex++;
			while (leftIndex >= 0 && rightIndex < arr[0].length) {
				if (!areColsEqual(arr, leftIndex, rightIndex)) {
					break;
				}
				leftIndex--;
				rightIndex++;
			}
			
			if (leftIndex == -1 ) {
				verticalReflections.add(j + 1);
			}
		}
		return verticalReflections;
	}
	
	private boolean areColsEqual(String[][] arr, int c1, int c2) {
		for (int j = 0; j < arr.length; j++) {
			if (!arr[j][c1].equals(arr[j][c2])) {
				return false;
			}
		}
		return true;
	}
	
	private List<Integer> getHorizontalReflections(String[][] arr) {
		List<Integer> horizontalReflections = new ArrayList<>();
		for (int i = 1; i < arr.length; i++) {
			int topIndex = i - 1;
			int bottomIndex = i;
			if (!areRowsEqual(arr, bottomIndex, topIndex)) {
				continue;
			}
			
			topIndex--;
			bottomIndex++;
			while (topIndex >= 0 && bottomIndex < arr.length) {
				if (!areRowsEqual(arr, topIndex, bottomIndex)) {
					break;
				}
				topIndex--;
				bottomIndex++;
			}
			
			if (bottomIndex == arr.length) {
				horizontalReflections.add(i);
			}
		}
		
		for (int i = 0; i < arr.length - 1; i++) {
			int topIndex = i;
			int bottomIndex = i + 1;
			if (!areRowsEqual(arr, bottomIndex, topIndex)) {
				continue;
			}
			
			topIndex--;
			bottomIndex++;
			while (topIndex >= 0 && bottomIndex < arr.length) {
				if (!areRowsEqual(arr, topIndex, bottomIndex)) {
					break;
				}
				topIndex--;
				bottomIndex++;
			}
			
			if (topIndex == -1) {
				horizontalReflections.add(i + 1);
			}
		}
		
		return horizontalReflections;
	}
	
	private boolean areRowsEqual(String[][] arr, int r1, int r2) {
		for (int j = 0; j < arr[0].length; j++) {
			if (!arr[r1][j].equals(arr[r2][j])) {
				return false;
			}
		}
		return true;
	}
}