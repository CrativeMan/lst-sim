package io.crative.event;

import java.time.Instant;

public abstract class Event {
    private final Instant timestamp;
    private final String debugName;

    protected Event(String debugName) {
        this.timestamp = Instant.now();
        this.debugName = debugName;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getDebugName() {
        return debugName;
    }
}
