package io.crative.backend.data;

import java.sql.Timestamp;
import java.util.UUID;

public class PhoneCall {
    private UUID uuid;
    private final String phoneNumber;
    private final String callerName;
    private final Location location;
    private final Timestamp timestamp;
    private PhoneCallStatus status;

    private Conversation conversation;

    public PhoneCall(String phoneNumber, String callerName, Location location) {
        this.uuid = UUID.randomUUID();
        this.phoneNumber = phoneNumber;
        this.callerName = callerName;
        this.location = location;
        this.timestamp = new Timestamp(0);
        this.status = PhoneCallStatus.RINGING;
    }

    public void accept() {
        this.status = PhoneCallStatus.ACTIVE;
    }

    public void hold() {
        this.status = PhoneCallStatus.ON_HOLD;
    }

    public void end() {
        this.status = PhoneCallStatus.ENDED;
    }

    public PhoneCallStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "PhoneCall{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", callerName='" + callerName + '\'' +
                ", location=" + location +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", conversation=" + conversation +
                '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallerName() {
        return callerName;
    }

    public Location getLocation() {
        return location;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Conversation getConversation() {
        return conversation;
    }
}
