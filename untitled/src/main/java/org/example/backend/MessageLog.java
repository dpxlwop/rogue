package org.example.backend;

import java.util.ArrayList;
import java.util.List;

public class MessageLog {
    private List<String> messages;
    private List<Integer> messageTicks;
    private static final int MAX_MESSAGES = 5;
    private static final int MESSAGE_LIFETIME = 5;
    private int currentTick;

    public MessageLog() {
        this.messages = new ArrayList<>();
        this.messageTicks = new ArrayList<>();
        this.currentTick = 0;
    }

    public void addMessage(String message) {
        messages.add(message);
        messageTicks.add(currentTick);
        if (messages.size() > MAX_MESSAGES) {
            messages.remove(0);
            messageTicks.remove(0);
        }
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public String getCenteredMessage() {
        if (messages.isEmpty()) {
            return "";
        }
        int lastTick = messageTicks.get(messageTicks.size() - 1);
        if (currentTick - lastTick >= MESSAGE_LIFETIME) {
            return "";
        }
        return messages.get(messages.size() - 1);
    }

    public void nextTick() {
        currentTick++;
        // Удаляем старые сообщения
        while (!messageTicks.isEmpty() && currentTick - messageTicks.get(0) >= MESSAGE_LIFETIME) {
            messages.remove(0);
            messageTicks.remove(0);
        }
    }

    public void clear() {
        messages.clear();
        messageTicks.clear();
    }
}
