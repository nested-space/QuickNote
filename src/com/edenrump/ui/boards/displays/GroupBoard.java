package com.edenrump.ui.boards.displays;

import com.edenrump.transitions.RegionTimelines;
import com.edenrump.ui.boards.animation.Defaults;
import com.edenrump.ui.boards.data.BoardTicket;
import com.edenrump.ui.boards.data.BoardTicketGroup;
import com.edenrump.ui.boards.data.LayoutType;
import com.edenrump.ui.boards.components.HolderRectangle;
import com.edenrump.ui.boards.contracts.Animatable;
import com.edenrump.ui.boards.contracts.CanDisplayBoard;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a board designed to display content separated into groups of named buckets
 */
public class GroupBoard extends BoardBase implements Animatable, CanDisplayBoard {

    /**
     * Whether transitions in board such as adding, moving or removing an item should be animated
     */
    private boolean transitionsAnimated;
    /**
     * A map of board identifier and board content. The string key should obviously be
     * unique (i.e the ID of the content) as maps do not support identical nomenclatures
     * and the identifier isn't wrapped.
     */
    private Map<String, BoardTicketGroup> boardsMap = new HashMap<>();

    /**
     * A map of ticket identifier to the node used to display the content
     */
    private Map<String, Region> contentMap = new HashMap<>();

    /**
     * Constructor creates a new ClusterBoard, setting the default content
     *
     * @param content the pane on which board content will be displayed
     */
    public GroupBoard(AnchorPane content) {
        super(content);
    }

    /**
     * Constructor creates new groupBoard, defining content and the ids of the groups to display
     *
     * @param content  the pane to display the content
     * @param groupIds the ids of the groups to display
     */
    public GroupBoard(AnchorPane content, String... groupIds) {
        super(content);
        for (String group : groupIds) {
            this.getBoardsMap().put(group, new BoardTicketGroup(group, LayoutType.CLUSTER));
        }
    }

    /**
     * Method to get the contents of the board
     *
     * @return a map of board identifier and board content
     */
    public Map<String, BoardTicketGroup> getBoardsMap() {
        return boardsMap;
    }

    /**
     * Method to get whether adding and removing content should be animated
     *
     * @return whether adding and removing content should be animated
     */
    @Override
    public boolean isTransitionsAnimated() {
        return transitionsAnimated;
    }

    /**
     * Method to set whether adding, removing and moving content should be animated.
     *
     * @param transitionsAnimated whether transitions should be animated
     */
    @Override
    public void setTransitionsAnimated(boolean transitionsAnimated) {
        this.transitionsAnimated = transitionsAnimated;
    }

    /**
     * Method to create a Timeline which animates the disappearence of a ticket of the specified id
     *
     * @param contentId the id of the content to be animated
     * @return the timeline which will animate the disappearence
     */
    @Override
    public Timeline animateDisappear(String contentId) {
        Region content = contentMap.get(contentId);
        return RegionTimelines.combineTimelines(
                RegionTimelines.opacityTimeline(content, content.getOpacity(), 0),
                RegionTimelines.translationTimeline(content, content.getTranslateX(), content.getTranslateY(),
                        content.getTranslateX(), content.getTranslateY() - 10));
    }

    /**
     * Method to create a Timeline which animates the appearence of a ticket of the specified id
     *
     * @param contentId the id of the content to be animated
     * @return the timeline which will animate the appearence
     */
    @Override
    public Timeline animateAppear(String contentId) {
        Region content = contentMap.get(contentId);
        content.setTranslateX(-20);
        content.setOpacity(0);

        return RegionTimelines.combineTimelines(
                RegionTimelines.opacityTimeline(content, content.getOpacity(), 1),
                RegionTimelines.translationTimeline(content,
                        content.getTranslateX(), content.getTranslateY(),
                        0, 0));
    }

    /**
     * Method to create a Timeline which animates the movement of a ticket of the specified id
     *
     * @param contentId the id of the content to be animated
     * @param sx        the initial layout X of the content
     * @param sy        the initial layout Y of the content
     * @param ex        the final layout X of the content
     * @param ey        the final layout Y of the content
     * @return the timeline which will animate the movement
     */
    @Override
    public Timeline animateMove(String contentId, double sx, double sy, double ex, double ey) {
        Region content = contentMap.get(contentId);
        return RegionTimelines.layoutTimeline(content, sx, sy, ex, ey);
    }

    /**
     * Method to remove all content from the board, and all content from the content maps
     */
    @Override
    public void clearContent() {
        for (String id : boardsMap.keySet()) {
            removeContent(boardsMap.get(id));
        }
        //content should be removed in the above loop, but just to be sure...
        boardsMap.clear();
        contentMap.clear();
        getContentPane().getChildren().clear();
    }

    /**
     * Method to remove content from the board associated with the specified ticket
     *
     * @param content the group to be removed
     */
    @Override
    public void removeContent(BoardTicketGroup content) {
        int count = 0;
        for (BoardTicket ticket : content.getContents()) {
            removeContent(content, ticket, count * Defaults.ANIMATION_DELAY);
        }
        boardsMap.remove(content.getIdentifier());
    }

    /**
     * Method to remove a ticket from the board at a specified delay (optional)
     * Delay is used when also implemented Animatable
     *
     * @param content the content to be removed
     * @param delay   (optional) animation delay to removing the content in milliseconds
     */
    @Override
    public void removeContent(BoardTicketGroup group, BoardTicket content, double delay) {
        if (isTransitionsAnimated()) {
            Timeline disappear = animateDisappear(content.getId());
            RegionTimelines.delay(disappear, Duration.millis(delay));
            disappear.setOnFinished(e -> getContentPane().getChildren().remove(
                    contentMap.get(content.getId())));
            disappear.playFromStart();
        } else {
            getContentPane().getChildren().remove(contentMap.get(content.getId()));
        }
        contentMap.remove(content.getId());
    }

    /**
     * Method to add a content group to the board
     *
     * @param content the content to be added
     */
    @Override
    public void addContent(BoardTicketGroup content) {
        //TODO: position content
        boardsMap.put(content.getIdentifier(), content);
        int count = 0;
        for (BoardTicket ticket : content.getContents()) {
            addContent(content, ticket, count * Defaults.ANIMATION_DELAY);
        }
    }

    /**
     * Method to add a single piece of content to the board
     *
     * @param content the content
     * @param delay   (optional) animation delay to adding the content in milliseconds
     */
    @Override
    public void addContent(BoardTicketGroup group, BoardTicket content, double delay) {
        HolderRectangle displayContent = (HolderRectangle) createDisplayableContent(content);
        contentMap.put(content.getId(), displayContent);

        if (isTransitionsAnimated()) {
            Timeline appear = animateAppear(content.getId());
            RegionTimelines.delay(appear, Duration.millis(delay));
            getContentPane().getChildren().add(displayContent);
            appear.playFromStart();
        } else {
            getContentPane().getChildren().add(displayContent);
        }
    }

    /**
     * Method to create the content which will represent the ticket in the display
     *
     * @param content the content to be converted into display object
     * @return the displayable content as a region
     */
    @Override
    public Region createDisplayableContent(BoardTicket content) {
        HolderRectangle contentDisplayRegion = new HolderRectangle();
        contentDisplayRegion.addHeaderBox(content.getTitle(), content.getId(), Color.RED);
        return contentDisplayRegion;
    }


}
