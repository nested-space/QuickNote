package com.edenrump.transitions;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class RegionTimelines {

    private static final Duration MOVEMENT_DURATION = Duration.millis(500);
    private static final Duration SHADING_DURATION = Duration.millis(150);

    public static Timeline sizeTimeline(Region region, double w1, double h1, double w2, double h2) {
        double w = region.getWidth();
        double h = region.getHeight();
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(region.prefWidthProperty(), w, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(0),
                        new KeyValue(region.prefHeightProperty(), h, Interpolator.EASE_BOTH)),
                new KeyFrame(MOVEMENT_DURATION,
                        new KeyValue(region.prefWidthProperty(), w2, Interpolator.EASE_BOTH)),
                new KeyFrame(MOVEMENT_DURATION,
                        new KeyValue(region.prefHeightProperty(), h2, Interpolator.EASE_BOTH))
        );
    }

    public static Timeline sizeTimelineWithEffect(Region region, double w2, double h2, EventHandler<ActionEvent> after) {
        double w1 = region.getWidth();
        double h1 = region.getHeight();
        return sizeTimelineWithEffect(region, w1, h1, w2, h2, after);
    }

    public static Timeline sizeTimelineWithEffect(Region region, double w1, double h1, double w2, double h2, EventHandler<ActionEvent> after) {
        Timeline t = sizeTimeline(region, w1, h1, w2, h2);
        t.setOnFinished(after);
        return t;
    }

    public static Timeline colourTimeline(Scene scene, Color cEnd) {
        Color cStart = (Color) scene.getFill();
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(scene.fillProperty(), cStart, Interpolator.LINEAR)),
                new KeyFrame(SHADING_DURATION,
                        new KeyValue(scene.fillProperty(), cEnd, Interpolator.EASE_IN)));
    }
}
