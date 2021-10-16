package com.edenrump.loaders;

import com.edenrump.models.task.TaskCluster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Utility class containing the methods required to load a TaskCluster
 */
public class TaskClusterLoader extends GsonLoader {

    @Override
    public TaskCluster loadFromFile(String filename) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(filename + ".json"));
            return gson.fromJson(bufferedReader, TaskCluster.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new TaskCluster(FILE_LOAD_ERROR);
    }
}
