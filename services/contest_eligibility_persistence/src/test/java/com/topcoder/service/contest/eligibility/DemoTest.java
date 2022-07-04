/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManager;

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
     * Represents the ContestEligibilityManager used in demo.
     */
    private ContestEligibilityManager contestEligibilityManager;

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
        Context context = new InitialContext();
        contestEligibilityManager =
            (ContestEligibilityManager) context
                .lookup("contest_eligibility_persistence/ContestEligibilityManagerBean/remote");
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
        contestEligibilityManager = null;
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

        // create a GroupContestEligibility instance named groupContestEligibility
        ContestEligibility groupContestEligibility = new GroupContestEligibility();
        groupContestEligibility.setContestId(16);
        groupContestEligibility.setStudio(true);

        // insert groupContestEligibility into DB
        groupContestEligibility = contestEligibilityManager.create(groupContestEligibility);

        // get a list of eligibilities for a contest
        List<ContestEligibility> list = contestEligibilityManager.getContestEligibility(16, true);
        System.out.println(list.size());

        // Save a list of eligibilities,you can add/update/delete entities.Here we just update it. You also
        // can refer to ContestEligibilityManagerBeanTests for more tests to add/update/delete.
        contestEligibilityManager.save(list);

        // Remove a contest eligibility
        contestEligibilityManager.remove(groupContestEligibility);
    }
}