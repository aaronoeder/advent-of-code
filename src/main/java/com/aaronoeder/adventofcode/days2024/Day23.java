package com.aaronoeder.adventofcode.days2024;

import com.aaronoeder.adventofcode.AdventDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        Map<String, List<String>> nodes = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split("-");

            String left = parts[0];
            String right = parts[1];

            if (!nodes.containsKey(left)) {
                nodes.put(left, new ArrayList<>());
            }
            nodes.get(left).add(right);

            if (!nodes.containsKey(right)) {
                nodes.put(right, new ArrayList<>());
            }
            nodes.get(right).add(left);
        }

        if (part == Part.ONE) {
            int sum = 0;
            for (String firstNode : nodes.keySet()) {
                for (String secondNode : nodes.get(firstNode)) {
                    for (String thirdNode : nodes.get(firstNode)) {
                        boolean isConnected = nodes.get(secondNode).contains(thirdNode);
                        boolean isHistorian = Arrays.asList(firstNode, secondNode, thirdNode).stream().anyMatch(node -> node.startsWith("t"));
                        if (isConnected && isHistorian) {
                            sum++;
                        }
                    }
                }
            }
            return sum / 6; // Divide by 3! (=6) to account for the different possible orderings of the 3 nodes
        }

        Set<Set<String>> groups = new HashSet<>();
        for (String node : nodes.keySet()) {
            Set<String> visitedNodes = new HashSet<>();
            Set<String> connectedNodes = new HashSet<>();

            Queue<String> queue = new LinkedList<>();
            queue.add(node);

            while (!queue.isEmpty()) {
                String nextNode = queue.poll();
                if (!visitedNodes.contains(nextNode) && nodes.get(nextNode).containsAll(connectedNodes)) {
                    visitedNodes.add(nextNode);
                    connectedNodes.add(nextNode);
                    for (String adjacentNode : nodes.get(nextNode)) {
                        if (!visitedNodes.contains(adjacentNode)) {
                            queue.add(adjacentNode);
                        }
                    }
                }
            }
            groups.add(connectedNodes);
        }

        return groups.stream()
                .max((s1, s2) -> s1.size() - s2.size())
                .get()
                .stream()
                .sorted()
                .collect(Collectors.joining(","));
    }
}