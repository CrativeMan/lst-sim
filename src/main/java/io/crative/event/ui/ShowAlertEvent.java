package io.crative.event.ui;

import io.crative.event.Event;
import javafx.scene.control.Alert;

public class ShowAlertEvent extends Event {
    private final String messageKey;
    private final Alert.AlertType type;

    public ShowAlertEvent(String messageKey, Alert.AlertType type) {
        this.messageKey = messageKey;
        this.type = type;
    }

    public String getMessageKey() { return messageKey; }
    public Alert.AlertType getType() { return type; }
}
