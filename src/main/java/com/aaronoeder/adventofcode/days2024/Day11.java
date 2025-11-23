package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Long> nums = Arrays.asList(lines.get(0).split(" ")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());

        long sum = 0;
        for (long num : nums) {
            sum += getLength(num, part == Part.ONE ? 25 : 75);
        }
        return sum;
    }

    private Map<CacheObject, Long> cache = new HashMap<>();
    private long getLength(long num, int iterations) {
        CacheObject cacheObject = new CacheObject(num, iterations);
        if (cache.containsKey(cacheObject)) {
            return cache.get(cacheObject);
        }

        if (iterations == 0) {
            return 1;
        }

        String strVal = "" + num;
        long length = 1;
        if (num == 0) {
            length = getLength(1, iterations - 1);
        } else if (strVal.length() % 2 == 0) {
            String left = strVal.substring(0, strVal.length() / 2);
            String right = strVal.substring(strVal.length() / 2);
            length = getLength(Long.parseLong(left), iterations - 1) + getLength(Long.parseLong(right), iterations - 1);
        } else {
            length = getLength(num * 2024, iterations - 1);
        }

        cache.put(cacheObject, length);
        return length;
    }

    @Data
    @AllArgsConstructor
    private static class CacheObject {
        private long num;
        private int iterations;
    }
}