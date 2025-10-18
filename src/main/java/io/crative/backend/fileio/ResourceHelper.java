package io.crative.backend.fileio;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ResourceHelper {
    private ResourceHelper() {

    }

    public static Image loadImage(String filepath) {
        Image img;
        try {
            img = ImageIO.read(Objects.requireNonNull(ResourceHelper.class.getResource(filepath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static Image loadImageScaled(String filepath, float scalingFactor) {
        Image baseImg = ResourceHelper.loadImage(filepath);
        if (baseImg == null) {
            return null;
        }

        int width = (int) (baseImg.getWidth(null) * scalingFactor);
        int height = (int) (baseImg.getHeight(null) * scalingFactor);

        return baseImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static java.util.List<String> listResources(String path, String suffix) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (!path.endsWith("/")) {
            path += "/";
        }

        List<String> result = new ArrayList<>();
        try {
            URL dirURL = Thread.currentThread().getContextClassLoader().getResource(path);
            if (dirURL == null) {
                System.err.println("ResourceUtils: No resource directory found at " + path);
                return result;
            }

            String protocol = dirURL.getProtocol();

            if (protocol.equals("file")) {
                File directory = new File(dirURL.toURI());
                if (directory.exists()) {
                    result = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                            .filter(file -> !file.isDirectory())
                            .map(File::getName)
                            .filter(name -> suffix == null || name.endsWith(suffix))
                            .collect(Collectors.toList());
                }
            } else if (protocol.equals("jar")) {
                String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
                try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.startsWith(path) && !name.equals(path) && !entry.isDirectory()) {
                            String fileName = name.substring(path.length());
                            if (suffix == null || fileName.endsWith(suffix)) {
                                result.add(fileName);
                            }
                        }
                    }
                }
            } else {
                System.err.println("ResourceUtils: Unsupported protocol " + protocol);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
