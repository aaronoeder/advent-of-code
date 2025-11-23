package com.aaronoeder.adventofcode.util;

import java.util.Collection;

public final class MathUtils {
	private MathUtils() {}
	
	public static long lcm(Collection<Long> numbers) {
	    return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
	}
	
	private static long gcd(long x, long y) {
	    return (y == 0) ? x : gcd(y, x % y);
	}
}