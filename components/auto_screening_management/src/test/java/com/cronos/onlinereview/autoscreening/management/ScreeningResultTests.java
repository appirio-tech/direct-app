/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ScreeningResult. Tests the class for proper behavior.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ScreeningResultTests extends TestCase {
    /**
     * <p>
     * The id used for testing.
     * </p>
     */
    private static final long ID = 123;

    /**
     * <p>
     * The ScreeningResponse used for testing.
     * </p>
     */
    private static final ScreeningResponse SCREENINGRESPONSE = new ScreeningResponse();

    /**
     * <p>
     * The dynamicText used for testing.
     * </p>
     */
    private static final String DYNAMICTEXT = "test dynamicText";

    /**
     * <p>
     * An instance of <code>ScreeningResult</code> which is tested.
     * </p>
     */
    private ScreeningResult target = null;

    /**
     * <p>
     * setUp() routine. Creates test ScreeningResult instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ScreeningResult();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ScreeningResult implements Serializable.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ScreeningResult does not implement Serializable.",
                target instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningResult()</code> Create for proper behavior.
     * </p>
     */
    public void testCreate_accuracy() {
        assertNotNull("Failed to initialize ScreeningResult object.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setId(long)</code> for proper behavior. Verify that id is
     * saved as is.
     * </p>
     */
    public void testSetId_accuracy() {
        target.setId(ID);
        assertEquals("id", ID, target.getId());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setId(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid id.
     * </p>
     */
    public void testSetId_1_failure() {
        try {
            target.setId(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setId(long)</code> for proper behavior. Verify that
     * IllegalArgumentException is thrown for invalid id.
     * </p>
     */
    public void testSetId_2_failure() {
        try {
            target.setId(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getId()</code> for proper behavior. Verify that id is
     * returned as is.
     * </p>
     */
    public void testGetId_accuracy() {
        target.setId(ID);
        assertEquals("id", ID, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setScreeningResponse(ScreeningResponse)</code> for proper
     * behavior. Verify that ScreeningResponse is saved as is.
     * </p>
     */
    public void testSetScreeningResponse_accuracy() {
        target.setScreeningResponse(SCREENINGRESPONSE);
        assertEquals("ScreeningResponse", SCREENINGRESPONSE, target.getScreeningResponse());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setScreeningResponse(ScreeningResponse)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid ScreeningResponse.
     * </p>
     */
    public void testSetScreeningResponse_1_failure() {
        try {
            target.setScreeningResponse(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningResponse()</code> for proper behavior. Verify
     * that ScreeningResponse is returned as is.
     * </p>
     */
    public void testGetScreeningResponse_accuracy() {
        target.setScreeningResponse(SCREENINGRESPONSE);
        assertEquals("ScreeningResponse", SCREENINGRESPONSE, target.getScreeningResponse());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setDynamicText(String)</code> for proper behavior. Verify
     * that DynamicText is saved as is.
     * </p>
     */
    public void testSetDynamicText_accuracy() {
        target.setDynamicText(DYNAMICTEXT);
        assertEquals("DynamicText", DYNAMICTEXT, target.getDynamicText());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setDynamicText(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid DynamicText.
     * </p>
     */
    public void testSetDynamicText_1_failure() {
        try {
            target.setDynamicText(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setDynamicText(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid DynamicText.
     * </p>
     */
    public void testSetDynamicText_2_failure() {
        try {
            target.setDynamicText("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getDynamicText()</code> for proper behavior. Verify that
     * DynamicText is returned as is.
     * </p>
     */
    public void testGetDynamicText_accuracy() {
        target.setDynamicText(DYNAMICTEXT);
        assertEquals("DynamicText", DYNAMICTEXT, target.getDynamicText());
    }
}
