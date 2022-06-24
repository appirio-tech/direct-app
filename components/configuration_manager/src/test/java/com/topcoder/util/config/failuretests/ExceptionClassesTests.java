/*
 * ExceptionClassesTests.java vesion 1.0 Created on Feb 8, 2004
 * 
 * Copyright © 2003, TopCoder, Inc. All rights reserved
 * */
package com.topcoder.util.config.failuretests;

import junit.framework.*;
import com.topcoder.util.config.*;


/**
 * TODO - add full javadoc description
 * 
 * @author WishingBone
 * @version 1.0
 */
public class ExceptionClassesTests extends TestCase {
    
    public ExceptionClassesTests(String method) {
        super(method);
    }
    
    /**
     * testing that message passed     *
     */
    public void testConfigLockedException() {
        String mess = "message";
        ConfigLockedException e = new ConfigLockedException(mess);
        assertTrue(e.getMessage().indexOf(mess) >= 0);        
    }
    /**
     * testing that message passed     *
     */
    public void testConfigManagerException() {
        String mess = "message";
        ConfigManagerException e = new ConfigManagerException(mess);
        assertTrue(e.getMessage().indexOf(mess) >= 0);        
    }
    /**
     * testing that message passed     *
     */
    public void testConfigParserException() {
        String mess = "message";
        ConfigParserException e = new ConfigParserException(mess);
        assertTrue(e.getMessage().indexOf(mess) >= 0);        
    }
    /**
     * testing that message passed     *
     */
    public void testDuplicatePropertyException() {
        String mess = "message";
        DuplicatePropertyException e = new DuplicatePropertyException(mess);
        assertTrue(e.getMessage().indexOf(mess) >= 0);        
    }
    
    /**
     * testing that message passed     *
     */
    public void testNamespaceAlreadyExistsException() {
        String mess = "message";
        NamespaceAlreadyExistsException e = new NamespaceAlreadyExistsException(mess);
        assertTrue(e.getMessage().indexOf(mess) >= 0);        
    }
    
    /**
     * testing that message passed     *
     */
    public void testUnknownConfigFormatException() {
        String mess = "message";
        UnknownConfigFormatException e = new UnknownConfigFormatException(mess);
        assertTrue(e.getMessage().indexOf(mess) >= 0);        
    }
    
    /**
     * testing that message passed     *
     */
    public void testUnknownNamespaceException() {
        String mess = "message";
        UnknownNamespaceException e = new UnknownNamespaceException(mess);
        assertTrue(e.getMessage().indexOf(mess) >= 0);        
    }
    
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ExceptionClassesTests.class);        
        return suite;        
    }
}
