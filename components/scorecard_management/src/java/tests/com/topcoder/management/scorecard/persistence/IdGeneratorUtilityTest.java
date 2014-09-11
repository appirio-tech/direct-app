/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

/**
 * Unit tests for IdGeneratorUtility class.
 *
 * @author kr00tki
 * @version 1.0
 */
public class IdGeneratorUtilityTest extends DbTestCase {

    /**
     * Test IdGeneratorUtility.loadIdGenerators(String).
     *
     * @throws Exception to Junit.
     */
    public void testLoadIdGenerators() throws Exception {
        IdGeneratorUtility.loadIdGenerators(NAMESPACE);
    }

    /**
     * Tests IdGeneratorUtility.getGroupIdGenerator(). Checks if the IdGenerator is returned.
     */
    public void testGetGroupIdGenerator() {
        assertNotNull("Should not be null.", IdGeneratorUtility.getGroupIdGenerator());
    }

    /**
     * Test IdGeneratorUtility.getQuestionIdGenerator(). Checks if the IdGenerator is returned.
     */
    public void testGetQuestionIdGenerator() {
        assertNotNull("Should not be null.", IdGeneratorUtility.getQuestionIdGenerator());
    }

    /**
     * Test IdGeneratorUtility.getScorecardIdGenerator(). Checks if the IdGenerator is returned.
     */
    public void testGetScorecardIdGenerator() {
        assertNotNull("Should not be null.", IdGeneratorUtility.getScorecardIdGenerator());
    }

    /**
     * Test IdGeneratorUtility.getSectionIdGenerator(). Checks if the IdGenerator is returned.
     */
    public void testGetSectionIdGenerator() {
        assertNotNull("Should not be null.", IdGeneratorUtility.getScorecardIdGenerator());
    }

}
