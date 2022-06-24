/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.metadata.TestsHelper;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;

/**
 * <p>
 * Unit tests for {@link DirectProjectMetadataKeyValidatorImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DirectProjectMetadataKeyValidatorImplUnitTests {
    /**
     * <p>
     * Represents the <code>DirectProjectMetadataKeyValidatorImpl</code> instance used in tests.
     * </p>
     */
    private DirectProjectMetadataKeyValidatorImpl instance;

    /**
     * <p>
     * Represents the project metadata key used in tests.
     * </p>
     */
    private DirectProjectMetadataKey projectMetadataKey;
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
        return new JUnit4TestAdapter(DirectProjectMetadataKeyValidatorImplUnitTests.class);
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

        instance = new DirectProjectMetadataKeyValidatorImpl();
        instance.setEntityManager(em);
        instance.setLoggerName("loggerName");

        projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setName("name3");
        projectMetadataKey.setDescription("some text");
        projectMetadataKey.setGrouping(null);
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setSingle(true);
        projectMetadataKey.setPredefinedValues(Arrays.asList(new DirectProjectMetadataPredefinedValue(),
            new DirectProjectMetadataPredefinedValue()));
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
     * Accuracy test for the constructor <code>DirectProjectMetadataKeyValidatorImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DirectProjectMetadataKeyValidatorImpl();

        assertNull("'entityManager' should be correct.", TestsHelper.getField(instance, "entityManager"));
        assertNull("'logger' should be correct.", TestsHelper.getField(instance, "logger"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_validate_1() throws Exception {
        instance.validate(projectMetadataKey);

        // Good
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_validate_2() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(true);
        projectMetadataKey.setPredefinedValues(null);

        instance.validate(projectMetadataKey);

        // Good
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_validate_3() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(true);
        projectMetadataKey.setPredefinedValues(new ArrayList<DirectProjectMetadataPredefinedValue>());

        instance.validate(projectMetadataKey);

        // Good
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_validate_projectMetadataKeyNull() throws Exception {
        projectMetadataKey = null;

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey.getName() is <code>null</code>.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyNameNull() throws Exception {
        projectMetadataKey.setName(null);

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey.getName() is empty.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyNameEmpty() throws Exception {
        projectMetadataKey.setName(TestsHelper.EMPTY_STRING);

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey.getName() already exist.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyNameExist() throws Exception {
        projectMetadataKey.setName("name1");

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey.getGrouping() is not <code>null</code>.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyGroupingNotNull() throws Exception {
        projectMetadataKey.setGrouping(true);

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey.getName() is too long.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyNameTooLong() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setName("012345678901234567890123456789");

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey.getGrouping() is <code>null</code>.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyGroupingNull() throws Exception {
        projectMetadataKey.setClientId(1L);

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Failure test for the method <code>validate(DirectProjectMetadataKey projectMetadataKey)</code> with
     * projectMetadataKey.getPredefinedValues() is not null and not empty.<br>
     * <code>ValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ValidationException.class)
    public void test_validate_projectMetadataKeyPredefinedValuesInvalid() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(true);

        instance.validate(projectMetadataKey);
    }
}
