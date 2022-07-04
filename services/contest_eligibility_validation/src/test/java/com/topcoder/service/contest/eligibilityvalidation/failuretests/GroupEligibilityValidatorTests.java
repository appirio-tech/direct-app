/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.contest.eligibilityvalidation.failuretests;

import junit.framework.TestCase;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibilityvalidation.GroupEligibilityValidator;

/**
 * <p>
 * UnitTest cases of the <code>GroupEligibilityValidator</code> class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupEligibilityValidatorTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GroupEligibilityValidator</code> instance used for
     * test.
     * </p>
     */
    private GroupEligibilityValidator instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        instance = new GroupEligibilityValidator("persistence-unit");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * 
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * Failure test case for constructor GroupEligibilityValidator(String
     * persistenceUnitName).IAE is expected because the argument is null.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    public void testConstructor2Failure1() throws Exception {
        try {
            new GroupEligibilityValidator(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for constructor GroupEligibilityValidator(String
     * persistenceUnitName).IAE is expected because the argument is empty.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    public void testConstructor2Failure2() throws Exception {
        try {
            new GroupEligibilityValidator("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for validate.
     * IAE is expected because the contestEligibility is not instanceof GroupContestEligibility.
     * </p>
     * 
     * @throws Exception to JUnit
     */
    public void testValidateFailure1() throws Exception {
        try {
            instance.validate(5, new ContestEligibility() {
            });
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }

    }

}