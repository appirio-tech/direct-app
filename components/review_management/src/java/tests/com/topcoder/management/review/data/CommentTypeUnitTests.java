/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

import junit.framework.TestCase;

/**
 * Unit tests for <code>CommentType</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class CommentTypeUnitTests extends TestCase {

    /**
     * The <code>CommentType</code> instance used to test against.
     */
    private CommentType type = null;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        type = new CommentType(20060624, "CommentType");
    }

    /**
     * Test whether <code>CommentType</code> class implements the <code>Serializable</code> interface.
     */
    public void testImplements() {
        assertTrue("CommentType should implement Serializable interface", type instanceof Serializable);
    }

    /**
     * Test the constructor <code>CommentType()</code>, all fields should have their default unassigned values.
     */
    public void testConstructorWithNoArgu() {
        CommentType ct = new CommentType();
        assertNotNull("CommentType instance should be created", ct);
        assertTrue("id field should be -1", ct.getId() == -1);
        assertNull("name field should be null", ct.getName());
    }

    /**
     * Test the constructor <code>CommentType(long)</code> with negative id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithNegativeId() {
        try {
            new CommentType(-20060624);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>CommentType(long)</code> with zero id, <code>IllegalArgumentException</code>
     * should be thrown.
     */
    public void testConstructorWithZeroId() {
        try {
            new CommentType(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>CommentType(long)</code> with positive id, instance of <code>CommentType</code>
     * should be created.
     */
    public void testConstructorWithPositiveId() {
        CommentType ct = new CommentType(2006);
        assertNotNull("CommentType instance should be created", ct);
        assertTrue("id field should be 2006", ct.getId() == 2006);
        assertNull("name field should be null", ct.getName());
    }

    /**
     * Test the constructor <code>CommentType(long, String)</code> with negative id and non-null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithNegativeIdAndNonNullName() {
        try {
            new CommentType(-20060624, "type");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>CommentType(long, String)</code> with zero id and non-null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithZeroIdAndNonNullName() {
        try {
            new CommentType(0, "type");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>CommentType(long, String)</code> with positive id and null name,
     * <code>IllegalArgumentException</code> should be thrown.
     */
    public void testConstructorWithPositiveIdAndNullName() {
        try {
            new CommentType(2006, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>CommentType(long, String)</code> with positive id and non-null name, instance of
     * <code>CommentType</code> should be created.
     */
    public void testConstructorWithPositiveIdAndNonNullName() {
        CommentType ct1 = new CommentType(2006, "type");
        assertNotNull("CommentType instance should be created", ct1);
        assertTrue("id field should be 2006", ct1.getId() == 2006);
        assertTrue("name field should be 'type'", ct1.getName().equals("type"));

        // empty name is acceptable
        CommentType ct2 = new CommentType(2006, "");
        assertNotNull("CommentType instance should be created", ct2);
        assertTrue("id field should be 2006", ct2.getId() == 2006);
        assertTrue("name field should be ''", ct2.getName().equals(""));

        // all whitespace name is acceptable
        CommentType ct3 = new CommentType(2006, "   ");
        assertNotNull("CommentType instance should be created", ct3);
        assertTrue("id field should be 2006", ct3.getId() == 2006);
        assertTrue("name field should be '   '", ct3.getName().equals("   "));
    }

    /**
     * Test the method <code>setId(long)</code> with negative id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            type.setId(-2006);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with zero id, <code>IllegalArgumentException</code> should be
     * thrown.
     */
    public void testSetIdWithZeroId() {
        try {
            type.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with positive id, the id field should be set successfully.
     */
    public void testSetIdWithPositiveId() {
        type.setId(2006);
        assertTrue("id field should be 2006", type.getId() == 2006);
    }

    /**
     * Test the method <code>getId()</code>, the id value should be returned successfully.
     */
    public void testGetId() {
        assertTrue("getId method should return 20060624", type.getId() == 20060624);
    }

    /**
     * Test the method <code>resetId()</code>, the id field should be set to -1.
     */
    public void testResetId() {
        type.resetId();
        assertTrue("id field should be -1", type.getId() == -1);
    }

    /**
     * Test the method <code>setName(String)</code>, the name field should be set successfully.
     */
    public void testSetName() {
        type.setName("other name");
        assertTrue("name field should be 'other name'", type.getName().equals("other name"));

        // null name is acceptable
        type.setName(null);
        assertNull("name field should be null", type.getName());

        // empty name is acceptable
        type.setName("");
        assertTrue("name field should be ''", type.getName().equals(""));

        // all whitespace name is acceptable
        type.setName("   ");
        assertTrue("name field should be '   '", type.getName().equals("   "));
    }

    /**
     * Test the method <code>getName()</code>, the name value should be returned successfully.
     */
    public void testGetName() {
        assertTrue("getName method should return 'CommentType'", type.getName().equals("CommentType"));
    }

    /**
     * Test the method <code>resetName()</code>, the name field should be set to null.
     */
    public void testResetName() {
        type.resetName();
        assertNull("name field should be null", type.getName());
    }
}
