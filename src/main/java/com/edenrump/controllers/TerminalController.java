package com.edenrump.controllers;

import com.edenrump.config.ApplicationDefaults;
import com.edenrump.models.task.Task;
import com.edenrump.models.task.TaskCluster;
import com.edenrump.models.terminal.CommandHistory;
import com.edenrump.models.terminal.TerminalDisplay;
import com.edenrump.ui.controls.LinuxTextField;
import com.edenrump.ui.transitions.RegionTimelines;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    /**
     * The command line history
     */
    private final CommandHistory commandHistory = new CommandHistory(7);

    private final TerminalDisplay display = new TerminalDisplay();

    /**
     * A map of all tasks available to the user
     */
    private final Map<Integer, Pair<Task, Label>> taskMap = new HashMap<>();

    public void initialize() {
        enterDisplayMode("Loading...");

        addKeyListeners();

        display.taskClusterProperty().addListener((obs, oldCluster, newCluster) -> displayCluster(newCluster));
        display.seedCluster();

        enterEditMode();
    }

    private void addKeyListeners() {
        terminalInputField.setOnKeyPressed(event -> {
            enterEditMode();
            if (event.getCode() == KeyCode.ENTER) {
                parseInput(terminalInputField.getText().toLowerCase());
            }

            if (event.getCode() == KeyCode.UP) {
                setInputText(commandHistory.increaseCaret());
            }

            if (event.getCode() == KeyCode.DOWN) {
                setInputText(commandHistory.decreaseCaret());
            }
        });
    }

    private void enterDisplayMode(String message) {
        changeMode(false, message);
    }
    private void enterEditMode() {
        changeMode(true, ">");
    }

    private void changeMode(boolean allowInput, String message) {
        leftMessageDisplay.setText(message);
        if (!allowInput) terminalInputField.setText("");
        terminalInputField.setEditable(allowInput);
    }

    private void parseInput(String input) {
        if (Objects.equals(terminalInputField.getText(), "")) return;
        commandHistory.put(terminalInputField.getText());
        List<String> arguments = new LinkedList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find()) arguments.add(m.group(1));

        if (arguments.get(0).startsWith(":")) {
            enterDisplayMode(handleSelectionTerminalCommand(arguments));
            return;
        }

        handleSingleWordTerminalCommand(arguments, "exit", () -> {
            setInputText("exiting...");
            terminalInputField.setEditable(false);
            PauseTransition exit = new PauseTransition(Duration.seconds(0.2));
            exit.setOnFinished((event -> Platform.exit()));
            exit.playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "wide", () -> {
            enterDisplayMode(ApplicationDefaults.DISPLAY_INDICATOR);
            RegionTimelines.sizeTimelineWithEffect(terminalAnchorLayer, ApplicationDefaults.WIDE_WIDTH, ApplicationDefaults.WIDE_HEIGHT, event -> enterEditMode()).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "small", () -> {
            enterDisplayMode(ApplicationDefaults.DISPLAY_INDICATOR);
            RegionTimelines.sizeTimelineWithEffect(terminalAnchorLayer, ApplicationDefaults.SMALL_WIDTH, ApplicationDefaults.SMALL_HEIGHT, event -> enterEditMode()).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "large", () -> {
            enterDisplayMode(ApplicationDefaults.DISPLAY_INDICATOR);
            RegionTimelines.sizeTimelineWithEffect(terminalAnchorLayer, ApplicationDefaults.LARGE_WIDTH, ApplicationDefaults.LARGE_HEIGHT, event -> enterEditMode()).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "darken", () -> {
            Color cStart = (Color) terminalInputField.getScene().getFill();
            RegionTimelines.createColourChange(terminalInputField.getScene(), cStart, ApplicationDefaults.DARK_BACKGROUND).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "lighten", () -> {
            Color cStart = (Color) terminalInputField.getScene().getFill();
            RegionTimelines.createColourChange(terminalInputField.getScene(), cStart, ApplicationDefaults.LIGHT_BACKGROUND).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "clear", () -> {
            Color cStart = (Color) terminalInputField.getScene().getFill();
            RegionTimelines.createColourChange(terminalInputField.getScene(), cStart, ApplicationDefaults.CLEAR_BACKGROUND).playFromStart();
        });

        if (arguments.get(0).equals("history") && arguments.size() == 1) {
            enterDisplayMode(Arrays.toString(commandHistory.getHistory()));
            return;
        } else if (arguments.get(0).equals("history") && arguments.size() > 1) {
            parseHistory(arguments);
            return;
        }

        setInputText("");
    }

    private String handleSelectionTerminalCommand(List<String> arguments) {
        int selection;
        try {
            selection = Integer.parseInt(arguments.get(0).substring(1));
            if (!taskMap.containsKey(selection)) return ApplicationDefaults.NO_TASK_OF_THAT_NUMBER;
        } catch (NumberFormatException n) {
            n.printStackTrace();
            return ApplicationDefaults.SELECTION_ERROR;
        }
        return "Selected task: " + taskMap.get(selection).getKey().getName();
    }

    private void handleSingleWordTerminalCommand(List<String> commands, String watchword, Runnable effect) {
        if (commands.get(0).equals(watchword) && commands.size() == 1) {
            effect.run();
        } else if (commands.get(0).equals("small") && commands.size() > 1) {
            enterDisplayMode("Command \"" + watchword + "\" does not accept arguments");
        }
    }

    private void parseHistory(List<String> args) {
        boolean clearFlag = false;
        args.remove(0); //get rid of history tag

        if (args.size() > 0) { //parse extra arguments
            if (args.get(0).equals("clear")) clearFlag = true;
        }
        if (clearFlag) commandHistory.clear();
    }

    private void setInputText(String text) {
        String textToSet = text == null ? " " : text;
        terminalInputField.setText(textToSet);
    }

    public void displayCluster(TaskCluster cluster) {
        clearDisplay();

        terminalMessageDisplay.getChildren().addAll(
                createTitlePane(cluster.getName()),
                createTaskPane(cluster.getTasks())
        );
    }

    public void clearDisplay(){
        terminalMessageDisplay.getChildren().clear();
    }

    public Node createTitlePane(String title){
        Label titleLabel = new Label(title);

        Separator horizontalBeam = new Separator(Orientation.HORIZONTAL);
        HBox.setHgrow(horizontalBeam, Priority.ALWAYS);

        HBox titleContainer = new HBox(titleLabel, horizontalBeam);
        titleContainer.setSpacing(5);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        return titleContainer;
    }

    public Node createTaskPane(List<Task> tasks){
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
