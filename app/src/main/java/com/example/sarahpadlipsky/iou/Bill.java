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
  private User sendUser;
  // Determines type of bill
  private boolean isPayBackBill;
  // The user receiving the bill (if payback).
  private User receiveUser;


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
  public User getSendUser() {
    return sendUser;
  }

  /**
   * @param sendUser The unique id of the group
   */
  public void setSendUser(User sendUser) {
    this.sendUser = sendUser;
  }

  /**
   * @return The user who paid the bill
   */
  public User getReceiveUser() {
    return sendUser;
  }

  /**
   * @param sendUser The unique id of the group
   */
  public void setReceiveUser(User sendUser) {
    this.sendUser = sendUser;
  }

  /**
   * @param isPayBackBill Determines whether the bill is a payback bill
   */
  public void setPayBackBill(boolean isPayBackBill) {
    this.isPayBackBill = isPayBackBill;
  }

  /**
   * @return Where the bill is a payback bill
   */
  public boolean getPayBackBill() {
    return isPayBackBill;
  }

  @Override
  public String toString() {
    return name;
  }

}
