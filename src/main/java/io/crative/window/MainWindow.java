package io.crative.window;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setTitle("Leitstellen Simulator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float scalingFactor = 0.3F;

        int width = (int) (screenSize.getWidth() * (1 - scalingFactor));
        int height = (int) (screenSize.getHeight() * (1 - scalingFactor));

        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Test"));

        this.add(mainPanel);
        this.setVisible(true);
    }
}
