package com.aaronoeder.adventofcode.days2025;

import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.util.InputUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Coord3D> coords = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            List<Integer> nums = InputUtils.getNums(lines.get(i));
            coords.add(new Coord3D(i, nums.getFirst(), nums.get(1), nums.getLast(), new HashSet<>()));
        }

        List<PossibleConnection> possibleConnections = new ArrayList<>();
        for (int i = 0; i < coords.size() - 1; i++) {
            for (int j = i + 1; j < coords.size(); j++) {
                Coord3D left = coords.get(i);
                Coord3D right = coords.get(j);
                double distance = getDistance(left, right);
                possibleConnections.add(new PossibleConnection(left, right, distance));
            }
        }
        possibleConnections = new ArrayList<>(possibleConnections.stream()
                .sorted(Comparator.comparingDouble(PossibleConnection::getDistance))
                .toList());

        int connectionsAttempted = 0;
        while (true) {
            PossibleConnection nextBestConnection = possibleConnections.removeFirst();

            Coord3D left = nextBestConnection.getLeft();
            Coord3D right = nextBestConnection.getRight();

            left.getConnections().add(right.getId());
            right.getConnections().add(left.getId());

            connectionsAttempted++;
            if (part == Part.ONE && connectionsAttempted == 1000) {
                Set<Set<Coord3D>> circuits = getCircuits(coords);
                List<Integer> circuitSizes = circuits.stream()
                        .map(Set::size)
                        .sorted((a, b) -> b - a)
                        .toList();

                long product = 1;
                for (int i = 0; i < 3; i++) {
                    product *= circuitSizes.get(i);
                }
                return product;
            }

            Set<Coord3D> firstCircuit = new HashSet<>();
            populateCircuit(coords.getFirst(), coords, firstCircuit);
            if (firstCircuit.size() == coords.size()) {
                return left.getX() * right.getX();
            }
        }
    }

    private double getDistance(Coord3D left, Coord3D right) {
        int dx = Math.abs(left.getX() - right.getX());
        int dy = Math.abs(left.getY() - right.getY());
        int dz = Math.abs(left.getZ() - right.getZ());
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2) + Math.pow(dz, 2));
    }

    private Set<Set<Coord3D>> getCircuits(List<Coord3D> coords) {
        Set<Set<Coord3D>> circuits = new HashSet<>();
        for (Coord3D coord : coords) {
            Set<Coord3D> circuit = new HashSet<>();
            populateCircuit(coord, coords, circuit);
            circuits.add(circuit);
        }
        return circuits;
    }

    private void populateCircuit(Coord3D coord, List<Coord3D> coords, Set<Coord3D> circuit) {
        for (int neighborId : coord.getConnections()) {
            Coord3D neighbor = coords.get(neighborId);
            if (!circuit.contains(neighbor)) {
                circuit.add(neighbor);
                populateCircuit(neighbor, coords, circuit);
            }
        }
    }

    @Data
    @AllArgsConstructor
    private static class Coord3D {
        private int id;
        private int x;
        private int y;
        private int z;
        private Set<Integer> connections;
    }

    @Data
    @AllArgsConstructor
    private static class PossibleConnection {
        private Coord3D left;
        private Coord3D right;
        private double distance;
    }
}