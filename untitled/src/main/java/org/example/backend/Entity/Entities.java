package org.example.backend.Entity;

public enum Entities {
    PLAYER('@'),
    ZOMBIE('Z'),
    VAMPIRE('V'),
    GHOST('G'),
    OGRE('O'),
    MAGICSNAKE('S');
    private char symbol;

    Entities(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
