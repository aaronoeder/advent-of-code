package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day24 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Map<String, Integer> values = new HashMap<>();
        List<Instruction> instructions = new ArrayList<>();

        boolean hitNewLine = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                hitNewLine = true;
            } else {
                if (!hitNewLine) {
                    String[] parts = line.split(": ");
                    values.put(parts[0], Integer.parseInt(parts[1]));
                } else {
                    String[] parts = line.split(" -> ");
                    String[] leftParts = parts[0].split(" ");
                    instructions.add(new Instruction(leftParts[0], leftParts[1], leftParts[2], parts[1]));
                }
            }
        }

        if (part == Part.ONE) {
            return getValueOfZ(instructions, values);
        }
        return getSwappedOutputs(instructions).stream()
                .sorted()
                .collect(Collectors.joining(","));
    }

    private long getValueOfZ(List<Instruction> instructions, Map<String, Integer> values) {
        List<String> zOutputs = instructions.stream()
                .map(Instruction::getOutput)
                .filter(output -> output.startsWith("z"))
                .sorted()
                .collect(Collectors.toList());

        int index = 0;
        while (true) {
            Instruction instruction = instructions.get(index);
            if (values.containsKey(instruction.getLeft()) && values.containsKey(instruction.getRight())) {
                int left = values.get(instruction.getLeft());
                int right = values.get(instruction.getRight());

                int output = 0;
                if (instruction.getOperator().equals("AND")) {
                    output = left & right;
                } else if (instruction.getOperator().equals("OR")) {
                    output = left | right;
                } else if (instruction.getOperator().equals("XOR")) {
                    output = left ^ right;
                }
                values.put(instruction.getOutput(), output);

                if (values.keySet().containsAll(zOutputs)) {
                    break;
                }
            }

            index++;
            if (index == instructions.size()) {
                index = 0;
            }
        }

        long amount = 0;
        for (int i = 0; i < zOutputs.size(); i++) {
            amount += (long)(Math.pow(2, i) * values.get(zOutputs.get(i)));
        }
        return amount;
    }

    private List<String> getSwappedOutputs(List<Instruction> instructions) {
        List<String> swappedOutputs = new ArrayList<>();
        for (int i = 0; i < 45; i++) {
            String n = StringUtils.leftPad(String.valueOf(i), 2, '0');
            String xor = getOutputHaving(instructions, "x" + n, "y" + n, "XOR");
            String and = getOutputHaving(instructions, "x" + n, "y" + n, "AND");
            Instruction z = instructions.stream()
                    .filter(instruction -> instruction.getOutput().equals("z" + n))
                    .findFirst()
                    .orElse(null);

            if (xor == null || and == null || z == null) {
                continue;
            }

            if (!z.getOperator().equals("XOR")) {
                swappedOutputs.add(z.getOutput());
            }

            Instruction or = instructions.stream()
                    .filter(instruction -> instruction.getLeft().equals(and) || instruction.getRight().equals(and))
                    .findFirst()
                    .orElse(null);
            if (or != null && !or.getOperator().equals("OR") && i > 0) {
                swappedOutputs.add(and);
            }

            Instruction after = instructions.stream()
                    .filter(instruction -> instruction.getLeft().equals(xor) || instruction.getRight().equals(xor))
                    .findFirst()
                    .orElse(null);
            if (after != null && after.getOperator().equals("OR")) {
                swappedOutputs.add(xor);
            }
        }
        swappedOutputs.addAll(instructions.stream()
                .filter(instruction -> {
                    return !instruction.getLeft().startsWith("x") &&
                        !instruction.getLeft().startsWith("y") &&
                        !instruction.getRight().startsWith("x") &&
                        !instruction.getRight().startsWith("y") &&
                        !instruction.getOutput().startsWith("z") &&
                        instruction.getOperator().equals("XOR");
                })
                .map(Instruction::getOutput)
                .collect(Collectors.toList()));

        return swappedOutputs;
    }

    private String getOutputHaving(List<Instruction> instructions, String operand1, String operand2, String operator) {
        return instructions.stream()
                .filter(i -> {
                    if (!i.getOperator().equals(operator)) {
                        return false;
                    }
                    if (i.getLeft().equals(operand1) && i.getRight().equals(operand2)) {
                        return true;
                    }
                    if (i.getLeft().equals(operand2) && i.getRight().equals(operand1)) {
                        return true;
                    }
                    return false;
                })
                .map(Instruction::getOutput)
                .findFirst()
                .orElse(null);
    }

    @Data
    @AllArgsConstructor
    private static class Instruction {
        private String left;
        private String operator;
        private String right;
        private String output;
    }
}