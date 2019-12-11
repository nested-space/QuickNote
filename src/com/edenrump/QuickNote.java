package com.edenrump;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.edenrump.config.Defaults.APP_ICON_URL;
import static com.edenrump.config.Defaults.APP_NAME_URL;

public class QuickNote extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // load the tron font.
        System.out.println(Font.loadFont(
                QuickNote.class.getClassLoader().getResourceAsStream("css/RedHatText-Regular.ttf"),
                10
        ));
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        primaryStage.setTitle(APP_NAME_URL);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(APP_ICON_URL)));
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
