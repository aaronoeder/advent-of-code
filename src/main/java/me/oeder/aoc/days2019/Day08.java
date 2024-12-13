package me.oeder.aoc.days2019;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.util.InputUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day08 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		List<Character[][]> layers = new ArrayList<>();

		int rows = 6;
		int cols = 25;

		Character[][] layer = new Character[rows][cols];
		int row = 0;
		int col = 0;

		String line = lines.get(0);
		for (char ch : line.toCharArray()) {
			if (col < cols) {
				layer[row][col] = ch;
				col++;
			} else if (row < rows - 1) {
				row++;
				col = 0;
				layer[row][col] = ch;
				col++;
			} else {
				layers.add(layer);
				layer = new Character[rows][cols];
				row = 0;
				col = 0;
				layer[row][col] = ch;
				col++;
			}
		}
		layers.add(layer);

		if (part == Part.ONE) {
			Collections.sort(layers, (l1, l2) -> getNumberOfDigits(l1, '0') - getNumberOfDigits(l2, '0'));
			Character[][] layerWithFewestZeroes = layers.get(0);
			return getNumberOfDigits(layerWithFewestZeroes, '1') * getNumberOfDigits(layerWithFewestZeroes, '2');
		}

		Character[][] results = new Character[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				char result = '#';
				for (Character[][] someLayer : layers) {
					char ch = someLayer[i][j];
					if (ch == '0') {
						result = '.';
					} else if (ch == '1') {
						break;
					}
				}
				results[i][j] = result;
			}
		}

		InputUtils.printGrid(results);
		return 0;
	}

	private int getNumberOfDigits(Character[][] layer, char digit) {
		int count = 0;
		for (int i = 0; i < layer.length; i++) {
			for (int j = 0; j < layer[i].length; j++) {
				if (layer[i][j] == digit) {
					count++;
				}
			}
		}
		return count;
	}
}