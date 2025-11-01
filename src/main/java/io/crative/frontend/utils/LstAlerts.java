package io.crative.frontend.utils;

import javafx.scene.control.Alert;

import static io.crative.backend.internationalization.TranslationManager.t;

public class LstAlerts {
    public static void errorAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(t("error"));
        alert.setContentText(text);

        alert.showAndWait();
    }

    public static void infoAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(t("information"));
        alert.setContentText(text);

        alert.showAndWait();
    }
}
