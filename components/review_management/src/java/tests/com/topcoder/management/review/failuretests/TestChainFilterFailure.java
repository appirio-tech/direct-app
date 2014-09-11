/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.failuretests;

import com.topcoder.management.review.ChainFilter;
import com.topcoder.search.builder.filter.GreaterThanFilter;

import junit.framework.TestCase;

/**
 * Failure test for class <code>ChainFilter </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestChainFilterFailure extends TestCase {

    /**
     * Test constructor <code>ChainFilter(Filter filter) </code>.
     * If the parameter filter is null, IllegalArgumentException should be thrown.
     *
     */
    public void testChainFilter() {
        try {
            new ChainFilter(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test method <code> ChainFilter and(Filter rhs) </code>.
     * If the parameter rhs is null, IllegalArgumentException should be thrown.
     */
    public void testAnd() {
        ChainFilter filter = new ChainFilter(new GreaterThanFilter("Age", new Integer(50)));

        try {
            filter.and(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }

    /**
     * Test method <code>ChainFilter or(Filter rhs) </code>.
     * If the parameter rhs is null, IllegalArgumentException should be thrown.
     */
    public void testOr() {
        ChainFilter filter = new ChainFilter(new GreaterThanFilter("Age", new Integer(50)));

        try {
            filter.or(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }
}
