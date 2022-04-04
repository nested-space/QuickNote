package com.edenrump.controllers;

import com.edenrump.models.task.Task;
import com.edenrump.models.task.TaskCluster;
import com.edenrump.models.terminal.TerminalDisplay;
import com.edenrump.ui.controls.LinuxTextField;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.edenrump.models.terminal.CommandHistory.SCROLL_DOWN;
import static com.edenrump.models.terminal.CommandHistory.SCROLL_UP;

public class TerminalController {

    /**
     * Container for all messages to be displayed via the terminal
     */
    public VBox terminalMessageDisplay;
    /**
     * The AnchorPane that holds the terminal display
     */
    public AnchorPane terminalAnchorLayer;
    /**
     * The input field in the terminal display
     */
    public LinuxTextField terminalInputField;
    /**
     * The label used to display feedback to user in place of the text field
     */
    public Label leftMessageDisplay;

    private final TerminalDisplay terminalDisplayModel = new TerminalDisplay();

    /**
     * A map of all tasks available to the user
     */
    private final Map<Integer, Pair<Task, Label>> taskMap = new HashMap<>();
    public Region backgroundLayer;

    public void initialize() {
        terminalInputField.setText("Loading...");

        addKeyListeners();
        linkWithModel();

        terminalDisplayModel.taskClusterProperty().addListener((obs, oldCluster, newCluster) -> displayCluster(newCluster));
        terminalDisplayModel.seedCluster();

        enterEditMode();
    }

    private void addKeyListeners() {
        terminalInputField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                parseInput(terminalInputField.getText().toLowerCase());
            }

            if (event.getCode() == KeyCode.UP) {
                terminalDisplayModel.displayHistory(SCROLL_UP);
            }

            if (event.getCode() == KeyCode.DOWN) {
                terminalDisplayModel.displayHistory(SCROLL_DOWN);
            }
        });
    }

    private void linkWithModel() {
        //link visible properties
        terminalAnchorLayer.prefWidthProperty().bind(terminalDisplayModel.getTerminalProperties().displayWidthProperty());
        terminalAnchorLayer.prefHeightProperty().bind(terminalDisplayModel.getTerminalProperties().displayHeightProperty());
        terminalDisplayModel.getTerminalProperties().backgroundColorProperty().addListener(
                (observableValue, oldColor, newColor) ->
                        backgroundLayer.setBackground(new Background(new BackgroundFill(newColor, CornerRadii.EMPTY, Insets.EMPTY))));

        //link command line input
        terminalDisplayModel.cliTextProperty().bindBidirectional(terminalInputField.textProperty());
    }

    private void enterEditMode() {
        leftMessageDisplay.setText(">");
        terminalInputField.setText("");
        terminalInputField.setEditable(true);
    }

    private void parseInput(String input) {
        if (Objects.equals(input, "")) return;
        terminalDisplayModel.handleCommand(terminalInputField.getText());
    }

    public void displayCluster(TaskCluster cluster) {
        clearDisplay();

        terminalMessageDisplay.getChildren().addAll(
                createTitlePane(cluster.getName()),
                createTaskPane(cluster.getTasks())
        );
    }

    public void clearDisplay() {
        terminalMessageDisplay.getChildren().clear();
    }

    public Node createTitlePane(String title) {
        Label titleLabel = new Label(title);

        Separator horizontalBeam = new Separator(Orientation.HORIZONTAL);
        HBox.setHgrow(horizontalBeam, Priority.ALWAYS);

        HBox titleContainer = new HBox(titleLabel, horizontalBeam);
        titleContainer.setSpacing(5);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        return titleContainer;
    }

    public Node createTaskPane(List<Task> tasks) {
        FlowPane taskPane = new FlowPane(Orientation.VERTICAL, 10, 1);
        int counter = 0;
        for (Task task : tasks) {
            Label label = new Label(++counter + ": " + task.getName());
            taskPane.getChildren().add(label);
            taskMap.put(counter, new Pair<>(task, label));
        }
        return taskPane;
    }
}
