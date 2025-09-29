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

    public static Image loadImageScaled(String filepath, float scalingFactor) {
        Image baseImg = FileLoader.loadImage(filepath);
        if (baseImg == null) {
            return null;
        }

        int width = (int) (baseImg.getWidth(null) * scalingFactor);
        int height = (int) (baseImg.getHeight(null) * scalingFactor);

        return baseImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

}
