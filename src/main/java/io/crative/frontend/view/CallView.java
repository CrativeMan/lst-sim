package io.crative.frontend.view;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CallView extends LstView{
    private SplitPane mainSplit;

    public CallView(MenuBar menuBar) {
        createLayout(menuBar);
    }

    private void createLayout(MenuBar menuBar) {
        mainSplit = new SplitPane();
        mainSplit.setOrientation(Orientation.VERTICAL);

        // Create your layout here
        Label placeholder = new Label("Call View Content");
        mainSplit.getItems().add(placeholder);

        root = new VBox(menuBar, mainSplit);
        root.setStyle("-fx-background-color: #1e1e1e;");
        VBox.setVgrow(mainSplit, Priority.ALWAYS);
    }
}