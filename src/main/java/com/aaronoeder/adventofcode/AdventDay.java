package com.aaronoeder.adventofcode;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AdventDay {

	private String year = getClass().getPackage().getName().split("days")[1];
	private String day = getClass().getSimpleName().replace("Day", "");

	public enum Part {
		ONE, TWO;
	}

	public void logAnswers(boolean useExample, boolean useTimer) {
		List<String> lines = getLines(useExample);
		log(String.format("Year %s, Day %s", year, day));
		logAnswer(useTimer, lines, Part.ONE);
		if ((Integer.parseInt(year) < 2025 && Integer.parseInt(day) < 25) || (Integer.parseInt(year) >= 2025 && Integer.parseInt(day) < 12)) {
			logAnswer(useTimer, lines, Part.TWO);
		}
	}

	private void logAnswer(boolean useTimer, List<String> lines, Part part) {
		if (!useTimer) {
			log(String.format("Part %d Solution:\n%s", part.ordinal() + 1, getAnswer(lines, part)));
		} else {
			long start = System.currentTimeMillis();
			Object answer = getAnswer(lines, part);
			long end = System.currentTimeMillis();
			log(String.format("Part %d Solution (%d ms):\n%s", part.ordinal() + 1, end - start, answer));
		}
	}
	
	private List<String> getLines(boolean useExample) {
		List<String> lines = new ArrayList<>();
		try {
			URL resourceUrl = getClass().getClassLoader().getResource(String.format("%s/Day%s.txt", year, day + (useExample ? "-example" : "")));
			lines.addAll(Files.readAllLines(Paths.get(resourceUrl.getPath())));
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