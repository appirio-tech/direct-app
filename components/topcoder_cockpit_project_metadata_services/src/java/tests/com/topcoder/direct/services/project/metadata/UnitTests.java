/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.metadata.impl.AbstractDirectProjectMetadataServiceUnitTests;
import com.topcoder.direct.services.project.metadata.impl.AbstractDirectProjectMetadataValidatorUnitTests;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyServiceImplUnitTests;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyValidatorImplUnitTests;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataServiceImplUnitTests;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImplUnitTests;
import com.topcoder.direct.services.project.metadata.impl.HelperUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());

        suite.addTest(HelperUnitTests.suite());

        suite.addTest(DirectProjectMetadataKeyServiceImplUnitTests.suite());

        suite.addTest(DirectProjectMetadataServiceImplUnitTests.suite());

        suite.addTest(DirectProjectMetadataKeyValidatorImplUnitTests.suite());
        suite.addTest(DirectProjectMetadataValidatorImplUnitTests.suite());
        suite.addTest(AbstractDirectProjectMetadataServiceUnitTests.suite());
        suite.addTest(AbstractDirectProjectMetadataValidatorUnitTests.suite());

        // Exceptions
        suite.addTest(ConfigurationExceptionUnitTests.suite());
        suite.addTest(DirectProjectServiceExceptionUnitTests.suite());
        suite.addTest(EntityNotFoundExceptionUnitTests.suite());
        suite.addTest(PersistenceExceptionUnitTests.suite());
        suite.addTest(ValidationExceptionUnitTests.suite());

        return suite;
    }

}
