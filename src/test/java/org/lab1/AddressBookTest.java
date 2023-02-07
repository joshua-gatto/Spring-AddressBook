package org.lab1;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AddressBookTest {

    public AddressBook buddies;

    @Before
    public void init(){
        this.buddies = new AddressBook();
        BuddyInfo Alice = new BuddyInfo("Alice", "613-499-3231");
        BuddyInfo Bob = new BuddyInfo("Bob", "613-842-1395");
        BuddyInfo Charlie = new BuddyInfo("Charlie", "613-291-9950");
        buddies.addBuddy(Alice);
        buddies.addBuddy(Bob);
        buddies.addBuddy(Charlie);
    }

    @Test
    public void addBuddyTest(){
        BuddyInfo Dan = new BuddyInfo("Dan", "613-750-8990");
        buddies.addBuddy(Dan);
        assertTrue(buddies.containsBuddy(Dan));
    }

    @Test
    public void removeBuddyTest(){
        BuddyInfo Alice = buddies.getBuddy(0);
        assertTrue(buddies.removeBuddy(0));
        assertFalse(buddies.containsBuddy(Alice));
    }
}