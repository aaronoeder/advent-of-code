package me.oeder.aoc;

public class AdventRunner {
	private static final int YEAR = 2024;
	private static final int DAY = 1;
	private static final boolean EXAMPLE = false;
	
	private static AdventDay getAdventDay() throws Exception {
		return (AdventDay)Class.forName(String.format("me.oeder.aoc.days%d.Day%s", YEAR, String.format("%02d", DAY)))
				.getConstructor()
				.newInstance();
	}
	
	public static void main(String[] args) throws Exception {
		getAdventDay().solve(EXAMPLE);
	}
}