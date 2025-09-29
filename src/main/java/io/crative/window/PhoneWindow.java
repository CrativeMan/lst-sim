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

    private static final int DEFAULT_X = 0;
    private static final int DEFAULT_Y = 0;
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 700;

    public static final float MAIN_SPLIT_WEIGHT = 1.0F;
    public static final float CONVERSATION_SPLIT_WEIGHT = 1.0F;
    public static final float PHONE_SPLIT_WEIGHT = 0.05F;

    public PhoneWindow() {
        super("phone",DEFAULT_X,DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initialize();
    }

    private void initialize() {
        this.setTitle(t("window.title.phone"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Conversation bubble Panel
        conversation = new JPanel();
        conversation.setLayout(new BoxLayout(conversation, BoxLayout.Y_AXIS));

        JPanel conversationWrapper = new JPanel(new BorderLayout());
        conversationWrapper.add(conversation, BorderLayout.NORTH);

        conversationScroll = new JScrollPane(conversationWrapper);
        conversationScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        conversationScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Conversation actions
        JPanel conversationActions = new JPanel();
        conversationActions.setLayout(new BoxLayout(conversationActions, BoxLayout.Y_AXIS));
        for (String key : conversationButtonKeys) {
            JButton b = new JButton(t(key));
            b.addActionListener(e -> {
                addMessage(t(key + ".message"), true);
                addMessage(t(key + ".message"), false);
            });
            conversationActions.add(b);
            conversationActions.add(Box.createVerticalStrut(5));
        }

        // Phone Actions
        JPanel phoneActions = createPhoneActions();

        // Phone Calls List
        JPanel phoneCalls = new JPanel();
        phoneCalls.setLayout(new BoxLayout(phoneCalls, BoxLayout.Y_AXIS));

        // Putting it all together
        JSplitPane splitPane = getSplitPane(phoneActions, phoneCalls, conversationActions);

        this.add(splitPane);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private JPanel createPhoneActions() {
        JPanel phoneActions = new JPanel(new FlowLayout());

        JButton callIncomingButton = new JButton();
        callIncomingButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-call.png")));
        callIncomingButton.addActionListener(e -> System.out.println("Accepted incoming call"));

        JButton callHoldButton = new JButton();
        callHoldButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-hold.png")));
        callHoldButton.addActionListener(e -> System.out.println("Put call on hold"));

        JButton callCancelButton = new JButton();
        callCancelButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-off.png")));
        callCancelButton.addActionListener(e -> System.out.println("Cancelled call"));

        JButton callOutgoingButton = new JButton();
        callOutgoingButton.setIcon(new ImageIcon(FileLoader.loadImage("/icons/phone/phone-outgoing.png")));
        callOutgoingButton.addActionListener(e -> System.out.println("Called an number"));

        phoneActions.add(callIncomingButton);
        phoneActions.add(callHoldButton);
        phoneActions.add(callCancelButton);
        phoneActions.add(callOutgoingButton);

        return phoneActions;
    }

    private JSplitPane getSplitPane(JPanel phoneActions, JPanel phoneCalls, JPanel conversationActions) {
        JSplitPane phoneCallsSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, phoneActions, phoneCalls);
        phoneCallsSplit.setResizeWeight(PHONE_SPLIT_WEIGHT);

        JSplitPane conversationSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, conversationScroll, conversationActions);
        conversationSplit.setResizeWeight(CONVERSATION_SPLIT_WEIGHT);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, conversationSplit, phoneCallsSplit);
        splitPane.setResizeWeight(MAIN_SPLIT_WEIGHT);
        return splitPane;
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
