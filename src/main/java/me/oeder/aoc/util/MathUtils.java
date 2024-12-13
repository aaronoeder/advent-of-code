package me.oeder.aoc.util;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public final class MathUtils {
	private MathUtils() {}
	
	public static long lcm(Collection<Long> numbers) {
	    return numbers.stream().reduce(1L, (x, y) -> x * (y / gcd(x, y)));
	}
	
	private static long gcd(long x, long y) {
	    return (y == 0) ? x : gcd(y, x % y);
	}
	
	public static long chineseRemainder(List<Congruence> congruences) {
		long m = 1;
		for (Congruence congruence : congruences) {
			m *= congruence.getM();
		}
		
		long answer = 0;
		for (Congruence congruence : congruences) {
			long ai = congruence.getA();
			long mi = m / congruence.getM();
			long ni = modInverse(mi, congruence.getM());
			answer = (answer + ai * mi % m * ni) % m;
		}
		
		return answer;
	}
	
	@Data
	@AllArgsConstructor
	public static class Congruence {
		private int m; // modulo
		private int a; // remainder
	}
	
	private static long modInverse(long a, long m) {
		for (long x = 1; x < m; x++)
			if (((a % m) * (x % m)) % m == 1)
				return x;
		return 1;
	}
}