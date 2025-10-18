package io.crative.frontend.utils;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {
    public ImageButton(String imagePath, double width, double height) {
        Image image = new javafx.scene.image.Image(imagePath);
        ImageView imageView = new javafx.scene.image.ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.setGraphic(imageView);
    }

    public ImageButton(String text, String imagePath, double width, double height) {
        Image image = new javafx.scene.image.Image(imagePath);
        ImageView imageView = new javafx.scene.image.ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.setText(text);
        this.setGraphic(imageView);
        this.setContentDisplay(ContentDisplay.TOP);
    }

    public ImageButton(String text, String imagePath) {
        Image image = new javafx.scene.image.Image(imagePath);
        ImageView imageView = new javafx.scene.image.ImageView(image);
        this.setText(text);
        this.setGraphic(imageView);
    }
}
