package io.crative.frontend.view;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PhoneView {
    private SplitPane mainSplit;
    private SplitPane conversationSplit;
    private SplitPane phoneSplit;

    private static final double MAIN_SPLIT_DIVIDER = 0.75;
    private static final double CONVERSATION_SPLIT_DIVIDER = 0.75;
    private static final double PHONE_SPLIT_DIVIDER = 0.20;

    public PhoneView() {
        initialize();
    }

    private void initialize() {
        mainSplit = createLayout();
    }

    private SplitPane createLayout() {
        mainSplit = new SplitPane();
        mainSplit.setOrientation(Orientation.HORIZONTAL);

        conversationSplit = createConversationSplit();
        phoneSplit = createPhoneSplit();

        mainSplit.getItems().addAll(conversationSplit, phoneSplit);
        mainSplit.setDividerPositions(MAIN_SPLIT_DIVIDER);

        return mainSplit;
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

        VBox phoneButtons = new VBox();
        phoneButtons.setStyle("-fx-background-color: lightyellow;");
        phoneButtons.setMinHeight(50);
        Label buttonsLabel = new Label("Phone Buttons");
        phoneButtons.getChildren().add(buttonsLabel);

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
        Scene scene = new Scene(mainSplit, 900, 700);
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