package com.edenrump.config;

import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Properties;

/**
 * Class that contains application defaults. Implements the singleton design pattern
 */
public class Defaults {

    /**
     * The default string displayed by the terminal in display mode
     */
    public final static String DISPLAY_INDICATOR = "^";
    /**
     * The default dark background colour
     */
    public static final Color DARK_BACKGROUND = new Color(0, 0, 0, 0.7);
    /**
     * The default light background colour
     */
    public static final Color LIGHT_BACKGROUND = new Color(1, 1, 1, 0.7);
    /**
     * The a completely transparent colour
     */
    public static final Color NO_BACKGROUND = new Color(0, 0, 0, 0);
    /**
     * The default width of the terminal in "wide" mode
     */
    public static double WIDE_WIDTH = 600;
    /**
     * The default height of the terminal in "wide" mode
     */
    public static double WIDE_HEIGHT = 150;
    /**
     * The default width of the terminal in "small" mode
     */
    public static double SMALL_WIDTH = 250;
    /**
     * The default height of the terminal in "small" mode
     */
    public static double SMALL_HEIGHT = 350;
    /**
     * The default width of the terminal in "large" mode
     */
    public static double LARGE_WIDTH = 800;
    /**
     * The default height of the terminal in "large" mode
     */
    public static double LARGE_HEIGHT = 500;
    /**
     * Static copy of application defaults loaded from properties file.
     */
    private static Properties application_defaults;
    /**
     * The name of the application
     */
    public static final String APP_NAME_URL = (String) Defaults.getApplication_defaults().get("app.name");
    /**
     * The relative location of the app icon image
     */
    public static final String APP_ICON_URL = (String) Defaults.getApplication_defaults().get("app.icon");

    /**
     * Constructor ensures properties are only loaded once
     */
    private Defaults() {
        application_defaults = getApplication_defaults();
    }

    /**
     * Method to get the application defaults properties object
     *
     * @return the application defaults properties object
     */
    private static Properties getApplication_defaults() {
        if (application_defaults == null) {
            application_defaults = new Properties();
            try {
                application_defaults.load(Defaults.class.getResourceAsStream("/config/application.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return application_defaults;
    }

}
