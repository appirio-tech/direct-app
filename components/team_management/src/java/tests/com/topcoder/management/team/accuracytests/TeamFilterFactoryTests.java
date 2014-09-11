/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.TeamFilterFactory;
import com.topcoder.management.team.UtilityFilterFactory;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

import junit.framework.TestCase;

/**
 * Accuracy test cases for the <code>TeamFilterFactory</code> class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class TeamFilterFactoryTests extends TestCase {
    /**
     * Tests the <code>createNameFilter</code> method.
     */
    public void testCreateNameFilter() {
        Filter filter = TeamFilterFactory.createNameFilter("Team TopCoder");
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be NAME", TeamFilterFactory.NAME, etf.getName());
        assertEquals("the value should be 'Team TopCoder'", "Team TopCoder", etf.getValue());
    }

    /**
     * Tests the <code>createDescriptionFilter</code> method.
     */
    public void testCreateDescriptionFilter() {
        Filter filter = TeamFilterFactory.createDescriptionFilter("Team TopCoder");
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be DESCRIPTION", TeamFilterFactory.DESCRIPTION, etf.getName());
        assertEquals("the value should be 'Team TopCoder'", "Team TopCoder", etf.getValue());
    }

    /**
     * Tests the <code>createDescriptionFilter</code> method with a <code>null</code> argument.
     */
    public void testCreateNameFilterNull() {
        Filter filter = TeamFilterFactory.createDescriptionFilter(null);
        NullFilter nf = (NullFilter) filter;
        assertEquals("the name should be DESCRIPTION", TeamFilterFactory.DESCRIPTION, nf.getName());
    }

    /**
     * Tests the <code>createProjectIdFilter</code> method.
     */
    public void testCreateProjectIdFilter() {
        Filter filter = TeamFilterFactory.createProjectIdFilter(5);
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be PROJECT_ID", TeamFilterFactory.PROJECT_ID, etf.getName());
        assertEquals("the value should be 5", new Long(5), etf.getValue());
    }

    /**
     * Tests the <code>createFinalizedFilter</code> method.
     */
    public void testCreateFinalizedFilter() {
        Filter filter = TeamFilterFactory.createFinalizedFilter(true);
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be FINALIZED", TeamFilterFactory.FINALIZED, etf.getName());
        assertEquals("the value should be BOOLEAN_TRUE", UtilityFilterFactory.BOOLEAN_TRUE, etf.getValue());

        filter = TeamFilterFactory.createFinalizedFilter(false);
        etf = (EqualToFilter) filter;
        assertEquals("the name should be FINALIZED", TeamFilterFactory.FINALIZED, etf.getName());
        assertEquals("the value should be BOOLEAN_FALSE", UtilityFilterFactory.BOOLEAN_FALSE, etf.getValue());
    }

    /**
     * Tests the <code>createCaptainResourceIdFilter</code> method.
     */
    public void testCreateCaptainResourceIdFilter() {
        Filter filter = TeamFilterFactory.createCaptainResourceIdFilter(5);
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be CAPTAIN_RESOURCE_ID", TeamFilterFactory.CAPTAIN_RESOURCE_ID, etf.getName());
        assertEquals("the value should be 5", new Long(5), etf.getValue());
    }

    /**
     * Tests the <code>createCustomPropertyNameFilter</code> method.
     */
    public void testCreateCustomPropertyNameFilter() {
        Filter filter = TeamFilterFactory.createCustomPropertyNameFilter("custom");
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be CUSTOM_PROPERTY_NAME", TeamFilterFactory.CUSTOM_PROPERTY_NAME, etf.getName());
        assertEquals("the value should be 'custom'", "custom", etf.getValue());
    }
}
