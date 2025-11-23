package com.aaronoeder.adventofcode.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node {
    private String name;
    private List<Node> shortestPath;
    private Double distance;
    private Map<Node, Double> adjacentNodes;
    
    public Node(String name) {
        this.name = name;
        this.shortestPath = new LinkedList<>();
        this.distance = Double.MAX_VALUE;
        this.adjacentNodes = new HashMap<>();
    }
    
    public void addDestination(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }
}