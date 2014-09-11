/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import com.topcoder.management.project.ProjectStatus;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ProjectStatus</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ProjectStatusFailureTests extends TestCase {

    /**
     * The <code>ProjectStatus</code> instance used to test against.
     */
    private ProjectStatus ps = new ProjectStatus(2006, "name", "desc");

    /**
     * Test the constructor <code>ProjectStatus(long, String)</code> with negative id, IllegalArgumentException should
     * be thrown.
     */
    public void testConstructorWithNegativeId1() {
        try {
            new ProjectStatus(-1, "name");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String)</code> with zero id, IllegalArgumentException should be
     * thrown.
     */
    public void testConstructorWithZeroId1() {
        try {
            new ProjectStatus(0, "name");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String)</code> with null name, IllegalArgumentException should
     * be thrown.
     */
    public void testConstructorWithNullName1() {
        try {
            new ProjectStatus(1, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String)</code> with empty name, IllegalArgumentException should
     * be thrown.
     */
    public void testConstructorWithEmptyName1() {
        try {
            new ProjectStatus(1, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String, String)</code> with negative id,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNegativeId2() {
        try {
            new ProjectStatus(-1, "name", "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String, String)</code> with zero id, IllegalArgumentException
     * should be thrown.
     */
    public void testConstructorWithZeroId2() {
        try {
            new ProjectStatus(0, "name", "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String, String)</code> with null name, IllegalArgumentException
     * should be thrown.
     */
    public void testConstructorWithNullName2() {
        try {
            new ProjectStatus(1, null, "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String, String)</code> with empty name, IllegalArgumentException
     * should be thrown.
     */
    public void testConstructorWithEmptyName2() {
        try {
            new ProjectStatus(1, " ", "desc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectStatus(long, String, String)</code> with null description,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullDescription() {
        try {
            new ProjectStatus(1, "name", null);
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
            ps.setId(-1);
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
            ps.setId(0);
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
            ps.setName(null);
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
            ps.setName(" ");
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
            ps.setDescription(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
