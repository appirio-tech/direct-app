/*
 * PropertyTests.java vesion 1.0 Created on Feb 10, 2004
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
public class PropertyTests extends TestCase {
    public PropertyTests(String str){
        super(str);
    }
    /**
     * tests of constructor exceptions
     *
     */
    public void testConstructor() {
        // tests of constructor with one argument
        try {
            Property p = new Property("");
            fail("Should be IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
        try {
            Property p = new Property(null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
        // tests of constructor with two arguments
        try {
            Property p = new Property("", "");
            fail("Should be IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
        try {
            Property p = new Property(null, "aa");
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
        try {
            Property p = new Property("aa", (String)null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
        // tests of constructor with two arguments second - array
        try {
            Property p = new Property("", new String[]{"aa"});
            fail("Should be IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
        try {
            Property p = new Property(null, new String[]{"aa"});
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
        try {
            Property p = new Property("aa", (String[])null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
    }
    
    public void testContains() {
        Property p = new Property("aaa");
        try {
            p.containsProperty(null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
        try {
            p.containsValue(null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
    }
    
    public void testGetValue() {
        Property p = new Property("aaa");
        try {
            p.getValue(null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
        try {
            p.getValue("");
            fail("Should be IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
        try {
            p.getValues(null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
        try {
            p.getValues("");
            fail("Should be IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
    /**
     * test comments
     *
     */
    public void testComments() {
        Property p = new Property("aaa");
        try {
            p.addComment(null);
            fail("Should be NullPointerException");
        } catch (NullPointerException e) {
            // OK
        }
    }
    /**
     * returns suite of tests in this class
     * @return Test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(PropertyTests.class);        
        return suite;        
    }
}
