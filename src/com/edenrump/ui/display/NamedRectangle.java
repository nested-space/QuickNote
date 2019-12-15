package com.edenrump.ui.display;

import com.edenrump.ui.Parameterisable;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamedRectangle extends StackPane implements Parameterisable {

    private final double DEFAULT_HEIGHT = 50;
    private final double DEFAULT_WIDTH = 100;
    private final Color DEFAULT_COLOR = Color.TRANSPARENT;
    /* ****************************************************************************************************************
     *   Rectangle
     * ***************************************************************************************************************/
    private Rectangle mBackgroundRectangle;
    private Label mLabel = new Label();

    /* ****************************************************************************************************************
     *   Label
     * ***************************************************************************************************************/
    //Parameter map
    private Map<String, String> parameterMap = new HashMap<>();

    /* ****************************************************************************************************************
     *   Nodes
     * ***************************************************************************************************************/
    private Color color = DEFAULT_COLOR;

    public NamedRectangle() {
        setLayout();
    }

    NamedRectangle(String name) {
        setLayout();
        setText(name);
    }

    private double getRectWidth() {
        return mBackgroundRectangle.getWidth();
    }

    /* ****************************************************************************************************************
     *   Parameters
     * ***************************************************************************************************************/

    private void setRectWidth(double width) {
        mBackgroundRectangle.setWidth(width);
    }

    private double getRectHeight() {
        return mBackgroundRectangle.getHeight();
    }

    private void setRectHeight(double height) {
        mBackgroundRectangle.setHeight(height);
    }

    /* ****************************************************************************************************************
     *   Style
     * ***************************************************************************************************************/

    public String getText() {
        return mLabel.getText();
    }

    public void setText(String name) {
        mLabel.setText(name);
    }

    public void setTextAlignment(Pos value) {
        mLabel.setAlignment(value);
    }

    public void storeParameter(String key, String value) {
        parameterMap.put(key, value);
    }

    public List<String> getParameterKeys() {
        return new ArrayList<>(parameterMap.keySet());
    }

    /* ****************************************************************************************************************
     *   Attached Connectors
     * ***************************************************************************************************************/

    public String getParameterValue(String key) {
        return ((parameterMap.getOrDefault(key, "")));
    }

    public void setMargin(Insets insets) {
        setPadding(insets);
    }

    /* ****************************************************************************************************************
     *   Constructors
     * ***************************************************************************************************************/

    private void setLayout() {
        //set up background
        mBackgroundRectangle = new Rectangle(DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.TRANSPARENT);
        mBackgroundRectangle.setArcHeight(DEFAULT_HEIGHT / 2);
        mBackgroundRectangle.setArcWidth(DEFAULT_HEIGHT / 2);

        //set up label
        mLabel.setPadding(new Insets(0, 5, 0, 10));
        mLabel.setWrapText(true);
        mLabel.setPrefWidth(90);
        mLabel.setTextAlignment(TextAlignment.CENTER);

        //set alignment
        mLabel.heightProperty().addListener((observable, oldValue, newValue) -> {
            mBackgroundRectangle.heightProperty().setValue(newValue);
            setMinHeight(newValue.doubleValue() + 2);
            setPrefHeight(newValue.doubleValue());

        });

        //add children
        getChildren().addAll(mBackgroundRectangle, mLabel);

    }

    public void setColor(Color colour) {
        BackgroundFill current = getBackground().getFills().get(0);
        this.getBackground().getFills().set(0, new BackgroundFill(colour, current.getRadii(), current.getInsets()));
    }
}
