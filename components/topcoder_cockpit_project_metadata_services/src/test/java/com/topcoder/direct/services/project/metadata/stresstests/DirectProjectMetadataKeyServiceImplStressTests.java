/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.stresstests;

import javax.persistence.EntityManager;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyService;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;

/**
 * <p>
 * Stress test cases for DirectProjectMetadataKeyServiceImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DirectProjectMetadataKeyServiceImplStressTests extends TestCase {
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long NUMBER = 500;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

    /**
     * <p>
     * The EntityManager instance for testing.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        entityManager = StressTestsHelper.getEntityManager();
        StressTestsHelper.clearDB(entityManager);
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        StressTestsHelper.clearDB(entityManager);
        entityManager = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DirectProjectMetadataServiceImplStressTests.class);
    }

    /**
     * <p>Tests the business methods in DirectProjectMetadataKeyServiceImpl for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAPI() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            // Retrieve services
            DirectProjectMetadataKeyService metadataKeyService = (DirectProjectMetadataKeyService)
            StressTestsHelper.APP_CONTEXT.getBean("directProjectMetadataKeyService");

            // Consider the user id is retrieved from session
            int userId = 1;

            // Persist key entity
            DirectProjectMetadataKey projectMetadataKey = new DirectProjectMetadataKey();
            projectMetadataKey.setName("name3");
            projectMetadataKey.setDescription("some text");
            projectMetadataKey.setGrouping(null);
            projectMetadataKey.setClientId(null);
            projectMetadataKey.setSingle(true);

            // Create project metadata key
            metadataKeyService.createProjectMetadataKey(projectMetadataKey, userId);

            DirectProjectMetadataKey result = metadataKeyService.getProjectMetadataKey(projectMetadataKey.getId());
            assertEquals("Failed to create project MetaData key.", "name3", result.getName());
            assertEquals("Failed to create project MetaData key.", "some text", result.getDescription());
            assertNull("Failed to create project MetaData key.", result.getClientId());
            assertNull("Failed to create project MetaData key.", result.getGrouping());
            assertTrue("Failed to create project MetaData key.", result.isSingle());

            // Update metadata key entity
            projectMetadataKey.setName("newName");
            metadataKeyService.updateProjectMetadataKey(projectMetadataKey, userId);
            result = metadataKeyService.getProjectMetadataKey(projectMetadataKey.getId());
            assertEquals("Failed to update project MetaData key.", "newName", result.getName());

            // Delete metadata key entity
            metadataKeyService.deleteProjectMetadataKey(projectMetadataKey.getId(), userId);
            result = metadataKeyService.getProjectMetadataKey(projectMetadataKey.getId());
            assertNull("Failed to delete project MetaData key.", result);
        }

        printResult("The business methods in DirectProjectMetadataKeyServiceImpl", NUMBER);
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    private void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }
}