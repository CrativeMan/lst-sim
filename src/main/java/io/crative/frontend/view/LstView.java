package io.crative.frontend.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class LstView {
    protected Parent root;

    public void show(Stage stage, Parent root, int width, int height, String title, String id) {
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle(title);

        FXWindowLayoutManager.loadLayout();
        FXWindowLayoutManager.applyWindowLayout(stage, id, 950, 100, 900, 700);

        stage.show();

        FXWindowLayoutManager.addLayoutSaveListeners(stage, id);
    }

    public void cleanup() {
        // Override if needed
    }

    public Parent getRoot() {
        return root;
    }

    public static String formatedBackgroundColor(String hex) {
        return "-fx-background-color: " + hex + ";";
    }
}
