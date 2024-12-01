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
	
	public static enum Part {
		ONE, TWO;
	}
	
	public void solve() {
		List<String> lines = getLines();
		log(String.format("Year %d, Day %d", year, day));
		log("Part 1 Solution:\n" + getAnswer(lines, Part.ONE));
		log("Part 2 Solution:\n" + getAnswer(lines, Part.TWO));
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
	
	public abstract Object getAnswer(List<String> lines, Part part);
	
	public static void log(Object obj) {
		System.out.println(obj);
	}
}