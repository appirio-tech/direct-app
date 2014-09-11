/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.asset.entities.AssetPermissionUnitTests;
import com.topcoder.asset.entities.AssetSearchCriteriaUnitTests;
import com.topcoder.asset.entities.AssetUnitTests;
import com.topcoder.asset.entities.AssetVersionUnitTests;
import com.topcoder.asset.entities.AuditRecordUnitTests;
import com.topcoder.asset.entities.BaseSearchCriteriaUnitTests;
import com.topcoder.asset.entities.CategorySearchCriteriaUnitTests;
import com.topcoder.asset.entities.CategoryUnitTests;
import com.topcoder.asset.entities.FileTypeIconUnitTests;
import com.topcoder.asset.entities.PagedResultUnitTests;
import com.topcoder.asset.entities.UserUnitTests;
import com.topcoder.asset.exceptions.AssetConfigurationExceptionUnitTests;
import com.topcoder.asset.exceptions.AssetExceptionUnitTests;
import com.topcoder.asset.exceptions.EntityNotFoundExceptionUnitTests;
import com.topcoder.asset.exceptions.PersistenceExceptionUnitTests;
import com.topcoder.asset.exceptions.ServiceExceptionUnitTests;
import com.topcoder.asset.services.impl.AssetCategoryServiceImplUnitTests;
import com.topcoder.asset.services.impl.AssetPermissionServiceImplUnitTests;
import com.topcoder.asset.services.impl.AssetServiceImplUnitTests;
import com.topcoder.asset.services.impl.AssetVersionServiceImplUnitTests;
import com.topcoder.asset.services.impl.BaseAssetServiceUnitTests;
import com.topcoder.asset.services.impl.BaseMiscServiceUnitTests;
import com.topcoder.asset.services.impl.FileTypeIconServiceImplUnitTests;
import com.topcoder.asset.services.impl.HelperUnitTests;
import com.topcoder.asset.services.impl.ManagerServiceImplUnitTests;
import com.topcoder.asset.services.impl.MiscHelperUnitTests;

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

        suite.addTest(AssetPermissionUnitTests.suite());
        suite.addTest(CategorySearchCriteriaUnitTests.suite());
        suite.addTest(CategoryUnitTests.suite());
        suite.addTest(FileTypeIconUnitTests.suite());
        suite.addTest(UserUnitTests.suite());

        suite.addTest(AssetCategoryServiceImplUnitTests.suite());
        suite.addTest(AssetPermissionServiceImplUnitTests.suite());
        suite.addTest(ManagerServiceImplUnitTests.suite());
        suite.addTest(FileTypeIconServiceImplUnitTests.suite());
        suite.addTest(BaseMiscServiceUnitTests.suite());
        suite.addTest(MiscHelperUnitTests.suite());

        suite.addTest(BaseSearchCriteriaUnitTests.suite());
        suite.addTest(AssetSearchCriteriaUnitTests.suite());
        suite.addTest(AuditRecordUnitTests.suite());
        suite.addTest(PagedResultUnitTests.suite());
        suite.addTest(AssetVersionUnitTests.suite());
        suite.addTest(AssetUnitTests.suite());

        suite.addTest(HelperUnitTests.suite());
        suite.addTest(AssetServiceImplUnitTests.suite());
        suite.addTest(AssetVersionServiceImplUnitTests.suite());
        suite.addTest(BaseAssetServiceUnitTests.suite());

        // Exceptions
        suite.addTest(AssetConfigurationExceptionUnitTests.suite());
        suite.addTest(AssetExceptionUnitTests.suite());
        suite.addTest(EntityNotFoundExceptionUnitTests.suite());
        suite.addTest(PersistenceExceptionUnitTests.suite());
        suite.addTest(ServiceExceptionUnitTests.suite());

        return suite;
    }

}
