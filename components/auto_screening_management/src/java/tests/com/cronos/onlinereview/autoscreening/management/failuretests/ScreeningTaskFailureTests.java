/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.util.Random;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.management.ScreeningTask;


/**
 * <p>
 * Failure test for ScreeningTask.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class ScreeningTaskFailureTests extends TestCase {
    /** An instance of ScreeningTask for testing. */
    private ScreeningTask task;

    /**
     * <p>
     * Set up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        this.task = new ScreeningTask();
    }

    /**
     * Failure test for setId(long). Inputs: zero id. Expectation: IllegalArgumentException should be thrown.
     */
    public void testSetIdZero() {
        try {
            task.setId(0L);
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
            task.setId(-1 * i);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setUpload(long). Inputs: zero upload id. Expectation: IllegalArgumentException should be
     * thrown.
     */
    public void testSetUploadZero() {
        try {
            task.setUpload(0L);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setUpload(long). Inputs: negative upload id. Expectation: IllegalArgumentException should be
     * thrown.
     */
    public void testSetUploadNegative() {
        int i = new Random().nextInt(Integer.MAX_VALUE) + 1;

        try {
            task.setUpload(-1 * i);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setScreener(long). Inputs: zero screener id. Expectation: IllegalArgumentException should be
     * thrown.
     */
    public void testSetScreenerZero() {
        try {
            task.setUpload(0L);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setScreener(long). Inputs: negative screener id. Expectation: IllegalArgumentException should
     * be thrown.
     */
    public void testSetScreenerNegative() {
        int i = new Random().nextInt(Integer.MAX_VALUE) + 1;

        try {
            task.setUpload(-1 * i);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setStartTimestamp(Date). Inputs: null startTimestamp. Expectation: IllegalArgumentException
     * should be thrown.
     */
    public void testSetStartTimestampNull() {
        try {
            task.setStartTimestamp(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for addScreeningResult(ScreeningResult). Inputs: null screeningResult. Expectation:
     * IllegalArgumentException should be thrown.
     */
    public void testAddScreeningResultNull() {
        try {
            task.addScreeningResult(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for removeScreeningResult(ScreeningResult). Inputs: null screeningResult. Expectation:
     * IllegalArgumentException should be thrown.
     */
    public void testRemoveScreeningResult() {
        try {
            task.removeScreeningResult(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setCreationUser(String). Inputs: null creationUser. Expectation: IllegalArgumentException
     * should be thrown.
     */
    public void testSetCreationUserNull() {
        try {
            task.setCreationUser(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setCreationUser(String). Inputs: empty creationUser. Expectation: IllegalArgumentException
     * should be thrown.
     */
    public void testSetCreationUserEmpty() {
        try {
            task.setCreationUser("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setModificationUser(String). Inputs: null modificationUser. Expectation:
     * IllegalArgumentException should be thrown.
     */
    public void testSetModificationUserNull() {
        try {
            task.setModificationUser(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setModificationUser(String). Inputs: empty modificationUser. Expectation:
     * IllegalArgumentException should be thrown.
     */
    public void testSetModificationUserEmpty() {
        try {
            task.setModificationUser("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for setModificationTimestamp(Date). Inputs: null modificationTimestamp. Expectation:
     * IllegalArgumentException should be thrown.
     */
    public void testSetModificationTimestampNull() {
        try {
            task.setModificationTimestamp(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
