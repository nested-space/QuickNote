package com.edenrump.ui.boards.displays;

import com.edenrump.ui.boards.components.HolderRectangle;
import com.edenrump.ui.boards.contracts.Animatable;
import com.edenrump.ui.boards.contracts.CanDisplayBoard;
import com.edenrump.ui.boards.data.BoardTicket;
import com.edenrump.ui.boards.data.BoardTicketGroup;
import com.edenrump.ui.boards.data.LayoutType;
import com.edenrump.ui.boards.transitions.RegionTimelines;
import com.edenrump.ui.boards.util.BoardSnapshot;
import com.edenrump.ui.boards.util.NodeSnapshotData;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.edenrump.ui.boards.transitions.RegionTimelines.DELAY_DURATION;

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
    private Map<String, BoardTicketGroup> ticketGroupIdMap = new HashMap<>();

    private static final double PADDING = 25d;

    /**
     * The position of the y caret used for positioning elements on the board
     */
    private double yCaret = PADDING;

    /**
     * The position of the x caret used for positioning elements on the board
     */
    private double xCaret = PADDING;

    /**
     * A map of ticket identifier to the node used to display the content
     */
    private Map<String, Region> displayedContentIdMap = new HashMap<>();

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
            this.getTicketGroupIdMap().put(group, new BoardTicketGroup(group, LayoutType.CLUSTER));
        }
    }

    /**
     * Method to get the contents of the board
     *
     * @return a map of board identifier and board content
     */
    private Map<String, BoardTicketGroup> getTicketGroupIdMap() {
        return ticketGroupIdMap;
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
     * @param content the id of the content to be animated
     * @return the timeline which will animate the disappearence
     */
    @Override
    public Timeline animateDisappear(Region content) {
        return RegionTimelines.combineTimelines(
                RegionTimelines.opacityTimeline(content, content.getOpacity(), 0),
                RegionTimelines.translationTimeline(content, content.getTranslateX(), content.getTranslateY(),
                        content.getTranslateX(), content.getTranslateY() - 10));
    }

    /**
     * Method to create a Timeline which animates the appearence of a ticket of the specified id
     *
     * @param content the id of the content to be animated
     * @return the timeline which will animate the appearence
     */
    @Override
    public Timeline animateAppear(Region content) {
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
     * @param content the id of the content to be animated
     * @param sx        the initial layout X of the content
     * @param sy        the initial layout Y of the content
     * @param ex        the final layout X of the content
     * @param ey        the final layout Y of the content
     * @return the timeline which will animate the movement
     */
    @Override
    public Timeline animateMove(Region content, double sx, double sy, double ex, double ey) {
        content.setLayoutX(sx);
        content.setLayoutY(sy);
        return RegionTimelines.layoutTimeline(content, sx, sy, ex, ey);
    }

    /**
     * Method to remove all content from the board, and all content from the content maps
     */
    @Override
    public void clearDisplay() {
        for (String id : ticketGroupIdMap.keySet()) {
            removeContent(ticketGroupIdMap.get(id));
        }
        //content should be removed in the above loop, but just to be sure...
        displayedContentIdMap.clear();
        getContentPane().getChildren().clear();
        xCaret = yCaret = PADDING;
    }

    @Override
    public void displayAllContent() {
        clearDisplay();

        for (String groupName : ticketGroupIdMap.keySet()) {
            if (isTransitionsAnimated()) {
                PauseTransition pauseTransition = new PauseTransition(DELAY_DURATION);
                pauseTransition.setOnFinished(e -> addContent(ticketGroupIdMap.get(groupName)));
                pauseTransition.playFromStart();
            } else {
                addContent(ticketGroupIdMap.get(groupName));
            }
        }
    }

    /**
     * Method to remove content from the board associated with the specified ticket
     *
     * @param content the group to be removed
     */
    @Override
    public void removeContent(BoardTicketGroup content) {
        //TODO: ticketGroup -> tickets needs to be in board's data model (need to remove once they're there!)
        //TODO need to remove board lables (when they're included)
        int count = 0;
        for (BoardTicket ticket : content.getContents()) {
            removeContent(content, ticket, count * DELAY_DURATION.toMillis());
        }
        ticketGroupIdMap.remove(content.getIdentifier());
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
        //TODO: Future State -> create snapshot, add content to data model, then generate new snapshot and update
        if (isTransitionsAnimated()) {
            Region display = displayedContentIdMap.get(content.getId());
            Timeline disappear = animateDisappear(display);
            RegionTimelines.delay(disappear, Duration.millis(delay));
            disappear.setOnFinished(e -> getContentPane().getChildren().remove(
                    display));
            disappear.playFromStart();
        } else {
            getContentPane().getChildren().remove(displayedContentIdMap.get(content.getId()));
        }
        displayedContentIdMap.remove(content.getId());
    }

    /**
     * Method to add a content group to the board
     *
     * @param content the content to be added
     */
    @Override
    public void addContent(BoardTicketGroup content) {
        //TODO: ticketGroup -> tickets needs to be in board's data model
        //TODO boardGroup labels would be handy
        ticketGroupIdMap.put(content.getIdentifier(), content);
        int count = 0;
        for (BoardTicket ticket : content.getContents()) {
            addContent(content, ticket, count * DELAY_DURATION.toMillis());
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
        //TODO: Future State -> create snapshot, add content to data model, then generate new snapshot and update
        Region displayContent = createDisplayableContent(content);
        displayedContentIdMap.put(content.getId(), displayContent);
        if (isTransitionsAnimated()) {
            Timeline appear = animateAppear(displayContent);
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

    /**
     * Method to capture a snapshot off all displayed node positions on the board
     *
     * @return a snapshot of the board's contents
     */
    @Override
    public BoardSnapshot getSnapshot() {
        BoardSnapshot snap = new BoardSnapshot(LayoutType.CLUSTER);
        for (String id : displayedContentIdMap.keySet()) {
            Node n = displayedContentIdMap.get(id);
            snap.addContent(id, n, n.getLayoutX(), n.getLayoutY());
        }
        return snap;
    }

    /**
     * Method to calculate what the layout of the board should be given the data associated with the board
     */
    private BoardSnapshot generateLayoutGivenData() {
        return null;
        //TODO: create method that determines layout of nodes
    }

    /**
     * Method to move from one layout to another
     *
     * @param old    the old snapshot
     * @param target the new snapshot
     */
    @Override
    public void updateDisplay(BoardSnapshot old, BoardSnapshot target) {
        //create a list of all target nodes -> this will be used alongside the existing_fromOld to move nodes
        Map<String, NodeSnapshotData> existing_fromTarget = new HashMap<>();
        for (NodeSnapshotData node : target.getNodeData()) {
            existing_fromTarget.put(node.getId(), node);
        }

        //create a list of all old nodes -> this will be used alongside the existing_fromTarget to move nodes
        Map<String, NodeSnapshotData> existing_fromOld = new HashMap<>();
        for (NodeSnapshotData node : old.getNodeData()) {
            existing_fromOld.put(node.getId(), node);
        }

        //create list of new nodes and trim new nodes map with nodes that
        List<NodeSnapshotData> newNodesList = new ArrayList<>();
        for (NodeSnapshotData newNode : target.getNodeData()) {
            if (old.getNodeById(newNode.getId()) != null) {
                newNodesList.add(newNode);
                existing_fromTarget.remove(newNode.getId());
            }
        }

        //create list of deleting nodes
        List<NodeSnapshotData> oldNodesList = new ArrayList<>();
        for (NodeSnapshotData oldNode : old.getNodeData()) {
            if (target.getNodeById(oldNode.getId()) != null) {
                oldNodesList.add(oldNode);
                existing_fromOld.remove(oldNode.getId());
            }
        }

        //create list of map of existing nodes that need to be moved
        Map<NodeSnapshotData, NodeSnapshotData> moveMap = new HashMap<>();
        for (String id : existing_fromTarget.keySet()) {
            moveMap.put(
                    existing_fromOld.get(id),
                    existing_fromTarget.get(id));
        }

        Timeline global = new Timeline();

        displayedContentIdMap.clear();
        for(NodeSnapshotData nd: oldNodesList){
            Timeline t = animateDisappear((Region) nd.getNode());
            global.getKeyFrames().addAll(t.getKeyFrames());
        }

        global.setOnFinished(e -> {
            for(NodeSnapshotData n : oldNodesList){
                getContentPane().getChildren().remove(n.getNode());
            }
        });

        for(NodeSnapshotData nd : newNodesList){
            displayedContentIdMap.put(nd.getId(), (Region) nd.getNode());
            Timeline t = animateAppear((Region) nd.getNode());
            getContentPane().getChildren().add(nd.getNode());
            global.getKeyFrames().addAll(t.getKeyFrames());
        }

        for(NodeSnapshotData oldN : moveMap.keySet()){
            NodeSnapshotData newN = moveMap.get(oldN);
            displayedContentIdMap.put(oldN.getId(), (Region) oldN.getNode());
            Timeline t = animateMove((Region) oldN.getNode(),
                    oldN.getLayout().getX(), oldN.getLayout().getY(),
                    newN.getLayout().getX(), newN.getLayout().getY());
            if(!getContentPane().getChildren().contains(oldN.getNode())) getContentPane().getChildren().add(oldN.getNode());
            global.getKeyFrames().addAll(t.getKeyFrames());
        }


    }


}
