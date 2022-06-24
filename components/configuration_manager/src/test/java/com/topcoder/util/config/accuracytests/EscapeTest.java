/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.util.config.accuracytests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import junit.framework.TestCase;

/**
 * <p>
 * Tests for the 'escape' fix.
 * </p>
 *
 * @author kr00tki
 * @version 2.1.4
 */
public class EscapeTest extends TestCase {

    /**
     * The namespace used in tests.
     */
    private static final String NAMESPACE = "escape";

    /**
     * <p>
     * Clears the ConfigManager.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        for (Iterator it = ConfigManager.getInstance().getAllNamespaces(); it.hasNext();) {
            ConfigManager.getInstance().removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Tests escaping the property key and value.
     * </p>
     *
     * @throws Exception
     */
    public void testEscape() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(NAMESPACE, "escape.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);

        assertEquals("Incorrect value", "\\x", cm.getString(NAMESPACE, "property"));
        assertEquals("Incorrect value", "x", cm.getString(NAMESPACE, "property=\\"));
        assertEquals("Incorrect value.", "\u0020\\\\u0020\u0020", cm.getProperty("escape", "!prop2"));
        assertEquals("Incorrect value.", "\n\r\t \\#!:=\u0020", cm.getString(NAMESPACE, "property#"));
    }

    /**
     * <p>
     * Tests escaping the custom list delimeter.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCustomDelimeterTest() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(NAMESPACE, "custom_escape.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        String[] v = cm.getStringArray(NAMESPACE, "prop3");
        assertEquals("Incorrect value.", "\\", v[0]);
        assertEquals("Incorrect value.", "#", v[1]);
    }

    /**
     * <p>
     * Tests escaping the custom list delimeter.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testInvalidEscapeSequenceTest() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            cm.add(NAMESPACE, "invalid_escape.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Invalid escape sequence, exception expected.");
        } catch (ConfigManagerException ex) {
            // ok
        }

    }
}
