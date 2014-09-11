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

import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.TestsHelper;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;

/**
 * <p>
 * Unit tests for {@link DirectProjectMetadataValidatorImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DirectProjectMetadataValidatorImplUnitTests {
    /**
     * <p>
     * Represents the <code>DirectProjectMetadataValidatorImpl</code> instance used in tests.
     * </p>
     */
    private DirectProjectMetadataValidatorImpl instance;

    /**
     * <p>
     * Represents the project metadata used in tests.
     * </p>
     */
    private DirectProjectMetadata projectMetadata;

    /**
     * <p>
     * Represents the validator mapping used in tests.
     * </p>
     */
    private Map<Long, String> validatorMapping;

    /**
     * <p>
     * Represents the predefined keys used in tests.
     * </p>
     */
    private Map<Long, Boolean> predefinedKeys;

    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager em;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DirectProjectMetadataValidatorImplUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        em = TestsHelper.getEntityManager();
        TestsHelper.clearDB(em);
        TestsHelper.loadDB(em);

        validatorMapping = new HashMap<Long, String>();
        validatorMapping.put(1L, "^0*[1-9][0-9]*$");
        validatorMapping.put(2L, "^xx$");
        validatorMapping.put(3L, "^value$");
        predefinedKeys = new HashMap<Long, Boolean>();
        predefinedKeys.put(1L, true);
        predefinedKeys.put(2L, true);
        predefinedKeys.put(3L, true);

        instance = new DirectProjectMetadataValidatorImpl();
        instance.setEntityManager(em);
        instance.setLoggerName("loggerName");
        instance.setValidatorMapping(validatorMapping);
        instance.setPredefinedKeys(predefinedKeys);

        DirectProjectMetadataKey key = new DirectProjectMetadataKey();
        key.setId(3);
        key.setSingle(true);
        List<DirectProjectMetadataPredefinedValue> predefinedValues =
            new ArrayList<DirectProjectMetadataPredefinedValue>();
        DirectProjectMetadataPredefinedValue predefinedValue = new DirectProjectMetadataPredefinedValue();
        predefinedValue.setPredefinedMetadataValue("value");
        predefinedValues.add(predefinedValue);
        key.setPredefinedValues(predefinedValues);

        projectMetadata = new DirectProjectMetadata();
        projectMetadata.setTcDirectProjectId(1);
        projectMetadata.setProjectMetadataKey(key);
        projectMetadata.setMetadataValue("value");
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        TestsHelper.clearDB(em);
        em = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DirectProjectMetadataValidatorImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DirectProjectMetadataValidatorImpl();

        assertNull("'entityManager' should be correct.", TestsHelper.getField(instance, "entityManager"));
        assertNull("'logger' should be correct.", TestsHelper.getField(instance, "logger"));

        assertNull("'validatorMapping' should be correct.",
            TestsHelper.getField(instance, "validatorMapping"));
        assertNull("'predefinedKeys' should be correct.",
            TestsHelper.getField(instance, "predefinedKeys"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_validate_1() throws Exception {
        instance.validate(projectMetadata);

        // Good
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_validate_2() throws Exception {
        validatorMapping.remove(1L);
        predefinedKeys.remove(1L);

        instance.validate(projectMetadata);

        // Good
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_validate_3() throws Exception {
        projectMetadata.getProjectMetadataKey().setClientId(1L);

        instance.validate(projectMetadata);

        // Good
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_validate_4() throws Exception {
        projectMetadata.getProjectMetadataKey().setSingle(false);

        instance.validate(projectMetadata);

        // Good
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code> with
     * projectMetadata is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_validate_projectMetadataNull() throws Exception {
        projectMetadata = null;

        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code> with
     * projectMetadata.getProjectMetadataKey() is <code>null</code>.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyNull() throws Exception {
        projectMetadata.setProjectMetadataKey(null);

        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code> with
     * projectMetadata.getProjectMetadataKey() already exists.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyExist() throws Exception {
        projectMetadata.getProjectMetadataKey().setId(1L);

        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code> with
     * metadata value is <code>null</code>.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataValueNull() throws Exception {
        projectMetadata.setMetadataValue(null);

        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code> with
     * metadata value doesn't match the pattern.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataValueInvalid1() throws Exception {
        validatorMapping.put(3L, "invalid_\n_value");

        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadata)</code> with
     * metadata value doesn't match any of the predefined values.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataValueInvalid2() throws Exception {
        projectMetadata.getProjectMetadataKey().getPredefinedValues().get(0)
            .setPredefinedMetadataValue("invalid_\n_value");

        instance.validate(projectMetadata);
    }

    /**
     * <p>
     * Accuracy test for the method <code>setValidatorMapping(Map&lt;Long, String&gt; validatorMapping)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setValidatorMapping() {
        assertSame("'setValidatorMapping' should be correct.",
            validatorMapping, TestsHelper.getField(instance, "validatorMapping"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPredefinedKeys(Map&lt;Long, Boolean&gt; predefinedKeys)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPredefinedKeys() {
        assertSame("'setPredefinedKeys' should be correct.",
            predefinedKeys, TestsHelper.getField(instance, "predefinedKeys"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInitialization()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_checkInitialization() throws Exception {
        instance.setEntityManager(TestsHelper.getEntityManager());

        instance.checkInitialization();

        // Good
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with entityManager is <code>null</code>.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_entityManagerNull() throws Exception {
        instance.setEntityManager(null);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with validatorMapping is <code>null</code>.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_validatorMappingNull() throws Exception {
        validatorMapping = null;
        instance.setValidatorMapping(validatorMapping);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with validatorMapping contains
     * <code>null</code> key.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_validatorMappingContainsNullKey() throws Exception {
        validatorMapping.put(null, "value");
        instance.setValidatorMapping(validatorMapping);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with validatorMapping contains
     * <code>null</code> value.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_validatorMappingContainsNullValue() throws Exception {
        validatorMapping.put(Long.MAX_VALUE, null);
        instance.setValidatorMapping(validatorMapping);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with validatorMapping contains
     * empty value.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_validatorMappingContainsEmptyValue() throws Exception {
        validatorMapping.put(Long.MAX_VALUE, TestsHelper.EMPTY_STRING);
        instance.setValidatorMapping(validatorMapping);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with validatorMapping contains
     * invalid value.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_validatorMappingContainsInvalidValue() throws Exception {
        validatorMapping.put(Long.MAX_VALUE, "[z-a]");
        instance.setValidatorMapping(validatorMapping);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with predefinedKeys contains
     * <code>null</code> key.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_predefinedKeysContainsNullKey() throws Exception {
        predefinedKeys.put(null, true);
        instance.setPredefinedKeys(predefinedKeys);

        instance.checkInitialization();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInitialization()</code> with predefinedKeys contains
     * <code>null</code> value.<br>
     * <code>ConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ConfigurationException.class)
    public void test_checkInitialization_predefinedKeysContainsNullValue() throws Exception {
        predefinedKeys.put(Long.MAX_VALUE, null);
        instance.setPredefinedKeys(predefinedKeys);

        instance.checkInitialization();
    }
}
