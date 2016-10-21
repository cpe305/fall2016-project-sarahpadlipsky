package com.example.sarahpadlipsky.iou;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UserTests {

    User user = new User();
	
    /* Testing the user's name */
    @Test
    public void testUserName() {
        System.out.println("----------------- TESTING ---------------------");
        System.out.println("Testing the user's name");
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
        System.out.println("-----------------------------------------------");
        System.out.println("Testing the amount of money the user has paid");
    	user.setMoneySpent("500");
        assertEquals("500", user.getMoneySpent());
    }

	@Test
    public void testMoneySpent1() {
    	user.setMoneySpent("30.555");
        assertEquals("30.555", user.getMoneySpent());
    }

	@Test
    public void testMoneySpent2() {
    	user.setMoneySpent("-200");
        assertEquals("-200", user.getMoneySpent());
    }

	@Test
    public void testMoneyOwed() {
        System.out.println("-----------------------------------------------");
        System.out.println("Testing the amount of money the user still owes");
    	user.setMoneyOwed("300");
        assertEquals("300", user.getMoneyOwed());
    }

	@Test
    public void testMoneyOwed1() {
    	user.setMoneyOwed("20.333");
        assertEquals("20.333", user.getMoneyOwed());
    }

	@Test
    public void testMoneyOwed2() {
    	user.setMoneyOwed("-100");
        assertEquals("-100", user.getMoneyOwed());
    }

	@Test
    public void testCurrentUser() {
        System.out.println("-----------------------------------------------");
        System.out.println("Testing the current user");
    	user.setIsCurrentUser("true");
        assertEquals("true", user.getIsCurrentUser());
    }

    @Test
    public void testCurrentUser1() {
    	user.setIsCurrentUser("false");
        assertEquals("false", user.getIsCurrentUser());
    
    }
    /*
	@Test
    public void testUserGroup() {
    	Group testGroup1 = new Group();
    	Group testGroup2 = new Group();
    	Group testGroup3 = new Group();

    	user.addGroup(testGroup1);
    	user.addGroup(testGroup2);
    	user.addGroup(testGroup3);

    	System.out.println("getting groups = " + user.getGroups());
        //assertEquals("-true", user.getIsCurrentUser());
    }
    */

    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("UserTests");
    }
}
