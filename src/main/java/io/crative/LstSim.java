package io.crative;

import io.crative.frontend.view.PhoneView;
import javafx.application.Application;
import javafx.stage.Stage;

public class LstSim extends Application {

    private Stage primaryStage;
    private static PhoneView phoneView;

    public static void main(String[] args) {
        phoneView = new PhoneView();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        phoneView.show(primaryStage);
    }
}