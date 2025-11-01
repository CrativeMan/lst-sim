package io.crative.event;

import java.time.Instant;

public abstract class Event {
    private final Instant timestamp;

    protected Event() {
        this.timestamp = Instant.now();
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
