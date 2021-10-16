package com.edenrump.models.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a cluster of tasks with a particular name
 */
public class TaskCluster {

    /**
     * The name of the cluster
     */
    private String name;

    /**
     * List of tasks in the cluster
     */
    private List<Task> tasks;

    /**
     * Method to create a cluster
     *
     * @param name  the name of the cluster
     * @param tasks the tasks in the cluster
     */
    public TaskCluster(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * Method to create a cluster with no tasks
     *
     * @param name the name of the cluster
     */
    public TaskCluster(String name) {
        this.name = name;
    }

    /**
     * Method to get the name of the cluster
     *
     * @return the name of the cluster
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of the cluster
     *
     * @param name the name of the cluster. Not recommended to be more than 40 characters.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the tasks in the cluster
     * Supports lazy initialisation
     *
     * @return the tasks in the cluster
     */
    public List<Task> getTasks() {
        return tasks == null ? tasks = new ArrayList<>() : tasks;
    }

    /**
     * Method to add a task to the cluster
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {
        if (tasks == null) tasks = new ArrayList<>();
        tasks.add(task);
    }

    /**
     * Method to add multiple tasks to a cluster
     *
     * @param tasks tasks to add to the cluster
     */
    public void addTasks(List<Task> tasks) {
        if (tasks == null) tasks = new ArrayList<>();
        this.tasks.addAll(tasks);
    }
}
