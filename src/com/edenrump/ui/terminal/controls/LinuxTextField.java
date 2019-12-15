package com.edenrump.ui.terminal.controls;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Class representing a text field that has been styled to look like a linux terminal and with controls modified to
 * recreate a linux terminal experience
 * <p>
 * Style modifications are:
 * The terminal is black
 * The cursor is wide (6px) and white
 * The text is white
 * TODO: the text font is similar to that of a Fedora terminal
 * <p>
 * Control modifications are:
 * Selection has been removed. User cannot select text.
 */
public class LinuxTextField extends TextField {

    private TextFieldSkin mSkin = new TextFieldCaretControlSkin(this, new Color(1, 1, 1, 0.5));
    /**
     * AnimationTimer periodically preventing cursor from blinking. This may have the effect of removing it..
     */
    final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long time) {
            mSkin.setCaretAnimating(false);
        }
    };

    /**
     * Constructor. Creates a text field that is styled to look like a linux terminal and controls are modified to
     * recreate a linux terminal experience
     */
    public LinuxTextField() {
        this.setSkin(mSkin);
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (!this.getSelectedText().isEmpty()) {
                this.deselect();
            }
        });
        this.addEventFilter(KeyEvent.KEY_TYPED, inputevent -> {
            if (!this.getSelectedText().isEmpty()) {
                this.deselect();
            }
        });
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setStyle(this.getStyle() + "-fx-text-inner-color: white;-fx-highlight-fill: null;-fx-highlight-text-fill: null; -fx-padding: 0;");
    }

    /**
     * Method to start the AnimationTimer preventing blinking remove behaviour class control over cursor blinking
     * This may have the effect of making this cursor disappear.
     */
    public void stopCursorBlink() {
        timer.start();
    }

    /**
     * Method to stop the AnimationTimer preventing blinking and return cursor blinking to native control
     */
    public void resetCursorBlink() {
        timer.stop();
        mSkin.setCaretAnimating(true);
    }

    /**
     * Class representing a TextFieldSkin that has been restyled to look like a linux terminal
     */
    private static class TextFieldCaretControlSkin extends TextFieldSkin {
        public TextFieldCaretControlSkin(TextField textField, Color caretColor) {
            super(textField);
            caretPath.strokeProperty().unbind();
            caretPath.fillProperty().unbind();
            caretPath.setStrokeWidth(6);
            caretPath.setStroke(caretColor);
            caretPath.setFill(caretColor);
            caretPath.setTranslateX(4);
        }
    }


}
