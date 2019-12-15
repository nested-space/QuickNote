package com.edenrump.ui.display.board.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

/**
 * Class representing the data that constitutes a ticket on a planning board
 */
public class BoardTicket {

    /**
     * The ID of the content (and therefore the ticket)
     */
    private final String id;
    /**
     * The title of the ticket
     */
    private StringProperty title;
    /**
     * The content of the ticket, as the title of the content and the content itself
     */
    private Map<String, String> contentPairs;

    /**
     * Constructor creates ticket data from title and ID
     *
     * @param title the title of the ticket
     * @param id    the id of the ticket
     */
    public BoardTicket(StringProperty title, String id) {
        this.title = title;
        this.id = id;
    }

    /**
     * Constructor creates ticket data from title, ID and all content
     *
     * @param title        the title of the ticket
     * @param id           the id of the ticket
     * @param contentPairs the content of the ticket as title and values
     */
    public BoardTicket(String title, Map<String, String> contentPairs, String id) {
        this.title = new SimpleStringProperty(title);
        this.contentPairs = contentPairs;
        this.id = id;
    }

    /**
     * Method to get the titleproperty
     *
     * @return the title of the ticket as a string property
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Method to get the title of the ticket as a string
     *
     * @return the title of the ticket as a string
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Method to set the title of the ticket
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Method to set the content of a tiket
     *
     * @return the content of a ticket
     */
    public Map<String, String> getContentPairs() {
        return contentPairs;
    }

    /**
     * Method to set the content of a tiket
     *
     * @param contentPairs the content of a ticket
     */
    public void setContentPairs(Map<String, String> contentPairs) {
        this.contentPairs = contentPairs;
    }

    /**
     * Method to add content to the ticket
     *
     * @param key   the title of the content
     * @param value the value of the content
     */
    public void addContentPair(String key, String value) {
        this.contentPairs.put(key, value);
    }

    /**
     * Method to get the id of the ticket
     *
     * @return the id of the ticket
     */
    public String getId() {
        return id;
    }
}
