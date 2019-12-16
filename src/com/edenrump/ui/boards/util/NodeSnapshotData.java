package com.edenrump.ui.boards.util;

import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Class representing all information necessary to store a snapshot of a node as displayed in an AnchorPane
 * for the purposes of creating a BoardSnapshot
 */
public class NodeSnapshotData {

    /**
     * The id associated with the node
     */
    private String id;

    /**
     * The node
     */
    private Node node;

    /**
     * The layout coordinates of the node
     */
    private Point2D layout;

    /**
     * Constructor creates NodeSnapshotData from all information
     * @param id The id associated with the node
     * @param node The node
     * @param layout The layout associated with the node
     */
    public NodeSnapshotData(String id, Node node, Point2D layout) {
        this.id = id;
        this.node = node;
        this.layout = layout;
    }

    /**
     * Method to get the id associated with the node
     * @return The id associated with the node
     */
    public String getId() {
        return id;
    }

    /**
     * Method to get the node
     * @return the node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Method to get the layout associated with the node
     * @return The layout associated with the node
     */
    public Point2D getLayout() {
        return layout;
    }
}
