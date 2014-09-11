/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.util.Arrays;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

/**
 * <p>
 * A utility factory for combining filters without needing to instantiate specific Filter classes. One can associate
 * filters by AND, OR, or NOT. Any filters, including the ones created in the TeamFilterFactory and
 * PositionFilterFactory can be passed.
 * </p>
 * <p>
 * This class also provides the used constants for boolean values. Since the SearchBuilder does not support passing
 * boolean values, boolean filters are constructed with the provided boolean constants for true and false.
 * </p>
 * <p>
 * Thread Safety: This is a static, immutable thread-safe class.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class UtilityFilterFactory {

    /**
     * Represents a Comparable String value used for boolean searches, since Search Builder does not support
     * boolean-enabled filters. This value is a marker for a true boolean value.
     */
    public static final String BOOLEAN_TRUE = "true";

    /**
     * Represents a Comparable String value used for boolean searches, since Search Builder does not support
     * boolean-enabled filters. This value is a marker for a false boolean value.
     */
    public static final String BOOLEAN_FALSE = "false";

    /**
     * Empty private constructor.
     */
    private UtilityFilterFactory() {
        // Does nothing.
    }

    /**
     * Creates an AND filter with the passed filters.
     * @return An AndFilter of the passed filters
     * @param first
     *            The first filter to put in the AndFilter
     * @param second
     *            The second filter to put in the AndFilter
     * @throws IllegalArgumentException
     *             if either filter is null
     */
    public static Filter createAndFilter(Filter first, Filter second) {
        return new AndFilter(first, second);
    }

    /**
     * Creates an AND filter with the passed filters.
     * @return An AndFilter of the passed filters
     * @param filters
     *            The array of filters put in the AndFilter
     * @throws IllegalArgumentException
     *             if filters is null, empty, or contains null elements
     */
    public static Filter createAndFilter(Filter[] filters) {
        Helper.assertObjectNotNull(filters, "filters");
        return new AndFilter(Arrays.asList(filters));
    }

    /**
     * Creates an OR filter with the passed filters.
     * @return An OrFilter of the passed filters
     * @param first
     *            The first filter to put in the OrFilter
     * @param second
     *            The second filter to put in the OrFilter
     * @throws IllegalArgumentException
     *             if either filter is null
     */
    public static Filter createOrFilter(Filter first, Filter second) {
        return new OrFilter(first, second);
    }

    /**
     * Creates an OR filter with the passed filters.
     * @return An OrFilter of the passed filters
     * @param filters
     *            The array of filters put in the OrFilter
     * @throws IllegalArgumentException
     *             if filters is null, empty, or contains null elements
     */
    public static Filter createOrFilter(Filter[] filters) {
        Helper.assertObjectNotNull(filters, "filters");
        return new OrFilter(Arrays.asList(filters));
    }

    /**
     * Creates a NOT filter of given filter.
     * @return A negation filter of the passed filter
     * @param filter
     *            The filter to negate
     * @throws IllegalArgumentException
     *             if filter is null
     */
    public static Filter createNotFilter(Filter filter) {
        return new NotFilter(filter);
    }
}
