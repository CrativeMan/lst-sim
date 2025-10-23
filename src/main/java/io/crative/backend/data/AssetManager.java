package io.crative.backend.data;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static Map<String, ImageView> imageViewMap = new HashMap<>();

    private AssetManager() {
    }

    public static ImageView getImageView(String key) {
        if (!imageViewMap.containsKey(key)) {
            Image image = new Image(key);
            ImageView imageView = new ImageView(image);
            imageViewMap.put(key, imageView);
            System.out.println("Loaded image: " + key);
        }
        return new ImageView(imageViewMap.get(key).getImage());
    }
}
