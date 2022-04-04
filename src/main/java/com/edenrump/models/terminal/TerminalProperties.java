package com.edenrump.models.terminal;

import com.edenrump.config.ApplicationDefaults;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class TerminalProperties {

    private final DoubleProperty displayWidth = new SimpleDoubleProperty(ApplicationDefaults.SMALL_WIDTH);

    private final DoubleProperty displayHeight = new SimpleDoubleProperty(ApplicationDefaults.SMALL_HEIGHT);

    private final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>(Color.BLACK);

    public DoubleProperty displayWidthProperty() {
        return displayWidth;
    }

    public void setDisplayWidth(double displayWidth) {
        this.displayWidth.set(displayWidth);
    }

    public DoubleProperty displayHeightProperty() {
        return displayHeight;
    }

    public void setDisplayHeight(double displayHeight) {
        this.displayHeight.set(displayHeight);
    }

    public ObjectProperty<Color> backgroundColorProperty() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor.set(backgroundColor);
    }
}
