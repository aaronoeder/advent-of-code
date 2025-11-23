package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Map<Long, List<Integer>> onesMap = new HashMap<>();
        Map<Long, List<Integer>> changesMap = new HashMap<>();

        long sum = 0;
        for (String line : lines) {
            long originalNum = InputUtils.getLongNums(line).get(0);
            onesMap.put(originalNum, new ArrayList<>());
            changesMap.put(originalNum, new ArrayList<>());

            int originalOne = (int)(originalNum % 10);

            long num = originalNum;
            for (int i = 0; i < 2000; i++) {
                num ^= (num * 64);
                num %= 16777216;
                num ^= (num / 32);
                num %= 16777216;
                num ^= (num * 2048);
                num %= 16777216;

                int one = (int)(num % 10);
                onesMap.get(originalNum).add(one);

                if (i == 0) {
                    changesMap.get(originalNum).add(one - originalOne);
                } else {
                    changesMap.get(originalNum).add(one - onesMap.get(originalNum).get(i - 1));
                }
            }
            sum += num;
        }

        if (part == Part.ONE) {
            return sum;
        }

        Map<Sequence, Long> sequencesMap = new HashMap<>();
        for (Map.Entry<Long, List<Integer>> entry : changesMap.entrySet()) {
            long num = entry.getKey();
            List<Integer> changes = entry.getValue();

            List<Sequence> sequencesForNum = new ArrayList<>();
            for (int i = 0; i < changes.size() - 4; i++) {
                Sequence sequence = new Sequence(changes.get(i), changes.get(i + 1), changes.get(i + 2), changes.get(i + 3));
                if (sequencesForNum.contains(sequence)) {
                    continue;
                }
                sequencesForNum.add(sequence);

                long value = onesMap.get(num).get(i + 3);
                sequencesMap.put(sequence, value + sequencesMap.getOrDefault(sequence, 0L));
            }
        }
        return sequencesMap.values().stream().max(Long::compareTo).get();
    }

    @Data
    @AllArgsConstructor
    private static class Sequence {
        private int a;
        private int b;
        private int c;
        private int d;
    }
}