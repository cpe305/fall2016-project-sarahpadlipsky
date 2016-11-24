package com.example.sarahpadlipsky.iou;

import io.realm.RealmObject;

/**
 * Represents a blil made by a user.
 * @author sarahpadlipsky
 * @version November 19, 2016
 */
public class Bill extends RealmObject{

  // The name of the bill
  private String name;
  // The amount the bill cost.
  private double amount;
  // The description of the bill.
  private String description;
  // The id number for the bill.
  private String billID;
  // The user who paid the bill.
  private User user;

  /**
   * @return The name of the bill
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The cost of the bill
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return The cost of the bill
   */
  public double getAmount() {
    return amount;
  }

  /**
   * @param amount The cose of the bill
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * @return The description of the bill
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description The description of the bill
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return The id of the bill
   */
  public String getId() {
    return billID;
  }

  /**
   * @param billID The unique id of the group
   */
  public void setId(String billID) {
    this.billID = billID;
  }

  /**
   * @return The user who paid the bill
   */
  public User getUser() {
    return user;
  }

  /**
   * @param user The unique id of the group
   */
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return name;
  }

}
