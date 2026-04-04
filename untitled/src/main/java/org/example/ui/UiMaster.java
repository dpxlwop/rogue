package org.example.ui;

public class UiMaster {
    private KeyHandler keyHandler;
    private Drawer drawer;

    public UiMaster() throws Exception {
        this.drawer = new Drawer();
        this.keyHandler = new KeyHandler(drawer.getScreen());
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public Drawer getDrawer() {
        return drawer;
    }
}
