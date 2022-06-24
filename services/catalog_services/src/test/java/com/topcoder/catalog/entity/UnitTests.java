/*
 * Copyright (C) 2007-2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import com.topcoder.catalog.entity.unittests.CategoryTest;
import com.topcoder.catalog.entity.unittests.CompClientTest;
import com.topcoder.catalog.entity.unittests.CompDocumentationTest;
import com.topcoder.catalog.entity.unittests.CompForumTest;
import com.topcoder.catalog.entity.unittests.CompLinkTest;
import com.topcoder.catalog.entity.unittests.CompUserTest;
import com.topcoder.catalog.entity.unittests.CompVersionDatesTest;
import com.topcoder.catalog.entity.unittests.CompVersionTest;
import com.topcoder.catalog.entity.unittests.ComponentTest;
import com.topcoder.catalog.entity.unittests.IdGeneratorTest;
import com.topcoder.catalog.entity.unittests.NamedQueriesTest;
import com.topcoder.catalog.entity.unittests.PhaseTest;
import com.topcoder.catalog.entity.unittests.StatusTest;
import com.topcoder.catalog.entity.unittests.StatusUserTypeTest;
import com.topcoder.catalog.entity.unittests.TechnologyTest;
import com.topcoder.catalog.entity.unittests.persistence.CategoryEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.CompClientEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.CompDocumentationEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.CompForumEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.CompLinkEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.CompUserEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.CompVersionDatesEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.CompVersionEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.ComponentEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.ComponentUnitTest;
import com.topcoder.catalog.entity.unittests.persistence.PhaseEntityTest;
import com.topcoder.catalog.entity.unittests.persistence.TechnologyEntityTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author Retunsky, KingStone
 * @version 1.1
 */
public class UnitTests extends TestCase {

    /**
     * <p>This test case aggregates all unit tests for the component.</p>
     *
     * @return the test suite for the component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // For BUGR-78
        suite.addTestSuite(ComponentUnitTest.class);

        // class tests
        suite.addTestSuite(CategoryTest.class);
        suite.addTestSuite(CompClientTest.class);
        suite.addTestSuite(CompForumTest.class);
        suite.addTestSuite(CompLinkTest.class);
        suite.addTestSuite(ComponentTest.class);
        suite.addTestSuite(CompUserTest.class);
        suite.addTestSuite(CompVersionTest.class);
        suite.addTestSuite(CompVersionDatesTest.class);
        suite.addTestSuite(IdGeneratorTest.class);
        suite.addTestSuite(PhaseTest.class);
        suite.addTestSuite(StatusUserTypeTest.class);
        suite.addTestSuite(TechnologyTest.class);
        suite.addTestSuite(StatusTest.class);
        // persistence tests
        suite.addTestSuite(CategoryEntityTest.class);
        suite.addTestSuite(CompClientEntityTest.class);
        suite.addTestSuite(CompForumEntityTest.class);
        suite.addTestSuite(CompLinkEntityTest.class);
        suite.addTestSuite(ComponentEntityTest.class);
        suite.addTestSuite(CompUserEntityTest.class);
        suite.addTestSuite(CompVersionEntityTest.class);
        suite.addTestSuite(CompVersionDatesEntityTest.class);
        suite.addTestSuite(PhaseEntityTest.class);
        suite.addTestSuite(TechnologyEntityTest.class);
        // add version 1.1 testcase
        suite.addTestSuite(CompDocumentationEntityTest.class);
        suite.addTestSuite(CompDocumentationTest.class);

        // named queries test
        suite.addTestSuite(NamedQueriesTest.class);
        // demo
        suite.addTestSuite(DemoTest.class);

        return suite;
    }

}
