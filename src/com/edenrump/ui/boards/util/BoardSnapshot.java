package com.edenrump.ui.boards.util;

import com.edenrump.ui.boards.data.LayoutType;
import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Class representing a "SnapShot" view of a board, containing all the information needed to re-create the board
 * visually
 */
public class BoardSnapshot {

    /**
     * The name of the snapshot
     */
    private String name;
    /**
     * The id of the snapshot
     */
    private String id;
    /**
     * The layout type of the board
     */
    private LayoutType layoutType;
    /**
     * A map of the layout of the contents of the board
     */
    private List<NodeSnapshotData> nodeLayouts;

    /**
     * Constructor creates a new snapshot with an automatically-generated id and name from current date and time
     */
    public BoardSnapshot(LayoutType layoutType) {
        this(layoutType, UUID.randomUUID().toString(), new Date().toString());
    }

    /**
     * Constructor creates a new shapshot from the given name and id
     *
     * @param name name of the snapshot
     * @param id   id of the snapshot
     */
    public BoardSnapshot(LayoutType layoutType, String name, String id) {
        this(layoutType, name, id, new ArrayList<>());
    }

    /**
     * Constructor creates a new shapshot from the given name, id and layoutmap
     *
     * @param name        name of the snapshot
     * @param id          id of the snapshot
     * @param nodeLayouts map of board contents
     */
    public BoardSnapshot(LayoutType layoutType, String name, String id, List<NodeSnapshotData> nodeLayouts) {
        this.layoutType = layoutType;
        this.name = name;
        this.id = id;
        this.nodeLayouts = nodeLayouts;
    }

    /**
     * Method to get the name of the board
     *
     * @return the name of the board
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the id of the board
     *
     * @return the id of the board
     */
    public String getId() {
        return id;
    }

    /**
     * Method to get the map of the layout of the contents of the board
     *
     * @return the map of the layout of the contents of the board
     */
    public List<NodeSnapshotData> getNodeData() {
        return nodeLayouts;
    }

    /**
     * Method to add content to the snapshot
     *
     * @param id      the id associated with the node
     * @param node    the node to be snapped
     * @param layoutx the layoutX value of the node
     * @param layouty the layouty value of the node
     */
    public void addContent(String id, Node node, double layoutx, double layouty) {
        getNodeData().add(new NodeSnapshotData(id, node, new Point2D(layoutx, layouty)));
    }

    /**
     * Method to get the layout type of the board
     *
     * @return the layout type of the board
     */
    public LayoutType getLayoutType() {
        return layoutType;
    }

    public NodeSnapshotData getNodeById(String id) {
        for (NodeSnapshotData currentNode : this.getNodeData()) {
            if (currentNode.getId().equals(id)) return currentNode;
        }
        return null;
    }
}
