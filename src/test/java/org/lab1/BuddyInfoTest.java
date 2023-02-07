package org.lab1;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BuddyInfoTest {

    public BuddyInfo Bob;

    @Before
    public void setUp(){
        Bob = new BuddyInfo("Bob", "613-842-1395");
    }

    @Test
    public void getNameTest(){
        assertTrue(Bob.getName().contentEquals("Bob"));
    }

    @Test
    public void setNameTest(){
        Bob.setName("Bobetta");
        assertTrue(Bob.getName().contentEquals("Bobetta"));
    }

    @Test
    public void setPhoneNoTest(){
        Bob.setPhoneNo("None");
        assertTrue(Bob.getPhoneNo().contentEquals("None"));
    }

    @Test
    public void getPhoneNoTest(){
        assertTrue(Bob.getPhoneNo().contentEquals("613-842-1395"));
    }
}