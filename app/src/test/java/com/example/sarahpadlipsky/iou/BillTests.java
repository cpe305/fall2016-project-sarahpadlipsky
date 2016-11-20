package com.example.sarahpadlipsky.iou;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Bill.
 * @author sarahpadlipsky
 * @version November 19, 2016
 */

public class BillTests {

  private Bill bill;

  @Before
  public void instanceGroup() {
    bill = new Bill();
  }

  @Test
  public void testBillNameAndToString() {
    String name = "Name Bill";
    bill.setName(name);
    assertEquals(bill.toString(), bill.getName());
  }

  @Test
  public void testBillAmount() {
    double amount = 5.63;
    bill.setAmount(amount);
    assertEquals(amount, bill.getAmount(), 0);
  }

  @Test
  public void testBillDescription() {
    String description = "This is the description";
    bill.setDescription(description);
    assertEquals(description, bill.getDescription());
  }

  @Test
  public void testBillID() {
    String id = "5";
    bill.setId(id);
    assertEquals(id, bill.getId());
  }

  @Test
  public void testBillUser() {
    User user = new User();
    bill.setUser(user);
    assertEquals(user, bill.getUser());
  }

  public static void main(String [] args) {
    org.junit.runner.JUnitCore.main("BillTests");
  }
}
