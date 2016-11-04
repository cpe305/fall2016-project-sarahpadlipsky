package com.example.sarahpadlipsky.iou;

import org.junit.Test;

import java.util.Arrays;

import io.realm.RealmList;

import static org.junit.Assert.*;

/**
 * Test class for CurrentUser.
 * @author paulaledgerwood
 * @author sarahpadlipsky
 * @version November 2, 2016
 */
public class UserTests {

    User user = new User();
    CurrentUser currUser = new CurrentUser();

    @Test
    public void testCurrentUser1() {
    	user.setName("Jane");
    	currUser.setCurrentUser(user);
        assertEquals(user, currUser.getCurrentUser());
    }

	@Test
    public void testCurrentUser2() {
    	user.setName("Paula");
    	currUser.setCurrentUser(user);
    	User temp = currUser.getCurrentUser();
        assertEquals("Paula", temp.getName());
    }

	@Test
    public void testCurrentUser3() {
    	user.setName("");
    	currUser.setCurrentUser(user);
    	User temp = currUser.getCurrentUser();
    	temp.setName("Paula");
        assertEquals("Paula", temp.getName());
    }

	@Test
    public void testMoneySpent() {
    	user.setMoneySpent(1000);
    	currUser.setCurrentUser(user);
    	User temp = currUser.getCurrentUser();
        assertEquals(1000, temp.getMoneySpent());
    }

	@Test
    public void testMoneySpent1() {
    	user.setMoneySpent(-1000);
    	currUser.setCurrentUser(user);
    	User temp = currUser.getCurrentUser();
        assertEquals(-1000, temp.getMoneySpent());
    }

	@Test
    public void testMoneyOwed() {
    	user.setMoneyOwed(0);
    	currUser.setCurrentUser(user);
    	User temp = currUser.getCurrentUser();
        assertEquals(0, temp.getMoneyOwed());
    }

	@Test
    public void testMoneyOwed1() {
    	user.setMoneyOwed(500.00);
    	User temp = currUser.getCurrentUser();
        assertFalse(400, temp.getMoneyOwed());
    }

    public static void main(String [] args) {
        org.junit.runner.JUnitCore.main("CurrentUserTests");
    }
}
