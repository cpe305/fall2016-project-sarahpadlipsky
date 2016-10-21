package com.example.sarahpadlipsky.iou;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GroupTests {
	Group group = new Group();

	/* Test all the get and set methods in Group.java */
    @Test
    public void testGroupName() {
    	System.out.println("----------------- TESTING ---------------------");
        System.out.println("Testing the group's name");
    	group.setName("Joe");
        assertEquals("Joe", group.getName());
    }

	@Test
    public void testGroupName1() {
    	group.setName("");
        assertEquals("", group.getName());
    }

	@Test
    public void testGroupName2() {
    	group.setName("Jane40");
        assertEquals("Jane40", group.getName());
    }

	@Test
    public void testMoneySpent() {
    	System.out.println("-----------------------------------------------");
        System.out.println("Testing the amount of money the group has paid");
    	group.setMoneySpent("139");
        assertEquals("139", group.getMoneySpent());
    }

	@Test
    public void testMoneySpent1() {
    	group.setMoneySpent("10.294");
        assertEquals("10.294", group.getMoneySpent());
    }

	@Test
    public void testMoneySpent2() {
    	group.setMoneySpent("-204");
        assertEquals("-204", group.getMoneySpent());
    }

    /*
    @Test
    public void testGroup() {
    	User user1 = new User();
    	User user2 = new User();
    	User user3 = new User();

    	RealmList<User> userGroup = new RealmList<User>();
    	userGroup.add(user1);
    	userGroup.add(user2);
    	userGroup.add(user3);


    	System.out.println("getting users = " + userGroup.getUsers());
        //assertEquals("-true", user.getIsCurrentUser());
    }
    */

    public static void main(String []args) {
    	org.junit.runner.JUnitCore.main("GroupTests");
    }
}
