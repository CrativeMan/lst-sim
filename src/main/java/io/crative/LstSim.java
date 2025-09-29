package io.crative;

import io.crative.internationalization.TranslationManager;
import static io.crative.internationalization.TranslationManager.t;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

class LstSim {
    private JFrame phoneWindow;
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

        createPhoneWindow();
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

    private void createPhoneWindow() {
        phoneWindow = new JFrame(t("window.title.phone"));
        phoneWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Conversation setup
        JPanel conversation = new JPanel();
        conversation.setLayout(new BoxLayout(conversation, BoxLayout.Y_AXIS));
        conversation.setBackground(new Color(32, 34, 37)); // dark background

        // Make conversation stick to top
        JPanel conversationWrapper = new JPanel(new BorderLayout());
        conversationWrapper.setBackground(new Color(32, 34, 37));
        conversationWrapper.add(conversation, BorderLayout.NORTH);

        JScrollPane conversationScroll = new JScrollPane(conversationWrapper);
        conversationScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        conversationScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel phoneActions = new JPanel(new GridLayout(1, 3));
        Image img;

        JButton callIncomingButton = new JButton();
        JButton callHoldButton = new JButton();
        JButton callCancelButton = new JButton();
        JButton callOutgoingButton = new JButton();

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/phone/phone-call-incoming.png")));
            callIncomingButton.setIcon(new ImageIcon(img));
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/phone/phone-call-pause.png")));
            callHoldButton.setIcon(new ImageIcon(img));
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/phone/phone-call-cancel.png")));
            callCancelButton.setIcon(new ImageIcon(img));
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/phone/phone-call-outgoing.png")));
            callOutgoingButton.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        phoneActions.add(callIncomingButton);
        phoneActions.add(callHoldButton);
        phoneActions.add(callCancelButton);
        phoneActions.add(callOutgoingButton);

        JPanel conversationActions = new JPanel(new GridLayout(13,1));
        conversationActions.add(new JButton("Gesprächsbeginn"));
        conversationActions.add(new JButton("Name Anrufer"));
        conversationActions.add(new JButton("Name Patient"));
        conversationActions.add(new JButton("Einsatzort"));
        conversationActions.add(new JButton("Was ist passiert"));
        conversationActions.add(new JButton("Bewusstseinszustand"));
        conversationActions.add(new JButton("Atemprobleme"));
        conversationActions.add(new JButton("Schmerzen"));
        conversationActions.add(new JButton("Verletzungen"));
        conversationActions.add(new JButton("Vorerkrankungen"));
        conversationActions.add(new JButton("Medikamente"));
        conversationActions.add(new JButton("Patientenalter"));
        conversationActions.add(new JButton("Rettungsdienst kommt"));


        JSplitPane actions = new JSplitPane(JSplitPane.VERTICAL_SPLIT, phoneActions, conversationActions);
        SwingUtilities.invokeLater(() -> actions.setDividerLocation(0.1));


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, conversationScroll, actions);
        SwingUtilities.invokeLater(() -> splitPane.setDividerLocation(0.6));

        phoneWindow.add(splitPane);
        phoneWindow.setSize(1000, 700);
    }

    private void addMessage(JPanel conversation, String text, String time, boolean isMine, JScrollPane scrollPane) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setOpaque(false);

        JLabel messageLabel = new JLabel("<html><body style='width: 200px'>" + text + "</body></html>");
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messageLabel.setForeground(Color.WHITE);

        if (isMine) {
            messageLabel.setBackground(new Color(0, 123, 255));
            messagePanel.add(messageLabel, BorderLayout.EAST);
        } else {
            messageLabel.setBackground(new Color(54, 57, 63));
            messagePanel.add(messageLabel, BorderLayout.WEST);
        }

        conversation.add(messagePanel);
        conversation.revalidate();

        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = scrollPane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
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