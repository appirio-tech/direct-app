/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.Component;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Unit test case for {@link CompClient}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CompClientTest extends TestCase {
    /**
     * <p>Represents CompClient instance for testing.</p>
     */
    private CompClient compClient;

    /**
     * <p>Creates a new instance of CompClient.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        compClient = new CompClient();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        compClient = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>CompClient()</code> constructor.</p>
     */
    public void testCompClient() {
        assertNotNull("Unable to instantiate CompClient", compClient);
    }

    /**
     * <p>Tests <code>getClientId()</code> and
     * <code>setClientId()</code> methods for accuracy.</p>
     */
    public void testGetSetClientId() {
        assertNull("Incorrect default clientId value", compClient.getClientId());
        Long value = 17179869189L;
        // set a clientId
        compClient.setClientId(value);
        assertEquals("Incorrect clientId value after setting a new one",
            value, compClient.getClientId());
    }

    /**
     * <p>Tests <code>setClientId(null)</code>.</p>
     */
    public void testClientIdAllowsNull() {
        // set a clientId
        // set null
        try {
            compClient.setClientId(null);
            assertNull("Field 'clientId' should contain 'null' value",
                compClient.getClientId());
        } catch (IllegalArgumentException e) {
            fail("Field 'clientId' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getComponent()</code> and
     * <code>setComponent()</code> methods for accuracy.</p>
     */
    public void testGetSetComponent() {
        assertNull("Incorrect default component value", compClient.getComponent());
        Component value = new Component();
        // set a component
        compClient.setComponent(value);
        assertEquals("Incorrect component value after setting a new one",
            value, compClient.getComponent());
    }

    /**
     * <p>Tests <code>setComponent(null)</code>.</p>
     */
    public void testComponentAllowsNull() {
        // set a component
        // set null
        try {
            compClient.setComponent(null);
            assertNull("Field 'component' should contain 'null' value",
                compClient.getComponent());
        } catch (IllegalArgumentException e) {
            fail("Field 'component' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getUsers()</code> and
     * <code>setUsers()</code> methods for accuracy.</p>
     */
    public void testGetSetUsers() {
        assertNull("Incorrect default users value", compClient.getUsers());
        Set<Long> value = new HashSet<Long>();
        // set a users
        compClient.setUsers(value);
        assertEquals("Incorrect users value after setting a new one",
            value, compClient.getUsers());
    }

    /**
     * <p>Tests <code>setUsers(null)</code>.</p>
     */
    public void testUsersAllowsNull() {
        // set a users
        // set null
        try {
            compClient.setUsers(null);
            assertNull("Field 'users' should contain 'null' value",
                compClient.getUsers());
        } catch (IllegalArgumentException e) {
            fail("Field 'users' should allow null values");
        }
    }
}
