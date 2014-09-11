/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of ScreeningResponse. Tests the class for proper saving the provided arguments.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class ScreeningResponseTests extends TestCase {
    /**
     * <p>
     * The id used for testing.
     * </p>
     */
    private static final long ID = 123;

    /**
     * <p>
     * The ResponseSeverity used for testing.
     * </p>
     */
    private static final ResponseSeverity RESPONSESEVERITY = new ResponseSeverity();

    /**
     * <p>
     * The responseCode used for testing.
     * </p>
     */
    private static final String RESPONSECODE = "test code";

    /**
     * <p>
     * The responseText used for testing.
     * </p>
     */
    private static final String RESPONSETEXT = "test text";

    /**
     * <p>
     * An instance of <code>ScreeningResponse</code> which is tested.
     * </p>
     */
    private ScreeningResponse target = null;

    /**
     * <p>
     * setUp() routine. Creates test ScreeningResponse instance.
     * </p>
     */
    protected void setUp() {
        this.target = new ScreeningResponse();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies ScreeningResponse implements Serializable.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ScreeningResponse does not implement Serializable.",
                target instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ScreeningResponse()</code> Create for proper behavior.
     * </p>
     */
    public void testCreate_accuracy() {
        assertNotNull("Failed to initialize ScreeningResponse object.", target);
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
     * Accuracy test. Tests the <code>setResponseSeverity(ResponseSeverity)</code> for proper
     * behavior. Verify that ResponseSeverity is saved as is.
     * </p>
     */
    public void testSetResponseSeverity_accuracy() {
        target.setResponseSeverity(RESPONSESEVERITY);
        assertEquals("ResponseSeverity", RESPONSESEVERITY, target.getResponseSeverity());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setResponseSeverity(ResponseSeverity)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for null.
     * </p>
     */
    public void testSetResponseSeverity_1_failure() {
        try {
            target.setResponseSeverity(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getResponseSeverity()</code> for proper behavior. Verify
     * that ResponseSeverity is returned as is.
     * </p>
     */
    public void testGetResponseSeverity_accuracy() {
        target.setResponseSeverity(RESPONSESEVERITY);
        assertEquals("ResponseSeverity", RESPONSESEVERITY, target.getResponseSeverity());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setResponseCode(String)</code> for proper behavior. Verify
     * that ResponseCode is saved as is.
     * </p>
     */
    public void testSetResponseCode_accuracy() {
        target.setResponseCode(RESPONSECODE);
        assertEquals("ResponseCode", RESPONSECODE, target.getResponseCode());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setResponseCode(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid ResponseCode.
     * </p>
     */
    public void testSetResponseCode_1_failure() {
        try {
            target.setResponseCode(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setResponseCode(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid ResponseCode.
     * </p>
     */
    public void testSetResponseCode_2_failure() {
        try {
            target.setResponseCode("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getResponseCode()</code> for proper behavior. Verify that
     * ResponseCode is returned as is.
     * </p>
     */
    public void testGetResponseCode_accuracy() {
        target.setResponseCode(RESPONSECODE);
        assertEquals("ResponseCode", RESPONSECODE, target.getResponseCode());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setResponseText(String)</code> for proper behavior. Verify
     * that ResponseText is saved as is.
     * </p>
     */
    public void testSetResponseText_accuracy() {
        target.setResponseText(RESPONSETEXT);
        assertEquals("ResponseText", RESPONSETEXT, target.getResponseText());
    }

    /**
     * <p>
     * Failure test. Tests the <code>setResponseText(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid ResponseText.
     * </p>
     */
    public void testSetResponseText_1_failure() {
        try {
            target.setResponseText(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>setResponseText(String)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid ResponseText.
     * </p>
     */
    public void testSetResponseText_2_failure() {
        try {
            target.setResponseText("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getResponseText()</code> for proper behavior. Verify that
     * ResponseText is returned as is.
     * </p>
     */
    public void testGetResponseText_accuracy() {
        target.setResponseText(RESPONSETEXT);
        assertEquals("ResponseText", RESPONSETEXT, target.getResponseText());
    }
}
