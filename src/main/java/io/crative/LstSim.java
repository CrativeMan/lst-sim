package io.crative;

import io.crative.internationalization.TranslationManager;
import io.crative.window.PhoneWindow;

import static io.crative.internationalization.TranslationManager.t;

import javax.swing.*;

class LstSim {
    private PhoneWindow phoneWindow;
    private JFrame radioWindow;
    private JFrame callWindow;
    private JFrame activeCallWindow;
    private JFrame mapWindow;
    private JFrame unitsWindow;

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
        TranslationManager tm = TranslationManager.getInstance();

        phoneWindow = new PhoneWindow();
        createRadioWindow();
        createCallWindow();
        createActiveCallsWindow();
        createMapWindow();
        createUnitsWindow();

        phoneWindow.setVisible(true);
        radioWindow.setVisible(true);
        callWindow.setVisible(true);
        activeCallWindow.setVisible(true);
        mapWindow.setVisible(true);
        unitsWindow.setVisible(true);
    }

    private void createRadioWindow() {
        radioWindow = new JFrame(t("window.title.radio"));
    }

    private void createCallWindow() {
        callWindow = new JFrame(t("window.title.call"));
    }

    private void createActiveCallsWindow() {
        activeCallWindow = new JFrame(t("window.title.activeCall"));
    }

    private void createMapWindow() {
        mapWindow = new JFrame(t("window.title.map"));
    }

    private void createUnitsWindow() {
        unitsWindow = new JFrame(t("window.title.units"));
    }
}