/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * This is a convenience class for creating filters to search for positions that match specific properties and values.
 * Searches can be done on a name, description, teamId, resourceId, projectId, published, and filled value, or to test
 * for the presence of a specific custom property key. The constants used as search criterion names are provided by
 * this class for each factory method.
 * </p>
 * <p>
 * To combine filters, the UtilityFilterFactory can be used.
 * </p>
 * <p>
 * Thread Safety: This is a static, immutable thread-safe class.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class PositionFilterFactory {

    /**
     * Represents the field name of the search criterion for a position name. Used in the createNameFilter factory
     * method.
     */
    public static final String NAME = "name";

    /**
     * Represents the field name of the search criterion for a position description. Used in the
     * createDescriptionFilter factory method.
     */
    public static final String DESCRIPTION = "description";

    /**
     * Represents the field name of the search criterion for a position's resource ID. Used in the
     * createResourceIdFilter factory method.
     */
    public static final String RESOURCE_ID = "resourceId";

    /**
     * Represents the field name of the search criterion for a position's team ID. Used in the createTeamIdFilter
     * factory method.
     */
    public static final String TEAM_ID = "teamId";

    /**
     * Represents the field name of the search criterion for a position's project ID. Used in the
     * createProjectIdFilter factory method.
     */
    public static final String PROJECT_ID = "projectId";

    /**
     * Represents the field name of the search criterion for a position flag of either being published or not. Used in
     * the createPublishedFilter factory method.
     */
    public static final String PUBLISHED = "published";

    /**
     * Represents the field name of the search criterion for a position flag of either being filled or not. Used in
     * the createFilledFilter factory method.
     */
    public static final String FILLED = "filled";

    /**
     * Represents the field name of the search criterion for a position custom property name. Used in the
     * createCustomPropertyNameFilter factory method.
     */
    public static final String CUSTOM_PROPERTY_NAME = "key";

    /**
     * Empty private constructor.
     */
    private PositionFilterFactory() {
        // your code here
    }

    /**
     * Create a filter that returns all positions with the given name.
     * @return Filter that matches the passed criterion
     * @param name
     *            The name to match positions with
     * @throws IllegalArgumentException
     *             if name is null/empty
     */
    public static Filter createNameFilter(String name) {
        Helper.assertStringNotNullOrEmpty(name, "name");
        return new EqualToFilter(NAME, name);
    }

    /**
     * Create a filter that returns all positions with the given description.
     * @return Filter that matches the passed criterion
     * @param description
     *            The description to match positions with
     */
    public static Filter createDescriptionFilter(String description) {
        if (description != null) {
            return new EqualToFilter(DESCRIPTION, description);
        } else {
            return new NullFilter(DESCRIPTION);
        }
    }

    /**
     * Create a filter that returns all positions with the given resource ID.
     * @return Filter that matches the passed criterion
     * @param resourceId
     *            The resource ID to match positions with
     * @throws IllegalArgumentException
     *             if resourceId is negative
     */
    public static Filter createResourceIdFilter(long resourceId) {
        Helper.assertNonNegative(resourceId, "resourceId");
        return new EqualToFilter(RESOURCE_ID, new Long(resourceId));
    }

    /**
     * Create a filter that returns all positions with the given team ID.
     * @return Filter that matches the passed criterion
     * @param teamId
     *            The team ID to match positions with
     * @throws IllegalArgumentException
     *             if teamId is negative
     */
    public static Filter createTeamIdFilter(long teamId) {
        Helper.assertNonNegative(teamId, "teamId");
        return new EqualToFilter(TEAM_ID, new Long(teamId));
    }

    /**
     * Create a filter that returns all positions with the given project ID.
     * @return Filter that matches the passed criterion
     * @param projectId
     *            The project ID to match positions with
     * @throws IllegalArgumentException
     *             if projectId is negative
     */
    public static Filter createProjectIdFilter(long projectId) {
        Helper.assertNonNegative(projectId, "projectId");
        return new EqualToFilter(PROJECT_ID, new Long(projectId));
    }

    /**
     * Create a filter that returns all positions with the given published flag value.
     * @return Filter that matches the passed criterion
     * @param published
     *            The published flag to match teams with
     */
    public static Filter createPublishedFilter(boolean published) {
        return new EqualToFilter(PUBLISHED, published ? UtilityFilterFactory.BOOLEAN_TRUE
            : UtilityFilterFactory.BOOLEAN_FALSE);
    }

    /**
     * Create a filter that returns all positions with the given filled flag value.
     * @return Filter that matches the passed criterion
     * @param filled
     *            The filled flag to match teams with
     */
    public static Filter createFilledFilter(boolean filled) {
        return new EqualToFilter(FILLED, filled ? UtilityFilterFactory.BOOLEAN_TRUE
            : UtilityFilterFactory.BOOLEAN_FALSE);
    }

    /**
     * Create a filter that returns all positions that have a custom property with the given name.
     * @return Filter that matches the passed criterion
     * @param name
     *            The name of a custom property to match Teams with
     * @throws IllegalArgumentException
     *             if name is null/empty
     */
    public static Filter createCustomPropertyNameFilter(String name) {
        Helper.assertStringNotNullOrEmpty(name, "name");
        return new EqualToFilter(CUSTOM_PROPERTY_NAME, name);
    }
}
