/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.failuretests;

import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBeanFailureTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
	/**
	 * Adds all test suites to the unit test suite and returns the unit test suite.
	 * 
	 * @return the unit test suite.
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTestSuite(SpecificationReviewServiceBeanFailureTest.class);

		return suite;
	}
}
