package com.edenrump.ui.display.contracts;

import javafx.animation.Timeline;

public interface Animatable {

    boolean isTransitionsAnimated();

    void setTransitionsAnimated(boolean animated);

    Timeline animateDisappear(String contentId);

    Timeline animateAppear(String contentId);

    Timeline animateMove(String contentId, double sx, double sy, double ex, double ey);
}
