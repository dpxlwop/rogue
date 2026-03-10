package org.example.ui;
import com.googlecode.lanterna.input.KeyType;
import org.example.backend.Entity.Player;
import com.googlecode.lanterna.input.KeyStroke;

import com.googlecode.lanterna.screen.TerminalScreen;


public class KeyHandler {
    private TerminalScreen screen;

    public KeyHandler(TerminalScreen screen) {
        this.screen = screen;
    }

    public int[] handleInput(Player player) throws Exception {
        KeyStroke key = screen.pollInput();
        int[] move = new int[]{0,0};
        while (key == null){
            key = screen.pollInput();
            if (key != null) {
                move = processInput(player, key);
            }
        }
        return move;
    }

    public int[] processInput(Player player, KeyStroke key) throws Exception {
        int[] move = new int[]{0,0};
        if (key != null && key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'w' -> {
                    move[0] = 0;
                    move[1] = -1;
                } case 's' -> {
                    move[0] = 0;
                    move[1] = 1;
                } case 'a' -> {
                    move[0] = -1;
                    move[1] = 0;
                } case 'd' -> {
                    move[0] = 1;
                    move[1] = 0;
                } case 'q' -> {
                    move[0] = -999;
                    move[1] = -999;
                } case '1' -> {
                    move[0] = 100;       //для всех кнопок инвентаря move[0] = 100
                    move[1] = 1;
                } case '2' -> {
                    move[0] = 100;
                    move[1] = 2;
                } case '3' -> {
                    move[0] = 100;
                    move[1] = 3;
                } case '4' -> {
                    move[0] = 100;
                    move[1] = 4;
                } case '5' -> {
                    move[0] = 100;
                    move[1] = 5;
                } case '6' -> {
                    move[0] = 100;
                    move[1] = 6;
                } case '7' -> {
                    move[0] = 100;
                    move[1] = 7;
                } case '8' -> {
                    move[0] = 100;
                    move[1] = 8;
                } case '9' -> {
                    move[0] = 100;
                    move[1] = 9;
                }
            }
        }
        return move;
    }

}
