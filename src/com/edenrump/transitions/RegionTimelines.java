package com.edenrump.transitions;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class RegionTimelines {

    private static Timeline sizeTimeline(Region node, double w1, double h1, double w2, double h2) {
        Duration cycleDuration = Duration.millis(500);
        double w = node.getWidth();
        double h = node.getHeight();
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.prefWidthProperty(), w, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.prefHeightProperty(), h, Interpolator.EASE_BOTH)),
                new KeyFrame(cycleDuration,
                        new KeyValue(node.prefWidthProperty(), w2, Interpolator.EASE_BOTH)),
                new KeyFrame(cycleDuration,
                        new KeyValue(node.prefHeightProperty(), h2, Interpolator.EASE_BOTH))
        );
    }

    public static Timeline getAndPlaySizeTransition(Region node, double w1, double h1, double w2, double h2, EventHandler<ActionEvent> after) {
        Timeline t = sizeTimeline(node, w1, h1, w2, h2);
        t.setOnFinished(after);
        t.playFromStart();
        return t;
    }

}
