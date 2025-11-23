package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        long prizeOffset = part == Part.ONE ? 0 : 10000000000000L;

        List<ClawMachine> clawMachines = new ArrayList<>();
        Point buttonA = null;
        Point buttonB = null;
        Point prize = null;
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (line.isEmpty()) {
                clawMachines.add(new ClawMachine(buttonA, buttonB, prize));
                buttonA = null;
                buttonB = null;
                prize = null;
            } else if (line.startsWith("Button A")) {
                int x = Integer.parseInt(parts[2].replace("X+", "").replace(",", ""));
                int y = Integer.parseInt(parts[3].replace("Y+", ""));
                buttonA = new Point(x, y);
            } else if (line.startsWith("Button B")) {
                int x = Integer.parseInt(parts[2].replace("X+", "").replace(",", ""));
                int y = Integer.parseInt(parts[3].replace("Y+", ""));
                buttonB = new Point(x, y);
            } else if (line.startsWith("Prize")) {
                int x = Integer.parseInt(parts[1].replace("X=", "").replace(",", ""));
                int y = Integer.parseInt(parts[2].replace("Y=", ""));
                prize = new Point(prizeOffset + x, prizeOffset + y);
            }
        }
        clawMachines.add(new ClawMachine(buttonA, buttonB, prize));

        long sum = 0;
        for (ClawMachine clawMachine : clawMachines) {
            Point pointOfIntersection = getPointOfIntersection(clawMachine);
            if (pointOfIntersection != null) {
                sum += 3 * pointOfIntersection.getX() + pointOfIntersection.getY();
            }
        }
        return sum;
    }

    /*
     * To find the number of "A" presses and "B" presses, we can set up a system of linear equations
     * For example, if we have:
     *
     * Button A: X+94, Y+34
     * Button B: X+22, Y+67
     * Prize: X=8400, Y=5400
     *
     * Then:
     * 94x + 22y = 8400
     * 34x + 67y = 5400
     *
     * This will always have a solution, but it's only possible in the context of the claw machine if (x, y) are integers
     */
    private Point getPointOfIntersection(ClawMachine clawMachine) {
        double a1 = clawMachine.getButtonA().getX();
        double b1 = clawMachine.getButtonB().getX();
        double c1 = clawMachine.getPrize().getX();

        double a2 = clawMachine.getButtonA().getY();
        double b2 = clawMachine.getButtonB().getY();
        double c2 = clawMachine.getPrize().getY();

        double x = (b2 * c1 - b1 * c2) / (a1 * b2 - a2 * b1);
        double y = (c2 * a1 - c1 * a2) / (a1 * b2 - a2 * b1);

        if (Math.abs(x - (long)x) < 0.001) {
            return new Point((long)x, (long)y);
        }
        return null;
    }

    @Data
    @AllArgsConstructor
    private static class ClawMachine {
        private Point buttonA;
        private Point buttonB;
        private Point prize;
    }

    @Data
    @AllArgsConstructor
    private static class Point {
        private long x;
        private long y;
    }
}