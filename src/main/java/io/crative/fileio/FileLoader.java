package io.crative.fileio;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FileLoader {
    private FileLoader() {

    }

    public static Image loadImage(String filepath) {
        Image img;
        try {
            img = ImageIO.read(Objects.requireNonNull(FileLoader.class.getResource(filepath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
}
