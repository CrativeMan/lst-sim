package io.crative.backend.data;

import java.sql.Timestamp;
import java.util.UUID;

public class PhoneCall {
    private UUID uuid;
    private String phoneNumber;
    private String callerName;
    private Location location;
    private Timestamp timestamp;
    private PhoneCallStatus status;

    private Conversation conversation;

    public PhoneCall(String phoneNumber, String callerName, Location location) {
        this.uuid = new UUID(0, 1);
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
}
