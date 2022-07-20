package com.edenrump;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public class QuickNote extends Application {

    private final static Color APPLICATION_PRIMARY_COLOR = Color.GREEN;
    private final static String APPLICATION_ICON_LOCATION = "img/tab.png";

    /**
     * The screen bounds of the primary screen
     */
    private final Rectangle2D screen = Screen.getPrimary().getBounds();

    /**
     * The entry point for this application
     *
     * @param args commandline arguments, not currently handled
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * The initial method for JavaFX program launch
     *
     * @param stage the stage to be loaded
     * @throws IOException an exception thrown if the fxmlLoader cannot load the parent element.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // load the terminal font.
        Font.loadFont(QuickNote.class.getClassLoader().getResourceAsStream("css/RedHatText-Regular.ttf"), 10);

        //create window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/QuickNoteMainWindow.fxml"));
        Parent root = fxmlLoader.load();

        setUpStage(stage, root);
        stage.show();
    }

    /**
     * Utility method containing all modifications to the parent stage prior to launch
     *
     * @param stage the parent stage
     * @param root  the root element of the parent stage
     */
    private void setUpStage(Stage stage, Parent root) {
        stage.setTitle("Quick Note");
        stage.getIcons().add(getApplicationImage());
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight(), Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setX(0);
        stage.setY(0);
    }

    private Image getApplicationImage() {
        InputStream is = getClass().getResourceAsStream(APPLICATION_ICON_LOCATION);
        if (is != null) {
            return new Image(is);
        } else {
            //If no image found, return square with application primary color as backup
            WritableImage image = new WritableImage(1, 1);
            image.getPixelWriter().setColor(0, 0, APPLICATION_PRIMARY_COLOR);
            return image;
        }
    }
}
