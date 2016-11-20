package com.example.sarahpadlipsky.iou;

import io.realm.RealmObject;

/**
 * Represents a blil made by a user.
 * @author sarahpadlipsky
 * @version November 19, 2016
 */
public class Bill extends RealmObject{
  // The amount the bill cost.
  private double amount;
  // The description of the bill.
  private String description;

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


}
