package org.example.backend.Item;

public enum ItemsIcons {
    FOOD('&'),
    ROLL('='),
    ELIX('*'),
    TREASURE('$'),
    WEAPON('~'),
    EXIT('E');
    private char symbol;

    ItemsIcons(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
