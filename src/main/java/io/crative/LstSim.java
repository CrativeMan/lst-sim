package io.crative;

import io.crative.internationalization.TranslationManager;
import io.crative.window.PhoneWindow;
import io.crative.window.RadioWindow;
import io.crative.window.Window;
import io.crative.window.WindowLayoutManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class LstSim {
    private Window phoneWindow;
    private Window radioWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("SETUP: Failed to set look and feel + " + e);
            }
            new LstSim().initialize();
        });
    }

    public void initialize() {
        TranslationManager.getInstance();

        phoneWindow = new PhoneWindow();
        radioWindow = new RadioWindow();

        WindowLayoutManager.initialize(List.of(phoneWindow, radioWindow));

        WindowLayoutManager.applyWindowLayout(phoneWindow);
        WindowLayoutManager.addLayoutSaveListener(phoneWindow);
        WindowLayoutManager.applyWindowLayout(radioWindow);
        WindowLayoutManager.addLayoutSaveListener(radioWindow);

        phoneWindow.setVisible(true);
        radioWindow.setVisible(true);
    }
}