package com.edenrump.controllers;

import com.edenrump.ui.LinuxTextField;
import com.edenrump.ui.data.CommandHistory;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindowController implements Initializable {

    /**
     * The current state of the app
     */
    private IntegerProperty APP_STATE = new SimpleIntegerProperty(-1);
    private final int INPUT_STATE = 0x001;
    private final int DISPLAY_STATE = 0x002;

    public AnchorPane anchorBase;
    private LinuxTextField inputField = new LinuxTextField();
    private Label leftMessageDisplay = new Label();
    private Label rightMessageDisplay = new Label();
    private HBox messageAndInputNode = new HBox(leftMessageDisplay, inputField, rightMessageDisplay);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialiseDisplay();
        Platform.runLater(() -> {
            addKeyListeners();
            addStateListener();
            enterEditMode();
        });
    }

    private void initialiseDisplay() {
        messageAndInputNode.setAlignment(Pos.CENTER_LEFT);
        anchorBase.getChildren().add(messageAndInputNode);
        AnchorPane.setBottomAnchor(messageAndInputNode, 0d);
        AnchorPane.setLeftAnchor(messageAndInputNode, 0d);
        AnchorPane.setRightAnchor(messageAndInputNode, 0d);
    }

    private final String DISPLAY_INDICATOR = "^";

    private void addKeyListeners() {
        inputField.getScene().setOnKeyPressed(e -> {
            if (APP_STATE.get() == DISPLAY_STATE && e.getCode()!= KeyCode.ENTER) {
                System.out.println("Scene Triggered by: " + e.getCode());
                //TODO: parse whether keystroke is edit-enabling key stroke, or not
                enterEditMode();
            }
            if (APP_STATE.get() == INPUT_STATE) {
                if (e.getCode() == KeyCode.ESCAPE) {
                    commandHistory.resetCaret();
                    enterDisplayMode(DISPLAY_INDICATOR);
                }
            }
        });

        inputField.setOnKeyPressed(event -> {
            enterEditMode();
            if (event.getCode() == KeyCode.ENTER) {
                parseInput(inputField.getText());
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
        if (STATE == DISPLAY_STATE) inputField.setText("");
        APP_STATE.set(STATE);
    }

    private void addStateListener() {
        APP_STATE.addListener((observable, oldValue, newValue) -> {
            switch (newValue.intValue()) {
                case INPUT_STATE:
                    inputField.setEditable(true);
                    break;
                case DISPLAY_STATE:
                    inputField.setEditable(false);
                    break;
            }
        });
    }

    private CommandHistory commandHistory = new CommandHistory(7);

    private void parseInput(String input) {
        if (Objects.equals(inputField.getText(), "")) return;
        commandHistory.put(inputField.getText());


        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find())
            list.add(m.group(1)); // Add .replace("\"", "") to remove surrounding quotes.

        //check for exit flag
        if (list.get(0).equals("exit") && list.size() == 1) {
            setInputText("exiting...");
            inputField.setEditable(false);
            PauseTransition exit = new PauseTransition(Duration.seconds(2));
            exit.setOnFinished((event -> Platform.exit()));
            exit.playFromStart();
            return;
        } else if (list.get(0).equals("exit") && list.size() > 1) {
            enterDisplayMode("Command \"exit\" does not accept arguments");
            return;
        }

        if (list.get(0).equals("history") && list.size() == 1) {
            System.out.println(Arrays.toString(commandHistory.getHistory()));
            enterDisplayMode(Arrays.toString(commandHistory.getHistory()));
            return;
        } else if (list.get(0).equals("history") && list.size() > 1) {
            parseHistory(list);
            return;
        }
        //TODO: other arguments

        setInputText("");
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
        inputField.setText(textToSet);
        Platform.runLater(() -> {
            inputField.stopCursorBlink();
            inputField.setEditable(false);
            PauseTransition pt = new PauseTransition(Duration.seconds(0.1));
            pt.setOnFinished(event -> {
                inputField.positionCaret(textToSet.length());
                inputField.resetCursorBlink();
                inputField.setEditable(true);
            });
            pt.playFromStart();
        });
    }
}
