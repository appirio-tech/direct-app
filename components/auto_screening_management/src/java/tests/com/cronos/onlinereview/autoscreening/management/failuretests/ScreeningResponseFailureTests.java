/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.util.Random;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.management.ScreeningResponse;


/**
 * <p>
 * Failure test for ScreeningResponse.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ScreeningResponseFailureTests extends TestCase {
    /** An instance of ScreeningResponse for testing. */
    private ScreeningResponse sr;

    /**
     * <p>
     * Set up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        this.sr = new ScreeningResponse();
    }

    /**
     * Failure test for setId(long). Inputs: zero id. Expectation: IllegalArgumentException should be thrown.
     */
    public void testSetIdZero() {
        try {
            sr.setId(0L);
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
            sr.setId(-1 * i);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setResponseSeverity(ResponseSeverity). Inputs: null ResponseSeverity. Expectation:
     * IllegalArgumentException should be thrown.
     */
    public void testSetResponseSeverityNull() {
        try {
            sr.setResponseSeverity(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setResponseCode(String). Inputs: null responseCode. Expectation: IllegalArgumentException
     * should be thrown.
     */
    public void testSetResponseCodeNull() {
        try {
            sr.setResponseCode(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setResponseCode(String). Inputs: empty responseCode. Expectation: IllegalArgumentException
     * should be thrown.
     */
    public void testSetResponseCodeEmpty() {
        try {
            sr.setResponseCode("     ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setResponseText(String). Inputs: null responseText. Expectation: IllegalArgumentException
     * should be thrown.
     */
    public void testSetResponseTextNull() {
        try {
            sr.setResponseText(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setResponseText(String). Inputs: empty responseText. Expectation: IllegalArgumentException
     * should be thrown.
     */
    public void testSetResponseTextEmpty() {
        try {
            sr.setResponseText("    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
