package com.edenrump.test;

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FrostyTech extends Application {

    private static final double BLUR_AMOUNT = 2;

    private static final Effect frostEffect =
            new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

    private static final ImageView background = new ImageView();
    private static final StackPane layout = new StackPane();

    @Override public void start(Stage stage) {
        layout.getChildren().setAll(background, createContent());
        layout.setStyle("-fx-background-color: null");

        Scene scene = new Scene(layout,800 , 800,Color.TRANSPARENT);

        Platform.setImplicitExit(false);

//        makeSmoke(stage);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        background.setImage(copyBackground(stage));
        background.setEffect(frostEffect);
    }

    // copy a background node to be frozen over.
    private Image copyBackground(Stage stage) {
        final int X = (int) stage.getX();
        final int Y = (int) stage.getY();
        final int W = (int) stage.getWidth();
        final int H = (int) stage.getHeight();

        try {
            java.awt.Robot robot = new java.awt.Robot();
            java.awt.image.BufferedImage image = robot.createScreenCapture(new java.awt.Rectangle(X, Y, W, H));

            return SwingFXUtils.toFXImage(image, null);
        } catch (java.awt.AWTException e) {
            System.out.println("The robot of doom strikes!");
            e.printStackTrace();

            return null;
        }
    }

    // create some content to be displayed on top of the frozen glass panel.
    private Label createContent() {
        Label label = new Label("Create a new question for drop shadow effects.\n\nDrag to move");
        label.setPadding(new Insets(10));
        label.setStyle("-fx-font-size: 15px; -fx-text-fill: green;");
        label.setMaxWidth(250);
        label.setWrapText(true);
        return label;
    }


    private javafx.scene.shape.Rectangle makeSmoke(Stage stage) {
        return new javafx.scene.shape.Rectangle(
                stage.getWidth(),
                stage.getHeight(),
                Color.WHITESMOKE.deriveColor(
                        0, 1, 1, 0.08
                )
        );
    }


    public static void main(String[] args) {
        launch(args);
    }
}