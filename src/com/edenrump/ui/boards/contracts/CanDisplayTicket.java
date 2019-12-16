package com.edenrump.ui.boards.contracts;

/**
 * Interface representing the methods required to display a ticket on a board
 */
public interface CanDisplayTicket {

    String getContentId();

    void reDisplayAll();

    void addTitleValuePair(String title, String value);

}
