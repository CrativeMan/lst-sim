package io.crative.frontend.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WindowLayoutManager {
    private static final String LAYOUT_FILE = "window_layout.properties";
    private static final Properties layoutProperties = new Properties();
    private static List<Window> windows = new ArrayList<>();
    private static boolean initialized = false;

    public static void initialize(List<Window> windows) {
        WindowLayoutManager.windows = windows;
        WindowLayoutManager.initialized = true;
        for (Window w : WindowLayoutManager.windows) {
            WindowLayoutManager.applyWindowLayout(w);
            WindowLayoutManager.addLayoutSaveListener(w);
        }
    }

    static {
        loadLayout();
    }

    public static void applyWindowLayout(Window window) {
        if (!initialized) System.err.println("WindowLayoutManager: Not initialized");
        int x = Integer.parseInt(layoutProperties.getProperty(window.getPrefix() + ".x", String.valueOf(window.getDefaultX())));
        int y = Integer.parseInt(layoutProperties.getProperty(window.getPrefix() + ".y", String.valueOf(window.getDefaultY())));
        int width = Integer.parseInt(layoutProperties.getProperty(window.getPrefix() + ".width", String.valueOf(window.getDefaultWidth())));
        int height = Integer.parseInt(layoutProperties.getProperty(window.getPrefix() + ".height", String.valueOf(window.getDefaultHeight())));

        window.setBounds(x, y, width, height);
    }

    public static void addLayoutSaveListener(Window window) {
        if (!initialized) System.err.println("WindowLayoutManager: Not initialized");
        window.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentMoved(java.awt.event.ComponentEvent e) {
                saveWindowPosition(window, window.getPrefix());
            }

            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                saveWindowPosition(window, window.getPrefix());
            }
        });
    }

    private static void saveWindowPosition(JFrame window, String prefix) {
        if (!initialized) System.err.println("WindowLayoutManager: Not initialized");
        Rectangle bounds = window.getBounds();
        layoutProperties.setProperty(prefix + ".x", String.valueOf(bounds.x));
        layoutProperties.setProperty(prefix + ".y", String.valueOf(bounds.y));
        layoutProperties.setProperty(prefix + ".width", String.valueOf(bounds.width));
        layoutProperties.setProperty(prefix + ".height", String.valueOf(bounds.height));
    }

    public static void loadLayout() {
        if (!initialized) System.err.println("WindowLayoutManager: Not initialized");
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
        for(Window w : windows) {
            WindowLayoutManager.applyWindowLayout(w);
        }
    }

    public static void loadDefaultLayout() {
        if (!initialized) System.err.println("WindowLayoutManager: Not initialized");

        for(Window w : windows) {
            w.setBounds(w.getDefaultX(), w.getDefaultY(), w.getDefaultWidth(), w.getDefaultHeight());
        }
    }

    public static void saveLayout() {
        if (!initialized) System.err.println("WindowLayoutManager: Not initialized");
        try (FileOutputStream fos = new FileOutputStream(LAYOUT_FILE)) {
            layoutProperties.store(fos, "Window Layouts");
        } catch (IOException e) {
            System.err.println("Could not save layout: " + e.getMessage());
        }
    }
}
