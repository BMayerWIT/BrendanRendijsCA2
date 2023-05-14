package com.example.brendanrendijsca2;

import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.Writer;


public class imageProcessor {

    public void setGrey() {
        Image originalImage = ImageView.getImage();

        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        WritableImage grayImage = new WritableImage(width, height);
        PixelWriter pixelWriter = grayImage.getPixelWriter();
        PixelReader pixelReader = originalImage.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                double gray = 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue();
                pixelWriter.setColor(x, y, new Color(gray, gray, gray, color.getOpacity()));
            }
        }

        ImageView.setImage(grayImage);
    }





}
