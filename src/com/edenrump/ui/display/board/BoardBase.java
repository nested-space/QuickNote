package com.edenrump.ui.display.board;

import javafx.scene.layout.AnchorPane;

/**
 * Abstract class represents the basic components of a board, but while it contains the skeleton infrastructure
 * to implement animation and layout control, it does not actively manage or implement it.
 * <p>
 * Instances of this board (such as the SwimlaneBoard and the ClusterBoard) may manage these features better.
 */
public abstract class BoardBase {

    /**
     * The pane on which board content will be displayed
     */
    private AnchorPane contentPane;

    /**
     * Constructor creates a new BoardBase, setting the default content
     *
     * @param contentPane the pane on which board content will be displayed
     */
    public BoardBase(AnchorPane contentPane) {
        this.contentPane = contentPane;
    }

    /**
     * Method to get the content pane of the board
     *
     * @return the content pane of the board
     */
    public AnchorPane getContentPane() {
        return contentPane;
    }

    /**
     * Method to set the content pane of the board.
     * Clears the content of the current board
     *
     * @param contentPane the new content pane of the board
     */
    public void setContentPane(AnchorPane contentPane) {
        contentPane.getChildren().clear();
        this.contentPane = contentPane;
    }

    /**
     * Method to get the height of the board
     *
     * @return the height of the board
     */
    public double getHeight() {
        return contentPane.getHeight();
    }

    /**
     * Method to get the width of the board
     *
     * @return the width of the board
     */
    public double getWidth() {
        return contentPane.getWidth();
    }
}
