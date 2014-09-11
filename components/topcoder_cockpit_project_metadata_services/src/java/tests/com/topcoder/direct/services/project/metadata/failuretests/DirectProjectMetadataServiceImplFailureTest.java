/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dto.CompositeFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataServiceImpl;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImpl;

/**
 * <p>
 * Failure test for {@link DirectProjectMetadataServiceImpl}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class DirectProjectMetadataServiceImplFailureTest extends BaseFailureTestCase {
	/**
     * <p>
     * Represents the DirectProjectMetadataServiceImpl instance to test against.
     * </p>
     */
    private MockDirectProjectMetadataServiceImpl instance;
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(DirectProjectMetadataServiceImplFailureTest.class);
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
        instance = new MockDirectProjectMetadataServiceImpl();
        Map<String, Integer> auditActionTypeIdMap = new HashMap<String, Integer>();
        auditActionTypeIdMap.put("create", 1);
        auditActionTypeIdMap.put("update", 2);
        auditActionTypeIdMap.put("delete", 3);
		instance.setAuditActionTypeIdMap(auditActionTypeIdMap );
		DirectProjectMetadataValidatorImpl directProjectMetadataValidator = new DirectProjectMetadataValidatorImpl();
        Map<Long, String> validatorMapping = new HashMap<Long, String>();
        validatorMapping.put(1000L, "name\\d+");
        validatorMapping.put(1001L, "d_");
        directProjectMetadataValidator.setValidatorMapping(validatorMapping );
        Map<Long, Boolean> predefinedKeys = new HashMap<Long, Boolean>();
        predefinedKeys.put(1000L, true);
        predefinedKeys.put(1001L, false);
        directProjectMetadataValidator.setPredefinedKeys(predefinedKeys );
        directProjectMetadataValidator.setEntityManager(ENTITY_MANAGER);
        
        
		instance.setDirectProjectMetadataValidator(directProjectMetadataValidator );
        instance.setEntityManager(ENTITY_MANAGER);
        instance.setLoggerName("logger");
        instance.checkInitialization();
    }
    public class MockDirectProjectMetadataServiceImpl extends DirectProjectMetadataServiceImpl {
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
    	instance.setDirectProjectMetadataValidator(null);
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
     * Failure test for method createProjectMetadata() with null input.
     * Expects IllegalArgumentException.
     */
    public void test_createProjectMetadata_Null() throws Exception {
        try {
            instance.createProjectMetadata(null, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method createProjectMetadata() with validation error.
     * Expects ValidationException.
     */
    public void test_createProjectMetadata_ValidationException() throws Exception {
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(1000L);
		projectMetadata.setProjectMetadataKey(key );
		projectMetadata.setMetadataValue("name1");
        try {
            instance.createProjectMetadata(projectMetadata, 1);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method updateProjectMetadata() with null input.
     * Expects IllegalArgumentException.
     */
    public void test_updateProjectMetadata_Null() throws Exception {
        try {
            instance.updateProjectMetadata(null, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method updateProjectMetadata() with validation error.
     * Expects ValidationException.
     */
    public void test_updateProjectMetadata_ValidationException() throws Exception {
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(1000L);
		projectMetadata.setProjectMetadataKey(key );
		projectMetadata.setMetadataValue("name1");
        try {
            instance.updateProjectMetadata(projectMetadata, 1);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method saveProjectMetadata() with null input.
     * Expects IllegalArgumentException.
     */
    public void test_saveProjectMetadata_Null() throws Exception {
        try {
            instance.saveProjectMetadata(null, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method saveProjectMetadata() with validation error.
     * Expects ValidationException.
     */
    public void test_saveProjectMetadata_ValidationException() throws Exception {
        DirectProjectMetadata projectMetadata = new DirectProjectMetadata();
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(1000L);
		projectMetadata.setProjectMetadataKey(key );
		projectMetadata.setMetadataValue("name1");
        try {
            instance.saveProjectMetadata(projectMetadata, 1);
            fail("Expects ValidationException");
        } catch (ValidationException e) {
            // good
        }
    }
    /**
     * Failure test for method deleteProjectMetadata().
     * Expects EntityNotFoundException.
     */
    public void test_deleteProjectMetadata_EntityNotFoundException() throws Exception {
        try {
            instance.deleteProjectMetadata(1200, 1);
            fail("Expects EntityNotFoundException");
        } catch (EntityNotFoundException e) {
            // good
        }
    }
    /**
     * Failure test for method searchProjects().
     * Expects IllegalArgumentException.
     */
    public void test_searchProjects_1() throws Exception {
        try {
            instance.searchProjects(null);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method searchProjects().
     * Expects IllegalArgumentException.
     */
    public void test_searchProjects_2() throws Exception {
        try {
            instance.searchProjects(new DirectProjectFilter() {
				private static final long serialVersionUID = -1590328730719255809L;

				public String toJSONString() {
					return "";
				}
            	
            });
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method searchProjects().
     * Expects IllegalArgumentException.
     */
    public void test_searchProjects_3() throws Exception {
    	CompositeFilter filter = new CompositeFilter();
        filter.setProjectFilters(new ArrayList<DirectProjectFilter>());
        //empty project filter
        try {
			instance.searchProjects(filter );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method searchProjects().
     * Expects IllegalArgumentException.
     */
    public void test_searchProjects_4() throws Exception {
    	MetadataKeyIdValueFilter filter = new MetadataKeyIdValueFilter();
    	filter.setMetadataValue("%%%%%%%%%");
    	filter.setProjectMetadataKeyId(1L);
		filter.setMetadataValueOperator(MetadataValueOperator.EQUALS );
        try {
			instance.searchProjects(filter );
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addProjectMetadata().
     * Expects IllegalArgumentException.
     */
    public void test_addProjectMetadata_1() throws Exception {
    	DirectProjectMetadataDTO projectMetadata = new DirectProjectMetadataDTO();
		projectMetadata.setProjectMetadataKeyId(1L);
		projectMetadata.setMetadataValue("name1");
        try {
            instance.addProjectMetadata(null, projectMetadata, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addProjectMetadata().
     * Expects IllegalArgumentException.
     */
    public void test_addProjectMetadata_2() throws Exception {
        try {
            instance.addProjectMetadata(new long[]{1}, null, 1);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addProjectMetadata().
     * Expects IllegalArgumentException.
     */
    public void test_addProjectMetadata_4() throws Exception {
        try {
            instance.addProjectMetadata(1, null, 10);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
    /**
     * Failure test for method addProjectMetadata().
     * Expects IllegalArgumentException.
     */
    public void test_addProjectMetadata_5() throws Exception {
        try {
            instance.addProjectMetadata(1, Arrays.asList((DirectProjectMetadataDTO)null), 10);
            fail("Expects IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
