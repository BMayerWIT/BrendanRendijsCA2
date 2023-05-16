package com.example.brendanrendijsca2;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.Writer;
import java.util.List;


public class imageProcessor {

    public static WritableImage setGrey(Image image) {
        Image originalImage = image;

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

        return grayImage;
    }





}
