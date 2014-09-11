/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.util.Random;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.management.ScreeningResult;


/**
 * <p>
 * Failure test for ScreeningResult.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ScreeningResultFailureTests extends TestCase {
    /** An instance of ScreeningResult for testing. */
    private ScreeningResult sr;

    /**
     * <p>
     * Set up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        this.sr = new ScreeningResult();
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
     * Failure test for setScreeningResponse(ScreeningResponse). Inputs: null ScreeningResponse. Expectation:
     * IllegalArgumentException should be thrown.
     */
    public void testSetScreeningResponseNull() {
        try {
            sr.setScreeningResponse(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDynamicText(String). Inputs: null dynamicText. Expectation: IllegalArgumentException should
     * be thrown.
     */
    public void testSetDynamicTextNull() {
        try {
            sr.setDynamicText(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setDynamicText(String). Inputs: empty dynamicText. Expectation: IllegalArgumentException should
     * be thrown.
     */
    public void testSetDynamicTextEmpty() {
        try {
            sr.setDynamicText("     ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
