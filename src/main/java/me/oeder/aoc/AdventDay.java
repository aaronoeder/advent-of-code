package me.oeder.aoc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AdventDay {
	private int year;
	private int day;
	
	public void solve() {
		List<String> lines = getLines();
		log("Part 1 Solution:\n" + solvePart1(lines));
		log("\n");
		log("Part 2 Solution:\n" + solvePart2(lines));
	}
	
	private List<String> getLines() {
		List<String> lines = new ArrayList<>();
		try {
			lines.addAll(Files.readAllLines(Paths.get(String.format("src/main/resources/%d/Day%s.txt", year, String.format("%02d", day)))));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return lines;
	}
	
	public abstract Object solvePart1(List<String> lines);
	public abstract Object solvePart2(List<String> lines);
	
	public void log(Object obj) {
		System.out.println(obj);
	}
}