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
  // The specific id for the user
  @PrimaryKey
  private String id;
  // The name of the user
  @Required
  private String name;
  @Required
  private String email;
  private  RealmList<Bill> bills;
  // The amount of money the user has paid
  private double moneySpent;
  // The amount of money the user still owes
  private double moneyOwed;
  // The groups the user belongs to
  private RealmList<Group> groups;

  /**
   * Constructor for the user class. Initializes the list of groups.
   */
  public User() {
    groups = new RealmList<>();
    bills = new RealmList<>();
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
   * @return The email of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email The new name of the user
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return The user's bills
   */
  public RealmList<Bill> getBills() {
    return bills;
  }

  /**
   * @param bill The bill to add to the user's bills
   */
  public void addBill(Bill bill) {
    bills.add(bill);

    double currentMoneyOwed = 0;

    for (Bill currentBill : bills) {
      currentMoneyOwed += currentBill.getAmount();
    }

    setMoneySpent(currentMoneyOwed);
  }

  /**
   * @return The name for the toString function
   */

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
   * @return The name for the toString function
   */
  @Override
  public String toString() {
    return name;
  }
}
