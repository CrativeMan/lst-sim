package io.crative;

import javax.swing.*;
import java.awt.*;

public class LstSim {
    private static JFrame mainFrame;
    private static JFrame callsWindow;
    private static JFrame unitsWindow;
    private static JDialog settingsDialog;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("SETUP: Failed to set the look and feel to the one from the System");
        }

        SwingUtilities.invokeLater(LstSim::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        createMainWindow();
        createCallsWindow();
        createUnitsWindow();
        createSettingsDialog();

        mainFrame.setVisible(true);
        callsWindow.setVisible(true);
        unitsWindow.setVisible(true);
    }

    private static void createMainWindow() {
        // Create the main frame
        mainFrame = new JFrame("Emergency Dispatch Simulator - Main Control");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 400);
        mainFrame.setLocation(100, 100);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Emergency Dispatch Control Center", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton startButton = new JButton("Start Simulation");
        startButton.setFont(new Font("Arial", Font.PLAIN, 14));
        startButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame,
                    "Simulation started!\nOther windows are now available.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton callsButton = new JButton("Show Calls Window");
        callsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        callsButton.addActionListener(e -> {
            callsWindow.setVisible(true);
            callsWindow.toFront();
        });

        JButton unitsButton = new JButton("Show Units Window");
        unitsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        unitsButton.addActionListener(e -> {
            unitsWindow.setVisible(true);
            unitsWindow.toFront();
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        settingsButton.addActionListener(e -> {
            settingsDialog.setVisible(true);
        });

        buttonsPanel.add(startButton);
        buttonsPanel.add(callsButton);
        buttonsPanel.add(unitsButton);
        buttonsPanel.add(settingsButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        mainFrame.add(mainPanel);
    }

    private static void createCallsWindow() {
        callsWindow = new JFrame("Active Emergency Calls");
        callsWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        callsWindow.setSize(600, 500);
        callsWindow.setLocation(650, 100);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel header = new JLabel("Emergency Calls Dashboard", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] callsData = {
                "CALL-001: House Fire - 123 Main St - HIGH PRIORITY",
                "CALL-002: Medical Emergency - 456 Oak Ave - MEDIUM PRIORITY",
                "CALL-003: Car Accident - Highway 101 - HIGH PRIORITY",
                "CALL-004: Theft Report - 789 Pine St - LOW PRIORITY"
        };

        JList<String> callsList = new JList<>(callsData);
        callsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        callsList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(callsList);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton dispatchButton = new JButton("Dispatch Unit");
        JButton closeCallButton = new JButton("Close Call");
        buttonsPanel.add(dispatchButton);
        buttonsPanel.add(closeCallButton);

        dispatchButton.addActionListener(e -> {
            String selected = callsList.getSelectedValue();
            if (selected != null) {
                JOptionPane.showMessageDialog(callsWindow, "Unit dispatched to: " + selected);
            } else {
                JOptionPane.showMessageDialog(callsWindow, "Please select a call first");
            }
        });

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        callsWindow.add(panel);
    }

    private static void createUnitsWindow() {
        unitsWindow = new JFrame("Emergency Units Status");
        unitsWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        unitsWindow.setSize(500, 400);
        unitsWindow.setLocation(100, 550);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel header = new JLabel("Emergency Units Dashboard", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] unitsData = {
                "FIRE-01: Available - Station 1",
                "FIRE-02: En Route - Main St Fire",
                "AMB-01: Available - Station 2",
                "AMB-02: Busy - Oak Ave Medical",
                "POLICE-01: Available - Patrol",
                "POLICE-02: En Route - Highway 101"
        };

        JList<String> unitsList = new JList<>(unitsData);
        unitsList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(unitsList);

        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout());
        statusPanel.add(new JLabel("Available Units: 3"));
        statusPanel.add(new JLabel("Busy Units: 3"));

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(statusPanel, BorderLayout.SOUTH);

        unitsWindow.add(panel);
    }

    private static void createSettingsDialog() {
        settingsDialog = new JDialog(mainFrame, "Settings", true);
        settingsDialog.setSize(400, 300);
        settingsDialog.setLocationRelativeTo(mainFrame);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Simulation Speed:"));
        JSlider speedSlider = new JSlider(1, 10, 5);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        formPanel.add(speedSlider);

        formPanel.add(new JLabel("Sound Effects:"));
        JCheckBox soundCheckbox = new JCheckBox("Enabled", true);
        formPanel.add(soundCheckbox);

        formPanel.add(new JLabel("Difficulty:"));
        JComboBox<String> difficultyCombo = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        formPanel.add(difficultyCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(settingsDialog, "Settings saved!");
            settingsDialog.setVisible(false);
        });

        cancelButton.addActionListener(e -> settingsDialog.setVisible(false));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        settingsDialog.add(panel);
    }
}