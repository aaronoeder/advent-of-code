package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Map<String, List<String>> neighbors = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(": ");
            neighbors.put(parts[0], Arrays.stream(parts[1].split(" ")).toList());
        }
        CACHE.clear();
        return getPaths(part, neighbors, (part == Part.ONE ? "you" : "svr"), false, false);
    }

    private static final Map<CacheItem, Long> CACHE = new HashMap<>();
    public long getPaths(Part part, Map<String, List<String>> neighbors, String cur, boolean dac, boolean fft) {
        CacheItem cacheItem = new CacheItem(cur, dac, fft);
        if (CACHE.containsKey(cacheItem)) {
            return CACHE.get(cacheItem);
        }

        if (cur.equals("out")) {
            if (part == Part.ONE) {
                return 1;
            }
            return (dac && fft ? 1 : 0);
        }

        long paths = 0;
        for (String neighbor : neighbors.get(cur)) {
            paths += getPaths(part, neighbors, neighbor, dac || neighbor.equals("dac"), fft || neighbor.equals("fft"));
        }
        CACHE.put(cacheItem, paths);
        return paths;
    }

    @Data
    @AllArgsConstructor
    private static class CacheItem {
        private String cur;
        private boolean dac;
        private boolean fft;
    }
}