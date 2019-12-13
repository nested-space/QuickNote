package com.edenrump.controllers;

import com.edenrump.config.Defaults;
import com.edenrump.models.task.Task;
import com.edenrump.models.task.TaskCluster;
import com.edenrump.transitions.RegionTimelines;
import com.edenrump.ui.terminal.LinuxTextField;
import com.edenrump.ui.data.CommandHistory;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindowController implements Initializable {

    /**
     * The application state associated with terminal user input
     */
    private final int INPUT_STATE = 0x001;
    /**
     * The application state associated with terminal display-only mode
     */
    private final int DISPLAY_STATE = 0x002;
    /**
     * Container for all messages to be displayed via the terminal
     */
    public VBox terminalMessageDisplay;
    /**
     * The base pane on which all other panes are layered
     */
    public StackPane screenBaseStackPane;
    /**
     * The loading pane to be displayed when the program is loading and not available for user interaction
     */
    public AnchorPane LoadingPane;
    /**
     * The base StackPane supporting all other nodes in the Terminal console
     */
    public StackPane terminalBaseStackLayer;
    /**
     * The anchorpane that holds the terminal dispaly
     */
    public AnchorPane terminalAnchorLayer;
    /**
     * The container tha holds the user input and display components of the terminal
     */
    public HBox terminalInputContainer;
    /**
     * The input field in the terminal display
     */
    public LinuxTextField terminalInputField;
    /**
     * The label used to display feedback to user in place of the text field
     */
    public Label leftMessageDisplay;
    /**
     * The current stage. Used for minimising programatically.
     */
    private Stage stage;
    /**
     * The current state of the app
     */
    private IntegerProperty APP_STATE = new SimpleIntegerProperty(-1);
    /**
     * The command line history
     */
    private CommandHistory commandHistory = new CommandHistory(7);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        terminalInputField.setEditable(false);
        Platform.runLater(() -> {
            PauseTransition pt = new PauseTransition(Duration.seconds(0.5));
            pt.setOnFinished((e) -> go());
            pt.play();
        });
    }

    public void go() {
        terminalInputField.setEditable(true);
        addKeyListeners();
        addStateListener();
        enterEditMode();
        terminalBaseStackLayer.getChildren().remove(LoadingPane);
    }

    private void addKeyListeners() {
        terminalInputField.getScene().setOnKeyPressed(e -> {
            if (APP_STATE.get() == DISPLAY_STATE && e.getCode() != KeyCode.ENTER) {
                System.out.println("Scene Triggered by: " + e.getCode());
                //TODO: parse whether keystroke is edit-enabling key stroke, or not
                enterEditMode();
            }
            if (APP_STATE.get() == INPUT_STATE) {
                if (e.getCode() == KeyCode.ESCAPE) {
                    commandHistory.resetCaret();
                    enterDisplayMode(Defaults.DISPLAY_INDICATOR);
                }
            }
        });

        terminalInputField.setOnKeyPressed(event -> {
            enterEditMode();
            if (event.getCode() == KeyCode.ENTER) {
                parseInput(terminalInputField.getText());
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
        changeMode(DISPLAY_STATE, message);
    }

    private void enterEditMode() {
        changeMode(INPUT_STATE, ">");
    }

    private void changeMode(int STATE, String message) {
        leftMessageDisplay.setText(message);
        if (STATE == DISPLAY_STATE) terminalInputField.setText("");
        APP_STATE.set(STATE);
    }

    private void addStateListener() {
        APP_STATE.addListener((observable, oldValue, newValue) -> {
            switch (newValue.intValue()) {
                case INPUT_STATE:
                    terminalInputField.setEditable(true);
                    break;
                case DISPLAY_STATE:
                    terminalInputField.setEditable(false);
                    break;
            }
        });
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
            enterDisplayMode(Defaults.DISPLAY_INDICATOR);
            RegionTimelines.sizeTimelineWithEffect(terminalAnchorLayer, Defaults.WIDE_WIDTH, Defaults.WIDE_HEIGHT, event -> enterEditMode()).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "small", () -> {
            enterDisplayMode(Defaults.DISPLAY_INDICATOR);
            RegionTimelines.sizeTimelineWithEffect(terminalAnchorLayer, Defaults.SMALL_WIDTH, Defaults.SMALL_HEIGHT, event -> enterEditMode()).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "large", () -> {
            enterDisplayMode(Defaults.DISPLAY_INDICATOR);
            RegionTimelines.sizeTimelineWithEffect(terminalAnchorLayer, Defaults.LARGE_WIDTH, Defaults.LARGE_HEIGHT, event -> enterEditMode()).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "darken", () -> {
            RegionTimelines.colourTimeline(terminalInputField.getScene(), Defaults.DARK_BACKGROUND).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "lighten", () -> {
            RegionTimelines.colourTimeline(terminalInputField.getScene(), Defaults.LIGHT_BACKGROUND).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "clear", () -> {
            RegionTimelines.colourTimeline(terminalInputField.getScene(), Defaults.CLEAR_BACKGROUND).playFromStart();
        });

        handleSingleWordTerminalCommand(arguments, "min", () -> {
            stage.setIconified(true);
        });

        if (arguments.get(0).equals("history") && arguments.size() == 1) {
            enterDisplayMode(Arrays.toString(commandHistory.getHistory()));
            return;
        } else if (arguments.get(0).equals("history") && arguments.size() > 1) {
            parseHistory(arguments);
            return;
        }
        //TODO: other arguments

        setInputText("");
    }

    private String handleSelectionTerminalCommand(List<String> arguments) {
        int selection;
        try{
            selection = Integer.parseInt(arguments.get(0).substring(1));
            if(!taskMap.containsKey(selection)) return Defaults.NO_TASK_OF_THAT_NUMBER;
        } catch (NumberFormatException n){
            n.printStackTrace();
            return Defaults.SELECTION_ERROR;
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
        System.out.println("parsing history");
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
        Platform.runLater(() -> {
            terminalInputField.stopCursorBlink();
            terminalInputField.setEditable(false);
            PauseTransition pt = new PauseTransition(Duration.seconds(0.1));
            pt.setOnFinished(event -> {
                terminalInputField.positionCaret(textToSet.length());
                terminalInputField.resetCursorBlink();
                terminalInputField.setEditable(true);
            });
            pt.playFromStart();
        });
    }

    public void loadCluster(TaskCluster cluster) {
        terminalMessageDisplay.getChildren().clear();

        int counter = 0;
        Label title = new Label(cluster.getName());
        title.setId("title");
        Separator s = new Separator(Orientation.HORIZONTAL);
        HBox.setHgrow(s, Priority.ALWAYS);
        HBox titleContainer = new HBox(title, s);
        titleContainer.setSpacing(5);
        titleContainer.setAlignment(Pos.CENTER_LEFT);
        terminalMessageDisplay.getChildren().add(titleContainer);
        FlowPane tasks = new FlowPane(Orientation.VERTICAL, 10, 1);
        for (Task task : cluster.getTasks()) {
            Label label = new Label(++counter + ": " + task.getName());
            tasks.getChildren().add(label);
            taskMap.put(counter, new Pair<>(task, label));
        }
        terminalMessageDisplay.getChildren().add(tasks);

    }

    /**
     * A map of all tasks available to the user
     */
    private Map<Integer, Pair<Task, Label>> taskMap = new HashMap<>();

    /**
     * Method to set the current stage
     *
     * @param stage the current stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
