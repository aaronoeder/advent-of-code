package me.oeder.aoc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AdventDay {

	private String year = getClass().getPackage().getName().split("days")[1];
	private String day = getClass().getSimpleName().replace("Day", "");

	public static enum Part {
		ONE, TWO;
	}
	
	public void solve(boolean useExample, boolean useTimer) {
		List<String> lines = getLines(useExample);
		log(String.format("Year %s, Day %s", year, day));
		logAnswer(useTimer, lines, Part.ONE);
		if (Integer.parseInt(day) < 25) {
			logAnswer(useTimer, lines, Part.TWO);
		}
	}
	
	private List<String> getLines(boolean useExample) {
		List<String> lines = new ArrayList<>();
		try {
			lines.addAll(Files.readAllLines(Paths.get(String.format("src/main/resources/%s/Day%s.txt", year, day + (useExample ? "-example" : "")))));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return lines;
	}

	public void logAnswer(boolean useTimer, List<String> lines, Part part) {
		if (!useTimer) {
			log(String.format("Part %d Solution:\n%s", part.ordinal() + 1, getAnswer(lines, part)));
		} else {
			long start = System.currentTimeMillis();
			Object answer = getAnswer(lines, part);
			long end = System.currentTimeMillis();
			log(String.format("Part %d Solution (%d ms):\n%s", part.ordinal() + 1, end - start, answer));
		}
	}
	
	public abstract Object getAnswer(List<String> lines, Part part);
	
	public static void log(Object obj) {
		System.out.println(obj);
	}
}