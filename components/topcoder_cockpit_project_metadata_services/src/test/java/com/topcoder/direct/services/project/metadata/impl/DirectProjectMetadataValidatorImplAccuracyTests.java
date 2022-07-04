/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.metadata.accuracytests.impl.AccuracyTestHelper;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.direct.services.project.metadata.impl.AbstractDirectProjectMetadataValidator;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImpl;

/**
 * <p>
 * Accuracy test for DirectProjectMetadataValidatorImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DirectProjectMetadataValidatorImplAccuracyTests {
    /**
     * <p>
     * Represents the instance of DirectProjectMetadataValidatorImpl used in tests.
     * </p>
     */
    private DirectProjectMetadataValidatorImpl instance;

    /**
     * <p>
     * Represents the instance of DirectProjectMetadata used in tests.
     * </p>
     */
    private DirectProjectMetadata projectMetadata;

    /**
     * <p>
     * Represents the instance of EntityManager used in tests.
     * </p>
     */
    private EntityManager em;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return the test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataValidatorImplAccuracyTests.class);
    }

    /**
     * <p>
     * Set up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        em = AccuracyTestHelper.createEntityManager();
        AccuracyTestHelper.loadDB(em);

        Map<Long, String> validatorMapping = new HashMap<Long, String>();
        validatorMapping.put(1L, "^[a-z]*$");
        validatorMapping.put(2L, "^[0-9]*$");

        Map<Long, Boolean> predefinedKeys = new HashMap<Long, Boolean>();
        predefinedKeys.put(1L, true);
        predefinedKeys.put(2L, true);

        // initializes the instance
        instance = new DirectProjectMetadataValidatorImpl();
        instance.setEntityManager(em);
        instance.setLoggerName("accTest");
        instance.setValidatorMapping(validatorMapping);
        instance.setPredefinedKeys(predefinedKeys);

        // initializes projectMetadata
        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(3);
        List<DirectProjectMetadataPredefinedValue> predefinedValues = new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue1 = new DirectProjectMetadataPredefinedValue();
        predefinedValue1.setPredefinedMetadataValue("v 1");
        predefinedValues.add(predefinedValue1);
        DirectProjectMetadataPredefinedValue predefinedValue2 = new DirectProjectMetadataPredefinedValue();
        predefinedValue1.setPredefinedMetadataValue("v 2");
        predefinedValues.add(predefinedValue2);
        DirectProjectMetadataPredefinedValue predefinedValue3 = new DirectProjectMetadataPredefinedValue();
        predefinedValue1.setPredefinedMetadataValue("description");
        predefinedValues.add(predefinedValue3);

        key.setPredefinedValues(predefinedValues);

        projectMetadata = new DirectProjectMetadata();
        projectMetadata.setTcDirectProjectId(1);
        projectMetadata.setProjectMetadataKey(key);
        projectMetadata.setMetadataValue("description");
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        AccuracyTestHelper.clearDB(em);
        em = null;
    }

    /**
     * <p>
     * Accuracy test for DirectProjectMetadataValidatorImpl(). The instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test
    public void testCtor() throws Exception {
        instance = new DirectProjectMetadataValidatorImpl();

        assertNull("The entityManager should be null.", AccuracyTestHelper.getField(
            AbstractDirectProjectMetadataValidator.class, instance, "entityManager"));
        assertNull("The logger should be null.", AccuracyTestHelper.getField(
            AbstractDirectProjectMetadataValidator.class, instance, "logger"));
        assertNull("The validatorMapping should be null.", AccuracyTestHelper.getField(null, instance,
            "validatorMapping"));
        assertNull("The predefinedKeys should be null.", AccuracyTestHelper.getField(null, instance, "predefinedKeys"));
    }

    /**
     * <p>
     * Accuracy test for validate(DirectProjectMetadata projectMetadata). When the projectMetadata is valid and metadata
     * key's single is true , no exception thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testValidate1() throws Exception {
        projectMetadata.getProjectMetadataKey().setSingle(true);
        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Accuracy test for validate(DirectProjectMetadata projectMetadata). When the projectMetadata is valid and metadata
     * key's single is false and clientId is null , no exception thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testValidate2() throws Exception {
        projectMetadata.getProjectMetadataKey().setSingle(false);
        projectMetadata.getProjectMetadataKey().setClientId(null);

        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Accuracy test for validate(DirectProjectMetadata projectMetadata). When the projectMetadata is valid and metadata
     * key's single is false and clientId is not null , no exception thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testValidate3() throws Exception {
        projectMetadata.getProjectMetadataKey().setClientId(1L);
        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Accuracy test for setValidatorMapping(Map&lt;Long, String&gt; validatorMapping). The validatorMapping should be
     * set.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testSetValidatorMapping() throws Exception {
        Map<Long, String> validatorMapping = new HashMap<Long, String>();
        instance.setValidatorMapping(validatorMapping);
        assertSame("The predefinedKeys should be correctly.", validatorMapping, AccuracyTestHelper.getField(null,
            instance, "validatorMapping"));
    }

    /**
     * <p>
     * Accuracy test for setPredefinedKeys(Map&lt;Long, Boolean&gt; predefinedKeys). The predefinedKeys should be set.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testSetPredefinedKeys() throws Exception {
        Map<Long, Boolean> predefinedKeys = new HashMap<Long, Boolean>();
        instance.setPredefinedKeys(predefinedKeys);
        assertSame("The predefinedKeys should be correctly.", predefinedKeys, AccuracyTestHelper.getField(null,
            instance, "predefinedKeys"));
    }

    /**
     * <p>
     * Accuracy test for checkInitialization() method. When the instance is configured properly, no exception should be
     * thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testCheckInitialization() throws Exception {
        instance.checkInitialization();
    }
}