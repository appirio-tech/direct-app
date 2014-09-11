/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.management.project.ProjectManagerImpl;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.util.Iterator;


/**
 * Helper class for accuracy tests of <code>ProjectManager</code>. Contains methods to load and unload configurations.
 *
 * @author skatou
 * @version 1.0
 */
abstract class ProjectManagerImplAccuracyTestsHelper extends TestCase {
    /** The path of the configuration file. */
    private static final String CONFIG = "accuracytests/config.xml";

    /** The <code>ProjectManagerImpl</code> instance to be tested. */
    protected ProjectManagerImpl manager = null;

    /**
     * Sets up test environment. Configurations are loaded and new instance of <code>ProjectManagerImpl</code> instance
     * is created.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        unloadConfig();
        loadConfig();

        manager = new ProjectManagerImpl();
    }

    /**
     * Cleans up test environment. Configurations are unloaded.
     *
     * @throws Exception pass to JUnit.
     */
    protected void tearDown() throws Exception {
        unloadConfig();
    }

    /**
     * Loads necessary configurations into ConfigManager.
     *
     * @throws Exception pass to JUnit.
     */
    protected void loadConfig() throws Exception {
        ConfigManager.getInstance().add(CONFIG);
    }

    /**
     * Unloads all configurations. All namespaces in ConfigManager are removed.
     *
     * @throws Exception pass to JUnit.
     */
    protected void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace(it.next().toString());
        }
    }
}
