/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.Deliverable;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Accuracy test cases for <code>Deliverable</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class DeliverableAccuracyTests extends TestCase {
    /** The <code>Deliverable</code> instance to be tested. */
    private Deliverable deliverable = null;

    /**
     * Sets up the test environment. New <code>Deliverable</code> instance is created.
     */
    protected void setUp() {
        deliverable = new Deliverable(2, 4, 8, new Long(16), true);
    }

    /**
     * Tests constructor.
     */
    public void testConstructor() {
        long project = 2;
        long phase = 16;
        long resource = 64;
        Long submission = new Long(1024);
        boolean required = false;
        deliverable = new Deliverable(project, phase, resource, submission, required);
        assertNotNull("Unable to instantiate Deliverable", deliverable);
        assertEquals("Project not set correctly", project, deliverable.getProject());
        assertEquals("Phase not set correctly", phase, deliverable.getPhase());
        assertEquals("Resource not set correctly", resource, deliverable.getResource());
        assertEquals("Submission not set correctly", submission, deliverable.getSubmission());
    }

    /**
     * Tests constructor with null submission.
     */
    public void testConstructorWithNullSubmission() {
        long project = 2;
        long phase = 16;
        long resource = 64;
        boolean required = true;
        deliverable = new Deliverable(project, phase, resource, null, required);
        assertNotNull("Unable to instantiate Deliverable", deliverable);
        assertEquals("Project not set correctly", project, deliverable.getProject());
        assertEquals("Phase not set correctly", phase, deliverable.getPhase());
        assertEquals("Resource not set correctly", resource, deliverable.getResource());
        assertEquals("Submission not set correctly", null, deliverable.getSubmission());
    }

    /**
     * Tests isRequired method.
     */
    public void testIsRequired() {
        deliverable = new Deliverable(2, 4, 8, new Long(16), true);
        assertEquals("IsRequired not functions correctly", true, deliverable.isRequired());
        deliverable = new Deliverable(2, 4, 8, new Long(16), false);
        assertEquals("IsRequired not functions correctly", false, deliverable.isRequired());
    }

    /**
     * Tests setCompletionDate and getCompletionDate methods.
     */
    public void testSetAndGetCompletionDate() {
        Date now = new Date();
        deliverable.setCompletionDate(now);
        assertEquals("SetCompletionDate or getCompletionDate not functions correctly", now,
            deliverable.getCompletionDate());
    }

    /**
     * Tests isComplete method.
     */
    public void testIsComplete() {
        assertEquals("IsComplete not functions correctly", false, deliverable.isComplete());
        deliverable.setCompletionDate(new Date());
        assertEquals("IsComplete not functions correctly", true, deliverable.isComplete());
    }

    /**
     * Tests isPerSubmission method.
     */
    public void testIsPerSubmission() {
        deliverable = new Deliverable(2, 4, 8, new Long(16), true);
        assertEquals("IsPerSubmission not functions correctly", true, deliverable.isPerSubmission());
        deliverable = new Deliverable(2, 4, 8, null, true);
        assertEquals("IsPerSubmission not functions correctly", false, deliverable.isPerSubmission());
    }
}
