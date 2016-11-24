package com.example.sarahpadlipsky.iou;

/**
 * Represents the current user using the application.
 * @author sarahpadlipsky
 * @version October 30, 2016
 */
public class CurrentUser {
  // Current user logged into the application.
  private static User appCurrentUser;

  /**
   * @return The current user
   */
  public static User getCurrentUser() {
    return appCurrentUser;
  }

  /**
   * @param user The current logged in user
   */
  public static void setCurrentUser(User user) { appCurrentUser = user; }

}
