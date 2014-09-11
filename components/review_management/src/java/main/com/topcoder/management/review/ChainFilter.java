/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;


/**
 * <p>
 * This <code>ChainFilter</code> class provides the convenient methods to construct composite
 * filters with "chain" operation. It will be used for complex search conditions.
 * It supports and, or and not operations.
 * </p>
 *
 * <p>Examples of usage of <code>ChainFilter</code> :
 * <pre>
 * If the search condition is that "search for the reviews which have been committed,
 * reviewer id is 10000, or project id is greater than 10001", we can construct the
 * filter in this way:
 *
 * // Create the EqualToFilter for the first search condition.
 * ChainFilter cf = new ChainFilter(new EqualToFilter("committed", new Integer(1)));
 *
 * // Chain another EqualToFilter for the second search condition.
 * cf = cf.and(new EqualToFilter("reviewer", new Long(10000)));
 *
 * // Chain the GreaterThanFilter for the third search condition.
 * cf = cf.or(new GreaterThanFilter("project", new Long(10001)));
 * </pre>
 * </p>
 *
 * <p>
 * Thread safety: The class is thread-safe, since it is immutable.
 * </p>
 *
 * @author woodjhon, icyriver
 * @version 1.0
 */
public class ChainFilter {
    /**
     * <p>
     * This variable represents the instance of <code>Filter</code> used by this class. It will be
     * initialized in the constructor, and is immutable.
     * </p>
     *
     * <p>
     * Using <code>getFilter()</code> method to get the current filter.
     * </p>
     */
    private final Filter filter;

    /**
     * <p>
     * Creates the <code>ChainFilter</code> instance with the given instance of
     * <code>Filter</code>.
     * </p>
     *
     * @param filter the filter to set.
     *
     * @throws IllegalArgumentException if the passed filter is null.
     */
    public ChainFilter(Filter filter) {
        Helper.checkNull(filter, "filter");
        this.filter = filter;
    }

    /**
     * <p>
     * This method implements the 'and' operation on the passed filter. It will return a new
     * <code>ChainFilter</code> instance which combines current filter with the passed filter
     * by 'and' operation.
     * </p>
     *
     * @param rhs the filter to be chained.
     *
     * @return the current filter combined with the passed filter by 'and' operation.
     *
     * @throws IllegalArgumentException if the passed filter is null.
     */
    public ChainFilter and(Filter rhs) {
        Helper.checkNull(rhs, "rhs");
        return new ChainFilter(new AndFilter(this.filter, rhs));
    }

    /**
     * <p>
     * This method implements the 'or' operation on the passed filter. It will return a new
     * <code>ChainFilter</code> instance which combines current filter with the passed filter
     * by 'or' operation.
     * </p>
     *
     * @param rhs the filter to be chained.
     *
     * @return the current filter combined with the passed filter by 'or' operation.
     *
     * @throws IllegalArgumentException if passed filter is null.
     */
    public ChainFilter or(Filter rhs) {
        Helper.checkNull(rhs, "rhs");
        return new ChainFilter(new OrFilter(this.filter, rhs));
    }

    /**
     * <p>
     * This method implements the 'not' operation on the current filter. It will return a new
     * <code>ChainFilter</code> instance which performs 'not' operation on current filter.
     * </p>
     *
     * @return the current filter performed with the 'not' operation.
     */
    public ChainFilter not() {
        return new ChainFilter(new NotFilter(this.filter));
    }

    /**
     * <p>
     * This method returns the current filter of this <code>ChainFilter</code> instance.
     * </p>
     *
     * @return the current filter of this <code>ChainFilter</code> instance.
     */
    public Filter getFilter() {
        return this.filter;
    }
}
