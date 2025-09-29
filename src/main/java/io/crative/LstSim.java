package io.crative;

import io.crative.internationalization.TranslationManager;
import io.crative.window.*;

import javax.swing.*;
import java.util.List;

class LstSim {

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

        PhoneWindow phoneWindow = new PhoneWindow();
        RadioWindow radioWindow = new RadioWindow();

        WindowLayoutManager.initialize(List.of(phoneWindow, radioWindow));

        phoneWindow.setVisible(true);
        radioWindow.setVisible(true);

        phoneWindow.addMessage("Test", false);
    }
}