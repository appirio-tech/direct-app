/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.failuretests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImpl;

/**
 * <p>
 * Failure test for {@link DirectProjectMetadataValidatorImpl}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class DirectProjectMetadataValidatorImplFailureTest extends BaseFailureTestCase {
    
    /**
     * <p>
     * Represents the DirectProjectMetadataValidatorImpl instance to test against.
     * </p>
     */
    private MockDirectProjectMetadataValidatorImpl instance;
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DirectProjectMetadataValidatorImplFailureTest.class);
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
        instance = new MockDirectProjectMetadataValidatorImpl();
        Map<Long, String> validatorMapping = new HashMap<Long, String>();
        validatorMapping.put(1000L, "name\\d+");
        validatorMapping.put(1001L, "d_");
		instance.setValidatorMapping(validatorMapping );
        Map<Long, Boolean> predefinedKeys = new HashMap<Long, Boolean>();
        predefinedKeys.put(1000L, true);
        predefinedKeys.put(1001L, false);
		instance.setPredefinedKeys(predefinedKeys );
		instance.setEntityManager(ENTITY_MANAGER);
        instance.checkInitialization();
    }
    public class MockDirectProjectMetadataValidatorImpl extends DirectProjectMetadataValidatorImpl {
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
     * Failure test for method checkInitialization() with illegal state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization0() throws Exception {
		instance.setValidatorMapping(null );
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with illegal state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization1() throws Exception {
    	instance.setEntityManager(null);
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with illegal state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization2() throws Exception {
        Map<Long, String> validatorMapping = new HashMap<Long, String>();
        validatorMapping.put(1L, null);
		instance.setValidatorMapping(validatorMapping );
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with illegal state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization3() throws Exception {
        Map<Long, String> validatorMapping = new HashMap<Long, String>();
        validatorMapping.put(1000L, "     ");
		instance.setValidatorMapping(validatorMapping );
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with illegal state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization4() throws Exception {
        Map<Long, String> validatorMapping = new HashMap<Long, String>();
        validatorMapping.put(1000L, "++( invalid regular express");
		instance.setValidatorMapping(validatorMapping );
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with illegal state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization5() throws Exception {
        Map<Long, Boolean> predefinedKeys = new HashMap<Long, Boolean>();
        predefinedKeys.put(1L, null);
		instance.setPredefinedKeys(predefinedKeys );
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with illegal state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization6() throws Exception {
		instance.setPredefinedKeys(null );
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
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        projectMetadata.setProjectMetadataKey(null);
		projectMetadata.setMetadataValue("meta1");
        try {
			instance.validate(projectMetadata);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method validate() with invalid meta key.
     * Expects ValidationException.
     */
    public void test_validate_0() throws Exception {
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(1000L);
		projectMetadata.setProjectMetadataKey(key );
		projectMetadata.setMetadataValue("   ");
        try {
        	//empty meta value
			instance.validate(projectMetadata);
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
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(1000L);
		projectMetadata.setProjectMetadataKey(key );
		projectMetadata.setMetadataValue("meta1");
        try {
        	//did't match the pattern
			instance.validate(projectMetadata);
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
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(1000L);
		projectMetadata.setProjectMetadataKey(key );
		projectMetadata.setMetadataValue("name1");
        try {
        	//did't match the predefined values
			instance.validate(projectMetadata);
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
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(1000L);
		projectMetadata.setProjectMetadataKey(key );
		projectMetadata.setMetadataValue("name1");
		key.setClientId(1000L);
		key.setSingle(true);
		projectMetadata.setTcDirectProjectId(2000l);
        try {
        	//duplicate direct projects
			instance.validate(projectMetadata);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
}
