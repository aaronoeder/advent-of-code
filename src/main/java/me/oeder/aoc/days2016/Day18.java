package me.oeder.aoc.days2016;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.common.Direction;
import me.oeder.aoc.util.InputUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<List<Character>> rows = new ArrayList<>();

        List<Character> firstRow = new ArrayList<>();
        for (char ch : lines.get(0).toCharArray()) {
            firstRow.add(ch);
        }
        rows.add(firstRow);

        for (int i = 1; i < (part == Part.ONE ? 40 : 400000); i++) {
            List<Character> newRow = new ArrayList<>();
            List<Character> prevRow = rows.get(i - 1);
            for (int j = 0; j < prevRow.size(); j++) {
                char left = j > 0 ? prevRow.get(j - 1) : '.';
                char center = prevRow.get(j);
                char right = j < prevRow.size() - 1 ? prevRow.get(j + 1) : '.';

                boolean isTrap = false;
                if (isTrap(left) && isTrap(center) && !isTrap(right)) {
                    isTrap = true;
                } else if (isTrap(center) && isTrap(right) && !isTrap(left)) {
                    isTrap = true;
                } else if (isTrap(left) && !isTrap(center) && !isTrap(right)) {
                    isTrap = true;
                } else if (!isTrap(left) && !isTrap(center) && isTrap(right)) {
                    isTrap = true;
                }
                newRow.add(isTrap ? '^' : '.');
            }
            rows.add(newRow);
        }

        return rows.stream().flatMap(list -> list.stream()).filter(ch -> !isTrap(ch)).count();
    }

    private boolean isTrap(char ch) {
        return ch == '^';
    }
}