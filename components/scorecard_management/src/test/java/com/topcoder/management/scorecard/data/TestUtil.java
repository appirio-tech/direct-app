/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import junit.framework.Assert;

/**
 * Contains a bunch of static test utility methods.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
final class TestUtil {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Prevents instantiation.
     */
    private TestUtil() {
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Asserters

    /**
     * Checks that the two arrays are exactly the same length, and contain the same objects in the same order.
     *
     * @param   message
     *          The beginning part of the error message.
     * @param   expected
     *          The expected array of objects.
     * @param   actual
     *          The actual array of objects.
     */
    static void assertArraysAreEqual(String message, Object[] expected, Object[] actual) {
        Assert.assertNotNull(message + ": The actual array should not be null.", actual);
        Assert.assertEquals(message + ": The array lengths do not match.", expected.length, actual.length);

        for (int i = 0; i < expected.length; ++i) {
            Assert.assertEquals(message + ": The elements at index " + i + " differ.", expected[i], actual[i]);
        }
    }
}
