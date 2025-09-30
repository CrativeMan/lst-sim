package io.crative.frontend.view;

import static io.crative.backend.internationalization.TranslationManager.t;

public class RadioWindow extends Window{
    public RadioWindow() {
        super("radio", 0, 720, 900, 350);
        initialize();
    }

    private void initialize() {
        this.setTitle(t("window.title.radio"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(1000, 360);
        this.setVisible(true);
    }
}
