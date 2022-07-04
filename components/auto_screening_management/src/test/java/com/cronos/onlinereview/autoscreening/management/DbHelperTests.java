/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of DbHelper. Tests the class for proper behavior.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class DbHelperTests extends TestCase {
    /**
     * <p>
     * Accuracy test. Tests the <code>checkNull(Object, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckNull_1_accuracy() {
        try {
            Helper.checkNull(null, "arg");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>checkNull(Object, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckNull_2_accuracy() {
        try {
            Helper.checkNull(" ", "arg");
            // ok
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException unexpected.");
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>checkPositive(long, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckPositive_1_accuracy() {
        try {
            Helper.checkNonPositive(0, "arg");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>checkPositive(long, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckPositive_2_accuracy() {
        try {
            Helper.checkNonPositive(-1, "arg");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>checkPositive(long, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckPositive_3_accuracy() {
        try {
            Helper.checkNonPositive(1, "arg");
            // ok
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException unexpected.");
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>checkString(String, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckString_1_accuracy() {
        try {
            Helper.checkString("  ", "arg");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>checkString(String, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckString_2_accuracy() {
        try {
            Helper.checkString(null, "arg");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>checkString(String, String)</code> for proper behavior.
     * </p>
     */
    public void testCheckString_3_accuracy() {
        try {
            Helper.checkString("sdf", "arg");
            // ok
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException unexpected.");
        }
    }
}
