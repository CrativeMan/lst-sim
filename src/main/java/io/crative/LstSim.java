package io.crative;

import io.crative.internationalization.TranslationManager;
import io.crative.window.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class LstSim {
    private PhoneWindow phoneWindow;
    private RadioWindow radioWindow;
    private TestWindow testWindow;

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
        testWindow = new TestWindow();

        WindowLayoutManager.initialize(List.of(phoneWindow, radioWindow, testWindow));

        phoneWindow.setVisible(true);
        radioWindow.setVisible(true);
        testWindow.setVisible(true);

        phoneWindow.addMessage("Test", false);
    }
}