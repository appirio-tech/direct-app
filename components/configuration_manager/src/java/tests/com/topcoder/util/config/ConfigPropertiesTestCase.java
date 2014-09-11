/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigPropertiesTestCase.java
 *
 * 2.1  02/01/2004
 */
package com.topcoder.util.config;

import junit.framework.TestCase;

/**
 * Tests the common behavior of ConfigProperties.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class ConfigPropertiesTestCase extends TestCase {

    /**
     * The ConfigProperties instance to test.
     */
    private ConfigProperties cp = null;

    /**
     * Set up testing environment.
     * Creates an anonymous implementation of ConfigProperties abstract class.
     */
    protected void setUp() {
        cp = new ConfigProperties() {
            public void save() {
            }
            public void load() {
            }
            public Object clone() {
                return null;
            }
        };
    }

    /**
     * Tear down testing environment.
     */
    protected void tearDown() {
        cp = null;
    }

    /**
     * Test set and get root.
     */
    public void testSetGetRoot() {
        Property root = new Property();
        cp.setRoot(root);
        assertTrue(cp.getRoot() == root);
        // root is null
        try {
            cp.setRoot(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

}
