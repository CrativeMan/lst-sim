package io.crative.frontend.view.messages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MessageBubble extends HBox {
    public MessageBubble(Message message) {
        setSpacing(10);
        setPadding(new Insets(5, 10, 5, 10));

        TextArea messageText = getTextArea(message);

        if (message.isSender()) {
            setAlignment(Pos.CENTER_RIGHT);
            messageText.getStyleClass().add("message-sender");
            getChildren().add(messageText);
        } else {
            setAlignment(Pos.CENTER_LEFT);
            messageText.getStyleClass().add("message-receiver");
            getChildren().add(messageText);
        }

        HBox.setHgrow(messageText, Priority.SOMETIMES);
    }

    private static TextArea getTextArea(Message message) {
        TextArea messageText = new TextArea(message.getText());
        messageText.setWrapText(true);
        messageText.setEditable(false);
        messageText.setPrefRowCount(3);
        messageText.setMaxWidth(400);
        messageText.setStyle(
                "-fx-control-inner-background: " + (message.isSender() ? "#007BFF" : "#363B3F") + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12;" +
                        "-fx-border-radius: 5;" +
                        "-fx-padding: 5;"
        );
        return messageText;
    }
}
