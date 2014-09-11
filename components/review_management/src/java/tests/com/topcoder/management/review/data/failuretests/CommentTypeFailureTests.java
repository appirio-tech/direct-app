/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.failuretests;

import com.topcoder.management.review.data.CommentType;

import junit.framework.TestCase;
/**
 * <p>
 * The failure tests for CommentType.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
public class CommentTypeFailureTests extends TestCase {
    /**
     * The CommentType instance used to test.
     */
    private CommentType commentType;

    /**
     * The setUp of the unit test.
     */
    protected void setUp() {
        commentType = new CommentType();
    }
    /**
     * The failure test of the constructor with id <= 0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidArgument1() {
        try {
            new CommentType(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with id <= 0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidArgument2() {
        try {
            new CommentType(0, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the constructor with name is null,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testctorWithInvalidArgument3() {
        try {
            new CommentType(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the setId method with id <= 0,
     * IllegalArgumentException should be thrown.
     *
     */
    public void testsetIdWithInvalidId() {
        try {
            commentType.setId(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
}
