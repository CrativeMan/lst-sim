package io.crative.frontend.view;

import io.crative.frontend.utils.ImageButton;
import io.crative.frontend.view.messages.*;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import static io.crative.backend.internationalization.TranslationManager.t;

public class PhoneView {
    private VBox root;
    private SplitPane mainSplit;
    private SplitPane conversationSplit;
    private SplitPane phoneSplit;

    private VBox conversationMessagesContainer;
    private ScrollPane conversationScroll;

    private final String[] CONVERSATION_BUTTON_KEYS = {
            "conversation.phone.start",
            "conversation.phone.name_caller",
            "conversation.phone.name_patient",
            "conversation.phone.call_location",
            "conversation.phone.what_happened",
            "conversation.phone.consciousness",
            "conversation.phone.breathing_problem",
            "conversation.phone.pain",
            "conversation.phone.injuries",
            "conversation.phone.previous_illnesses",
            "conversation.phone.drugs",
            "conversation.phone.age_patient",
            "conversation.phone.ems_coming"
    };

    private static final double MAIN_SPLIT_DIVIDER = 0.75;
    private static final double CONVERSATION_SPLIT_DIVIDER = 0.75;
    private static final double PHONE_SPLIT_DIVIDER = 0.20;

    private static final double CALL_BUTTON_SIZE = 16;

    public PhoneView(MenuBar menuBar) {
        createLayout(menuBar);
    }

    public void show(Stage stage) {
        Scene scene = new Scene(root, 900, 700);

        scene.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Phone Simulator");

        FXWindowLayoutManager.loadLayout();
        FXWindowLayoutManager.applyWindowLayout(stage, "phoneView", 100, 100, 900, 700);

        stage.show();

        FXWindowLayoutManager.addLayoutSaveListeners(stage, "phoneView");

        stage.setOnShown(e -> {
            mainSplit.setDividerPositions(MAIN_SPLIT_DIVIDER);
            conversationSplit.setDividerPositions(CONVERSATION_SPLIT_DIVIDER);
            phoneSplit.setDividerPositions(PHONE_SPLIT_DIVIDER);
        });
    }

    private void createLayout(MenuBar menuBar) {
        mainSplit = new SplitPane();
        mainSplit.setOrientation(Orientation.HORIZONTAL);

        conversationSplit = createConversationSplit();
        phoneSplit = createPhoneSplit();

        mainSplit.getItems().addAll(conversationSplit, phoneSplit);
        mainSplit.setDividerPositions(MAIN_SPLIT_DIVIDER);

        root = new VBox(menuBar, mainSplit);
        root.setStyle("-fx-background-color: #1e1e1e;");
        VBox.setVgrow(mainSplit, Priority.ALWAYS);
    }

    private SplitPane createConversationSplit() {
        SplitPane split = new SplitPane();
        split.setOrientation(Orientation.HORIZONTAL);

        // =============================================================================================================
        // Conversation Bubble Area
        conversationMessagesContainer = new VBox(5);
        conversationMessagesContainer.setStyle("-fx-padding: 10;");
        conversationMessagesContainer.getStyleClass().add("conversation-messages");

        conversationScroll = new ScrollPane(conversationMessagesContainer);
        conversationScroll.setFitToWidth(true);
        conversationScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        conversationScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Auto-scroll to bottom when new messages added
        conversationMessagesContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            conversationScroll.setVvalue(1.0);
        });

        // =============================================================================================================
        // Conversation Action Buttons

        VBox conversationActions = new VBox();
        conversationActions.setMinWidth(50);
        conversationActions.getStyleClass().add("conversation-actions");

        for (String key : CONVERSATION_BUTTON_KEYS) {
            Button actionButton = new ImageButton(t(key), "/icons/message-circle-question-mark.png");
            actionButton.setContentDisplay(ContentDisplay.LEFT);
            actionButton.setAlignment(Pos.CENTER_LEFT);
            actionButton.setMaxWidth(Double.MAX_VALUE);
            actionButton.setOnAction(e -> {
                System.out.println("Action: " + t(key + ".message"));
                addMessage(t(key + ".message"), true);
                addMessage(t(key + ".response"), false);
            });
            conversationActions.getChildren().add(actionButton);
        }

        split.getItems().addAll(conversationScroll, conversationActions);
        split.setDividerPositions(CONVERSATION_SPLIT_DIVIDER);
        return split;
    }

    private SplitPane createPhoneSplit() {
        SplitPane split = new SplitPane();
        split.setOrientation(Orientation.VERTICAL);

        // =============================================================================================================
        // Phone Call Buttons
        HBox phoneButtons = new HBox(10);
        phoneButtons.setMinHeight(50);
        phoneButtons.setAlignment(Pos.CENTER);
        phoneButtons.getStyleClass().add("phone-buttons");

        Button acceptCallButton = new ImageButton(t("call.phone.accept"), "/icons/phone/phone-call.png", CALL_BUTTON_SIZE, CALL_BUTTON_SIZE);
        acceptCallButton.setOnAction(e -> {
            System.out.println("Accept Call Pressed");
        });
        Button holdCallButton = new ImageButton(t("call.phone.hold"), "/icons/phone/phone-hold.png", CALL_BUTTON_SIZE, CALL_BUTTON_SIZE);
        holdCallButton.setOnAction(e -> {
            System.out.println("Hold Call Pressed");
        });
        Button endCallButton = new ImageButton(t("call.phone.end"), "/icons/phone/phone-off.png", CALL_BUTTON_SIZE, CALL_BUTTON_SIZE);
        endCallButton.setOnAction(e -> {
            System.out.println("End Call Pressed");
        });
        phoneButtons.getChildren().addAll(acceptCallButton, holdCallButton, endCallButton);

        // =============================================================================================================
        // Call List Area
        VBox callList = new VBox();
        callList.setMinHeight(100);
        callList.getStyleClass().add("call-list");

        Label listLabel = new Label("Call List");
        callList.getChildren().add(listLabel);

        split.getItems().addAll(phoneButtons, callList);
        split.setDividerPositions(PHONE_SPLIT_DIVIDER);
        return split;
    }

    public void addMessage(String text, boolean isSender) {
        Message message = new Message(text, isSender);
        MessageBubble bubble = new MessageBubble(message);
        conversationMessagesContainer.getChildren().add(bubble);
    }
}