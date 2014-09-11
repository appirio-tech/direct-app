/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.failuretests;

import com.topcoder.project.phases.PhaseStatus;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>PhaseStatus</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class PhaseStatusFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>PhaseStatus</code> to test.
     * </p>
     */
    private PhaseStatus tester;

    /**
     * <p>
     * Create an instance of <code>PhaseStatus</code> to test.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        tester = new PhaseStatus(10, "name");
    }

    /**
     * <p>
     * Test ctor PhaseStatus(long id, String name),
     * when id is negative, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_IdIsNegative() {
        try {
            new PhaseStatus(-1, "name");
            fail("when id is negative, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor PhaseStatus(long id, String name),
     * when name is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_NameIsNull() {
        try {
            new PhaseStatus(10, null);
            fail("when name is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setId(long id),
     * when id is negative, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetId_IdIsNegative() {
        try {
            tester.setId(-1);
            fail("when id is negative, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test setName(String name),
     * when name is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetName_NameIsNull() {
        try {
            tester.setName(null);
            fail("when name is null, IllegalArgumentException is expected. ");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
