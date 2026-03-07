package org.example.backend.Entity;

public enum Entities {
    PLAYER('@'),
    ZOMBIE('Z');
    private char symbol;

    Entities(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
