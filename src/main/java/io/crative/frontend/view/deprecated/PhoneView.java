package io.crative.frontend.view.deprecated;

import io.crative.backend.CallListener;
import io.crative.backend.CallManager;
import io.crative.backend.data.PhoneCall;
import io.crative.backend.data.PhoneCallStatus;
import io.crative.backend.fileio.ResourceHelper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static io.crative.backend.internationalization.TranslationManager.t;

@Deprecated
public class PhoneView extends Window implements CallListener {
    private JPanel conversation;
    private JScrollPane conversationScroll;
    private JPanel phoneCalls;
    private PhoneCall selectedCall;
    private JPanel selectedCallPanel;

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

    private final Color SELECTED_COLOR = new Color(200, 220, 255); // Light blue

    private static final float MAIN_SPLIT_WEIGHT = 1.0F;
    private static final float CONVERSATION_SPLIT_WEIGHT = 1.0F;
    private static final float PHONE_SPLIT_WEIGHT = 0.05F;

    public PhoneView() {
        super("phone", DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initializeView();
        CallManager cm = CallManager.getInstance();
        cm.registerListener(this);
    }

    private void initializeView() {
        this.setTitle(t("window.title.phone"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Conversation bubble Panel
        createConversation();

        // Conversation actions
        JPanel conversationActions = createConversationActions();

        // Phone Actions
        JPanel phoneActions = createPhoneActions();

        // Phone Calls List
        phoneCalls = new JPanel();
        phoneCalls.setLayout(new BoxLayout(phoneCalls, BoxLayout.Y_AXIS));

        JPanel phoneCallsWrapper = new JPanel(new BorderLayout());
        phoneCallsWrapper.add(phoneCalls, BorderLayout.NORTH);

        JScrollPane phoneCallsScroll = new JScrollPane(phoneCallsWrapper);
        phoneCallsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        phoneCallsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Putting it all together
        JSplitPane splitPane = getSplitPane(phoneActions, phoneCallsScroll, conversationActions);

        this.add(splitPane);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setVisible(true);
    }

    private void createConversation() {
        conversation = new JPanel();
        conversation.setLayout(new BoxLayout(conversation, BoxLayout.Y_AXIS));

        JPanel conversationWrapper = new JPanel(new BorderLayout());
        conversationWrapper.add(conversation, BorderLayout.NORTH);

        conversationScroll = new JScrollPane(conversationWrapper);
        conversationScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        conversationScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private JPanel createConversationActions() {
        JPanel conversationActions = new JPanel();
        conversationActions.setLayout(new BoxLayout(conversationActions, BoxLayout.Y_AXIS));
        for (String key : conversationButtonKeys) {
            JButton b = new JButton(t(key));
            b.addActionListener(e -> {
                System.out.println(key + " pressed");
            });
            conversationActions.add(b);
            conversationActions.add(Box.createVerticalStrut(5));
        }
        return conversationActions;
    }

    private JPanel createPhoneActions() {
        JPanel phoneActions = new JPanel(new FlowLayout());

        JButton callIncomingButton = new JButton();
        callIncomingButton.setIcon(new ImageIcon(ResourceHelper.loadImage("/icons/phone/phone-call.png")));
        callIncomingButton.addActionListener(e -> {
            if (selectedCall == null) {
                System.err.println("PhoneActions: No call selected");
                return;
            }
            if (selectedCall.getStatus() == PhoneCallStatus.RINGING) {
                CallManager.getInstance().acceptCall(selectedCall);
            } else {
                if (selectedCall.getStatus() == PhoneCallStatus.ON_HOLD) {
                    CallManager.getInstance().resumeCall(selectedCall);
                } else {
                    System.err.println("Selected call is not ringing or on hold.");
                }
            }
        });

        JButton callHoldButton = new JButton();
        callHoldButton.setIcon(new ImageIcon(ResourceHelper.loadImage("/icons/phone/phone-hold.png")));
        callHoldButton.addActionListener(e -> {
            if (selectedCall != null) {
                CallManager.getInstance().holdCall(selectedCall);
            } else {
                System.err.println("No call selected to hold.");
            }
        });

        JButton callCancelButton = new JButton();
        callCancelButton.setIcon(new ImageIcon(ResourceHelper.loadImage("/icons/phone/phone-off.png")));
        callCancelButton.addActionListener(e->{
            if (selectedCall != null) {
                CallManager.getInstance().endCall(selectedCall);
            } else {
                System.err.println("No call selected to end.");
            }
        });

        JButton callOutgoingButton = new JButton();
        callOutgoingButton.setIcon(new ImageIcon(ResourceHelper.loadImage("/icons/phone/phone-outgoing.png")));
        callOutgoingButton.addActionListener(e -> System.out.println("Called an number"));

        phoneActions.add(callIncomingButton);
        phoneActions.add(callHoldButton);
        phoneActions.add(callCancelButton);
        phoneActions.add(callOutgoingButton);

        return phoneActions;
    }

    private JSplitPane getSplitPane(JPanel phoneActions, JComponent phoneCalls, JPanel conversationActions) {
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

    public void addCallEntry(PhoneCall call) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(3, 1));
        panelInfo.add(new JLabel(call.getCallerName() != null ? call.getCallerName() : "Unknown"));
        panelInfo.add(new JLabel(call.getPhoneNumber()));
        panelInfo.add(new JLabel(call.getLocation().toString()));

        panel.add(panelInfo, BorderLayout.WEST);
        Image img = ResourceHelper.loadImageScaled("/icons/phone/phone-call.png", 2.0F);
        assert img != null;
        panel.add(new JLabel(new ImageIcon(img)), BorderLayout.EAST);

        panel.setBorder(new LineBorder(Color.BLACK));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectCall(call, panel);
            }
        });

        phoneCalls.add(panel, 0);

        phoneCalls.add(Box.createVerticalStrut(2), 1);

        phoneCalls.revalidate();
        phoneCalls.repaint();
    }

    private void selectCall(PhoneCall call, JPanel panel) {
        if (selectedCallPanel != null) {
            selectedCallPanel.setBackground(UIManager.getColor("Panel.background"));
            selectedCallPanel.setOpaque(true);
            selectedCallPanel.repaint();
        }

        selectedCall =call;
        selectedCallPanel = panel;

        selectedCallPanel.setBackground(SELECTED_COLOR);
        selectedCallPanel.setOpaque(true);
        selectedCallPanel.repaint();
    }

    // =================================================================================================================
    // Listener Functions

    @Override
    public void onCallReceived(PhoneCall call) {
        addCallEntry(call);
    }

    @Override
    public void onCallAccepted(PhoneCall call) {
        addMessage(t("conversation.phone.start"), true);
    }

    @Override
    public void onCallEnded(PhoneCall call) {

    }

    @Override
    public void onCallHeld(PhoneCall call) {

    }

    @Override
    public void onCallResumed(PhoneCall call) {

    }
}
