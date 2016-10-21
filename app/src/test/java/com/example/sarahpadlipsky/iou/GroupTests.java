package com.example.sarahpadlipsky.iou;

import org.junit.Before;
import org.junit.Test;

import io.realm.RealmList;

import static org.junit.Assert.*;

/**
 * Test class for Group.
 * @author sonianara
 * @author sarahpadlipsky
 * @version October 20, 2016
 */
public class GroupTests {

    Group group;

    @Before
    public void instanceGroup() {
        group = new Group();
    }

	/* Test all the get and set methods in Group.java */
    @Test
    public void testGroupName() {
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
    	group.setMoneySpent(139);
        assertEquals(139, group.getMoneySpent(),0);
    }

	@Test
    public void testMoneySpent1() {
    	group.setMoneySpent(10.294);
        assertEquals(10.294, group.getMoneySpent(), 0);
    }

	@Test
    public void testMoneySpent2() {
    	group.setMoneySpent(-204);
        assertEquals(-204, group.getMoneySpent(), 0);
    }


    @Test
    public void testGroup() {
    	User user1 = new User();
    	User user2 = new User();
    	User user3 = new User();

    	RealmList<User> userGroup = new RealmList<User>();
    	userGroup.add(user1);
    	userGroup.add(user2);
    	userGroup.add(user3);

        group.addUser(user1);
        group.addUser(user2);
        group.addUser(user3);

        assertEquals(group.getUsers(), userGroup);
    }

    @Test
    public void testToString() {
        String name = "Sarah";
        group.setName(name);
        assertEquals(group.toString(), name);
    }


    public static void main(String [] args) {
    	org.junit.runner.JUnitCore.main("GroupTests");
    }
}
