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
    public void testUserName1() {
    	user.setName("");
        assertEquals("", user.getName());
    }

	@Test
    public void testUserName2() {
    	user.setName("Joe5");
        assertEquals("Joe5", user.getName());
    }

	@Test
    public void testMoneySpent() {
    	user.setMoneySpent(500);
        assertEquals(500, user.getMoneySpent(), 0);
    }

	@Test
    public void testMoneySpent1() {
    	user.setMoneySpent(30.555);
        assertEquals(30.555, user.getMoneySpent(), 0);
    }

	@Test
    public void testMoneySpent2() {
    	user.setMoneySpent(-200);
        assertEquals(-200, user.getMoneySpent(), 0);
    }

	@Test
    public void testMoneyOwed() {
    	user.setMoneyOwed(300);
        assertEquals(300, user.getMoneyOwed(), 0);
    }

	@Test
    public void testMoneyOwed1() {
    	user.setMoneyOwed(20.333);
        assertEquals(20.333, user.getMoneyOwed(), 0);
    }

	@Test
    public void testMoneyOwed2() {
    	user.setMoneyOwed(-100);
        assertEquals(-100, user.getMoneyOwed(), 0);
    }

	@Test
    public void testCurrentUser() {
    	user.setIsCurrentUser(true);
        assertEquals(true, user.getIsCurrentUser());
    }

    @Test
    public void testCurrentUser1() {
    	user.setIsCurrentUser(false);
        assertEquals(false, user.getIsCurrentUser());
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

    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("UserTests");
    }
}
