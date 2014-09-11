/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.phase.failuretests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * The base class for all the failure cases.
 *
 * @author assistant
 * @version 1.0
 */
public class FailureTestBase extends TestCase {

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        // first clean the configurations from other cases
        this.tearDown();

        // add the namespaces
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("failure/failure.xml");
    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        // remove all the namespaces
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }
}
