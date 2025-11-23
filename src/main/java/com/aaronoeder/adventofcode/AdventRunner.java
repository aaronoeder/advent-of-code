package com.aaronoeder.adventofcode;

public class AdventRunner {
	private static final int YEAR = 2025;
	private static final int DAY = 1;
	private static final boolean USE_EXAMPLE = false;
	private static final boolean USE_TIMER = false;
	
	public static void main(String[] args) throws Exception {
		getAdventDay(YEAR, DAY).logAnswers(USE_EXAMPLE, USE_TIMER);
	}

	public static AdventDay getAdventDay(int year, int day) {
        try {
            return (AdventDay)Class.forName(String.format("com.aaronoeder.adventofcode.days%d.Day%s", year, String.format("%02d", day)))
                    .getConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}