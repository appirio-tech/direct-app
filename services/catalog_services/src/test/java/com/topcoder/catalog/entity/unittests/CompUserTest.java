/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.Component;
import junit.framework.TestCase;

/**
 * <p>Unit test case for {@link CompUser}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CompUserTest extends TestCase {
    /**
     * <p>Represents CompUser instance for testing.</p>
     */
    private CompUser compUser;

    /**
     * <p>Creates a new instance of CompUser.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        compUser = new CompUser();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        compUser = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>CompUser()</code> constructor.</p>
     */
    public void testCompUser() {
        assertNotNull("Unable to instantiate CompUser", compUser);
    }

    /**
     * <p>Tests <code>getUserId()</code> and
     * <code>setUserId()</code> methods for accuracy.</p>
     */
    public void testGetSetUserId() {
        assertNull("Incorrect default userId value", compUser.getUserId());
        Long value = 60129542159L;
        // set a userId
        compUser.setUserId(value);
        assertEquals("Incorrect userId value after setting a new one",
            value, compUser.getUserId());
    }

    /**
     * <p>Tests <code>setUserId(null)</code>.</p>
     */
    public void testUserIdAllowsNull() {
        // set a userId
        // set null
        try {
            compUser.setUserId(null);
            assertNull("Field 'userId' should contain 'null' value",
                compUser.getUserId());
        } catch (IllegalArgumentException e) {
            fail("Field 'userId' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getComponent()</code> and
     * <code>setComponent()</code> methods for accuracy.</p>
     */
    public void testGetSetComponent() {
        assertNull("Incorrect default component value", compUser.getComponent());
        Component value = new Component();
        // set a component
        compUser.setComponent(value);
        assertEquals("Incorrect component value after setting a new one",
            value, compUser.getComponent());
    }

    /**
     * <p>Tests <code>setComponent(null)</code>.</p>
     */
    public void testComponentAllowsNull() {
        // set a component
        // set null
        try {
            compUser.setComponent(null);
            assertNull("Field 'component' should contain 'null' value",
                compUser.getComponent());
        } catch (IllegalArgumentException e) {
            fail("Field 'component' should allow null values");
        }
    }
}
