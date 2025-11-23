package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<String> options = Arrays.asList(lines.get(0).split(", "));

        long sum = 0;
        for (int i = 2; i < lines.size(); i++) {
            long count = getArrangementCount(lines.get(i), "", options);
            sum += (part == Part.ONE ? Math.min(1, count) : count);
        }
        return sum;
    }

    private Map<CacheObject, Long> cache = new HashMap<>();
    private long getArrangementCount(String design, String curDesign, List<String> options) {
        CacheObject cacheObject = new CacheObject(design, curDesign);
        if (cache.containsKey(cacheObject)) {
            return cache.get(cacheObject);
        }

        long count = 0;
        if (curDesign.equals(design)) {
            count = 1;
        } else {
            String remainingDesign = design.substring(curDesign.length());
            for (String option : options) {
                if (remainingDesign.startsWith(option) && (curDesign + option).length() <= design.length()) {
                   count += getArrangementCount(design, curDesign + option, options);
                }
            }
        }
        cache.put(cacheObject, count);
        return count;
    }

    @Data
    @AllArgsConstructor
    private static class CacheObject {
        private String design;
        private String curDesign;
    }
}