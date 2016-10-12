package com.example.sarahpadlipsky.iou;

/**
 * Represents a group of users of the IOU app.
 * @author cesiu
 * @version October 10, 2016
 */

import java.util.ArrayList;

public class Group {
    // The name of the group
    private String name;
    // The users in the group
    private ArrayList<User> users;
    // The amount of money the group has paid
    private double totalMoneySpent;

    public Group() {
        users = new ArrayList<User>();
    }

    /**
     * @return The name of the group
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The new name of the group
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The users in the group.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param user The user to add to the group
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * @return The total amount of money the group has paid
     */
    public double getMoneySpent() {
        return totalMoneySpent;
    }

    /**
     * @param moneySpent The new total amount of money the group has paid
     */
    public void setMoneySpent(double moneySpent) {
        totalMoneySpent = moneySpent;
    }

    /**
     * @return The name for the toString function
     */
    public String toString() {
        return name;
    }
}
