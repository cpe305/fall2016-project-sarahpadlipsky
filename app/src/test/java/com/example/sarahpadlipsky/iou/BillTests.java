package com.example.sarahpadlipsky.iou;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Bill.
 * @author sarahpadlipsky
 * @version December 2, 2016
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
  public void testBillSendUser() {
    User user = new User();
    bill.setSendUser(user);
    assertEquals(user, bill.getSendUser());
  }

  @Test
  public void testBillReceiveUser() {
    User user = new User();
    bill.setReceiveUser(user);
    assertEquals(user, bill.getReceiveUser());
  }

  @Test
  public void testIfPayBackBill() {
    bill.setPayBackBill(true);
    assertEquals(true, bill.getPayBackBill());
  }

  public static void main(String [] args) {
    org.junit.runner.JUnitCore.main("BillTests");
  }
}
