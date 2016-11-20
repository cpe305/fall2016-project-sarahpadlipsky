package com.example.sarahpadlipsky.iou;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Represents a group of users of the IOU app.
 * @author cesiu
 * @author sarahpadlipsky
 * @version October 16, 2016
 */
public class Group extends RealmObject {
  // The name of the group
  @Required
  private String name;
  // The description of the group
  private String description;
  // The amount of money the group has paid
  private double totalMoneySpent;
  // The users in the group
  private RealmList<User> users;
  // The id number for the group.
  private String groupID;


  /**
   * Constructor for a group. Initializes the users list.
   */
  public Group() {
        users = new RealmList<>();
    }

  /**
   * @return The id of the group
   */
  public String getId() {
    return groupID;
  }

  /**
   * @param groupID The unique id of the group
   */
  public void setId(String groupID) {
    this.groupID = groupID;
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
   * @return The description of the group
   */
  public String getDescription() {
        return description;
    }

  /**
   * @param description The description explaining the purpose of the group
   */
  public void setDescription(String description) {
        this.description = description;
    }

  /**
   * @return The users in the group.
   */
  public RealmList<User> getUsers() {
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
  @Override
  public String toString() {
        return name;
    }
}
