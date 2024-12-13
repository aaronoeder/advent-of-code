package me.oeder.aoc.common;

public enum HexagonalDirection {

    NW(-0.5, -1),
    N(-1, 0),
    NE(-0.5, 1),
    SW(0.5, -1),
    S(1, 0),
    SE(0.5, 1);

    private double rowDiff;
    private double colDiff;

    private HexagonalDirection(double rowDiff, double colDiff) {
        this.rowDiff = rowDiff;
        this.colDiff = colDiff;
    }

    public double getRowDiff() {
        return rowDiff;
    }

    public double getColDiff() {
        return colDiff;
    }
}