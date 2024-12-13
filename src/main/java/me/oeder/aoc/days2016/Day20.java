package me.oeder.aoc.days2016;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day20 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        if (part == Part.ONE) {
            return getMin(lines, Part.ONE);
        }
        return getMin(lines, Part.TWO);
    }

    private long getMin(List<String> lines, Part part) {
        List<Range> ranges = new ArrayList<>();
        for (String line : lines) {
            String[] lineParts = line.split("-");
            long min = Long.parseLong(lineParts[0]);
            long max = Long.parseLong(lineParts[1]);
            ranges.add(new Range(min, max));
        }
        Collections.sort(ranges, (r1, r2) -> Long.compare(r1.getMin(), r2.getMin()));

        long count = 0;
        long index = 0;
        while (index <= 4294967295L) {
            if (isWhitelisted(ranges, index)) {
                if (part == Part.ONE) {
                    return index;
                } else {
                    count++;
                }
            }
            for (Range range : ranges) {
                if (range.isInRange(index)) {
                    index = range.getMax();
                }
            }
            index++;
        }
        return count;
    }

    private boolean isWhitelisted(List<Range> ranges, long index) {
        for (Range range : ranges) {
            if (range.isInRange(index)) {
                return false;
            }
        }
        return true;
    }

    @Data
    @AllArgsConstructor
    private class Range {
        private long min;
        private long max;

        public boolean isInRange(long index) {
            return index >= min && index <= max;
        }
    }
}