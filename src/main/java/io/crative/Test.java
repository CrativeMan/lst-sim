package io.crative;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Test {
    // Window references
    private JFrame controlWindow;
    private JFrame mapWindow;
    private JFrame unitsWindow;
    private JFrame incidentsWindow;
    private JFrame communicationsWindow;

    // Shared components
    private JTree unitsTree;
    private JTextArea incidentDetailsArea;
    private JTextArea communicationsLog;
    private JLabel timeLabel;
    private Timer gameTimer;

    // Layout persistence
    private static final String LAYOUT_FILE = "dispatcher_layout.properties";
    private Properties layoutProperties;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Test().initialize();
        });
    }

    public void initialize() {
        loadLayout();
        createControlWindow();
        createMapWindow();
        createUnitsWindow();
        createIncidentsWindow();
        createCommunicationsWindow();
        startGameTimer();

        // Show control window first
        controlWindow.setVisible(true);
    }

    private void createControlWindow() {
        controlWindow = new JFrame("Dispatch Control Center");
        controlWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Emergency Dispatch Control Center", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Window control buttons
        JPanel buttonsPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton showMapButton = new JButton("Show Map Window");
        JButton showUnitsButton = new JButton("Show Units Window");
        JButton showIncidentsButton = new JButton("Show Incidents Window");
        JButton showCommButton = new JButton("Show Communications Window");
        JButton saveLayoutButton = new JButton("Save Layout");
        JButton loadLayoutButton = new JButton("Load Layout");

        // Button actions
        showMapButton.addActionListener(e -> toggleWindow(mapWindow));
        showUnitsButton.addActionListener(e -> toggleWindow(unitsWindow));
        showIncidentsButton.addActionListener(e -> toggleWindow(incidentsWindow));
        showCommButton.addActionListener(e -> toggleWindow(communicationsWindow));
        saveLayoutButton.addActionListener(e -> saveLayout());
        loadLayoutButton.addActionListener(e -> loadAndApplyLayout());

        buttonsPanel.add(showMapButton);
        buttonsPanel.add(showUnitsButton);
        buttonsPanel.add(showIncidentsButton);
        buttonsPanel.add(showCommButton);
        buttonsPanel.add(saveLayoutButton);
        buttonsPanel.add(loadLayoutButton);

        // Status area
        JPanel statusPanel = new JPanel(new FlowLayout());
        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        statusPanel.add(new JLabel("Current Time: "));
        statusPanel.add(timeLabel);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);
        panel.add(statusPanel, BorderLayout.SOUTH);

        controlWindow.add(panel);
        applyWindowLayout(controlWindow, "control", 100, 100, 300, 400);

        // Save layout when window is moved/resized
        addLayoutSaveListener(controlWindow, "control");
    }

    private void createMapWindow() {
        mapWindow = new JFrame("Operational Map - München");
        mapWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Map area
        JPanel mapArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw a street map-like grid
                g2.setColor(new Color(240, 240, 240));
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Major streets
                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(3));
                for (int x = 0; x < getWidth(); x += 100) {
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 0; y < getHeight(); y += 80) {
                    g2.drawLine(0, y, getWidth(), y);
                }

                // Minor streets
                g2.setColor(new Color(220, 220, 220));
                g2.setStroke(new BasicStroke(1));
                for (int x = 50; x < getWidth(); x += 100) {
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 40; y < getHeight(); y += 80) {
                    g2.drawLine(0, y, getWidth(), y);
                }

                // Emergency stations
                drawEmergencyStation(g2, 150, 120, "🚒", "Fire Station 1");
                drawEmergencyStation(g2, 350, 200, "🚒", "Fire Station 2");
                drawEmergencyStation(g2, 500, 150, "🏥", "Hospital Nord");
                drawEmergencyStation(g2, 200, 300, "🏥", "Hospital Süd");
                drawEmergencyStation(g2, 400, 350, "👮", "Police Station");

                // Active incidents
                drawIncident(g2, 250, 250, "🔥", "House Fire", Color.RED);
                drawIncident(g2, 450, 280, "🚑", "Medical Emergency", Color.ORANGE);
            }

            private void drawEmergencyStation(Graphics2D g2, int x, int y, String emoji, String label) {
                g2.setColor(Color.GREEN);
                g2.fillRect(x-10, y-10, 20, 20);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.PLAIN, 16));
                g2.drawString(emoji, x-8, y+5);
                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                g2.drawString(label, x+15, y+5);
            }

            private void drawIncident(Graphics2D g2, int x, int y, String emoji, String label, Color color) {
                g2.setColor(color);
                g2.fillOval(x-8, y-8, 16, 16);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.drawString(emoji, x-6, y+4);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                g2.drawString(label, x+12, y+4);
            }
        };

        mapArea.setBackground(Color.WHITE);
        mapArea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                createIncidentAtLocation(e.getX(), e.getY());
            }
        });

        panel.add(mapArea, BorderLayout.CENTER);

        // Map controls
        JPanel controlsPanel = new JPanel(new FlowLayout());
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");
        JButton centerButton = new JButton("Center Map");
        JLabel coordinatesLabel = new JLabel("Click to create incident");

        controlsPanel.add(zoomInButton);
        controlsPanel.add(zoomOutButton);
        controlsPanel.add(centerButton);
        controlsPanel.add(Box.createHorizontalStrut(20));
        controlsPanel.add(coordinatesLabel);

        panel.add(controlsPanel, BorderLayout.SOUTH);

        mapWindow.add(panel);
        applyWindowLayout(mapWindow, "map", 450, 50, 700, 500);
        addLayoutSaveListener(mapWindow, "map");
    }

    private void createUnitsWindow() {
        unitsWindow = new JFrame("Emergency Units Status");
        unitsWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Units tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Emergency Services");

        DefaultMutableTreeNode fireNode = new DefaultMutableTreeNode("🚒 Fire Department");
        fireNode.add(new DefaultMutableTreeNode("🟢 Alarm SEG Tr 1 West - Available"));
        fireNode.add(new DefaultMutableTreeNode("🟡 Alarm SEG Tr 2 Ost - En Route"));
        fireNode.add(new DefaultMutableTreeNode("🟢 Alarm SEG Tr 3 Nord - Available"));
        fireNode.add(new DefaultMutableTreeNode("🔴 Alarm SEG Tr 4 Süd - Busy"));
        root.add(fireNode);

        DefaultMutableTreeNode ambulanceNode = new DefaultMutableTreeNode("🚑 Emergency Medical");
        ambulanceNode.add(new DefaultMutableTreeNode("🟢 AK Hohenbrunn 71/1 - Available"));
        ambulanceNode.add(new DefaultMutableTreeNode("🟡 AK Obersending 71/1 - En Route"));
        ambulanceNode.add(new DefaultMutableTreeNode("🟢 AK Sendling 71/1 - Available"));
        ambulanceNode.add(new DefaultMutableTreeNode("🔴 AK Sendling 71/2 - Busy"));
        root.add(ambulanceNode);

        DefaultMutableTreeNode policeNode = new DefaultMutableTreeNode("👮 Police Units");
        policeNode.add(new DefaultMutableTreeNode("🟢 Patrol 1 - Available"));
        policeNode.add(new DefaultMutableTreeNode("🟡 Patrol 2 - En Route"));
        policeNode.add(new DefaultMutableTreeNode("🟢 Traffic Unit - Available"));
        root.add(policeNode);

        DefaultMutableTreeNode flightNode = new DefaultMutableTreeNode("🚁 Flight Operations");
        flightNode.add(new DefaultMutableTreeNode("🟢 FL Airbus 79/1 - Available"));
        flightNode.add(new DefaultMutableTreeNode("🟢 FL Aschheim 79/1 - Available"));
        root.add(flightNode);

        unitsTree = new JTree(new DefaultTreeModel(root));
        unitsTree.setRootVisible(false);
        unitsTree.setShowsRootHandles(true);

        // Expand all nodes
        for (int i = 0; i < unitsTree.getRowCount(); i++) {
            unitsTree.expandRow(i);
        }

        JScrollPane treeScrollPane = new JScrollPane(unitsTree);
        panel.add(treeScrollPane, BorderLayout.CENTER);

        // Unit controls
        JPanel controlsPanel = new JPanel(new FlowLayout());
        JButton dispatchButton = new JButton("Dispatch Selected Unit");
        JButton recallButton = new JButton("Recall Unit");
        JButton statusButton = new JButton("Unit Details");

        dispatchButton.addActionListener(e -> dispatchSelectedUnit());

        controlsPanel.add(dispatchButton);
        controlsPanel.add(recallButton);
        controlsPanel.add(statusButton);

        panel.add(controlsPanel, BorderLayout.SOUTH);

        // Status summary
        JPanel statusPanel = new JPanel(new GridLayout(1, 3));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        statusPanel.add(new JLabel("Available: 7", JLabel.CENTER));
        statusPanel.add(new JLabel("En Route: 3", JLabel.CENTER));
        statusPanel.add(new JLabel("Busy: 2", JLabel.CENTER));

        panel.add(statusPanel, BorderLayout.NORTH);

        unitsWindow.add(panel);
        applyWindowLayout(unitsWindow, "units", 1200, 50, 400, 600);
        addLayoutSaveListener(unitsWindow, "units");
    }

    private void createIncidentsWindow() {
        incidentsWindow = new JFrame("Active Incidents");
        incidentsWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Incident details area
        incidentDetailsArea = new JTextArea();
        incidentDetailsArea.setEditable(false);
        incidentDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        incidentDetailsArea.setText(
                "INCIDENT #001 - ACTIVE\n" +
                        "=====================================\n" +
                        "Type: House Fire\n" +
                        "Priority: HIGH\n" +
                        "Location: Rosenheimer Straße 123\n" +
                        "         München-Haidhausen Süd\n" +
                        "Time: 22:15:30\n\n" +
                        "Assigned Units:\n" +
                        "• Fire Station 2 (En Route - ETA 5 min)\n" +
                        "• Ambulance 71/1 (En Route - ETA 7 min)\n" +
                        "• Police Patrol 2 (En Route - ETA 3 min)\n\n" +
                        "Status Updates:\n" +
                        "22:15:30 - Incident reported by caller\n" +
                        "22:16:45 - Units dispatched\n" +
                        "22:18:12 - Fire unit acknowledges\n" +
                        "22:19:33 - Police on scene\n\n" +
                        "Current Situation:\n" +
                        "Structure fire in residential building.\n" +
                        "Unknown casualties. Police securing area.\n" +
                        "Fire department ETA 2 minutes.\n\n" +
                        "=====================================\n\n" +
                        "INCIDENT #002 - ACTIVE\n" +
                        "=====================================\n" +
                        "Type: Medical Emergency\n" +
                        "Priority: MEDIUM\n" +
                        "Location: Marienplatz 15\n" +
                        "Time: 22:18:45\n\n" +
                        "Assigned Units:\n" +
                        "• Ambulance AK Sendling 71/1 (Dispatched)\n\n" +
                        "Status: Chest pain, conscious patient\n"
        );

        JScrollPane detailsScrollPane = new JScrollPane(incidentDetailsArea);
        panel.add(detailsScrollPane, BorderLayout.CENTER);

        // Incident controls
        JPanel controlsPanel = new JPanel(new FlowLayout());
        JButton newIncidentButton = new JButton("New Incident");
        JButton closeIncidentButton = new JButton("Close Incident");
        JButton updateButton = new JButton("Update Status");
        JButton printButton = new JButton("Print Report");

        newIncidentButton.addActionListener(e -> createNewIncident());

        controlsPanel.add(newIncidentButton);
        controlsPanel.add(closeIncidentButton);
        controlsPanel.add(updateButton);
        controlsPanel.add(printButton);

        panel.add(controlsPanel, BorderLayout.SOUTH);

        incidentsWindow.add(panel);
        applyWindowLayout(incidentsWindow, "incidents", 1200, 400, 500, 400);
        addLayoutSaveListener(incidentsWindow, "incidents");
    }

    private void createCommunicationsWindow() {
        communicationsWindow = new JFrame("Communications Log");
        communicationsWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // Communications log
        communicationsLog = new JTextArea();
        communicationsLog.setEditable(false);
        communicationsLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        communicationsLog.setText(
                "22:15:30 📞 [INCOMING CALL] Emergency call from Rosenheimer Straße\n" +
                        "22:15:32 🗣️  Caller: \"There's a fire in my building, please help!\"\n" +
                        "22:15:45 📋 [DISPATCH] Creating incident #001 - House Fire\n" +
                        "22:16:12 📡 [RADIO] Dispatching Fire Station 2 to incident\n" +
                        "22:16:15 📡 [RADIO] Fire Station 2: \"Unit responding, ETA 8 minutes\"\n" +
                        "22:16:45 📡 [RADIO] Dispatching Ambulance 71/1 as precaution\n" +
                        "22:17:12 📡 [RADIO] Police Patrol 2: \"En route to secure area\"\n" +
                        "22:18:30 📡 [RADIO] Police Patrol 2: \"On scene, evacuating residents\"\n" +
                        "22:18:45 📞 [INCOMING CALL] Medical emergency at Marienplatz\n" +
                        "22:19:00 📋 [DISPATCH] Creating incident #002 - Medical Emergency\n" +
                        "22:19:15 📡 [RADIO] Dispatching AK Sendling 71/1\n" +
                        "22:19:33 📡 [RADIO] Fire Station 2: \"On scene, beginning suppression\"\n" +
                        "22:20:15 📡 [RADIO] Ambulance 71/1: \"On scene incident #001, no casualties\"\n" +
                        "22:20:48 📡 [RADIO] AK Sendling: \"En route to Marienplatz, ETA 4 minutes\"\n" +
                        "22:21:12 ⚡ [SYSTEM] Data transmission successful - All units updated\n"
        );

        JScrollPane logScrollPane = new JScrollPane(communicationsLog);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Auto-scroll to bottom
        logScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (!e.getValueIsAdjusting()) {
                JScrollBar scrollBar = (JScrollBar) e.getSource();
                scrollBar.setValue(scrollBar.getMaximum());
            }
        });

        panel.add(logScrollPane, BorderLayout.CENTER);

        // Communication controls
        JPanel controlsPanel = new JPanel(new FlowLayout());
        JButton clearLogButton = new JButton("Clear Log");
        JButton saveLogButton = new JButton("Save Log");
        JCheckBox autoScrollBox = new JCheckBox("Auto-scroll", true);
        JButton filterButton = new JButton("Filter Messages");

        controlsPanel.add(clearLogButton);
        controlsPanel.add(saveLogButton);
        controlsPanel.add(autoScrollBox);
        controlsPanel.add(filterButton);

        panel.add(controlsPanel, BorderLayout.SOUTH);

        communicationsWindow.add(panel);
        applyWindowLayout(communicationsWindow, "communications", 450, 600, 700, 300);
        addLayoutSaveListener(communicationsWindow, "communications");
    }

    private void toggleWindow(JFrame window) {
        if (window.isVisible()) {
            window.setVisible(false);
        } else {
            window.setVisible(true);
            window.toFront();
        }
    }

    private void startGameTimer() {
        gameTimer = new Timer(1000, (ActionEvent e) -> {
            LocalTime currentTime = LocalTime.now();
            timeLabel.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            // Simulate communications updates
            if (Math.random() < 0.05) { // 5% chance each second
                addCommunicationMessage(generateRandomMessage());
            }
        });
        gameTimer.start();
    }

    private void createIncidentAtLocation(int x, int y) {
        String[] incidentTypes = {"Fire", "Medical Emergency", "Traffic Accident", "Police Matter"};
        String type = incidentTypes[(int)(Math.random() * incidentTypes.length)];

        String message = String.format("Creating new %s incident at map coordinates (%d, %d)", type, x, y);
        JOptionPane.showMessageDialog(mapWindow, message);

        addCommunicationMessage(String.format("📋 [DISPATCH] New %s incident created at coordinates %d,%d", type, x, y));
    }

    private void dispatchSelectedUnit() {
        if (unitsTree.getSelectionPath() != null) {
            Object selected = unitsTree.getSelectionPath().getLastPathComponent();
            String unitName = selected.toString();
            JOptionPane.showMessageDialog(unitsWindow, "Dispatching unit: " + unitName);
            addCommunicationMessage("📡 [RADIO] Dispatching " + unitName);
        } else {
            JOptionPane.showMessageDialog(unitsWindow, "Please select a unit first");
        }
    }

    private void createNewIncident() {
        JDialog dialog = new JDialog(incidentsWindow, "Create New Incident", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(incidentsWindow);

        // Simple form for demo
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Type:"));
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Fire", "Medical", "Accident", "Police"});
        panel.add(typeCombo);

        panel.add(new JLabel("Priority:"));
        JComboBox<String> priorityCombo = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        panel.add(priorityCombo);

        panel.add(new JLabel("Location:"));
        JTextField locationField = new JTextField();
        panel.add(locationField);

        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");

        createButton.addActionListener(e -> {
            String type = (String) typeCombo.getSelectedItem();
            String priority = (String) priorityCombo.getSelectedItem();
            String location = locationField.getText();

            addCommunicationMessage(String.format("📋 [DISPATCH] New %s (%s priority) at %s", type, priority, location));
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        panel.add(createButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void addCommunicationMessage(String message) {
        if (communicationsLog != null) {
            LocalTime currentTime = LocalTime.now();
            String timeStamp = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String fullMessage = timeStamp + " " + message + "\n";

            SwingUtilities.invokeLater(() -> {
                communicationsLog.append(fullMessage);
                communicationsLog.setCaretPosition(communicationsLog.getDocument().getLength());
            });
        }
    }

    private String generateRandomMessage() {
        String[] messages = {
                "📡 [RADIO] Unit performing status check",
                "📡 [RADIO] Unit requesting backup",
                "📡 [RADIO] Unit reporting all clear",
                "⚡ [SYSTEM] GPS position updated",
                "📡 [RADIO] Unit returning to station"
        };
        return messages[(int)(Math.random() * messages.length)];
    }

    // Layout persistence methods
    private void applyWindowLayout(JFrame window, String prefix, int defaultX, int defaultY, int defaultWidth, int defaultHeight) {
        int x = Integer.parseInt(layoutProperties.getProperty(prefix + ".x", String.valueOf(defaultX)));
        int y = Integer.parseInt(layoutProperties.getProperty(prefix + ".y", String.valueOf(defaultY)));
        int width = Integer.parseInt(layoutProperties.getProperty(prefix + ".width", String.valueOf(defaultWidth)));
        int height = Integer.parseInt(layoutProperties.getProperty(prefix + ".height", String.valueOf(defaultHeight)));

        window.setBounds(x, y, width, height);
    }

    private void addLayoutSaveListener(JFrame window, String prefix) {
        window.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentMoved(java.awt.event.ComponentEvent e) {
                saveWindowPosition(window, prefix);
            }

            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                saveWindowPosition(window, prefix);
            }
        });
    }

    private void saveWindowPosition(JFrame window, String prefix) {
        Rectangle bounds = window.getBounds();
        layoutProperties.setProperty(prefix + ".x", String.valueOf(bounds.x));
        layoutProperties.setProperty(prefix + ".y", String.valueOf(bounds.y));
        layoutProperties.setProperty(prefix + ".width", String.valueOf(bounds.width));
        layoutProperties.setProperty(prefix + ".height", String.valueOf(bounds.height));
    }

    private void loadLayout() {
        layoutProperties = new Properties();
        try {
            File layoutFile = new File(LAYOUT_FILE);
            if (layoutFile.exists()) {
                try (FileInputStream fis = new FileInputStream(layoutFile)) {
                    layoutProperties.load(fis);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load layout: " + e.getMessage());
        }
    }

    private void saveLayout() {
        try (FileOutputStream fos = new FileOutputStream(LAYOUT_FILE)) {
            layoutProperties.store(fos, "Dispatcher Window Layout");
            JOptionPane.showMessageDialog(controlWindow, "Layout saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(controlWindow, "Could not save layout: " + e.getMessage());
        }
    }

    private void loadAndApplyLayout() {
        loadLayout();

        if (mapWindow != null) applyWindowLayout(mapWindow, "map", 450, 50, 700, 500);
        if (unitsWindow != null) applyWindowLayout(unitsWindow, "units", 1200, 50, 400, 600);
        if (incidentsWindow != null) applyWindowLayout(incidentsWindow, "incidents", 1200, 400, 500, 400);
        if (communicationsWindow != null) applyWindowLayout(communicationsWindow, "communications", 450, 600, 700, 300);
        if (controlWindow != null) applyWindowLayout(controlWindow, "control", 100, 100, 300, 400);

        JOptionPane.showMessageDialog(controlWindow, "Layout loaded and applied!");
    }
}