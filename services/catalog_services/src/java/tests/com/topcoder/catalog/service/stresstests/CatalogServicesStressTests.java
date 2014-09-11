/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.stresstests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.SearchCriteria;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Stress test cases for catalog services component.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class CatalogServicesStressTests extends TestCase {
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long NUMBER = 1000;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

    /**
     * <p>AssetDTO instance for testing.</p>
     */
    private AssetDTO assertDTO;

    /**
     * <p>SearchCriteria instance for testing.</p>
     */
    private SearchCriteria criteria;

    /**
     * <p>Category list for testing.</p>
     */
    private List<Category> categories;

    /**
     * <p>Category instance for testing.</p>
     */
    private Category category;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        category = new Category();
        categories = new ArrayList<Category>();
        categories.add(category);

        assertDTO = new AssetDTO();
        criteria = new SearchCriteria(new Long(1), new Long(1), categories, "name", "description", null, null, null, null);
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        criteria = null;
        assertDTO = null;
        categories = null;
        category = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CatalogServicesStressTests.class);
    }

    /**
     * <p>Tests AssetDTO#setName(String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetName() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setName("name");
            assertEquals("Failed to set name.", "name", assertDTO.getName());
        }

        printResult("AssetDTO#setName", NUMBER);

    }

    /**
     * <p>Tests AssetDTO#setCategories(List&lt;Category&gt;) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetCategories() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setCategories(categories);
            assertEquals("Failed to set categories.", 1, assertDTO.getCategories().size());
            assertSame("Failed to set categories.", category, assertDTO.getCategories().get(0));
        }

        printResult("AssetDTO#setCategories", NUMBER);

    }

    /**
     * <p>Tests AssetDTO#setDetailedDescription(String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetDetailedDescription() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setDetailedDescription("detailedDescription");
            assertEquals("Failed to set detailed description.", "detailedDescription",
                assertDTO.getDetailedDescription());
        }

        printResult("AssetDTO#setDetailedDescription", NUMBER);

    }

    /**
     * <p>Tests AssetDTO#setForum(CompForum) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetForum() throws Exception {
        CompForum forum = new CompForum();
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setForum(forum);
            assertSame("Failed to set forum.", forum, assertDTO.getForum());
        }

        printResult("AssetDTO#setForum", NUMBER);

    }

    /**
     * <p>Tests AssetDTO#setFunctionalDescription(String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetFunctionalDescription() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setFunctionalDescription("functionalDescription");
            assertEquals("Failed to set functional description.", "functionalDescription",
                assertDTO.getFunctionalDescription());
        }

        printResult("AssetDTO#setFunctionalDescription", NUMBER);

    }

    /**
     * <p>Tests AssetDTO#setTechnologies(List&lt;Technology&gt;) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetTechnologies() throws Exception {
        Technology technology = new Technology();
        List<Technology> technologies = new ArrayList<Technology>();
        technologies.add(technology);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setTechnologies(technologies);
            assertEquals("Failed to set technologies.", 1, assertDTO.getTechnologies().size());
            assertSame("Failed to set technologies.", technology, assertDTO.getTechnologies().get(0));
        }

        printResult("AssetDTO#setTechnologies", NUMBER);

    }

    /**
     * <p>Tests AssetDTO#setVersionNumber(Long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetVersionNumber() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setVersionNumber(new Long(1));
            assertEquals("Failed to set version number.", new Long(1), assertDTO.getVersionNumber());
        }

        printResult("AssetDTO#setVersionNumber", NUMBER);

    }

    /**
     * <p>Tests AssetDTO#setClientIds(List&lt;Long&gt;) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetClientIds() throws Exception {
        List<Long> clientId = new ArrayList<Long>();
        clientId.add(new Long(2));

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setClientIds(clientId);
            assertEquals("Failed to set client ids.", 1, assertDTO.getClientIds().size());
            assertEquals("Failed to set client ids.", new Long(2), assertDTO.getClientIds().get(0));
        }

        printResult("AssetDTO#setClientIds", NUMBER);
    }

    /**
     * <p>Tests AssetDTO#setLink(CompLink) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testSetLink() throws Exception {
        CompLink link = new CompLink();
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertDTO.setLink(link);
            assertSame("Failed to set link.", link, assertDTO.getLink());
        }

        printResult("AssetDTO#setLink", NUMBER);

    }

    /**
     * <p>Tests SearchCriteria#getCategories() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetCategories() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertEquals("Failed to get categories.", 1, criteria.getCategories().size());
            assertSame("Failed to get categories.", category, criteria.getCategories().get(0));
        }

        printResult("SearchCriteria#getCategories", NUMBER);

    }

    /**
     * <p>Tests SearchCriteria#getClientId() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetClientId() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertEquals("Failed to get client id.", new Long(1), criteria.getClientId());
        }

        printResult("SearchCriteria#getClientId", NUMBER);

    }

    /**
     * <p>Tests SearchCriteria#getDescription() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetDescription() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertEquals("Failed to get description.", "description", criteria.getDescription());
        }

        printResult("SearchCriteria#getDescription", NUMBER);

    }

    /**
     * <p>Tests SearchCriteria#getName() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetName() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertEquals("Failed to get name.", "name", criteria.getName());
        }

        printResult("SearchCriteria#getName", NUMBER);

    }

    /**
     * <p>Tests SearchCriteria#getUserId() for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetUserId() throws Exception {
        start();

        for (int i = 0; i < NUMBER; i++) {
            assertEquals("Failed to get user id.", new Long(1), criteria.getUserId());
        }

        printResult("SearchCriteria#getUserId", NUMBER);

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