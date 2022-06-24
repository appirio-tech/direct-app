/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;

import junit.framework.TestCase;

/**
 * The testcase for Helper class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTest extends TestCase {
    /**
     * Test <code>checkEntityManager</code> method for proper behavior.
     */
    public void test_checkEntityManager_1() {
        try {
            Helper.checkEntityManager(null);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * Test <code>checkNull(Object param, String paramName)</code> method for
     * proper behavior.
     */
    public void test_checkNull_1() {
        try {
            Helper.checkNull(null, "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test <code>checkNullAndEmpty(String param, String paramName)</code>
     * method for proper behavior.
     */
    public void test_checkNullAndEmpty_1() {
        try {
            Helper.checkNullAndEmpty(null, "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test <code>checkNullAndEmpty(String param, String paramName)</code>
     * method for proper behavior.
     */
    public void test_checkNullAndEmpty_2() {
        try {
            Helper.checkNullAndEmpty("  ", "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test <code>checkNullAndEmpty(String param, String paramName)</code>
     * method for proper behavior.
     */
    public void test_checkNullAndEmpty_3() {
        try {
            Helper.checkNullAndEmpty("\t", "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test <code>checkNullAndEmpty(String param, String paramName)</code>
     * method for proper behavior.
     */
    public void test_checkNullAndEmpty_4() {
        try {
            Helper.checkNullAndEmpty("\n", "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test <code>WrapExceptionWithDAOException</code> method for proper
     * behavior.
     */
    public void test_WrapExceptionWithDAOException_1() {
        Exception e = new Exception();
        String message = "msg";
        DAOException res = Helper.wrapWithDAOException(e, message);
        assertTrue("should be DAOException", res instanceof DAOException);
    }

    /**
     * Test <code>WrapExceptionWithDAOException</code> method for proper
     * behavior.
     */
    public void test_WrapExceptionWithDAOException_2() {
        String message = "msg";
        DAOException e = new DAOException(message);
        DAOException res = Helper.wrapWithDAOException(e, message);
        assertTrue("should be DAOException", res instanceof DAOException);
    }
}
