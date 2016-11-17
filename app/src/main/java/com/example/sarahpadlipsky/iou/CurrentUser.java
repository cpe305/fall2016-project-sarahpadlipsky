package com.example.sarahpadlipsky.iou;

/**
 * Represents the current User using the eapplication.
 * @author sarahpadlipsky
 * @version October 30, 2016
 */
public class CurrentUser {
  private static User appCurrentUser;

  public static User getCurrentUser() {
    return appCurrentUser;
  }

  public static void setCurrentUser(User user) { appCurrentUser = user; }

}

