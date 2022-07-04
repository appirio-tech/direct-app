/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.accuracytests.impl;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.direct.services.project.metadata.impl.AbstractDirectProjectMetadataValidator;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyValidatorImpl;

/**
 * <p>
 * Accuracy test for DirectProjectMetadataKeyValidatorImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DirectProjectMetadataKeyValidatorImplAccuracyTests {
    /**
     * <p>
     * Represents the instance of DirectProjectMetadataKeyValidatorImpl used in tests.
     * </p>
     */
    private DirectProjectMetadataKeyValidatorImpl instance;

    /**
     * <p>
     * Represents the instance of DirectProjectMetadataKey used in tests.
     * </p>
     */
    private DirectProjectMetadataKey projectMetadataKey;

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
        return new JUnit4TestAdapter(DirectProjectMetadataKeyValidatorImplAccuracyTests.class);
    }

    /**
     * <p>
     * Sets up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        em = AccuracyTestHelper.createEntityManager();
        AccuracyTestHelper.loadDB(em);

        instance = new DirectProjectMetadataKeyValidatorImpl();
        instance.setEntityManager(em);
        instance.setLoggerName("acctest");

        projectMetadataKey = new DirectProjectMetadataKey();
        projectMetadataKey.setName("key1");
        projectMetadataKey.setDescription("desc1");
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
     * Accuracy test for DirectProjectMetadataKeyValidatorImpl(). The instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test
    public void testCtor() throws Exception {
        instance = new DirectProjectMetadataKeyValidatorImpl();

        assertNull("The entityManager should be null.", AccuracyTestHelper.getField(
            AbstractDirectProjectMetadataValidator.class, instance, "entityManager"));
        assertNull("The logger should be null.", AccuracyTestHelper.getField(
            AbstractDirectProjectMetadataValidator.class, instance, "logger"));
    }

    /**
     * <p>
     * Accuracy test for validate(DirectProjectMetadataKey projectMetadataKey). The projectMetadataKey is valid, no
     * exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testValidate1() throws Exception {
        projectMetadataKey.setClientId(null);
        projectMetadataKey.setGrouping(null);
        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Accuracy test for validate(DirectProjectMetadataKey projectMetadataKey). The projectMetadataKey is valid, no
     * exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testValidate2() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(Boolean.TRUE);
        projectMetadataKey.setPredefinedValues(null);

        instance.validate(projectMetadataKey);
    }

    /**
     * <p>
     * Accuracy test for validate(DirectProjectMetadataKey projectMetadataKey). The projectMetadataKey is valid, no
     * exception should be thrown.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testValidate3() throws Exception {
        projectMetadataKey.setClientId(1L);
        projectMetadataKey.setGrouping(Boolean.FALSE);
        projectMetadataKey.setPredefinedValues(new ArrayList<DirectProjectMetadataPredefinedValue>());
        instance.validate(projectMetadataKey);
    }
}