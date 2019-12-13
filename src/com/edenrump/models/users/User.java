package com.edenrump.models.users;

import java.util.UUID;

/**
 * Class representing a user of the system
 */
public class User {

    /**
     * The id of the user
     */
    private String _id;

    /**
     * The name of the user
     */
    private String name;

    /**
     * The email address of the user
     */
    private String email;

    /**
     * Method to create a user where the id is known
     *
     * @param _id   the id of the user
     * @param name  the name of the user
     * @param email the email address of the user
     */
    public User(String _id, String name, String email) {
        this._id = _id;
        this.name = name;
        this.email = email;
    }

    /**
     * Method to create a new user
     *
     * @param name  the name of the user
     * @param email the email of the user
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this._id = UUID.randomUUID().toString();
    }

    /**
     * Method to get the id of the user
     *
     * @return the id of the user
     */
    public String get_id() {
        return _id;
    }

    /**
     * Method to get the name of user
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of user
     *
     * @return the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the email address of user
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set the email address of user
     *
     * @return the email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
