package io.crative.window;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static io.crative.internationalization.TranslationManager.t;

public class PhoneWindow extends JFrame {
    private JPanel conversation;
    private JScrollPane conversationScroll;

    private final String[] conversationButtonKeys = {
            "conversation.phone.start",
            "conversation.phone.name_caller",
            "conversation.phone.name_patient",
            "conversation.phone.call_location",
            "conversation.phone.what_happened",
            "conversation.phone.consciousness",
            "conversation.phone.breathing_problem",
            "conversation.phone.pain",
            "conversation.phone.injuries",
            "conversation.phone.previous_illnesses",
            "conversation.phone.drugs",
            "conversation.phone.age_patient",
            "conversation.phone.ems_coming"
    };

    public PhoneWindow() {
        initialize();
    }

    private void initialize() {
        this.setTitle(t("window.title.phone"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        conversation = new JPanel();
        conversation.setLayout(new BoxLayout(conversation, BoxLayout.Y_AXIS));

        JPanel conversationWrapper = new JPanel(new BorderLayout());
        conversationWrapper.add(conversation, BorderLayout.NORTH);

        conversationScroll = new JScrollPane(conversationWrapper);
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
        for (String key : conversationButtonKeys) {
            JButton b = new JButton(t(key));
            b.addActionListener(e -> {
                addMessage(key, true);
            });
            conversationActions.add(b);
        }


        JSplitPane actions = new JSplitPane(JSplitPane.VERTICAL_SPLIT, phoneActions, conversationActions);
        SwingUtilities.invokeLater(() -> actions.setDividerLocation(0.1));


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, conversationScroll, actions);
        SwingUtilities.invokeLater(() -> splitPane.setDividerLocation(0.7));

        this.add(splitPane);
        this.setSize(1000, 700);
    }

    public void addMessage(String text, boolean isMine) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setOpaque(false);

        JTextArea messageArea = new JTextArea(text);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setColumns(20);
        messageArea.setEditable(false);
        messageArea.setOpaque(true);
        messageArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messageArea.setForeground(Color.WHITE);

        if (isMine) {
            messageArea.setBackground(new Color(0, 123, 255));
            messagePanel.add(messageArea, BorderLayout.EAST);
        } else {
            messageArea.setBackground(new Color(54, 57, 63));
            messagePanel.add(messageArea, BorderLayout.WEST);
        }

        conversation.add(messagePanel);
        conversation.revalidate();

        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = conversationScroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

}
