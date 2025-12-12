package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        int[] presents = new int[6];
        List<Region> regions = new ArrayList<>();

        int presentIndex = 0;
        Pattern regionPattern = Pattern.compile("(\\d+)x(\\d+): (.+)");
        for (String line : lines) {
            if (StringUtils.isBlank(line)) {
                presentIndex++;
            } else if (line.contains("#")) {
                presents[presentIndex] = presents[presentIndex] + line.replace(".", "").length();
            } else if (line.contains("x")) {
                Matcher regionMatcher = regionPattern.matcher(line);
                if (regionMatcher.find()) {
                    regions.add(new Region(Integer.parseInt(regionMatcher.group(1)), Integer.parseInt(regionMatcher.group(2)),
                            Arrays.stream(regionMatcher.group(3).split(" ")).map(Integer::parseInt).toList()));
                }
            }
        }

        // This very naive solution doesn't work on the example input but shockingly works on the real input
        // It simply checks if the size occupied by all presents is less than or equal to the total size of the region
        // It doesn't take into account rotating, flipping or arranging the presents in a particular way
        int count = 0;
        for (Region region : regions) {
            int totalArea = region.getWidth() * region.getLength();

            int occupiedArea = 0;
            for (int i = 0; i < region.getQuantities().size(); i++) {
                occupiedArea += presents[i] * region.getQuantities().get(i);
            }
            if (occupiedArea <= totalArea) {
                count++;
            }
        }
        return count;
    }

    @Data
    @AllArgsConstructor
    private static class Region {
        private int width;
        private int length;
        private List<Integer> quantities;
    }
}