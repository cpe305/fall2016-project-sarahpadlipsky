package com.example.sarahpadlipsky.iou;

import org.junit.Test;

import java.util.Arrays;

import io.realm.RealmList;

import static org.junit.Assert.*;

/**
 * Test class for User.
 * @author sonianara
 * @author sarahpadlipsky
 * @version October 20, 2016
 */
public class UserTests {

    User user = new User();
	
    @Test
    public void testUserName() {
    	user.setName("Jane");
        assertEquals("Jane", user.getName());
    }

	@Test
    public void testUserNameEmptyString() {
    	user.setName("");
        assertEquals("", user.getName());
    }

	@Test
    public void testMoneySpentRoundNumber() {
    	user.setMoneySpent(500);
        assertEquals(500, user.getMoneySpent(), 0);
    }

	@Test
    public void testMoneySpentDecimal() {
    	user.setMoneySpent(30.555);
        assertEquals(30.555, user.getMoneySpent(), 0);
    }

	@Test
    public void testMoneyOwedRoundNumber() {
    	user.setMoneyOwed(300);
        assertEquals(300, user.getMoneyOwed(), 0);
    }

	@Test
    public void testMoneyOwedDecimal() {
    	user.setMoneyOwed(20.333);
        assertEquals(20.333, user.getMoneyOwed(), 0);
    }

	@Test
    public void testUserGroup() {
    	Group testGroup1 = new Group();
        Group testGroup2 = new Group();

        RealmList<Group> testList = new RealmList<>();
        testList.add(testGroup1);
        testList.add(testGroup2);

    	user.addGroup(testGroup1);
        user.addGroup(testGroup2);

    	assertEquals(testList, user.getGroups());
    }

  @Test
  public void testToString() {
    String name = "name";
    user.setName(name);

    assertEquals(user.toString(), name);
  }

  @Test
  public void testEmail() {
    String email = "testing@gmail.com";
    user.setEmail(email);
    assertEquals(email, user.getEmail());
  }

  @Test
  public void testUserBills() {
    Bill bill1 = new Bill();
    Bill bill2 = new Bill();

    RealmList<Bill> testList = new RealmList<>();
    testList.add(bill1);
    testList.add(bill2);

    user.addBill(bill1);
    user.addBill(bill2);

    assertEquals(testList, user.getBills());
  }


    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("UserTests");
    }
}
