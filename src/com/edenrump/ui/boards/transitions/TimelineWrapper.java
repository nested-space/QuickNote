package com.edenrump.ui.boards.transitions;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class TimelineWrapper {

    /**
     * The current timeline
     */
    private Timeline timeline;

    /**
     * Contructor creates a new wrapper
     *
     * @param timeline the timeline
     */
    private TimelineWrapper(Timeline timeline) {
        this.timeline = timeline;
    }

    /**
     * Method to create a new timeline wrapper from defined keyframes
     *
     * @param keyframes the keyframes that constitute the new timeline to be wrapped
     * @return the created wrapper
     */
    public static TimelineWrapper create(KeyFrame... keyframes) {
        return new TimelineWrapper(new Timeline(keyframes));
    }

    public static TimelineWrapper wrap(Timeline t) {
        System.out.println("Wrapping timeline " + t);
        return new TimelineWrapper(t);
    }

    /**
     * Method to set execution of stated timeline after the current one
     *
     * @param t2 the timeline to delay
     * @return the delayed timeline wrapped in a wrapper
     */
    public TimelineWrapper chain(Timeline t2) {
        this.timeline.setOnFinished(e -> {
            t2.play();
            System.out.println("Second animation playing");
        });
        return new TimelineWrapper(t2);
    }

    /**
     * Utility method to play the wrapped timeline
     */
    public void play() {
        System.out.println("Playing animation " + timeline);
        timeline.play();
    }

    /**
     * Method to get the wrapped timeline
     *
     * @return the wrapped timeline
     */
    public Timeline get() {
        return timeline;
    }


}
