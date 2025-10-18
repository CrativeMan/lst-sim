package io.crative.frontend.view.messages;

import java.time.LocalDateTime;

public class Message {
    private String text;
    private boolean isSender; // true = dispatcher, false = caller
    private LocalDateTime timestamp;

    public Message(String text, boolean isSender) {
        this.text = text;
        this.isSender = isSender;
        this.timestamp = LocalDateTime.now();
    }

    public String getText() {
        return text;
    }

    public boolean isSender() {
        return isSender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
