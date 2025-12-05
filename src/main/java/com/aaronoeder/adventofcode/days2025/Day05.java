package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day05 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        long total = 0;

        List<Range> ranges = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        boolean hitBlank = false;
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                hitBlank = true;
            } else if (!hitBlank) {
                String[] parts = line.split("-");
                ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            } else {
                values.add(Long.parseLong(line));
            }
        }

        if (part == Part.ONE) {
            for (long value : values) {
                for (Range range : ranges) {
                    if (value >= range.getStart() && value <= range.getEnd()) {
                       total++;
                       break;
                    }
                }
            }
        } else {
            while (consolidateRanges(ranges));
            for (Range range : ranges) {
                total += (range.getEnd() - range.getStart() + 1);
            }
        }
        return total;
    }

    private boolean consolidateRanges(List<Range> ranges) {
        for (int i = 0; i < ranges.size() - 1; i++) {
            for (int j = i + 1; j < ranges.size(); j++) {
                Range r1 = ranges.get(i);
                Range r2 = ranges.get(j);

                Range mergedRange = getMergedRange(r1, r2);
                if (mergedRange != null) {
                    ranges.remove(r1);
                    ranges.remove(r2);
                    ranges.add(getMergedRange(r1, r2));
                    return true;
                }
            }
        }
        return false;
    }

    // Must consider three cases:
    // 1) ranges chained together
    //  [1, 5]
    //  [2, 7]
    // 2) one range nested inside the other
    //  [1, 5]
    //  [2, 4]
    // 3) both ranges starting from the same point
    //  [1, 4]
    //  [1, 8]
    private Range getMergedRange(Range r1, Range r2) {
        List<Range> ranges = Stream.of(r1, r2).sorted().toList();

        Range first = ranges.getFirst();
        Range last = ranges.getLast();

        // No overlap
        if (last.getStart() > first.getEnd()) {
            return null;
        }

        if (last.getStart() > first.getStart() && last.getEnd() < first.getEnd()) {
            return new Range(first.getStart(), first.getEnd());
        }

        return new Range(first.getStart(), last.getEnd());
    }

    @Data
    @AllArgsConstructor
    private static class Range implements Comparable<Range> {
        private long start;
        private long end;

        @Override
        public int compareTo(Range other) {
            if (this.start != other.start) {
                return (this.start < other.start ? -1 : 1);
            }
            if (this.end != other.end) {
                return (this.end < other.end ? -1 : 1);
            }
            return 0;
        }
    }
}