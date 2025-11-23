package com.aaronoeder.adventofcode.days2024;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.aaronoeder.adventofcode.AdventDay;
import com.aaronoeder.adventofcode.common.Coord;
import com.aaronoeder.adventofcode.common.Direction;
import com.aaronoeder.adventofcode.util.GridUtils;
import com.aaronoeder.adventofcode.util.InputUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day15 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        List<String> gridLines = new ArrayList<>();
        String instructions = "";

        boolean hitNewLine = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                hitNewLine = true;
            } else {
                if (!hitNewLine) {
                    if (part == Part.TWO) {
                        line = line
                                .replace("#", "##")
                                .replace("O", "[]")
                                .replace(".", "..")
                                .replace("@", "@.");
                    }
                    gridLines.add(line);
                } else {
                    instructions += line;
                }
            }
        }

        Character[][] grid = InputUtils.loadLinesIntoGrid(gridLines);
        Coord robotCoord = GridUtils.getCoordInGridHaving(grid, ch -> ch == '@');

        Map<Character, Direction> instructionToDirectionMap = new HashMap<>();
        instructionToDirectionMap.put('^', Direction.N);
        instructionToDirectionMap.put('v', Direction.S);
        instructionToDirectionMap.put('<', Direction.W);
        instructionToDirectionMap.put('>', Direction.E);

        for (int i = 0; i < instructions.length(); i++) {
            char instruction = instructions.charAt(i);
            Direction dir = instructionToDirectionMap.get(instruction);
            move(grid, robotCoord, dir);
        }

        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'O' || grid[i][j] == '[') {
                    sum += 100 * i + j;
                }
            }
        }
        return sum;
    }

    private void move(Character[][] grid, Coord robotCoord, Direction dir) {
        int row = robotCoord.getRow();
        int col = robotCoord.getCol();

        Coord nextCoord = new Coord(robotCoord.getRow() + dir.getRowDiff(), robotCoord.getCol() + dir.getColDiff());
        char nextCoordVal = grid[nextCoord.getRow()][nextCoord.getCol()];

        boolean canMoveRobot = false;
        if (nextCoordVal == '.') {
            canMoveRobot = true;
        } else if (nextCoordVal != '#') {
            Set<Box> boxes = getBoxesInFrontOfRobot(grid, nextCoord, dir);
            boolean canMoveBoxes = boxes.stream().filter(box -> box.isOnFarEnd).allMatch(box -> box.isOnFarEndAndCanMove);
            if (canMoveBoxes) {
                canMoveRobot = true;
                for (Box box : boxes) {
                    for (BoxPiece boxPiece : box.getBoxPieces()) {
                        grid[boxPiece.getCoord().getRow()][boxPiece.getCoord().getCol()] = '.';
                    }
                }
                for (Box box : boxes) {
                    for (BoxPiece boxPiece : box.getBoxPieces()) {
                        grid[boxPiece.getCoord().getRow() + dir.getRowDiff()][boxPiece.getCoord().getCol() + dir.getColDiff()] = boxPiece.getVal();
                    }
                }
            }
        }

        if (canMoveRobot) {
            grid[row][col] = '.';
            grid[row + dir.getRowDiff()][col + dir.getColDiff()] = '@';
            robotCoord.setRow(robotCoord.getRow() + dir.getRowDiff());
            robotCoord.setCol(robotCoord.getCol() + dir.getColDiff());
        }
    }

    private Set<Box> getBoxesInFrontOfRobot(Character[][] grid, Coord boxCoord, Direction dir) {
        Set<Box> boxes = new HashSet<>();

        char boxCoordVal = grid[boxCoord.getRow()][boxCoord.getCol()];

        List<BoxPiece> boxPieces = new ArrayList<>();
        boxPieces.add(new BoxPiece(boxCoord, boxCoordVal));

        if (boxCoordVal == '[') {
            Coord rightHalfCoord = new Coord(boxCoord.getRow(), boxCoord.getCol() + 1);
            char rightHalfCoordVal = grid[rightHalfCoord.getRow()][rightHalfCoord.getCol()];
            boxPieces.add(new BoxPiece(rightHalfCoord, rightHalfCoordVal));
        } else if (boxCoordVal == ']') {
            Coord leftHalfCoord = new Coord(boxCoord.getRow(), boxCoord.getCol() - 1);
            char leftHalfCoordVal = grid[leftHalfCoord.getRow()][leftHalfCoord.getCol()];
            boxPieces.add(new BoxPiece(leftHalfCoord, leftHalfCoordVal));
        }

        List<BoxPiece> nextBoxPieces = new ArrayList<>();
        for (BoxPiece boxPiece : boxPieces) {
            Coord nextBoxPieceCoord = new Coord(boxPiece.getCoord().getRow() + dir.getRowDiff(), boxPiece.getCoord().getCol() + dir.getColDiff());
            BoxPiece nextBoxPiece = new BoxPiece(nextBoxPieceCoord, grid[nextBoxPieceCoord.getRow()][nextBoxPieceCoord.getCol()]);
            if (!boxPieces.contains(nextBoxPiece)) {
                nextBoxPieces.add(nextBoxPiece);
            }
        }

        if (nextBoxPieces.stream().allMatch(nextBoxPiece -> nextBoxPiece.getVal() == '.')) {
            boxes.add(new Box(boxPieces, true, true));
        } else if (nextBoxPieces.stream().anyMatch(nextBoxPiece -> nextBoxPiece.getVal() == '#')) {
            boxes.add(new Box(boxPieces, true, false));
        } else {
            boxes.add(new Box(boxPieces, false, false));
            for (BoxPiece nextBoxPiece : nextBoxPieces) {
                if (nextBoxPiece.getVal() == 'O' || nextBoxPiece.getVal() == '[' || nextBoxPiece.getVal() == ']') {
                    boxes.addAll(getBoxesInFrontOfRobot(grid, nextBoxPiece.getCoord(), dir));
                }
            }
        }
        return boxes;
    }

    @Data
    @AllArgsConstructor
    private static class Box {
        List<BoxPiece> boxPieces;
        private boolean isOnFarEnd;
        private boolean isOnFarEndAndCanMove;
    }

    @Data
    @AllArgsConstructor
    private static class BoxPiece {
        private Coord coord;
        private char val;
    }
}