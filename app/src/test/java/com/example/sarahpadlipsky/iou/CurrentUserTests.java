package com.example.sarahpadlipsky.iou;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for CurrentUser.
 * @author paulaledgerwood
 * @author sarahpadlipsky
 * @version November 2, 2016
 */
public class CurrentUserTests {

  User user = new User();
  CurrentUser currUser = new CurrentUser();

  @Test
  public void testCurrentUser() {
    user.setName("Jane");
    currUser.setCurrentUser(user);
    assertEquals(user, currUser.getCurrentUser());
  }


  @Test
  public void testMoneySpent() {
    user.setMoneySpent(1000);
    currUser.setCurrentUser(user);
    User temp = currUser.getCurrentUser();
    assertEquals(1000, temp.getMoneySpent(), 0);
  }

  @Test
  public void testMoneyOwed() {
    user.setMoneyOwed(0);
    currUser.setCurrentUser(user);
    User temp = currUser.getCurrentUser();
    assertEquals(0, temp.getMoneyOwed(), 0);
  }

  public static void main(String [] args) {
    org.junit.runner.JUnitCore.main("CurrentUserTests");
  }
}