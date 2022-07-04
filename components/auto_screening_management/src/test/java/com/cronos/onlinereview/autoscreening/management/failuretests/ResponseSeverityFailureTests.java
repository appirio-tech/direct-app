/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.util.Random;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.management.ResponseSeverity;


/**
 * <p>
 * Failure test for ResponseSeverity.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ResponseSeverityFailureTests extends TestCase {
    /** An instance of ResponseSeverity for testing. */
    private ResponseSeverity rs;

    /**
     * <p>
     * Set up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        this.rs = new ResponseSeverity();
    }

    /**
     * Failure test for setId(long). Inputs: zero id. Expectation: IllegalArgumentException should be thrown.
     */
    public void testSetIdZero() {
        try {
            rs.setId(0L);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setId(long). Inputs: negative id. Expectation: IllegalArgumentException should be thrown.
     */
    public void testSetIdNegative() {
        int i = new Random().nextInt(Integer.MAX_VALUE) + 1;

        try {
            rs.setId(-1 * i);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setName(String). Inputs: null name. Expectation: IllegalArgumentException should be thrown.
     */
    public void testSetNameNull() {
        try {
            rs.setName(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setName(String). Inputs: empty name. Expectation: IllegalArgumentException should be thrown.
     */
    public void testSetNameEmpty() {
        try {
            rs.setName("    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
