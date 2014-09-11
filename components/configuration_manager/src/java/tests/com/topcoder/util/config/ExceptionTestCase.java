/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ExceptionTestCase.java
 *
 * 2.1  02/01/2004
 */
package com.topcoder.util.config;

import junit.framework.TestCase;

/**
 * Tests the various exception classes.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class ExceptionTestCase extends TestCase {

    /**
     * Tests ConfigManagerException.
     */
    public void testConfigManagerException() {
        // no-arg
        Exception cme = new ConfigManagerException();
        assertNotNull(cme);
        // with message
        cme = new ConfigManagerException("msg");
        assertNotNull(cme);
        assertTrue(cme.getMessage().indexOf("msg") != -1);
    }

    /**
     * Tests UnknownNamespaceException.
     */
    public void testUnknownNamespaceException() {
        // no-arg
        Exception une = new UnknownNamespaceException();
        assertNotNull(une);
        // with message
        une = new UnknownNamespaceException("msg");
        assertNotNull(une);
        assertTrue(une.getMessage().indexOf("msg") != -1);
    }

    /**
     * Tests NamespaceAlreadyExistsException.
     */
    public void testNamespaceAlreadyExistsException() {
        // no-arg
        Exception nsae = new NamespaceAlreadyExistsException();
        assertNotNull(nsae);
        // with message
        nsae = new NamespaceAlreadyExistsException("msg");
        assertNotNull(nsae);
        assertTrue(nsae.getMessage().indexOf("msg") != -1);
    }

    /**
     * Tests UnknownConfigFormatException.
     */
    public void testUnknownConfigFormatException() {
        // no-arg
        Exception ucfe = new UnknownConfigFormatException();
        assertNotNull(ucfe);
        // with message
        ucfe = new UnknownConfigFormatException("msg");
        assertNotNull(ucfe);
        assertTrue(ucfe.getMessage().indexOf("msg") != -1);
    }

    /**
     * Tests DuplicatePropertyException.
     */
    public void testDuplicatePropertyException() {
        // no-arg
        Exception dpe = new DuplicatePropertyException();
        assertNotNull(dpe);
        // with message
        dpe = new DuplicatePropertyException("msg");
        assertNotNull(dpe);
        assertTrue(dpe.getMessage().indexOf("msg") != -1);
    }

    /**
     * Tests ConfigLockedException.
     */
    public void testConfigLockedException() {
        // no-arg
        Exception cle = new ConfigLockedException();
        assertNotNull(cle);
        // with message
        cle = new ConfigLockedException("msg");
        assertNotNull(cle);
        assertTrue(cle.getMessage().indexOf("msg") != -1);
    }

    /**
     * Tests ConfigParserException.
     */
    public void testConfigParserException() {
        // no-arg
        Exception cpe = new ConfigParserException();
        assertNotNull(cpe);
        // with message
        cpe = new ConfigParserException("msg");
        assertNotNull(cpe);
        assertTrue(cpe.getMessage().indexOf("msg") != -1);
    }

}
