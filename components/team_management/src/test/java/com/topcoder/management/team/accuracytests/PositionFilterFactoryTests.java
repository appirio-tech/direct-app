/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.team.accuracytests;

import com.topcoder.management.team.PositionFilterFactory;
import com.topcoder.management.team.UtilityFilterFactory;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

import junit.framework.TestCase;

/**
 * Accuracy test cases for the <code>PositionFilterFactory</code> class.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class PositionFilterFactoryTests extends TestCase {
    /**
     * Tests the <code>createNameFilter</code> method.
     */
    public void testCreateNameFilter() {
        Filter filter = PositionFilterFactory.createNameFilter("the boss");
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be NAME", PositionFilterFactory.NAME, etf.getName());
        assertEquals("the value should be 'the boss'", "the boss", etf.getValue());
    }

    /**
     * Tests the <code>createDescriptionFilter</code> method.
     */
    public void testCreateDescriptionFilter() {
        Filter filter = PositionFilterFactory.createDescriptionFilter("the top position");
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be DESCRIPTION", PositionFilterFactory.DESCRIPTION, etf.getName());
        assertEquals("the value should be 'the top position'", "the top position", etf.getValue());
    }

    /**
     * Tests the <code>createResourceIdFilter</code> method.
     */
    public void testCreateResourceIdFilter() {
        Filter filter = PositionFilterFactory.createResourceIdFilter(5);
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be RESOURCE_ID", PositionFilterFactory.RESOURCE_ID, etf.getName());
        assertEquals("the value should be 5", new Long(5), etf.getValue());
    }

    /**
     * Tests the <code>createTeamIdFilter</code> method.
     */
    public void testCreateTeamIdFilter() {
        Filter filter = PositionFilterFactory.createTeamIdFilter(5);
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be TEAM_ID", PositionFilterFactory.TEAM_ID, etf.getName());
        assertEquals("the value should be 5", new Long(5), etf.getValue());
    }

    /**
     * Tests the <code>createPublishedFilter</code> method.
     */
    public void testCreatePublishedFilter() {
        Filter filter = PositionFilterFactory.createPublishedFilter(true);
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be PUBLISHED", PositionFilterFactory.PUBLISHED, etf.getName());
        assertEquals("the value should be BOOLEAN_TRUE", UtilityFilterFactory.BOOLEAN_TRUE, etf.getValue());

        filter = PositionFilterFactory.createPublishedFilter(false);
        etf = (EqualToFilter) filter;
        assertEquals("the name should be PUBLISHED", PositionFilterFactory.PUBLISHED, etf.getName());
        assertEquals("the value should be BOOLEAN_FALSE", UtilityFilterFactory.BOOLEAN_FALSE, etf.getValue());
    }

    /**
     * Tests the <code>createFilledFilter</code> method.
     */
    public void testCreateFilledFilter() {
        Filter filter = PositionFilterFactory.createFilledFilter(true);
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be FILLED", PositionFilterFactory.FILLED, etf.getName());
        assertEquals("the value should be BOOLEAN_TRUE", UtilityFilterFactory.BOOLEAN_TRUE, etf.getValue());

        filter = PositionFilterFactory.createFilledFilter(false);
        etf = (EqualToFilter) filter;
        assertEquals("the name should be FILLED", PositionFilterFactory.FILLED, etf.getName());
        assertEquals("the value should be BOOLEAN_FALSE", UtilityFilterFactory.BOOLEAN_FALSE, etf.getValue());
    }

    /**
     * Tests the <code>createCustomPropertyNameFilter</code> method.
     */
    public void testCreateCustomPropertyNameFilter() {
        Filter filter = PositionFilterFactory.createCustomPropertyNameFilter("custom");
        EqualToFilter etf = (EqualToFilter) filter;
        assertEquals("the name should be CUSTOM_PROPERTY_NAME",
                     PositionFilterFactory.CUSTOM_PROPERTY_NAME, etf.getName());
        assertEquals("the value should be 'custom'", "custom", etf.getValue());
    }
}
