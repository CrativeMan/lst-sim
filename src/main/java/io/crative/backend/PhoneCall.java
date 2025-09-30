package io.crative.backend;

import java.sql.Timestamp;

public class PhoneCall {
    private String phoneNumber;
    private String callerName;
    private Location location;
    private Timestamp timestamp;
    private PhoneCallStatus status;

    private Conversation conversation;

    public PhoneCall(String phoneNumber, String callerName, Location location, Timestamp timestamp, PhoneCallStatus status) {
        this.phoneNumber = phoneNumber;
        this.callerName = callerName;
        this.location = location;
        this.timestamp = timestamp;
        this.status = status;
    }
}
