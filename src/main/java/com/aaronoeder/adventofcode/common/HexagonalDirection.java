package com.aaronoeder.adventofcode.common;

import lombok.Getter;

@Getter
public enum HexagonalDirection {

    NW(-0.5, -1),
    N(-1, 0),
    NE(-0.5, 1),
    SW(0.5, -1),
    S(1, 0),
    SE(0.5, 1);

    private final double rowDiff;
    private final double colDiff;

    HexagonalDirection(double rowDiff, double colDiff) {
        this.rowDiff = rowDiff;
        this.colDiff = colDiff;
    }
}