package me.oeder.aoc.days2021;

import java.util.List;

import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import me.oeder.aoc.graph.AStar;
import me.oeder.aoc.graph.Graph;
import me.oeder.aoc.graph.Node;
import me.oeder.aoc.util.DistanceUtils;
import me.oeder.aoc.util.InputUtils;

public class Day15 extends AdventDay {

	@Override
	public Object getAnswer(List<String> lines, Part part) {
		Character[][] grid = InputUtils.loadLinesIntoGrid(lines);
		
		Node first = null;
		Node last = null;
		Graph graph = new Graph();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				Node node = new Node(getKey(i, j));
				if (first == null) {
					first = node;
				}
				last = node;
				graph.addNode(node);
			}
		}
		
		for (Node node : graph.getNodes()) {
			Coord coord = getCoord(node.getName());
			List<Coord> neighbors = coord.getNeighbors(false);
			for (Coord neighbor : neighbors) {
				if (neighbor.getRow() < 0 || neighbor.getRow() >= grid.length || neighbor.getCol() < 0 || neighbor.getCol() >= grid[0].length) {
					continue;
				}
				Node neighborNode = graph.getNodes().stream().filter(n -> n.getName().equals(getKey(neighbor.getRow(), neighbor.getCol()))).findFirst().get();
				node.addDestination(neighborNode, Character.getNumericValue(grid[neighbor.getRow()][neighbor.getCol()]));
			}
		}
		
		final Node end = last;
		AStar.traverseGraph(graph, first, end, (cur, e) -> getDistance(cur, e));
		
		return graph.getNodes().stream().filter(n -> n.getName().equals(end.getName())).findFirst().get().getDistance();
	}
	
	private String getKey(int i, int j) {
		return i + "," + j;
	}
	
	private Coord getCoord(String key) {
		String[] parts = key.split(",");
		return new Coord(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}
	
	private double getDistance(Node n1, Node n2) {
		Coord c1 = getCoord(n1.getName());
		Coord c2 = getCoord(n2.getName());
		return DistanceUtils.getTaxiCabDistance(c1, c2);
	}
}