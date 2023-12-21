package me.oeder.aoc;

public class AdventRunner {
	private static final int YEAR = 2023;
	private static final int DAY = 21;
	private static final boolean RUN_FULL_YEAR = false;
	
	private static AdventDay getAdventDay(int day) throws Exception {
		return (AdventDay)Class.forName(String.format("me.oeder.aoc.days%d.Day%s", YEAR, String.format("%02d", day)))
				.getConstructor()
				.newInstance();
	}
	
	public static void main(String[] args) throws Exception {
		if (RUN_FULL_YEAR) {
			for (int day = 1; day <= DAY; day++) {
				getAdventDay(day).solve();
			}
		} else {
			getAdventDay(DAY).solve();
		}
	}
}