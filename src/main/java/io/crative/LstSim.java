package io.crative;

import io.crative.backend.internationalization.TranslationManager;
import io.crative.frontend.view.PhoneView;
import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.close());
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().add(fileMenu);

        phoneView = new PhoneView(menuBar);
        phoneView.show(primaryStage);
    }
}