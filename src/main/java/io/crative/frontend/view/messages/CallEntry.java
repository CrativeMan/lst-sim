package io.crative.frontend.view.messages;

import io.crative.backend.AssetManager;
import io.crative.backend.data.call.PhoneCall;
import io.crative.backend.data.call.PhoneCallStatus;
import io.crative.frontend.view.LstView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class CallEntry extends HBox {
    // private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final UUID callUuid;
    private boolean selected = false;

    private final String CALL_ENTRY_SELECTED_BACKGROUND_COLOR = "#3c3c3c";

    public CallEntry(PhoneCall call) {
        this.callUuid = call.getUuid();
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("call-entry");
        this.getStylesheets().add(getClass().getResource("/styles/call_entry.css").toExternalForm());

        // Status indicator icon
        ImageView statusIcon = createStatusIcon(call.getStatus());

        VBox callInfo = new VBox(3);

        Label callerNameLabel = new Label(call.getCallerName() != null ? call.getCallerName() : "Unknown Caller");
        callerNameLabel.getStyleClass().add("call-entry-name");

        Label phoneNumberLabel = new Label(call.getPhoneNumber());
        phoneNumberLabel.getStyleClass().add("call-entry-number");

        Label locationLabel = new Label(call.getLocation() != null ? call.getLocation().toString() : "Unknown Location");
        locationLabel.getStyleClass().add("call-entry-location");

        callInfo.getChildren().addAll(callerNameLabel, phoneNumberLabel, locationLabel);
        HBox.setHgrow(callInfo, Priority.ALWAYS);

        // Assemble the entry
        this.getChildren().addAll(statusIcon, callInfo);

        // Make clickable
        this.setOnMouseClicked(e -> handleClick());

        // Hover effect
        this.setOnMouseEntered(e -> {
            if (!selected) this.setStyle("-fx-background-color: #afb0b3;");
        });
        this.setOnMouseExited(e -> {
            if (!selected) this.setStyle("");
        });
    }

    private ImageView createStatusIcon(PhoneCallStatus status) {
        String iconPath = status.toImagePath();

        try {
            ImageView imageView = AssetManager.getImageView(iconPath);
            imageView.setFitWidth(24);
            imageView.setFitHeight(24);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            // Fallback if icon is not found
            ImageView placeholder = new ImageView();
            placeholder.setFitWidth(24);
            placeholder.setFitHeight(24);
            return placeholder;
        }
    }

    private void handleClick() {
        System.out.println("Selected call: " + this.callUuid);
        if (!selected) {
            this.setStyle(LstView.formatedBackgroundColor(CALL_ENTRY_SELECTED_BACKGROUND_COLOR));
            selected = true;
        } else {
            this.setStyle("");
            selected = false;
        }
    }

    public UUID getCallUuid() {
        return callUuid;
    }
}