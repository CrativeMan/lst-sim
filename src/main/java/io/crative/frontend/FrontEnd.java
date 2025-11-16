package io.crative.frontend;

import io.crative.backend.data.call.CallManager;
import io.crative.backend.data.call.Location;
import io.crative.event.EndApplicationEvent;
import io.crative.event.EventBus;
import io.crative.event.ui.ShowAlertEvent;
import io.crative.frontend.utils.LstAlerts;
import io.crative.frontend.view.CallView;
import io.crative.frontend.view.PhoneView;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLOutput;

import static io.crative.backend.internationalization.TranslationManager.t;

public class FrontEnd {
    private static Stage primaryStage;
    private static PhoneView phoneView;
    private static CallView callView;


    public void initialize(Stage stage) {
        EventBus.getInstance().subscribe(ShowAlertEvent.class, this::handleAlert);

        primaryStage = stage;

        MenuBar phoneViewMenuBar = createMenuBar();
        MenuBar callViewMenuBar = createMenuBar();

        phoneView = new PhoneView(phoneViewMenuBar);
        Stage phoneStage = new Stage();
        phoneView.show(phoneStage, phoneView.getRoot(), 900, 700, t("phone.simulator.title"), "phoneView");

        callView = new CallView(callViewMenuBar);
        Stage callStage = new Stage();
        callView.show(callStage, callView.getRoot(), 600, 400, t("call.simulator.title"), "callView");

        stage.setOnCloseRequest(this::exitApplication);
        phoneStage.setOnCloseRequest(this::exitApplication);
        callStage.setOnCloseRequest(this::exitApplication);

        javafx.application.Platform.runLater(() -> {
            System.out.println("Creating test call...");
            CallManager.getInstance().receiveIncomingCall("+49 1515 2249137", "Kiara Hannig", new Location());
        });
    }


    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu(t("app.menu.file"));
        MenuItem exitItem = new MenuItem(t("app.menu.file.exit"));
        exitItem.setOnAction(e -> exitApplication(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        fileMenu.getItems().add(exitItem);

        Menu viewMenu = new Menu(t("app.menu.view"));
        CheckMenuItem phoneViewItem = new CheckMenuItem(t("app.menu.view.phone"));
        phoneViewItem.setSelected(true);
        CheckMenuItem callViewItem = new CheckMenuItem(t("app.menu.view.call"));
        callViewItem.setSelected(true);
        viewMenu.getItems().addAll(phoneViewItem, callViewItem);

        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(viewMenu);

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

        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                phoneView.cleanup();
                callView.cleanup();
                EventBus.getInstance().unsubscribe(ShowAlertEvent.class, this::handleAlert);
                EventBus.getInstance().postOnUIThread(new EndApplicationEvent("User initiated exit"));
            } else {
                e.consume();
            }
        });
    }

    // TODO: implement other alert types
    private void handleAlert(ShowAlertEvent event) {
        switch (event.getType()) {
            case ERROR -> LstAlerts.errorAlert(t(event.getMessageKey()));
            case INFORMATION -> LstAlerts.infoAlert(t(event.getMessageKey()));
            case NONE, WARNING, CONFIRMATION -> System.out.println("ShowAlertEvent: Alert type not yet implemented: " + event.getType());
            default -> System.out.println("ShowAlertEvent: Unsupported alert type: " + event.getType());
        }
    }
}
