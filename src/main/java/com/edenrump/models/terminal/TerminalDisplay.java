package com.edenrump.models.terminal;

import com.edenrump.QuickNote;
import com.edenrump.config.ApplicationDefaults;
import com.edenrump.loaders.TaskClusterLoader;
import com.edenrump.models.task.TaskCluster;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.edenrump.models.terminal.CommandHistory.SCROLL_DOWN;
import static com.edenrump.models.terminal.CommandHistory.SCROLL_UP;

public class TerminalDisplay {

    public ObjectProperty<TaskCluster> taskClusterProperty() {
        return taskCluster;
    }

    private final ObjectProperty<TaskCluster> taskCluster = new SimpleObjectProperty<>();

    private final TerminalProperties terminalProperties = new TerminalProperties();

    private final StringProperty cliText = new SimpleStringProperty();

    private final CommandHistory commandHistory = new CommandHistory(7);

    public void seedCluster(){
        TaskClusterLoader tcl = new TaskClusterLoader();
        try {
            taskCluster.set(tcl.loadFromFile(
                    new File(QuickNote.class.getResource("seedFiles/tasks.json").toURI())));

        } catch (Exception e) {
            //TODO: handle with response to user
            e.printStackTrace();
            System.out.println("Unable to load task cluster from seed file");
            taskCluster.set(null);
        }
    }

    public void setTaskCluster(TaskCluster taskCluster) {
        this.taskCluster.set(taskCluster);
    }

    public TerminalProperties getTerminalProperties() {
        return terminalProperties;
    }

    public void handleCommand(String command){
        //update command line history prior to processing
        commandHistory.put(command);

        //decipher command
        List<String> arguments = new LinkedList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while (m.find()) arguments.add(m.group(1));

        //process command
        boolean error = false;
        if(arguments.size() == 1) {
            switch (arguments.get(0)) {
                case "exit":
                    Platform.exit();
                    break;
                case "wide":
                    terminalProperties.setDisplayWidth(ApplicationDefaults.WIDE_WIDTH);
                    terminalProperties.setDisplayHeight(ApplicationDefaults.WIDE_HEIGHT);
                    break;
                case "large":
                    terminalProperties.setDisplayWidth(ApplicationDefaults.LARGE_WIDTH);
                    terminalProperties.setDisplayHeight(ApplicationDefaults.LARGE_HEIGHT);
                    break;
                case "small":
                    terminalProperties.setDisplayWidth(ApplicationDefaults.SMALL_WIDTH);
                    terminalProperties.setDisplayHeight(ApplicationDefaults.SMALL_HEIGHT);
                    break;
                case "darken":
                    terminalProperties.setBackgroundColor(ApplicationDefaults.DARK_BACKGROUND);
                    break;
                case "lighten":
                    terminalProperties.setBackgroundColor(ApplicationDefaults.LIGHT_BACKGROUND);
                    break;
                case "clear":
                    terminalProperties.setBackgroundColor(ApplicationDefaults.CLEAR_BACKGROUND);
                    break;
                default:
                    //TODO: add error statement to user
                    error = true;
                    break;
            }
        }

        //reset command line after command has been processed
        if (!error) cliText.set("");
    }

    public void displayHistory(int scroll) {
        if(scroll == SCROLL_UP){
            cliText.set(commandHistory.increaseCaret());
        } else if (scroll == SCROLL_DOWN){
            cliText.set(commandHistory.decreaseCaret());
        }

    }

    public StringProperty cliTextProperty() {
        return cliText;
    }

}
