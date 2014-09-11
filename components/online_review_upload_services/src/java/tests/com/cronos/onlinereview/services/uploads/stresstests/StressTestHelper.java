/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.stresstests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class for stress tests.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class StressTestHelper {
    /**
     * <p>
     * Empty constructor for utility class.
     * </p>
     */
    private StressTestHelper() {
        // empty
    }

    /**
     * <p>
     * Use the given file to config the configuration manager.
     * </p>
     * @param fileName config file to set up environment
     * @throws Exception to JUnit.
     */
    public static void loadConfig(String fileName) throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        config.add(fileName);
    }

    /**
     * <p>
     * Clear all the name spaces from the configuration manager.
     * </p>
     * @throws Exception to JUnit.
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }
}
