package io.crative;

import io.crative.backend.CallManager;
import io.crative.backend.DebugCallListener;
import io.crative.backend.data.Location;
import io.crative.backend.internationalization.TranslationManager;
import io.crative.frontend.view.PhoneView;
import io.crative.frontend.view.RadioWindow;
import io.crative.frontend.view.WindowLayoutManager;

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
        CallManager cm = CallManager.getInstance();
        DebugCallListener cl = new DebugCallListener();

        PhoneView phoneWindow = new PhoneView();
        RadioWindow radioWindow = new RadioWindow();

        cm.receiveIncomingCall("+49 1515 2249137", "Kiara Hannig", new Location());
        cm.receiveIncomingCall("+49 82140 3058910", "Daniela Hannig", new Location());
        cm.acceptCall(cm.getIncomingCalls().getFirst());
        cm.holdCall(cm.getActiveCall());
        cm.endCall(cm.getActiveCall());
        cm.acceptCall(cm.getIncomingCalls().getFirst());

        WindowLayoutManager.initialize(List.of(phoneWindow, radioWindow));
    }
}