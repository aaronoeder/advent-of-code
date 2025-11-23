package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day14 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<Robot> robots = new ArrayList<>();
        for (String line : lines) {
            List<Integer> nums = InputUtils.getNums(line);
            robots.add(new Robot(new Coord(nums.get(1), nums.get(0)), new Coord(nums.get(3), nums.get(2))));
        }

        int rows = 103;
        int cols = 101;

        for (int i = 1; i <= (part == Part.ONE ? 100 : Integer.MAX_VALUE); i++) {
            for (Robot robot : robots) {
                int newRow = robot.getPosition().getRow() + robot.getVelocity().getRow();
                int newCol = robot.getPosition().getCol() + robot.getVelocity().getCol();

                if (newRow < 0) {
                    newRow = rows + newRow;
                } else if (newRow >= rows) {
                    newRow = newRow % rows;
                }
                if (newCol < 0) {
                    newCol = cols + newCol;
                } else if (newCol >= cols) {
                    newCol = newCol % cols;
                }
                robot.setPosition(new Coord(newRow, newCol));
            }
            if (part == Part.TWO && areRobotsPositionedInLineWithLength(robots, 10)) {
                return i;
            }
        }
        return getSafetyFactor(robots, rows, cols);
    }

    private int getSafetyFactor(List<Robot> robots, int rows, int cols) {
        int topLeft = 0;
        int topRight = 0;
        int bottomLeft = 0;
        int bottomRight = 0;
        for (Robot robot : robots) {
            int middleRow = rows / 2;
            int middleCol = cols / 2;

            int row = robot.getPosition().getRow();
            int col = robot.getPosition().getCol();

            if (row == middleRow || col == middleCol) {
                continue;
            }
            if (row < middleRow) {
                if (col < middleCol) {
                    topLeft++;
                } else {
                    topRight++;
                }
            } else {
                if (col < middleCol) {
                    bottomLeft++;
                } else {
                    bottomRight++;
                }
            }
        }
        return topLeft * topRight * bottomLeft * bottomRight;
    }

    private boolean areRobotsPositionedInLineWithLength(List<Robot> robots, int length) {
        Collections.sort(robots, (r1, r2) -> {
            if (r1.getPosition().getRow() != r2.getPosition().getRow()) {
                return r1.getPosition().getRow() - r2.getPosition().getRow();
            }
            return r1.getPosition().getCol() - r2.getPosition().getCol();
        });

        for (int i = 0; i < robots.size() - length; i++) {
            Robot robot = robots.get(i);
            Robot robotAtEndOfLength = robots.get(i + length);
            if (robot.getPosition().getRow() == robotAtEndOfLength.getPosition().getRow() &&
                robot.getPosition().getCol() + length == robotAtEndOfLength.getPosition().getCol()) {
                return true;
            }
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    private static class Robot {
        private Coord position;
        private Coord velocity;
    }
}