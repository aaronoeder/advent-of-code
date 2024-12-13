package me.oeder.aoc.days2016;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Disc> discs = new ArrayList<>();

        Pattern pattern = Pattern.compile("Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+).");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                discs.add(new Disc(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }
        }
        if (part == Part.TWO) {
            discs.add(new Disc(11, 0));
        }

        int t = 0;
        while (true) {
            boolean valid = true;
            for (int i = 0; i < discs.size(); i++) {
                int elapsed = i + 1;
                Disc disc = discs.get(i);
                int pos = (disc.getInitialPosition() + t + elapsed) % disc.getPositions();
                if (pos != 0) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                return t;
            }

            t++;
        }
    }

    @Data
    @AllArgsConstructor
    private static class Disc {
        private int positions;
        private int initialPosition;
    }
}