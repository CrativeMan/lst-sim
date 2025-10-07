package io.crative.frontend.view.deprecated;

import io.crative.backend.fileio.FileLoader;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class TestWindow extends Window{
    public TestWindow() {
        super("test", 500, 500, 400, 180);
        this.setTitle("Test");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(3, 1));
        panelInfo.add(new JLabel("112"));
        panelInfo.add(new JLabel("+49 1515 2249137"));
        panelInfo.add(new JLabel("München"));

        panel.add(panelInfo, BorderLayout.WEST);
        Image img = FileLoader.loadImageScaled("/icons/phone/phone-call.png", 2.0F);
        assert img != null;
        panel.add(new JLabel(new ImageIcon(img)), BorderLayout.EAST);
        this.add(panel);
    }
}
