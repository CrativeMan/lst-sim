package io.crative.frontend.view.messages;

import io.crative.backend.data.PhoneCall;
import io.crative.backend.data.PhoneCallStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class CallEntry extends HBox {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public CallEntry(PhoneCall call) {
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("call-entry");

        // Status indicator icon
        ImageView statusIcon = createStatusIcon(call.getStatus());

        // Call information (left side)
        VBox callInfo = new VBox(3);

        Label callerNameLabel = new Label(call.getCallerName() != null ? call.getCallerName() : "Unknown Caller");
        callerNameLabel.getStyleClass().add("call-entry-name");

        Label phoneNumberLabel = new Label(call.getPhoneNumber());
        phoneNumberLabel.getStyleClass().add("call-entry-number");

        Label locationLabel = new Label(call.getLocation() != null ? call.getLocation().toString() : "Unknown Location");
        locationLabel.getStyleClass().add("call-entry-location");

        callInfo.getChildren().addAll(callerNameLabel, phoneNumberLabel, locationLabel);
        HBox.setHgrow(callInfo, Priority.ALWAYS);

        // Time and status (right side)
        VBox statusInfo = new VBox(3);
        statusInfo.setAlignment(Pos.TOP_RIGHT);

        Label timeLabel = new Label(call.getTimestamp().toLocalDateTime().format(TIME_FORMATTER));
        timeLabel.getStyleClass().add("call-entry-time");

        Label statusLabel = new Label(getStatusText(call.getStatus()));
        statusLabel.getStyleClass().add("call-entry-status");
        applyStatusStyle(statusLabel, call.getStatus());

        statusInfo.getChildren().addAll(timeLabel, statusLabel);

        // Assemble the entry
        this.getChildren().addAll(statusIcon, callInfo, statusInfo);

        // Make clickable
        this.setOnMouseClicked(e -> handleClick(call));

        // Hover effect
        this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #3c3c3c;"));
        this.setOnMouseExited(e -> this.setStyle(""));
    }

    private ImageView createStatusIcon(PhoneCallStatus status) {
        String iconPath = switch (status) {
            case RINGING -> "/icons/phone/phone-incoming.png";
            case ACTIVE -> "/icons/phone/phone-call.png";
            case ON_HOLD -> "/icons/phone/phone-hold.png";
            case ENDED -> "/icons/phone/phone-off.png";
        };

        try {
            Image image = new Image(getClass().getResourceAsStream(iconPath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            // Fallback if icon not found
            ImageView placeholder = new ImageView();
            placeholder.setFitWidth(24);
            placeholder.setFitHeight(24);
            return placeholder;
        }
    }

    private String getStatusText(PhoneCallStatus status) {
        return switch (status) {
            case RINGING -> "Incoming";
            case ACTIVE -> "Active";
            case ON_HOLD -> "On Hold";
            case ENDED -> "Ended";
        };
    }

    private void applyStatusStyle(Label label, PhoneCallStatus status) {
        String colorStyle = switch (status) {
            case RINGING -> "-fx-text-fill: #FFA500;"; // Orange
            case ACTIVE -> "-fx-text-fill: #00FF00;";  // Green
            case ON_HOLD -> "-fx-text-fill: #FFFF00;"; // Yellow
            case ENDED -> "-fx-text-fill: #FF0000;";   // Red
        };
        label.setStyle(colorStyle);
    }

    private void handleClick(PhoneCall call) {
        // TODO: Handle selection - maybe highlight this entry
        // TODO: Update active call in CallManager
        // TODO: Load conversation for this call
        System.out.println("Selected call: " + call.getPhoneNumber());
    }
}