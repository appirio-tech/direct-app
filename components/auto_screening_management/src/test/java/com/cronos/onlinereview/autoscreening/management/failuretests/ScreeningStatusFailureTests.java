/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.util.Random;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.management.ScreeningStatus;


/**
 * <p>
 * Failure test for ScreeningStatus.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ScreeningStatusFailureTests extends TestCase {
    /** An instance of ScreeningStatus for testing. */
    private ScreeningStatus status;

    /**
     * <p>
     * Set up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        this.status = new ScreeningStatus();
    }

    /**
     * Failure test for setId(long). Inputs: zero id. Expectation: IllegalArgumentException should be thrown.
     */
    public void testSetIdZero() {
        try {
            status.setId(0L);
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
            status.setId(-1 * i);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setName(String). Inputs: illegal status name. Expectation: IllegalArgumentException should be
     * thrown.
     */
    public void testSetNameIllegal() {
        try {
            status.setName("Illegal");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setName(String). Inputs: Pending status names. Expectation: should be ok.
     */
    public void testSetNamePending() {
        status.setName("Pending");
    }

    /**
     * Failure test for setName(String). Inputs: Screening status names. Expectation: should be ok.
     */
    public void testSetNameScreening() {
        status.setName("Screening");
    }

    /**
     * Failure test for setName(String). Inputs: Failed status names. Expectation: should be ok.
     */
    public void testSetNameFailed() {
        status.setName("Failed");
    }

    /**
     * Failure test for setName(String). Inputs: Passed status names. Expectation: should be ok.
     */
    public void testSetNamePassed() {
        status.setName("Passed");
    }

    /**
     * Failure test for setName(String). Inputs: "Passed With Warning" status names. Expectation: should be ok.
     */
    public void testSetNamePassedWithWarning() {
        status.setName("Passed With Warning");
    }
}
