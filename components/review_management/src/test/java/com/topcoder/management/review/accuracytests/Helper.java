package com.topcoder.management.review.accuracytests;

import com.topcoder.util.config.ConfigManager;

import java.io.File;
import java.util.Iterator;

/**
 * <p>
 * This class contains some utility methods, that are used so that to set up and
 * tear down environment.
 * </p>
 *
 * @author wiedzmin
 * @version 1.0
 */
final class Helper {

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private Helper() {
        //empty
    }

    /**
     * <p>
     * Add all necessary config files.
     * </p>
     *
     * @throws Exception exception
     */
    public static void initNamespace() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        //add file with invalid configurations
        manager.add(new File("test_files/accuracy/acc_config.xml").getAbsolutePath());
    }

    /**
     * Clear namespaces.
     *
     * @throws Exception exception
     */
    public static void clearNamespace() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();
        Iterator iter = manager.getAllNamespaces();
        while (iter.hasNext()) {
            String ns = (String) iter.next();
            manager.removeNamespace(ns);
        }
    }
}