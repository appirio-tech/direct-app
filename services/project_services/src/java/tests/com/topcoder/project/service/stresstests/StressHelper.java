/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This class helps in managing the configuration files.
 * </p>
 * 
 * @author stylecheck
 * @version 1.1
 * @since 1.0
 */
public class StressHelper {

    /**
     * The times each method needs to be called.
     */
    public static long STRESS_COUNT = 50;

    /**
     * <p>
     * Loads the configuration file.
     * </p>
     */
    public static void load() {
        release();
        ConfigManager configManager = ConfigManager.getInstance();
        try {
            configManager.add(System.getProperty("user.dir") + "/test_files/Stress/Stress_Config.xml");
        } catch (ConfigManagerException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Releases all configurations loaded previously.
     * </p>
     */
    public static void release() {
        ConfigManager configManager = ConfigManager.getInstance();
        for (Iterator iterator = configManager.getAllNamespaces(); iterator.hasNext();) {
            try {
                configManager.removeNamespace((String) iterator.next());
            } catch (UnknownNamespaceException e) {
                // ignore
            }
        }
    }
}
