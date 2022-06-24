/**
 * Copyright (c) 2005, TopCoder Software, Inc. All rights reserved.
 */
package com.topcoder.util.config;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Iterator;


/**
 * Tests the loading of namespace from a properties file within a jar file. It is assumed that the jar file has been
 * added to the classpath.
 *
 * @author colau
 * @version 2.1
 */
public class ConfigManagerJarTestCase extends TestCase {
    /**
     * The path of the XML config file (inside the jar file).
     */
    private static final String XML_CONFIG_FILE = "com/topcoder/util/config/SampleJarXML.xml";

    /**
     * The path of the properties config file (inside the jar file).
     */
    private static final String PROP_CONFIG_FILE = "com/topcoder/util/config/SampleJarProperties.properties";

    /**
     * The path of the multiple XML config file (inside the jar file).
     */
    private static final String MULTI_XML_CONFIG_FILE = "com/topcoder/util/config/SampleJarMultiXML.xml";

    /**
     * The name of the testing namespace.
     */
    private static final String NAMESPACE = "JarNamespace";

    /**
     * The ConfigManager instance to test against.
     */
    private ConfigManager cm = null;

    /**
     * Set up testing environment. Clear the config manager.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        cm = ConfigManager.getInstance();
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
    }

    /**
     * Tear down testing environment. Clear the config manager and remove created files.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
        cm = null;
    }

    /**
     * Tests the loading of namespace from the XML config file. Verifies if the property values are loaded correctly.
     * Expects ConfigManagerException when saving the namespace.
     *
     * @throws Exception to JUnit.
     */
    public void testLoadSaveXML() throws Exception {
        cm.add(NAMESPACE, XML_CONFIG_FILE, ConfigManager.CONFIG_XML_FORMAT);

        assertEquals("Fails to load XML config", "Value", cm.getString(NAMESPACE, "Prop1"));
        assertTrue("Fails to load XML config",
            Arrays.equals(cm.getStringArray(NAMESPACE, "Prop2"), new String[] {"Value1", "Value2"}));

        try {
            cm.createTemporaryProperties(NAMESPACE);
            cm.commit(NAMESPACE, "user");
            fail("Saves properties to jar file");
        } catch (ConfigManagerException e) {
            // good
        }
    }

    /**
     * Tests the loading of namespace from the properties config file. Verifies if the property values are loaded
     * correctly. Expects ConfigManagerException when saving the namespace.
     *
     * @throws Exception to JUnit.
     */
    public void testLoadSaveProp() throws Exception {
        cm.add(NAMESPACE, PROP_CONFIG_FILE, ConfigManager.CONFIG_PROPERTIES_FORMAT);

        assertEquals("Fails to load properties config", "Value", cm.getString(NAMESPACE, "Prop1"));
        assertTrue("Fails to load properties config",
            Arrays.equals(cm.getStringArray(NAMESPACE, "Prop2"), new String[] {"Value1", "Value2"}));

        try {
            cm.createTemporaryProperties(NAMESPACE);
            cm.commit(NAMESPACE, "user");
            fail("Saves properties to jar file");
        } catch (ConfigManagerException e) {
            // good
        }
    }

    /**
     * Tests the loading of namespace from the multiple XML config file. Verifies if the namespaces are loaded
     * correctly. Expects ConfigManagerException when saving the namespace.
     *
     * @throws Exception to JUnit.
     */
    public void testLoadSaveMultiXML() throws Exception {
        cm.add(MULTI_XML_CONFIG_FILE);

        assertTrue("Fails to load multiple XML config", cm.existsNamespace("Jar Component A"));
        assertTrue("Fails to load multiple XML config", cm.existsNamespace("Jar Component B"));

        try {
            cm.createTemporaryProperties("Component A");
            cm.commit(NAMESPACE, "user");
            fail("Saves properties to jar file");
        } catch (ConfigManagerException e) {
            // good
        }
    }
}
