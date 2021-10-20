package com.edenrump.models.terminal;

import com.edenrump.QuickNote;
import com.edenrump.loaders.TaskClusterLoader;
import com.edenrump.models.task.TaskCluster;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

public class TerminalDisplay {

    public TaskCluster getTaskCluster() {
        return taskCluster.get();
    }

    public ObjectProperty<TaskCluster> taskClusterProperty() {
        return taskCluster;
    }

    private ObjectProperty<TaskCluster> taskCluster = new SimpleObjectProperty<>();

    public void seedCluster(){
        TaskClusterLoader tcl = new TaskClusterLoader();
        try {
            taskCluster.set(tcl.loadFromFile(
                    new File(QuickNote.class.getResource("seedFiles/tasks.json").toURI())));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to load task cluster from seed file");
            taskCluster.set(null);
        }
    }

}
