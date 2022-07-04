/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;

/**
 * <p>
 * The demo usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends TestCase {

    /**
     * Represents the ContestEligibilityValidationManager used in demo.
     */
    private ContestEligibilityValidationManager contestEligibilityValidationManager;

    /**
     * <p>
     * Creates a test suite for the tests.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DemoTest.class);
        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        TestHelper.runSQL("drop.sql", entityManager);
        TestHelper.runSQL("setup.sql", entityManager);
        Context context = new InitialContext();
        contestEligibilityValidationManager =
            (ContestEligibilityValidationManager) context
                .lookup("contest_eligibility_validation/ContestEligibilityValidationManagerBean/remote");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void tearDown() throws Exception {
        EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        TestHelper.runSQL("drop.sql", entityManager);
        contestEligibilityValidationManager = null;
    }

    /**
     * <p>
     * The demo usage of this component.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemoUsage() throws Exception {

        // create the eligibilities list.It contains one GroupContestEligibility with group id=5
        GroupContestEligibility contestEligibility1 = new GroupContestEligibility();
        contestEligibility1.setGroupId(5);
        GroupContestEligibility contestEligibility2 = new GroupContestEligibility();
        contestEligibility2.setGroupId(3);
        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
        eligibilities.add(contestEligibility1);
        eligibilities.add(contestEligibility2);
        boolean result = contestEligibilityValidationManager.validate(5, eligibilities);
        System.out.println("Result it:" + result);
    }
}