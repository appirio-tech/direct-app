/**
 * Copyright (c) 2011, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.project.metadata.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * 
 * @author mumujava
 * @version 1.0
 */
public class FailureTests extends TestCase {

	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTestSuite(DirectProjectMetadataKeyValidatorImplFailureTest.class);
		suite.addTestSuite(DirectProjectMetadataValidatorImplFailureTest.class);
		suite.addTestSuite(DirectProjectMetadataKeyServiceImplFailureTest.class);
		suite.addTestSuite(DirectProjectMetadataServiceImplFailureTest.class);
		return suite;
	}

}
