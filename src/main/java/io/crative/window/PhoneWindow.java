package io.crative.window;

import io.crative.fileio.FileLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static io.crative.internationalization.TranslationManager.t;

public class PhoneWindow extends Window {
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
        super("phone",0,0, 900, 700);
        initialize();
    }

    private void initialize() {
        this.setTitle(t("window.title.phone"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        conversation = new JPanel();
        conversation.setLayout(new BoxLayout(conversation, BoxLayout.Y_AXIS));

        //--------------------------------------------------------------------------------------------------------------
        // Conversation bubble Panel
        JPanel conversationWrapper = new JPanel(new BorderLayout());
        conversationWrapper.add(conversation, BorderLayout.NORTH);

        conversationScroll = new JScrollPane(conversationWrapper);
        conversationScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        conversationScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //--------------------------------------------------------------------------------------------------------------
        // Conversation actions
        JPanel conversationActions = new JPanel();
        conversationActions.setLayout(new BoxLayout(conversationActions, BoxLayout.Y_AXIS));
        for (String key : conversationButtonKeys) {
            JButton b = new JButton(t(key));
            b.addActionListener(e -> {
                addMessage(t(key + ".message"), true);
            });
            conversationActions.add(b);
            conversationActions.add(Box.createVerticalStrut(5));
        }

        //--------------------------------------------------------------------------------------------------------------
        // Phone Actions
        JPanel phoneActions = new JPanel(new GridLayout(1, 3));
        JButton callIncomingButton = new JButton();
        callIncomingButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-call.png")));
        callIncomingButton.addActionListener(e -> {
            System.out.println("Accepted incoming call");
        });
        JButton callHoldButton = new JButton();
        callHoldButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-hold.png")));
        callHoldButton.addActionListener(e -> {
            System.out.println("Put call on hold");
        });
        JButton callCancelButton = new JButton();
        callCancelButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-off.png")));
        callCancelButton.addActionListener(e -> {
            System.out.println("Cancelled call");
        });
        JButton callOutgoingButton = new JButton();
        callOutgoingButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-outgoing.png")));
        callOutgoingButton.addActionListener(e -> {
            System.out.println("Called an number");
        });

        phoneActions.add(callIncomingButton);
        phoneActions.add(callHoldButton);
        phoneActions.add(callCancelButton);
        phoneActions.add(callOutgoingButton);

        //--------------------------------------------------------------------------------------------------------------
        // Phone Calls List
        JPanel phoneCalls = new JPanel();
        phoneCalls.setLayout(new BoxLayout(phoneCalls, BoxLayout.Y_AXIS));

        //--------------------------------------------------------------------------------------------------------------
        // Putting it all together
        JSplitPane phoneCallsSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, phoneActions, phoneCalls);
        phoneCallsSplit.setResizeWeight(0.1);
        SwingUtilities.invokeLater(() -> phoneCallsSplit.setDividerLocation(0.1));

        JSplitPane conversationSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, conversationScroll, conversationActions);
        conversationSplit.setResizeWeight(1.0);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, conversationSplit, phoneCallsSplit);
        phoneCallsSplit.setResizeWeight(0.7);
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
        messageArea.setBorder(new LineBorder(Color.DARK_GRAY));
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
