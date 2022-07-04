/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import java.io.File;
import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;
/**
 * Helper class for stress test.
 * @author still
 * @version 1.0
 */
final class Helper {
    /** The config file path. */
    private static final String CONFIG_FILE =  "stress_config.xml";

    /**
     * private constructor.
     */
    private Helper() {
    }
    /**
     * Load all configurations from files.
     *
     * @throws Exception if any errors happened
     */
    public static void loadConfig() throws Exception {
        unloadConfig();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/stresstest/" + CONFIG_FILE).getAbsolutePath());
    }

    /**
     * unload all configurations from files.
     *
     * @throws Exception if any errors happened
     */
    public static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }
}
