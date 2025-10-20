package io.crative;

import io.crative.backend.internationalization.TranslationManager;
import io.crative.frontend.view.CallView;
import io.crative.frontend.view.PhoneView;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static io.crative.backend.internationalization.TranslationManager.t;

public class LstSim extends Application {
    private Stage primaryStage;
    private PhoneView phoneView;
    private CallView callView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        MenuBar menuBar = createMenuBar();

        phoneView = new PhoneView(menuBar);
        Stage phoneStage = new Stage();
        phoneView.show(phoneStage, phoneView.getRoot(), 900, 700, t("phone.simulator.title"), "phoneView");

        callView = new CallView(menuBar);
        Stage callStage = new Stage();
        callView.show(callStage, callView.getRoot(), 600, 400, t("call.simulator.title"), "callView");

        stage.setOnCloseRequest(this::exitApplication);
        phoneStage.setOnCloseRequest(this::exitApplication);
        callStage.setOnCloseRequest(this::exitApplication);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu(t("app.menu.file"));
        MenuItem exitItem = new MenuItem(t("app.menu.file.exit"));
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitItem);

        Menu viewMenu = new Menu(t("app.menu.view"));
        CheckMenuItem phoneViewItem = new CheckMenuItem(t("app.menu.view.phone"));
        phoneViewItem.setSelected(true);
        phoneViewItem.setOnAction(e -> {
            if (phoneViewItem.isSelected()) {
                phoneView.getRoot().getScene().getWindow().show();
            } else {
                phoneView.getRoot().getScene().getWindow().hide();
            }
        });
        CheckMenuItem callViewItem = new CheckMenuItem(t("app.menu.view.call"));
        callViewItem.setSelected(true);
        callViewItem.setOnAction(e -> {
            if (callViewItem.isSelected()) {
                callView.getRoot().getScene().getWindow().show();
            } else {
                callView.getRoot().getScene().getWindow().hide();
            }
        });
        viewMenu.getItems().addAll(phoneViewItem, callViewItem);


        menuBar.getMenus().add(fileMenu);
        fileMenu.getItems().add(viewMenu);

        return menuBar;
    }

    private void exitApplication(WindowEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(t("app.exit.confirm.title"));
        alert.setHeaderText(t("app.exit.confirm.header"));
        alert.setContentText(t("app.exit.confirm.content"));

        ButtonType okButton = new ButtonType(t("button.yes"), ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType(t("button.no"), ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(okButton, cancelButton);

        if (alert.showAndWait().orElse(cancelButton) == cancelButton) {
            e.consume();
        }
    }
}