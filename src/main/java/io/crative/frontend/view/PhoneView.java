package io.crative.frontend.view;

import io.crative.frontend.utils.ImageButton;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import static io.crative.backend.internationalization.TranslationManager.t;

public class PhoneView {
    private VBox root;
    private SplitPane mainSplit;
    private SplitPane conversationSplit;
    private SplitPane phoneSplit;

    private static final double MAIN_SPLIT_DIVIDER = 0.75;
    private static final double CONVERSATION_SPLIT_DIVIDER = 0.75;
    private static final double PHONE_SPLIT_DIVIDER = 0.20;

    private static final double CALL_BUTTON_SIZE = 16;

    public PhoneView(MenuBar menuBar) {
        createLayout(menuBar);
    }

    private void createLayout(MenuBar menuBar) {
        mainSplit = new SplitPane(menuBar);
        mainSplit.setOrientation(Orientation.HORIZONTAL);

        conversationSplit = createConversationSplit();
        phoneSplit = createPhoneSplit();

        mainSplit.getItems().addAll(conversationSplit, phoneSplit);
        mainSplit.setDividerPositions(MAIN_SPLIT_DIVIDER);

        root = new VBox(menuBar, mainSplit);
        VBox.setVgrow(mainSplit, Priority.ALWAYS);
    }

    private SplitPane createConversationSplit() {
        SplitPane split = new SplitPane();
        split.setOrientation(Orientation.HORIZONTAL);

        VBox conversationArea = new VBox();
        conversationArea.setStyle("-fx-background-color: lightblue;");
        conversationArea.setMinWidth(100);
        Label convLabel = new Label("Conversation Messages");
        conversationArea.getChildren().add(convLabel);

        VBox conversationActions = new VBox();
        conversationActions.setStyle("-fx-background-color: lightgreen;");
        conversationActions.setMinWidth(50);
        Label actionsLabel = new Label("Actions");
        conversationActions.getChildren().add(actionsLabel);

        split.getItems().addAll(conversationArea, conversationActions);
        split.setDividerPositions(CONVERSATION_SPLIT_DIVIDER);
        return split;
    }

    private SplitPane createPhoneSplit() {
        SplitPane split = new SplitPane();
        split.setOrientation(Orientation.VERTICAL);

        HBox phoneButtons = new HBox(10);
        phoneButtons.setStyle("-fx-background-color: lightyellow;");
        phoneButtons.setMinHeight(50);
        phoneButtons.setAlignment(Pos.CENTER);

        Button acceptCallButton = new ImageButton(t("call.phone.accept"),"/icons/phone/phone-call.png", CALL_BUTTON_SIZE, CALL_BUTTON_SIZE);
        Button holdCallButton = new ImageButton(t("call.phone.hold"),"/icons/phone/phone-hold.png", CALL_BUTTON_SIZE, CALL_BUTTON_SIZE);
        Button endCallButton = new ImageButton(t("call.phone.end"),"/icons/phone/phone-off.png", CALL_BUTTON_SIZE, CALL_BUTTON_SIZE);
        phoneButtons.getChildren().addAll(acceptCallButton, holdCallButton, endCallButton);

        VBox callList = new VBox();
        callList.setStyle("-fx-background-color: lightcoral;");
        callList.setMinHeight(100);
        Label listLabel = new Label("Call List");
        callList.getChildren().add(listLabel);

        split.getItems().addAll(phoneButtons, callList);
        split.setDividerPositions(PHONE_SPLIT_DIVIDER);
        return split;
    }

    public void show(Stage stage) {
        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
        stage.setTitle("Phone Simulator");
        stage.show();

        stage.setOnShown(e -> {
            mainSplit.setDividerPositions(MAIN_SPLIT_DIVIDER);
            conversationSplit.setDividerPositions(CONVERSATION_SPLIT_DIVIDER);
            phoneSplit.setDividerPositions(PHONE_SPLIT_DIVIDER);
        });
    }
}