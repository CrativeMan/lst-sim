package io.crative.backend.data;

public class Message {
    private String content;
    private boolean dispatcher;

    public Message(String content, boolean dispatcher) {
        this.content = content;
        this.dispatcher = dispatcher;
    }
}
