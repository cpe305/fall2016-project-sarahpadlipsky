package com.example.sarahpadlipsky.iou;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Represents a user of the IOU app.
 * @author cesiu
 * @author sarahpadlipsky
 * @version October 16, 2016
 */

public class User extends RealmObject {
    @PrimaryKey
    private String id;
    // The name of the user
    @Required
    private String name;
    // Is the main user
    private boolean isCurrentUser;
    // The amount of money the user has paid
    private double moneySpent;
    // The amount of money the user still owes
    private double moneyOwed;
    // The groups the user belongs to
    private RealmList<Group> groups;


    public User() {
        groups = new RealmList<>();
    }

    /**
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The amount of money the user has paid
     */
    public double getMoneySpent() {
        return moneySpent;
    }

    /**
     * @param moneySpent The new amount of money the user has paid
     */
    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }

    /**
     * @return The amount of money the user still owes
     */
    public double getMoneyOwed() {
        return moneyOwed;
    }

    /**
     * @param moneyOwed The new amount of money the user still owes
     */
    public void setMoneyOwed(double moneyOwed) {
        this.moneyOwed = moneyOwed;
    }

    /**
     * @return The user's groups
     */
    public RealmList<Group> getGroups() {
        return groups;
    }

    /**
     * @param group The group to add to the user's groups
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * @param isCurrentUser Determines if this user is the current user
     */
    public void setIsCurrentUser(boolean isCurrentUser) {
        this.isCurrentUser = isCurrentUser;
    }

    /**
     * @return If the user is the current user
     */
    public boolean getIsCurrentUser() {
        return isCurrentUser;
    }

    /**
     * @return The name for the toString function
     */
    public String toString() {
        return name + ": $" + getMoneySpent();
    }
}
