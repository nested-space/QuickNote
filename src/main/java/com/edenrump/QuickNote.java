package com.edenrump;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static com.edenrump.config.ApplicationDefaults.APP_ICON_URL;
import static com.edenrump.config.ApplicationDefaults.APP_NAME_URL;

public class QuickNote extends Application {

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
        stage.setTitle(APP_NAME_URL);
        stage.getIcons().add(new Image(getClass().getResourceAsStream(APP_ICON_URL)));
        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight(), Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
    }
}
