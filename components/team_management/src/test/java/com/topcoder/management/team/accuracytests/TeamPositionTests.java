/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.TeamPosition;

import java.util.Map;

import junit.framework.TestCase;

/**
 * Accuracy test cases for the <code>TeamPosition</code> class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class TeamPositionTests extends TestCase {
    /**
     * Tests the constructor.
     */
    public void testCtor() {
        TeamPosition pos = new TeamPosition("description", true, 5, 50, "name", 123, false);

        assertEquals("the description should be 'description'", "description", pos.getDescription());
        assertTrue("filled should be true", pos.getFilled());
        assertEquals("member resource ID should be 5", 5, pos.getMemberResourceId());
        assertEquals("payment percentage should be 50", 50, pos.getPaymentPercentage());
        assertEquals("name should be 'name'", "name", pos.getName());
        assertEquals("position ID should be 123", 123, pos.getPositionId());
        assertFalse("published should be false", pos.getPublished());
    }

    /**
     * Tests the <code>getProperty</code> and <code>setProperty</code> methods.
     */
    public void testGetSetProperty() {
        TeamPosition pos = new TeamPosition();

        assertNull("property should initially be null", pos.getProperty("property"));
        pos.setProperty("property", "value");
        assertEquals("property should now equal 'value'", "value", pos.getProperty("property"));
        pos.setProperty("property", null);
        assertNull("property should now be null again", pos.getProperty("property"));
    }

    /**
     * Tests the <code>getAllProperties</code> method.
     */
    public void testGetAllProperties() {
        TeamPosition pos = new TeamPosition();

        assertEquals("there should initially be no properties", 0, pos.getAllProperties().size());

        pos.setProperty("prop1", "value1");
        pos.setProperty("prop2", "value2");

        Map props = pos.getAllProperties();

        assertEquals("there should now be 2 properties", 2, props.size());
        assertEquals("prop1 should have value 'value1'", "value1", props.get("prop1"));
        assertEquals("prop2 should have value 'value2'", "value2", props.get("prop2"));

        pos.setProperty("prop3", "value3");
        assertEquals("there should still be 2 properties (due to the shallow copy)", 2, props.size());
    }

    /**
     * Tests the <code>getDescription</code> and <code>setDescription</code> methods.
     */
    public void testGetSetDescription() {
        TeamPosition pos = new TeamPosition();

        assertNull("the description should initially be null", pos.getDescription());
        pos.setDescription("description");
        assertEquals("the description should now be 'description'", "description", pos.getDescription());
    }

    /**
     * Tests the <code>getFilled</code> and <code>setFilled</code> methods.
     */
    public void testGetSetFilled() {
        TeamPosition pos = new TeamPosition();

        assertFalse("filled should initially be false", pos.getFilled());
        pos.setFilled(true);
        assertTrue("filled should now be true", pos.getFilled());
    }

    /**
     * Tests the <code>getMemberResourceId</code> and <code>setMemberResourceId</code> methods.
     */
    public void testGetSetMemberResourceId() {
        TeamPosition pos = new TeamPosition();

        assertEquals("the member resource ID should initially be -1", -1, pos.getMemberResourceId());
        pos.setMemberResourceId(15);
        assertEquals("the member resource ID should now be 15", 15, pos.getMemberResourceId());
    }

    /**
     * Tests the <code>getName</code> and <code>setName</code> methods.
     */
    public void testGetSetName() {
        TeamPosition pos = new TeamPosition();

        assertNull("the name should initially be null", pos.getName());
        pos.setName("name");
        assertEquals("the name should now be 'name'", "name", pos.getName());
    }

    /**
     * Tests the <code>getPaymentPercentage</code> and <code>setPaymentPercentage</code> methods.
     */
    public void testGetSetPaymentPercentage() {
        TeamPosition pos = new TeamPosition();

        // http://forums.topcoder.com/?module=Thread&threadID=574474&start=0
        assertEquals("the payment percentage should initially be -1", -1, pos.getPaymentPercentage());
        pos.setPaymentPercentage(15);
        assertEquals("the payment percentage should now be 15", 15, pos.getPaymentPercentage());
    }

    /**
     * Tests the <code>getPositionId</code> and <code>setPositionId</code> methods.
     */
    public void testGetSetPositionId() {
        TeamPosition pos = new TeamPosition();

        assertEquals("the position ID should initially be -1", -1, pos.getPositionId());
        pos.setPositionId(15);
        assertEquals("the position ID should now be 15", 15, pos.getPositionId());
    }

    /**
     * Tests the <code>getPublished</code> and <code>setPublished</code> methods.
     */
    public void testGetSetPublished() {
        TeamPosition pos = new TeamPosition();

        assertFalse("published should initially be false", pos.getPublished());
        pos.setPublished(true);
        assertTrue("published should now be true", pos.getPublished());
    }
}
