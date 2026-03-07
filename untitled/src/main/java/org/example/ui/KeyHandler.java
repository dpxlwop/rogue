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
                case 'w' :
                    move[0] = 0;
                    move[1] = -1;
                    break;
                case 's':
                    move[0] = 0;
                    move[1] = 1;
                    break;
                case 'a':
                    move[0] = -1;
                    move[1] = 0;
                    break;
                case 'd':
                    move[0] = 1;
                    move[1] = 0;
                    break;
            }
        }
        return move;
    }

}
