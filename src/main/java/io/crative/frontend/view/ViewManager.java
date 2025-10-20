package io.crative.frontend.view;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private final Map<String, Stage> views = new HashMap<>();

    public void registerView(String key, Stage stage) {
        views.put(key, stage);
    }

    public Stage getView(String key) {
        return views.get(key);
    }

    public void show(String key) {
        Stage stage = views.get(key);
        if (stage != null) stage.show();
    }

    public void hide(String key) {
        Stage stage = views.get(key);
        if (stage != null) stage.hide();
    }

    public void toggle(String key, boolean visible) {
        if (visible) show(key);
        else hide(key);
    }

    public Map<String, Stage> getAllViews() {
        return views;
    }
}
