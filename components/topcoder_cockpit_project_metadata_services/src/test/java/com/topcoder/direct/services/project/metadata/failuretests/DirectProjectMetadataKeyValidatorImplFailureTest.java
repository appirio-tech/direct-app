/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.failuretests;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyValidatorImpl;

/**
 * <p>
 * Failure test for {@link DirectProjectMetadataKeyValidatorImpl}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class DirectProjectMetadataKeyValidatorImplFailureTest extends BaseFailureTestCase {
    
    /**
     * <p>
     * Represents the DirectProjectMetadataKeyValidatorImpl instance to test against.
     * </p>
     */
    private MockDirectProjectMetadataKeyValidatorImpl instance;
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DirectProjectMetadataKeyValidatorImplFailureTest.class);
        return suite;
    }
    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
    	super.setUp();
        instance = new MockDirectProjectMetadataKeyValidatorImpl();
        instance.setEntityManager(ENTITY_MANAGER);
        instance.setLoggerName("logger");
        instance.checkInitialization();
    }
    public class MockDirectProjectMetadataKeyValidatorImpl extends DirectProjectMetadataKeyValidatorImpl {
    	public void checkInitialization() {
    		super.checkInitialization();
    		
    	}
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
    	super.tearDown();
    }
    /**
     * Failure test for method checkInitialization() with null state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization5() throws Exception {
    	instance.setEntityManager(null);
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method validate() with null input.
     * Expects IllegalArgumentException.
     */
    public void test_validate_Null() throws Exception {
        try {
            instance.validate(null);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method validate() with invalid meta key.
     * Expects ValidationException.
     */
    public void test_validate_2() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setName(null);
        try {
			instance.validate(projectMetadataKey);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method validate() with invalid meta key.
     * Expects ValidationException.
     */
    public void test_validate_3() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setName("   ");
        try {
			instance.validate(projectMetadataKey);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method validate() with invalid meta key.
     * Expects ValidationException.
     */
    public void test_validate_4() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(true);
        projectMetadataKey.setName("too long xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        try {
			instance.validate(projectMetadataKey);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method validate() with invalid meta key.
     * Expects ValidationException.
     */
    public void test_validate_5() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setGrouping(true);
        projectMetadataKey.setName("key");
        try {
			instance.validate(projectMetadataKey);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method validate() with invalid meta key.
     * Expects ValidationException.
     */
    public void test_validate_6() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setGrouping(true);
        projectMetadataKey.setName("existedkey");
        try {
			instance.validate(projectMetadataKey);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
}
