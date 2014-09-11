/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompVersion;
import junit.framework.TestCase;

/**
 * <p>Unit test case for {@link CompForum}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CompForumTest extends TestCase {
    /**
     * <p>Represents CompForum instance for testing.</p>
     */
    private CompForum compForum;

    /**
     * <p>Creates a new instance of CompForum.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        compForum = new CompForum();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        compForum = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>CompForum()</code> constructor.</p>
     */
    public void testCompForum() {
        assertNotNull("Unable to instantiate CompForum", compForum);
    }

    /**
     * <p>Tests <code>getJiveCategoryId()</code> and
     * <code>setJiveCategoryId()</code> methods for accuracy.</p>
     */
    public void testGetSetJiveCategoryId() {
        assertNull("Incorrect default jiveCategoryId value", compForum.getJiveCategoryId());
        Long value = 25769803783L;
        // set a jiveCategoryId
        compForum.setJiveCategoryId(value);
        assertEquals("Incorrect jiveCategoryId value after setting a new one",
            value, compForum.getJiveCategoryId());
    }

    /**
     * <p>Tests <code>setJiveCategoryId(null)</code>.</p>
     */
    public void testJiveCategoryIdAllowsNull() {
        // set a jiveCategoryId
        // set null
        try {
            compForum.setJiveCategoryId(null);
            assertNull("Field 'jiveCategoryId' should contain 'null' value",
                compForum.getJiveCategoryId());
        } catch (IllegalArgumentException e) {
            fail("Field 'jiveCategoryId' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCompVersion()</code> and
     * <code>setCompVersion()</code> methods for accuracy.</p>
     */
    public void testGetSetVersion() {
        assertNull("Incorrect default version value", compForum.getCompVersion());
        CompVersion value = new CompVersion();
        // set a version
        compForum.setCompVersion(value);
        assertEquals("Incorrect version value after setting a new one",
            value, compForum.getCompVersion());
    }

    /**
     * <p>Tests <code>setCompVersion(null)</code>.</p>
     */
    public void testVersionAllowsNull() {
        // set a version
        // set null
        try {
            compForum.setCompVersion(null);
            assertNull("Field 'version' should contain 'null' value",
                compForum.getCompVersion());
        } catch (IllegalArgumentException e) {
            fail("Field 'version' should allow null values");
        }
    }
}
