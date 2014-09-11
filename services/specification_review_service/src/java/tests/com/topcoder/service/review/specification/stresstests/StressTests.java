/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.review.specification.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

	/**
	 * Aggregate all stress cases.
	 * @return the aggregated cases
	 */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(SpecificationReviewServiceBeanStressTest.class);
        return suite;
    }
}