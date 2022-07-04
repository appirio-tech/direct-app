/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import junit.framework.TestCase;

/**
 * Unit tests for the class: Helper.
 *
 * @author kinfkong, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class HelperTest extends TestCase {

    /**
     * Tests method: checkLongPositive(long, String).
     *
     * Checks if the IllegalArgumentException could be thrown.
     */
    public void testCheckLongPositive() {
        try {
            Helper.checkLongPositive(-1, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: checkLongPositive(long, String).
     *
     * It should not throw any exception when providing valid value.
     * @since 1.1
     */
    public void testCheckLongPositive2() {
        Helper.checkLongPositive(1, "test");
    }

    /**
     * Tests method: checkNull(Object, String).
     *
     * Checks if the IllegalArgumentException could be thrown.
     */
    public void testCheckNull() {
        try {
            Helper.checkNull(null, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: checkNull(Object, String).
     *
     * It should not throw any exception when providing valid value.
     * @since 1.1
     */
    public void testCheckNull2() {
        Helper.checkNull(new Object(), "test");
    }

    /**
     * Tests method: checkLongPositive(Long, String).
     *
     * Checks if the IllegalArgumentException could be thrown.
     */
    public void testCheckLongPositive_LongString_NonPositive() {
        try {
            Helper.checkLongPositive(new Long(-1), "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: checkLongPositive(Long, String).
     *
     * Checks if the IllegalArgumentException could be thrown.
     */
    public void testCheckLongPositive_LongString_Null() {
        try {
            Helper.checkLongPositive(null, "test");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Tests method: checkLongPositive(Long, String).
     *
     * It should not throw any exception when providing valid value.
     * @since 1.1
     */
    public void testCheckLongPositive_LongString_Valid() {
        Helper.checkLongPositive(new Long(1), "test");
    }
}
