/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import com.topcoder.search.builder.filter.Filter;

import java.io.Serializable;


/**
 * <p>
 * A custom filter used as a wrapper aroung the actual filter which is delegated
 * to process the calls to the methods.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestServiceFilter implements Serializable {
    /**
     * <p>
     * A <code>Filter</code> to be used for delegating the calls to the methods.
     * </p>
     */
    private Filter filter = null;

    /**
     * <p>
     * Constructs new <code>ContestServiceFilter</code> instance. This
     * implementation does nothing.
     * </p>
     */
    public ContestServiceFilter() {
    }

    /**
     * <p>
     * Constructs new <code>ContestServiceFilter</code> instance wrapping the
     * specified filter which will service the calls.
     * </p>
     *
     * @param filter
     *            a <code>Filter</code> to delegate the calls to.
     * @throws IllegalArgumentException
     *             if specified <code>filter</code> is <code>null</code>.
     */
    public ContestServiceFilter(Filter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("The parameter [filter] is NULL");
        }

        this.filter = filter;
    }

    /**
     * <p>
     * Gets the actual filter to be used for filtering the records.
     * </p>
     *
     * @return a <code>Filter</code> to be used for filtering.
     */
    public Filter getFilter() {
        return this.filter;
    }
}
