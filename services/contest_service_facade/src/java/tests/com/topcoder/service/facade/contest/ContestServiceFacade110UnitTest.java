/*
 * Copyright (C) 2009-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean;


/**
 * The unittest cases using the mockup studioservice
 *
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 * @author TCS Deveoper,
 * @version 1.1
 */
public class ContestServiceFacade110UnitTest extends TestCase {
    private ContestServiceFacade contestServiceFacade;

    /**
     * The setup method for preparing the necessary resource for testing
     *
     * @since 1.1
     */
    protected void setUp() throws Exception {
        this.contestServiceFacade = new ContestServiceFacadeBean();

//        MockUpStudioServiceBean mo = new MockUpStudioServiceBean();
        java.lang.reflect.Field f = ContestServiceFacadeBean.class.getDeclaredField(
                "studioService");
        f.setAccessible(true);
//        f.set(contestServiceFacade, mo);
    }

    /**
     * The method return the test suite for this class.
     *
     * @return
     * @since 1.1
     */
    public static TestSuite suite() {
        return new TestSuite(ContestServiceFacade110UnitTest.class);
    }

}
