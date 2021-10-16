package com.edenrump.models.users;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a cluster of users with a particular name
 */
public class UserCluster {

    /**
     * The name of the cluster
     */
    private String name;

    /**
     * List of users in the cluster
     */
    private List<User> users;

    /**
     * Method to create a cluster
     *
     * @param name  the name of the cluster
     * @param users the users in the cluster
     */
    public UserCluster(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    /**
     * Method to create a cluster with no users
     *
     * @param name the name of the cluster
     */
    public UserCluster(String name) {
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
     * Method to get the users in the cluster
     * Supports lazy initialisation
     *
     * @return the users in the cluster
     */
    public List<User> getUsers() {
        return users == null ? users = new ArrayList<>() : users;
    }

    /**
     * Method to add a user to the cluster
     *
     * @param user the user to be added
     */
    public void addUser(User user) {
        if (users == null) users = new ArrayList<>();
        users.add(user);
    }

    /**
     * Method to add multiple users to a cluster
     *
     * @param users users to add to the cluster
     */
    public void addUsers(List<User> users) {
        if (users == null) users = new ArrayList<>();
        this.users.addAll(users);
    }
}
