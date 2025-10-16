// Java
package io.crative.frontend.view;

import javafx.stage.Stage;
import java.io.*;
import java.util.Properties;

public class FXWindowLayoutManager {
    private static final String LAYOUT_FILE = "window_layout.properties";
    private static final Properties layoutProperties = new Properties();

    public static void applyWindowLayout(Stage stage, String prefix, double defaultX, double defaultY, double defaultWidth, double defaultHeight) {
        double x = Double.parseDouble(layoutProperties.getProperty(prefix + ".x", String.valueOf(defaultX)));
        double y = Double.parseDouble(layoutProperties.getProperty(prefix + ".y", String.valueOf(defaultY)));
        double width = Double.parseDouble(layoutProperties.getProperty(prefix + ".width", String.valueOf(defaultWidth)));
        double height = Double.parseDouble(layoutProperties.getProperty(prefix + ".height", String.valueOf(defaultHeight)));

        stage.setX(x);
        stage.setY(y);
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static void addLayoutSaveListeners(Stage stage, String prefix) {
        stage.xProperty().addListener((obs, oldVal, newVal) -> saveWindowPosition(stage, prefix));
        stage.yProperty().addListener((obs, oldVal, newVal) -> saveWindowPosition(stage, prefix));
        stage.widthProperty().addListener((obs, oldVal, newVal) -> saveWindowPosition(stage, prefix));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> saveWindowPosition(stage, prefix));
    }

    private static void saveWindowPosition(Stage stage, String prefix) {
        layoutProperties.setProperty(prefix + ".x", String.valueOf(stage.getX()));
        layoutProperties.setProperty(prefix + ".y", String.valueOf(stage.getY()));
        layoutProperties.setProperty(prefix + ".width", String.valueOf(stage.getWidth()));
        layoutProperties.setProperty(prefix + ".height", String.valueOf(stage.getHeight()));
        saveLayout();
    }

    public static void loadLayout() {
        try (FileInputStream fis = new FileInputStream(LAYOUT_FILE)) {
            layoutProperties.load(fis);
        } catch (IOException ignored) {}
    }

    public static void saveLayout() {
        try (FileOutputStream fos = new FileOutputStream(LAYOUT_FILE)) {
            layoutProperties.store(fos, "Window Layouts");
        } catch (IOException ignored) {}
    }
}
