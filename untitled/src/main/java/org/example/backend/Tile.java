package org.example.backend;

public enum Tile {
    WALL((char)219),
    FLOOR('.');
    private char symbol;

    Tile(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
