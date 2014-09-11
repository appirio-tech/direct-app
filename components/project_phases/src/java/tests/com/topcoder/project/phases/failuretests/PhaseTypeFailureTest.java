/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.failuretests;

import com.topcoder.project.phases.PhaseType;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>PhaseType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class PhaseTypeFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>PhaseType</code> to test.
     * </p>
     */
    private PhaseType tester;

    /**
     * <p>
     * Creates an instance of <code>PhaseType</code>.
     * </p>
     *
     * @throws Exception Exception to Junit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        tester = new PhaseType(10, "name");
    }

    /**
     * <p>
     * Test ctor PhaseType(long id, String name),
     * when id is negative, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_IdIsNegative() {
        try {
            new PhaseType(-1, "name");
            fail("when id is negative, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor PhaseType(long id, String name),
     * when name is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCtor_NameIsNegative() {
        try {
            new PhaseType(10, null);
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
            fail("when name is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
