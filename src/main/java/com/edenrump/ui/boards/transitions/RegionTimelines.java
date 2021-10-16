package com.edenrump.ui.boards.transitions;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Set;

public class RegionTimelines {

    public static final Duration MOVEMENT_DURATION = Duration.millis(200);
    public static final Duration SHADING_DURATION = Duration.millis(200);
    public static final Duration DELAY_DURATION = Duration.millis(100);

    public static Timeline opacityTimeline(Region region, double o1, double o2) {
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(region.opacityProperty(), o1, Interpolator.LINEAR)),
                new KeyFrame(SHADING_DURATION,
                        new KeyValue(region.opacityProperty(), o2, Interpolator.LINEAR)));
    }

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

    public static Timeline translationTimeline(Region region, double x1, double y1, double x2, double y2) {
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(region.translateXProperty(), x1, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(0),
                        new KeyValue(region.translateYProperty(), y1, Interpolator.EASE_BOTH)),
                new KeyFrame(MOVEMENT_DURATION,
                        new KeyValue(region.translateXProperty(), x2, Interpolator.EASE_BOTH)),
                new KeyFrame(MOVEMENT_DURATION,
                        new KeyValue(region.translateYProperty(), y2, Interpolator.EASE_BOTH))
        );

    }

    public static Timeline layoutTimeline(Region region, double x1, double y1, double x2, double y2) {
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(region.layoutXProperty(), x1, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(0),
                        new KeyValue(region.layoutYProperty(), y1, Interpolator.EASE_BOTH)),
                new KeyFrame(MOVEMENT_DURATION,
                        new KeyValue(region.layoutXProperty(), x2, Interpolator.EASE_BOTH)),
                new KeyFrame(MOVEMENT_DURATION,
                        new KeyValue(region.layoutYProperty(), y2, Interpolator.EASE_BOTH))
        );

    }

    public static Timeline createColourChange(Scene scene, Color cStart, Color cEnd) {
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(scene.fillProperty(), cStart, Interpolator.LINEAR)),
                new KeyFrame(SHADING_DURATION,
                        new KeyValue(scene.fillProperty(), cEnd, Interpolator.LINEAR)));
    }

    public static Timeline createColourChange(Region region, Color cStart, Color cEnd) {
        BackgroundFill bgf = region.getBackground().getFills().get(0);
        Insets i = bgf.getInsets();
        CornerRadii c = bgf.getRadii();
        DoubleProperty t = new SimpleDoubleProperty(0);
        t.addListener((obs, o, n) -> region.setBackground(new Background(new BackgroundFill(
                new Color(cStart.getRed() * (1 - t.get()) + cEnd.getRed() * t.get(),
                        cStart.getBlue() * (1 - t.get()) + cEnd.getBlue() * t.get(),
                        cStart.getGreen() * (1 - t.get()) + cEnd.getGreen() * t.get(),
                        cStart.getOpacity() * (1 - t.get()) + cEnd.getOpacity() * t.get()), c, i))));
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(t, 0, Interpolator.LINEAR)),
                new KeyFrame(SHADING_DURATION,
                        new KeyValue(t, 1, Interpolator.LINEAR)));
    }

    private static Timeline createInstantColorChange(Scene scene, Color cStart, Color cEnd) {
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(scene.fillProperty(), cStart, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(1),
                        new KeyValue(scene.fillProperty(), cEnd, Interpolator.LINEAR)),
                new KeyFrame(SHADING_DURATION,
                        new KeyValue(scene.fillProperty(), cEnd, Interpolator.LINEAR))
        );
    }

    private static Timeline createInstantColorChange(Region region, Color cStart, Color cEnd) {
        BackgroundFill bgf = region.getBackground().getFills().get(0);
        Insets i = bgf.getInsets();
        CornerRadii c = bgf.getRadii();
        DoubleProperty t = new SimpleDoubleProperty(0);
        t.addListener((obs, o, n) -> region.setBackground(new Background(new BackgroundFill(
                new Color(cStart.getRed() * (1 - t.get()) + cEnd.getRed() * t.get(),
                        cStart.getBlue() * (1 - t.get()) + cEnd.getBlue() * t.get(),
                        cStart.getGreen() * (1 - t.get()) + cEnd.getGreen() * t.get(),
                        cStart.getOpacity() * (1 - t.get()) + cEnd.getOpacity() * t.get()), c, i))));
        return new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(t, 0, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(1),
                        new KeyValue(t, 1, Interpolator.LINEAR)),
                new KeyFrame(SHADING_DURATION,
                        new KeyValue(t, 1, Interpolator.LINEAR))
        );
    }

    public static Timeline createFlash(Region region, Color cStart, Color cHighlight) {
        Timeline t1 = RegionTimelines.createFlash(region, cStart, cHighlight);
        Timeline t2 = RegionTimelines.createFlash(region, cHighlight, cStart);
        Timeline t3 = RegionTimelines.createFlash(region, cStart, cHighlight);
        Timeline t4 = RegionTimelines.createFlash(region, cHighlight, cStart);
        Timeline t5 = RegionTimelines.createFlash(region, cStart, cHighlight);
        Timeline t6 = RegionTimelines.createFlash(region, cHighlight, cStart);
        Timeline t7 = RegionTimelines.createFlash(region, cStart, cHighlight);
        Timeline t8 = RegionTimelines.createFlash(region, cHighlight, cStart);
        TimelineWrapper.wrap(t1).chain(t2).chain(t3).chain(t4).chain(t5).chain(t6).chain(t7).chain(t8);
        return t1;

    }

    public static void createCascade(Timeline... timelines) {
        for (int i = 0; i < timelines.length; i++) {
            timelines[i].setDelay(new Duration(i * DELAY_DURATION.toMillis()));
        }
    }

    public static Timeline combineTimelines(Timeline... timelines) {
        Timeline combined = new Timeline();
        for (int i = 0; i < timelines.length; i++) {
            combined.getKeyFrames().addAll(timelines[i].getKeyFrames());
        }
        return combined;
    }

    public static Timeline delay(Timeline timeline, Duration delay) {
        int frameCount = 0;
        KeyFrame[] keyFrames = new KeyFrame[timeline.getKeyFrames().size()];
        for (KeyFrame k : timeline.getKeyFrames()) {
            Set<KeyValue> values = k.getValues();
            KeyValue[] keyValues = new KeyValue[values.size()];
            int valueCount = 0;
            for (KeyValue value : values) keyValues[valueCount++] = value;
            keyFrames[frameCount++] = new KeyFrame(Duration.millis(k.getTime().toMillis() + delay.toMillis()), keyValues);
        }
        return new Timeline(keyFrames);
    }
}
