/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import com.topcoder.management.project.ProjectType;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ProjectType</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ProjectTypeFailureTests extends TestCase {

    /**
     * The <code>ProjectType</code> instance used to test against.
     */
    private ProjectType pt = new ProjectType(2006, "name", "desc");

    /**
     * Test the constructor <code>ProjectType(long, String)</code> with negative id, IllegalArgumentException should
     * be thrown.
     */
    public void testConstructorWithNegativeId1() {
        try {
            new ProjectType(-1, "name");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String)</code> with zero id, IllegalArgumentException should be
     * thrown.
     */
    public void testConstructorWithZeroId1() {
        try {
            new ProjectType(0, "name");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String)</code> with null name, IllegalArgumentException should be
     * thrown.
     */
    public void testConstructorWithNullName1() {
        try {
            new ProjectType(1, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String)</code> with empty name, IllegalArgumentException should be
     * thrown.
     */
    public void testConstructorWithEmptyName1() {
        try {
            new ProjectType(1, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String, String)</code> with negative id, IllegalArgumentException
     * should be thrown.
     */
    public void testConstructorWithNegativeId2() {
        try {
            new ProjectType(-1, "name", "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String, String)</code> with zero id, IllegalArgumentException
     * should be thrown.
     */
    public void testConstructorWithZeroId2() {
        try {
            new ProjectType(0, "name", "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String, String)</code> with null name, IllegalArgumentException
     * should be thrown.
     */
    public void testConstructorWithNullName2() {
        try {
            new ProjectType(1, null, "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String, String)</code> with empty name, IllegalArgumentException
     * should be thrown.
     */
    public void testConstructorWithEmptyName2() {
        try {
            new ProjectType(1, " ", "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectType(long, String, String)</code> with null description,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullDescription() {
        try {
            new ProjectType(1, "name", null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with negative id, IllegalArgumentException should be thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            pt.setId(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with zero id, IllegalArgumentException should be thrown.
     */
    public void testSetIdWithZeroId() {
        try {
            pt.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setName(String)</code> with null name, IllegalArgumentException should be thrown.
     */
    public void testSetNameWithNullName() {
        try {
            pt.setName(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setName(String)</code> with empty name, IllegalArgumentException should be thrown.
     */
    public void testSetNameWithEmptyName() {
        try {
            pt.setName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setDescription(String)</code> with null description, IllegalArgumentException should be
     * thrown.
     */
    public void testSetDescriptionWithNullName() {
        try {
            pt.setDescription(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
