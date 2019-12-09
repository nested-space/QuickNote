package com.edenrump.config;

import java.io.IOException;
import java.util.Properties;

/**
 * Class that contains application defaults. Implements the singleton design pattern
 */
public class Defaults {

    /**
     * Static copy of application defaults loaded from properties file.
     */
    private static Properties application_defaults;

    /**
     * Constructor ensures properties are only loaded once
     */
    private Defaults() {
        application_defaults = getApplication_defaults();
    }

    /**
     * Method to get the application defaults properties object
     * @return the application defaults properties object
     */
    private static Properties getApplication_defaults() {
        if(application_defaults==null){
            application_defaults = new Properties();
            try {
                application_defaults.load(Defaults.class.getResourceAsStream("/config/application.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return application_defaults;
    }

    /**
     * The name of the application
     */
    public static final String APP_NAME_URL = (String) Defaults.getApplication_defaults().get("app.name");

    /**
     * The relative location of the app icon image
     */
    public static final String APP_ICON_URL = (String) Defaults.getApplication_defaults().get("app.icon");

}
