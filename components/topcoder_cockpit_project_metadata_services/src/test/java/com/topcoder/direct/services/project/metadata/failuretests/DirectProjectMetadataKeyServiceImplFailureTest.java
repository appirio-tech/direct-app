/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.failuretests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyServiceImpl;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyValidatorImpl;

/**
 * <p>
 * Failure test for {@link DirectProjectMetadataKeyServiceImpl}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class DirectProjectMetadataKeyServiceImplFailureTest extends BaseFailureTestCase {
	/**
     * <p>
     * Represents the DirectProjectMetadataKeyServiceImpl instance to test against.
     * </p>
     */
    private MockDirectProjectMetadataKeyServiceImpl instance;
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DirectProjectMetadataKeyServiceImplFailureTest.class);
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
        instance = new MockDirectProjectMetadataKeyServiceImpl();
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
		instance.setAuditActionTypeIdMap(auditActionTypeIdMap );
		DirectProjectMetadataKeyValidatorImpl directProjectMetadataKeyValidator = new DirectProjectMetadataKeyValidatorImpl();
        directProjectMetadataKeyValidator.setEntityManager(ENTITY_MANAGER);
        directProjectMetadataKeyValidator.setLoggerName("logger");
		instance.setDirectProjectMetadataKeyValidator(directProjectMetadataKeyValidator );
        instance.setEntityManager(ENTITY_MANAGER);
        instance.setLoggerName("logger");
        instance.checkInitialization();
    }
    public class MockDirectProjectMetadataKeyServiceImpl extends DirectProjectMetadataKeyServiceImpl {
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
    }
    /**
     * Failure test for method checkInitialization() with null state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization1() throws Exception {
    	instance.setAuditActionTypeIdMap(null);
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with null state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization2() throws Exception {
    	instance.setAuditActionTypeIdMap(new HashMap<String, Integer>());
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with null state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization3() throws Exception {
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("   ", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
		instance.setAuditActionTypeIdMap(auditActionTypeIdMap );
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
    }
    /**
     * Failure test for method checkInitialization() with null state.
     * Expects ConfigurationException.
     */
    public void test_checkInitialization4() throws Exception {
    	instance.setDirectProjectMetadataKeyValidator(null);
        try {
            instance.checkInitialization();
            fail("Expects ConfigurationException");
        } catch (ConfigurationException e) {
            // good
        }
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
     * Failure test for method createProjectMetadataKey() with null input.
     * Expects IllegalArgumentException.
     */
    public void test_createProjectMetadataKey_Null() throws Exception {
        try {
            instance.createProjectMetadataKey(null, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method createProjectMetadataKey() with validation error.
     * Expects ValidationException.
     */
    public void test_createProjectMetadataKey_ValidationException() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setGrouping(true);
        projectMetadataKey.setName("existedkey");
        try {
            instance.createProjectMetadataKey(projectMetadataKey, 1);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method updateProjectMetadataKey() with null input.
     * Expects IllegalArgumentException.
     */
    public void test_updateProjectMetadataKey_Null() throws Exception {
        try {
            instance.updateProjectMetadataKey(null, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
  

    /**
     * Failure test for method updateProjectMetadataKey() with persistence error.
     * Expects EntityNotFoundException.
     */
    public void test_updateProjectMetadataKey_entitynotfound() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setGrouping(null);
        projectMetadataKey.setName("name");
        try {
            instance.updateProjectMetadataKey(projectMetadataKey, 1);
            fail("Expects EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // good
        }
    }
    
    /**
     * Failure test for method saveProjectMetadataKey() with null input.
     * Expects IllegalArgumentException.
     */
    public void test_saveProjectMetadataKey_Null() throws Exception {
        try {
            instance.saveProjectMetadataKey(null, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method saveProjectMetadataKey() with validation error.
     * Expects ValidationException.
     */
    public void test_saveProjectMetadataKey_ValidationException() throws Exception {
        DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setGrouping(true);
        projectMetadataKey.setName("existedkey");
        try {
            instance.saveProjectMetadataKey(projectMetadataKey, 1);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method deleteProjectMetadataKey().
     * Expects EntityNotFoundException.
     */
    public void test_deleteProjectMetadataKey_Null() throws Exception {
        try {
            instance.deleteProjectMetadataKey(10, 1);
            fail("Expects EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // good
        }
    }
}
