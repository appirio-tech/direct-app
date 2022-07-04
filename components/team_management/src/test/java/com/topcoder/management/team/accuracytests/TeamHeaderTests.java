/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.TeamHeader;

import java.util.Map;

import junit.framework.TestCase;

/**
 * Accuracy test cases for the <code>TeamHeader</code> class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class TeamHeaderTests extends TestCase {
    /**
     * Tests the constructor.
     */
    public void testCtor() {
        TeamHeader pos = new TeamHeader("name", true, 5, 10, 15, 20, "description");

        assertEquals("name should be 'name'", "name", pos.getName());
        assertTrue("finalized should be true", pos.getFinalized());
        assertEquals("project ID should be 5", 5, pos.getProjectId());
        assertEquals("team ID should be 5", 10, pos.getTeamId());
        assertEquals("captain resource ID should be 15", 15, pos.getCaptainResourceId());
        assertEquals("captain payment percentage should be 20", 20, pos.getCaptainPaymentPercentage());
        assertEquals("description should be 'description'", "description", pos.getDescription());
    }

    /**
     * Tests the <code>getProperty</code> and <code>setProperty</code> methods.
     */
    public void testGetSetProperty() {
        TeamHeader h = new TeamHeader();

        assertNull("property should initially be null", h.getProperty("property"));
        h.setProperty("property", "value");
        assertEquals("property should now equal 'value'", "value", h.getProperty("property"));
        h.setProperty("property", null);
        assertNull("property should now be null again", h.getProperty("property"));
    }

    /**
     * Tests the <code>getAllProperties</code> method.
     */
    public void testGetAllProperties() {
        TeamHeader h = new TeamHeader();

        assertEquals("there should initially be no properties", 0, h.getAllProperties().size());

        h.setProperty("prop1", "value1");
        h.setProperty("prop2", "value2");

        Map props = h.getAllProperties();

        assertEquals("there should now be 2 properties", 2, props.size());
        assertEquals("prop1 should have value 'value1'", "value1", props.get("prop1"));
        assertEquals("prop2 should have value 'value2'", "value2", props.get("prop2"));

        h.setProperty("prop3", "value3");
        assertEquals("there should still be 2 properties (due to the shallow copy)", 2, props.size());
    }

    /**
     * Tests the <code>getName</code> and <code>setName</code> methods.
     */
    public void testGetSetName() {
        TeamHeader h = new TeamHeader();

        assertNull("the name should initially be null", h.getName());
        h.setName("name");
        assertEquals("the name should now be 'name'", "name", h.getName());
    }

    /**
     * Tests the <code>getFinalized</code> and <code>setFinalized</code> methods.
     */
    public void testGetSetFinalized() {
        TeamHeader h = new TeamHeader();

        assertFalse("finalized should initially be false", h.getFinalized());
        h.setFinalized(true);
        assertTrue("finalized should now be true", h.getFinalized());
    }


    /**
     * Tests the <code>getProjectId</code> and <code>setProjectId</code> methods.
     */
    public void testGetSetProjectId() {
        TeamHeader h = new TeamHeader();

        assertEquals("the project ID should initially be -1", -1, h.getProjectId());
        h.setProjectId(15);
        assertEquals("the project ID should now be 15", 15, h.getProjectId());
    }


    /**
     * Tests the <code>getTeamId</code> and <code>setTeamId</code> methods.
     */
    public void testGetSetTeamId() {
        TeamHeader h = new TeamHeader();

        assertEquals("the team ID should initially be -1", -1, h.getTeamId());
        h.setTeamId(15);
        assertEquals("the team ID should now be 15", 15, h.getTeamId());
    }

    /**
     * Tests the <code>getCaptainResourceId</code> and <code>setCaptainResourceId</code> methods.
     */
    public void testGetSetCaptainResourceId() {
        TeamHeader h = new TeamHeader();

        assertEquals("the captain resource ID should initially be -1", -1, h.getCaptainResourceId());
        h.setCaptainResourceId(15);
        assertEquals("the captain resource ID should now be 15", 15, h.getCaptainResourceId());
    }

    /**
     * Tests the <code>getCaptainPaymentPercentage</code> and <code>setCaptainPaymentPercentage</code> methods.
     */
    public void testGetSetCaptainPaymentPercentage() {
        TeamHeader h = new TeamHeader();

        assertEquals("the captain resource ID should initially be -1", -1, h.getCaptainPaymentPercentage());
        h.setCaptainPaymentPercentage(15);
        assertEquals("the captain resource ID should now be 15", 15, h.getCaptainPaymentPercentage());
    }

    /**
     * Tests the <code>getDescription</code> and <code>setDescription</code> methods.
     */
    public void testGetSetDescription() {
        TeamHeader h = new TeamHeader();

        assertNull("the description should initially be null", h.getDescription());
        h.setDescription("description");
        assertEquals("the description should now be 'description'", "description", h.getDescription());
    }
}
