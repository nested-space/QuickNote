package com.edenrump.ui.boards.components;

import com.edenrump.ui.boards.contracts.CanDisplayTicket;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class representing a planning ticket displaying a highlight, a title and some textual content.
 */
public class BoardTicketNode extends AnchorPane implements CanDisplayTicket {

    /**
     * The id of the ticket
     */
    private String id;

    /**
     * The title of the ticket
     */
    private String title;

    /**
     * The default label height of the title for height calculations
     */
    private static final double TITLE_LABEL_HEIGHT = 30d;

    /**
     * The default label height of other content for height calculations
     */
    private static final double LABEL_HEIGHT = 5d;

    /**
     * The default left anchor value
     */
    private static final double LEFT_ANCHOR = 5d;

    /**
     * The default top anchor value
     */
    private static final double TOP_ANCHOR = 5d;

    /**
     * The default right anchor value
     */
    private static final double RIGHT_ANCHOR = 5d;

    /**
     * The variable used to calculate vertical display layouts
     */
    private double vCaret = TOP_ANCHOR;

    /**
     * A map of content titles and descriptions
     */
    private Map<String, String> contentMap = new LinkedHashMap<>();

    /**
     * A region which can be coloured to highlight the ticket
     */
    private Region highlightRectangle;

    /**
     * Constructor creates ticket from id and title
     * @param id the id of the ticket
     * @param title the title of the ticket
     */
    public BoardTicketNode(String id, String title) {
        this.id = id;
        this.title = title;
        reDisplayAll();
    }

    @Override
    public String getContentId() {
        return id;
    }

    @Override
    public void addTitleValuePair(String title, String value) {
        contentMap.put(title, value);
        reDisplayAll();
    }

    @Override
    public void reDisplayAll() {
        vCaret = TOP_ANCHOR;
        getChildren().clear();

        createBackground();
        addHighlight();
        addTitle();
        addContent();
    }

    /**
     * Method to create the background of the ticket
     */
    private void createBackground() {
        this.setBackground(new Background(new BackgroundFill(
                Color.LIGHTBLUE,
                new CornerRadii(5d, 0d, 0d, 5d, false),
                Insets.EMPTY)));
        setPrefHeight(100);
        setPrefWidth(100);
        setPadding(new Insets(2));
    }

    /**
     * Method to add the highlight region to the ticket
     */
    private void addHighlight() {
        highlightRectangle = new AnchorPane();
        highlightRectangle.setPrefHeight(5d);
        highlightRectangle.setBackground(new Background(new BackgroundFill(
                Color.YELLOW,
                new CornerRadii(5d, 5d, 5d, 5d, false),
                Insets.EMPTY)));
        AnchorPane.setTopAnchor(highlightRectangle, TOP_ANCHOR);
        AnchorPane.setLeftAnchor(highlightRectangle, LEFT_ANCHOR);
        AnchorPane.setRightAnchor(highlightRectangle, RIGHT_ANCHOR);

        vCaret = AnchorPane.getTopAnchor(highlightRectangle) + highlightRectangle.getPrefHeight();

        this.getChildren().add(highlightRectangle);
    }


    /**
     * Method to add the title, if not null, to the ticket
     */
    private void addTitle() {
        if (title == null) return;
        Label titleLabel = new Label(title);
        titleLabel.setMaxWidth(195);
        titleLabel.setFont(new Font(15));
        AnchorPane.setLeftAnchor(titleLabel, LEFT_ANCHOR);
        AnchorPane.setTopAnchor(titleLabel, vCaret + TOP_ANCHOR);
        getChildren().add(titleLabel);
        vCaret += TOP_ANCHOR + TITLE_LABEL_HEIGHT;
    }


    /**
     * Method to add content to the ticket in a gridpane
     */
    private void addContent() {
        GridPane gridPane = new GridPane();
        double gridPad = 3d;
        gridPane.setVgap(gridPad);
        gridPane.setHgap(gridPad);
        int gridCaret = 0;
        for(String subTitle : contentMap.keySet()){
            System.out.println(subTitle);
            Label st = new Label(subTitle + ":   ");
            Label content = new Label(contentMap.get(subTitle));
            GridPane.setColumnIndex(content, 1);
            GridPane.setRowIndex(st, gridCaret);
            GridPane.setRowIndex(content, gridCaret);
            gridPane.getChildren().addAll(st, content);
            gridCaret++;
        }
        AnchorPane.setTopAnchor(gridPane, vCaret + TOP_ANCHOR);
        AnchorPane.setLeftAnchor(gridPane, LEFT_ANCHOR);
        this.getChildren().add(gridPane);
        vCaret += TOP_ANCHOR + gridCaret * (LABEL_HEIGHT * gridPad);
    }


}
