package io.crative.frontend.utils;

import io.crative.backend.AssetManager;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {
    public ImageButton(String imagePath, double width, double height) {
        ImageView imageView = AssetManager.getImageView(imagePath);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.setGraphic(imageView);
    }

    public ImageButton(String text, String imagePath, double width, double height) {
        ImageView imageView = AssetManager.getImageView(imagePath);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.setText(text);
        this.setGraphic(imageView);
        this.setContentDisplay(ContentDisplay.TOP);
    }

    public ImageButton(String text, String imagePath) {
        ImageView imageView = AssetManager.getImageView(imagePath);
        this.setText(text);
        this.setGraphic(imageView);
    }
}
