package com.aaronoeder.adventofcode.graph;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Graph {
    private Set<Node> nodes;
    
    public Graph() {
    	this.nodes = new HashSet<>();
    }
    
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }
}