package me.oeder.aoc.days2016;

import lombok.Data;
import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Map<Integer, Output> outputs = new TreeMap<>();
        Map<Integer, Bot> bots = new TreeMap<>();

        // Init bots
        for (int i = 0; i <= 209; i++) {
            bots.put(i, new Bot());
        }

        // Init outputs
        for (int i = 0; i <= 21; i++) {
            outputs.put(i, new Output());
        }

        Pattern takePattern = Pattern.compile("value (\\d+) goes to bot (\\d+)");
        for (String line : lines) {
            if (line.startsWith("value")) {
                Matcher matcher = takePattern.matcher(line);
                if (matcher.matches()) {
                    int val = Integer.parseInt(matcher.group(1));
                    int botId = Integer.parseInt(matcher.group(2));
                    bots.get(botId).getChips().add(val);
                }
            }
        }

        // Set bot instructions
        for (String line : lines) {
            if (line.startsWith("bot")) {
                String[] parts = line.split(" ");
                bots.get(Integer.parseInt(parts[1])).setInstruction(line);
            }
        }

        Pattern givePattern = Pattern.compile("bot (\\d+) gives ([a-z]+) to (bot|output) (\\d+) and ([a-z]+) to (bot|output) (\\d+)");
        while (true) {
            for (int i = 0; i <= 209; i++) {
                Bot bot = bots.get(i);

                if (part == Part.ONE) {
                    if (bot.getChips().contains(61) && bot.getChips().contains(17)) {
                        return i;
                    }
                } else {
                    if (!outputs.get(0).getChips().isEmpty() && !outputs.get(1).getChips().isEmpty() && !outputs.get(2).getChips().isEmpty()) {
                        return outputs.get(0).getChips().get(0) * outputs.get(1).getChips().get(0) * outputs.get(2).getChips().get(0);
                    }
                }

                if (bot.canGiveChips()) {
                    giveChips(givePattern, bot, bots, outputs);
                }
            }
        }
    }

    private void giveChips(Pattern givePattern, Bot bot, Map<Integer, Bot> bots, Map<Integer, Output> outputs) {
        Matcher matcher = givePattern.matcher(bot.getInstruction());
        if (matcher.matches()) {
            int giverBotId = Integer.parseInt(matcher.group(1));
            boolean isFirstTakerLow = matcher.group(2).equals("low");
            boolean isFirstTakerBot = matcher.group(3).equals("bot");
            int firstTakerId = Integer.parseInt(matcher.group(4));
            boolean isSecondTakerLow = matcher.group(5).equals("low");
            boolean isSecondTakerBot = matcher.group(6).equals("bot");
            int secondTakerId = Integer.parseInt(matcher.group(7));

            Bot giverBot = bots.get(giverBotId);
            if (isFirstTakerBot) {
                if (isFirstTakerLow) {
                    bots.get(firstTakerId).getChips().add(giverBot.giveLowChip());
                } else {
                    bots.get(firstTakerId).getChips().add(giverBot.giveHighChip());
                }
            } else {
                if (isFirstTakerLow) {
                    outputs.get(firstTakerId).getChips().add(giverBot.giveLowChip());
                } else {
                    outputs.get(firstTakerId).getChips().add(giverBot.giveHighChip());
                }
            }
            if (isSecondTakerBot) {
                if (isSecondTakerLow) {
                    bots.get(secondTakerId).getChips().add(giverBot.giveLowChip());
                } else {
                    bots.get(secondTakerId).getChips().add(giverBot.giveHighChip());
                }
            } else {
                if (isSecondTakerLow) {
                    outputs.get(secondTakerId).getChips().add(giverBot.giveLowChip());
                } else {
                    outputs.get(secondTakerId).getChips().add(giverBot.giveHighChip());
                }
            }
        }
    }

    @Data
    private static class Output {
        private List<Integer> chips = new ArrayList<>();
    }

    @Data
    private static class Bot {
        private List<Integer> chips = new ArrayList<>();
        private String instruction;

        public boolean canGiveChips() {
            return chips.size() == 2;
        }

        public int giveLowChip() {
            Collections.sort(chips);
            return chips.remove(0);
        }

        public int giveHighChip() {
            Collections.sort(chips, (n1, n2) -> n2 - n1);
            return chips.remove(0);
        }
    }
}