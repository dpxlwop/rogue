package org.example.ui;
import org.example.backend.Entity.Entity;
import org.example.backend.Tile;


import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.example.backend.Entity.Player;
import java.lang.String;
import java.util.ArrayList;

public class Drawer {

    private TerminalSize size;
    private DefaultTerminalFactory factory;
    private Terminal terminal;
    private TerminalScreen screen;
    private TextGraphics tg;
    private int width;
    private int mapHeight;
    private int screenHeight;


    public Drawer(int width, int mapHeight, int screenHeight) throws Exception {
        this.width = width;
        this.mapHeight = mapHeight;
        this.screenHeight = screenHeight;
        size = new TerminalSize(width, screenHeight);
        factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(size);
        terminal = factory.createTerminal();
        screen = new TerminalScreen(terminal);
        tg = screen.newTextGraphics();
        screen.startScreen();
        screen.setCursorPosition(null);
    }

    private void clear() throws Exception {
        screen.clear();
    }

    public void stop() throws Exception {
        screen.stopScreen();
    }

    private void refresh() throws Exception {
        screen.refresh();
    }

    public TerminalScreen getScreen() {
        return screen;
    }

    private String whitespaceLine(String left, String right) {
        int spaces = width - left.length() - right.length();
        if (spaces < 1) spaces = 1;
        return left + " ".repeat(spaces) + right;
    }

    private String[] getHUDasMatrix(Player player) {
        String[] hud = new String[2];
        hud[0] = whitespaceLine(
                "Rogue-like by procluha",
                String.format("Health: %d/%d", player.getHealth(), player.getMaxHealth())
        );
        hud[1] = whitespaceLine(
                String.format("Agility: %d Strength: %d", player.getAgility(), player.getStrength()),
                String.format("X: %d Y: %d", player.getCordXY()[0], player.getCordXY()[1])
        );
        return hud;
    }

    private void drawHUD(Player player){
        String[] hud = getHUDasMatrix(player);
        for (int i = 0; i < hud.length; i++) {
            tg.putString(0, mapHeight + i, hud[i]);
        }
    }

    public void draw(Tile[][] map, Player player, ArrayList<Entity> enemies) throws Exception {
        screen.clear();
        drawField(map);
        drawPlayer(player);
        drawHUD(player);
        drawEnemies(enemies);
        screen.refresh();
    }

    private void drawField(Tile[][] map){
        for (int y = 0; y < this.mapHeight; y++) {
            for (int x = 0; x < this.width; x++) {
                tg.putString(x, y, String.valueOf(map[y][x].getSymbol()));
            }
        }
    }

    private void drawPlayer(Player player){
        int[] cords = player.getCordXY();
        tg.putString(cords[0], cords[1], String.valueOf(player.getSymbol()));
    }

    private void drawEnemies(ArrayList<Entity> enemies){
        for (Entity e : enemies) {
            if (e != null) {
                int[] cords = e.getCordXY();
                tg.putString(cords[0], cords[1], String.valueOf(e.getSymbol()));
            }
        }
    }

    public void drawWelcomeScreen() throws Exception{
        screen.clear();
        String[] welcome = new String[3];
        welcome[0] = "RogueLike by Procluha";
        welcome[1] = "v. Alpha 0.2";
        welcome[2] = "Press any key to start...";
        for (int i = 0; i < welcome.length; i++) {
            welcome[i] = stringCenterizer(welcome[i]);
        }
        tg.putString(0, screenHeight / 2, welcome[0]);
        tg.putString(0, screenHeight / 2 + 1, welcome[1]);
        tg.putString(0, screenHeight / 2 + 3, welcome[2]);
        screen.refresh();
    }


    private String stringCenterizer(String str) {
        int spaces = (this.width - str.length()) / 2;
        if (spaces < 0) spaces = 0;
        return " ".repeat(spaces) + str;
    }
}
