package io.crative.frontend.view;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class Window extends JFrame {
    private String prefix;
    private int defaultX, defaultY, defaultWidth, defaultHeight;

    protected Window(String prefix, int defaultX, int defaultY, int defaultWidth, int defaultHeight) {
        this.prefix = prefix;
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        menuBar();
    }

    private void menuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu layoutMenu = layoutMenu();
        menuBar.add(layoutMenu);

        this.setJMenuBar(menuBar);
    }

    private JMenu layoutMenu() {
        JMenu menu = new JMenu("Layout");

        JMenuItem saveLayout = new JMenuItem(new AbstractAction("Save Layout") {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowLayoutManager.saveLayout();
            }
        });
        JMenuItem loadLayout = new JMenuItem(new AbstractAction("Load Layout") {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowLayoutManager.loadLayout();
            }
        });
        JMenuItem loadDefaultLayout = new JMenuItem(new AbstractAction("Load Default Layout") {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowLayoutManager.loadDefaultLayout();
            }
        });
        menu.add(saveLayout);
        menu.add(loadLayout);
        menu.add(loadDefaultLayout);
        return menu;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Getter Setter
    public String getPrefix() {
        return prefix;
    }

    public int getDefaultX() {
        return defaultX;
    }

    public int getDefaultY() {
        return defaultY;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }
}
