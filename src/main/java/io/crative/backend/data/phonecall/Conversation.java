package io.crative.backend.data.phonecall;

import java.sql.Timestamp;
import java.util.List;

public class Conversation {
    private Timestamp start;
    private Timestamp end;
    private List<Message> messages;

    public Conversation() {

    }
}
