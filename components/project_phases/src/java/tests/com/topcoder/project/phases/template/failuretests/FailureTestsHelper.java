/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved. TestHelper.java
 */
package com.topcoder.project.phases.template.failuretests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * Set of help functions.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class FailureTestsHelper {

    /**
     * Storage for all configurations tests.
     */
    private static final String CONFIG_TESTS_FILE = "failuretests/configurations.xml";

    /**
     * empty construction.
     */
    private FailureTestsHelper() {
    }

    /**
     * Loading test configurations.
     * 
     * @throws Exception
     *             wrap all exceptions
     */
    public static void loadAllConfig() throws Exception {
        clearTestConfig();
        ConfigManager config = ConfigManager.getInstance();
        config.add(CONFIG_TESTS_FILE);
    }

    /**
     * Clearing all configurations.
     * 
     * @throws Exception
     *             wrap all exceptions
     */
    public static void clearTestConfig() throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        Iterator iter = config.getAllNamespaces();
        while (iter.hasNext()) {
            String namespace = (String) iter.next();
            config.removeNamespace(namespace);
        }
    }
}