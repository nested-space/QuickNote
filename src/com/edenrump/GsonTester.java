package com.edenrump;

import com.edenrump.models.User;
import com.edenrump.models.task.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Date;

public class GsonTester {
    public static void main(String args[]) {

//        GsonTester tester = new GsonTester();
        try {
            User user = new User("Ed Eden-Rump", "ed.edenrump@gmail.com");
            Task task = new Task("First Task", new Date(), user.get_id());
            writeJSON("users", user);
            writeJSON("tasks", task);
        } catch(IOException e) {
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

    private static void writeJSON(String filename, Task task) throws IOException {
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
    public Student(){}

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
        return "Student [ name: "+name+", age: "+ age+ " ]";
    }  }