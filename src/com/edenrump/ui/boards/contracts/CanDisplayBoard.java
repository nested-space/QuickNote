package com.edenrump.ui.boards.contracts;

import com.edenrump.ui.boards.data.BoardTicket;
import com.edenrump.ui.boards.data.BoardTicketGroup;
import javafx.scene.layout.Region;

public interface CanDisplayBoard {

    /**
     * Method to prompt the board to reload its content
     */
    void clearContent();

    /**
     * Method to remove an entire group of objects from the board
     *
     * @param content the group to be removed
     */
    void removeContent(BoardTicketGroup content);

    /**
     * Method to remove a single piece of content from the board
     *
     * @param group   the group from which the ticket should be removed
     * @param content the content to be removed
     * @param delay   (optional) animation delay to removing the content in milliseconds
     */
    void removeContent(BoardTicketGroup group, BoardTicket content, double delay);

    /**
     * Method to add content to the board
     *
     * @param content the content to be added
     */
    void addContent(BoardTicketGroup content);

    /**
     * Method to add a single piece of content to the board to a specific group
     *
     * @param group   the ticket group to which the content should be added
     * @param content the content
     * @param delay   (optional) animation delay to adding the content in milliseconds
     */
    void addContent(BoardTicketGroup group, BoardTicket content, double delay);

    /**
     * @param content the content to be converted into display object
     * @return the Region created from the content to be displayed
     */
    Region createDisplayableContent(BoardTicket content);
}
