package me.oeder.aoc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public abstract class AdventDay {

	private String year = StringUtils.substringAfter(getClass().getPackageName(), "days");
	private String day = getClass().getSimpleName().replace("Day", "");

	public static enum Part {
		ONE, TWO;
	}
	
	public void solve(boolean example) {
		List<String> lines = getLines(example);
		log(String.format("Year %s, Day %s", year, day));
		log("Part 1 Solution:\n" + getAnswer(lines, Part.ONE));
		log("Part 2 Solution:\n" + getAnswer(lines, Part.TWO));
	}
	
	private List<String> getLines(boolean example) {
		List<String> lines = new ArrayList<>();
		try {
			lines.addAll(Files.readAllLines(Paths.get(String.format("src/main/resources/%s/Day%s.txt", year, day + (example ? "-example" : "")))));
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