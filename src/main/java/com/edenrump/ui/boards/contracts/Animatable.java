package com.edenrump.ui.boards.contracts;

import javafx.animation.Timeline;
import javafx.scene.layout.Region;

/**
 * Interface representing necessary tasks to perform (optional) animations
 */
public interface Animatable {

    /**
     * Method to get whether transitions should be animated
     * @return whether transitions should be animated
     */
    boolean isTransitionsAnimated();

    /**
     * Method to set whether transitions should be animated
     * @param animated whether transitions should be animated
     */
    void setTransitionsAnimated(boolean animated);

    /**
     * Method to get a Timeline that animates disappearence of content defined by the specified id
     * @param content the id of the content to disappear
     * @return the Timeline that animates disappearence of content defined by the specified id
     */
    Timeline animateDisappear(Region content);

    /**
     * Method to get a Timeline that animates appearence of content defined by the specified id
     * @param content the id of the content to appear
     * @return the Timeline that animates appearence of content defined by the specified id
     */
    Timeline animateAppear(Region content);

    /**
     * Method to get a Timeline that animates motion of content defined by the specified id
     * @param content the id of the content to move
     * @return the Timeline that animates motion of content defined by the specified id
     */
    Timeline animateMove(Region content, double sx, double sy, double ex, double ey);
}
