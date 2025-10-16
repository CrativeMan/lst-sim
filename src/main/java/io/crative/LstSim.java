package io.crative;

import io.crative.frontend.view.PhoneView;
import javafx.application.Application;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class LstSim extends Application {

    private Stage primaryStage;
    private static PhoneView phoneView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        MenuBar menuBar = new MenuBar();
        phoneView = new PhoneView(menuBar);
        phoneView.show(primaryStage);
    }
}