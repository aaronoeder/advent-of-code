package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.InputUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        long total = 0;
        List<Problem> problems = (part == Part.ONE ? getProblemsPart1(lines) : getProblemsPart2(lines));
        for (Problem problem : problems) {
            total += problem.getTotal();
        }
        return total;
    }

    private List<Problem> getProblemsPart1(List<String> lines) {
        List<Problem> problems = new ArrayList<>();
        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            if (row < lines.size() - 1) {
                List<Long> numbers = InputUtils.getLongNums(line);
                for (int j = 0; j < numbers.size(); j++) {
                    if (problems.size() <= j) {
                        problems.add(new Problem());
                    }
                    problems.get(j).getNumbers().add(numbers.get(j));
                }
            } else {
                List<String> operations = Arrays.stream(line.replace(" ", "").split("")).toList();
                for (int j = 0; j < operations.size(); j++) {
                    problems.get(j).setOperation(operations.get(j));
                }
            }
        }
        return problems;
    }

    private List<Problem> getProblemsPart2(List<String> lines) {
        int maxLengthCol = 0;
        for (String line : lines) {
            maxLengthCol = Math.max(maxLengthCol, line.length());
        }

        List<Problem> problems = new ArrayList<>();
        Problem currentProblem = new Problem();
        for (int col = 0; col < maxLengthCol; col++) {
            String assembledNumber = "";
            for (int row = 0; row < lines.size() - 1; row++) {
                String ch = getSafeSubstring(lines.get(row), col);
                if (StringUtils.isNotBlank(ch)) {
                    assembledNumber += ch;
                }
            }

            String operation = getSafeSubstring(lines.getLast(), col);
            if (StringUtils.isNotBlank(operation)) {
                currentProblem.setOperation(operation);
            }

            if (StringUtils.isNotBlank(assembledNumber)) {
                currentProblem.getNumbers().add(Long.parseLong(assembledNumber));
            } else {
                problems.add(currentProblem);
                currentProblem = new Problem();
            }
        }
        problems.add(currentProblem);
        return problems;
    }

    private String getSafeSubstring(String line, int index) {
        if (index < line.length()) {
            return line.substring(index, index + 1);
        }
        return "";
    }

    @Data
    private static class Problem {
        private List<Long> numbers = new ArrayList<>();
        private String operation;

        public long getTotal() {
            if (operation.equals("+")) {
                return numbers.stream().reduce(0L, (acc, cur) -> acc + cur);
            } else {
                return numbers.stream().reduce(1L, (acc, cur) -> acc * cur);
            }
        }
    }
}