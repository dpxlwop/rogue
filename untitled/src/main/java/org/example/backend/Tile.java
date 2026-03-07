package org.example.backend;

public enum Tile {
    WALL('#'),
    FLOOR('.');
    private char symbol;

    Tile(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
