/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.stresstests;

import javax.persistence.EntityManager;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;

/**
 * <p>
 * Stress test cases for DirectProjectMetadataServiceImpl.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class DirectProjectMetadataServiceImplStressTests extends TestCase {
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
        StressTestsHelper.loadDB(entityManager);
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
     * <p>Tests the business methods in DirectProjectMetadataServiceImpl for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAPI() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            // Retrieve services
            DirectProjectMetadataService metadataService = (DirectProjectMetadataService)
            StressTestsHelper.APP_CONTEXT.getBean("directProjectMetadataService");

            // Consider the user id is retrieved from session
            int userId = 1;

            // Persist key entity
            DirectProjectMetadataKey key = new DirectProjectMetadataKey();
            key.setName("name3");
            key.setDescription("some text");
            key.setGrouping(null);
            key.setClientId(null);
            key.setSingle(true);
            key.setId(1);

            // Persist metadata entity
            DirectProjectMetadata metadata = new DirectProjectMetadata();
            metadata.setTcDirectProjectId(5);
            metadata.setProjectMetadataKey(key);
            metadata.setMetadataValue("088");

            // Create project metadata
            long metadataId = metadataService.createProjectMetadata(metadata, userId);

            DirectProjectMetadata directProjectMetadata = metadataService.getProjectMetadata(metadataId);

            assertEquals("Failed to create project MetaData.", "088", directProjectMetadata.getMetadataValue());
            assertEquals("Failed to create project MetaData.", 5, directProjectMetadata.getTcDirectProjectId());

            // Update metadata entity
            metadata.setTcDirectProjectId(8);
            metadataService.updateProjectMetadata(metadata, userId);

            directProjectMetadata = metadataService.getProjectMetadata(metadataId);
            assertEquals("Failed to update project MetaData.", 8, directProjectMetadata.getTcDirectProjectId());

            // Delete metadata entity
            metadataService.deleteProjectMetadata(metadata.getId(), userId);

            directProjectMetadata = metadataService.getProjectMetadata(metadataId);
            assertNull("Failed to delete project MetaData.", directProjectMetadata);

        }

        printResult("The business methods in DirectProjectMetadataServiceImpl", NUMBER);
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