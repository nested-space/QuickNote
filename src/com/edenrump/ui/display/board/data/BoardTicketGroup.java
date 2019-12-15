package com.edenrump.ui.display.board.data;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class BoardTicketGroup {

    /**
     * The contents of this board content
     */
    ObservableList<BoardTicket> contents;
    /**
     * The title associated with this boardContent
     */
    private StringProperty title;
    /**
     * The itentifier associated with this boardContent
     */
    private String identifier;
    /**
     * The layout of this board content
     */
    private LayoutType layoutType;

    /**
     * Constructor creates new Board content from identifier and layout type
     *
     * @param identifier the identifier to be associated with the board
     * @param layoutType the layout type to be applied to the board
     */
    public BoardTicketGroup(String identifier, LayoutType layoutType) {
        this.identifier = identifier;
        this.layoutType = layoutType;
    }

    /**
     * Constructor creates new Board content from identifier and layout type
     *
     * @param identifier the identifier to be associated with the board
     * @param layoutType the layout type to be applied to the board
     * @param contents   the contents of the board
     */
    public BoardTicketGroup(String identifier, LayoutType layoutType, List<BoardTicket> contents) {
        this.identifier = identifier;
        this.layoutType = layoutType;
        this.contents = FXCollections.observableArrayList(contents);
    }

    /**
     * Method to get the identifier associated with this boardContent
     *
     * @return the identifier associated with this boardContent
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Method to set the identifier associated with this boardContent
     *
     * @param identifier the identifier associated with this boardContent
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Method to get the title associated with this boardContent
     *
     * @return the title associated with this boardContent
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Method to set the title associated with this boardContent
     *
     * @param title the title associated with this boardContent
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Method to get the title associated with this boardContent as a StringProperty
     *
     * @return the title associated with this boardContent as a StringProperty
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Method to get the layout type associated with the board
     *
     * @return the layout type associated with the board
     */
    public LayoutType getLayoutType() {
        return layoutType;
    }

    /**
     * Method to set the layout type associated with the board
     *
     * @param layoutType the layout type associated with the board
     */
    public void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
    }

    /**
     * Method to get the contents of the board
     *
     * @return the contents of the board
     */
    public List<BoardTicket> getContents() {
        return contents;
    }

    /**
     * Method to set the contents of the board
     *
     * @param contents the contents of the board
     */
    public void setContents(List<BoardTicket> contents) {
        this.contents = FXCollections.observableArrayList(contents);
    }

    /**
     * Method to add content to the board
     *
     * @param content the content to be added
     */
    public void addContent(BoardTicket content) {
        if(contents==null) contents = FXCollections.observableArrayList();
        this.contents.add(content);
    }

    /**
     * Method to remove content from the board
     *
     * @param content the content to be removed
     */
    public void removeContent(BoardTicket content) {
        this.contents.remove(content);
    }
}
