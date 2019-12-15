package com.edenrump.test;

import com.edenrump.models.task.Task;
import com.edenrump.models.task.TaskCluster;
import com.edenrump.models.users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GsonTester {

    public static void main(String[] args) {
        try {
            User user = new User("Ed Eden-Rump", "ed.edenrump@gmail.com");
            Task task = new Task("First Task", new Date(), user.get_id());
            Task t2 = new Task("Second Task", new Date(), user.get_id());
            Task t3 = new Task("Third Task", new Date(), user.get_id());
            TaskCluster taskCluster = new TaskCluster("To Dos", new ArrayList<>(Arrays.asList(task, t2, t3)));
            writeJSON("users", user);
            writeJSON("tasks", taskCluster);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeJSON(String filename, User user) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(filename + ".json");
        writer.write(gson.toJson(user));
        writer.close();
    }

    private static void writeJSON(String filename, TaskCluster task) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(filename + ".json");
        writer.write(gson.toJson(task));
        writer.close();
    }

    private static Student readJSON(String filename) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filename + ".json"));

        Student student = gson.fromJson(bufferedReader, Student.class);
        return student;
    }
}

class Student {
    private String name;
    private int age;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student [ name: " + name + ", age: " + age + " ]";
    }
}