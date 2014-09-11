/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigManagerTestCase.java
 *
 * 2.1  02/01/2004
 */
package com.topcoder.util.config;

import junit.framework.TestCase;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Tests the escape.
 *
 * @author zsudraco
 * @version 2.1.4
 */
public class EscapeEnhancementTest extends TestCase {

    /**
     * The singleton ConfigManager instance.
     */
    private ConfigManager cm = null;

    /**
     * Set up testing environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        cm = ConfigManager.getInstance();
    }

    /**
     * Tear down testing environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
    }

    /**
     * Test the escape functionality.
     *
     * @throws Exception to JUnit.
     */
    public void testEscapel() throws Exception {
        cm.add("EscapeEnhancement", "TestEscape.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        String s = cm.getString("EscapeEnhancement", "Prop1");
        assertEquals("test\n\r\t ! =\\!#test[ = !:%)", s);
    }

    /**
     * Test the escape functionality.
     *
     * @throws Exception to JUnit.
     */
    public void testEscape2() throws Exception {
        cm.add("EscapeEnhancement", "TestEscape.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        String[] ss = cm.getStringArray("EscapeEnhancement", "Prop2");
        assertEquals(4, ss.length);

        String s = ss[0];
        assertEquals(2, s.length());
        assertEquals((char) 0xffff, s.charAt(0));
        assertEquals((char) 0x1234, s.charAt(1));
    }

    /**
     * Test the escape functionality.
     *
     * @throws Exception to JUnit.
     */
    public void testEscape3() throws Exception {
        cm.add("EscapeEnhancement", "TestEscape2.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        String s = cm.getString("EscapeEnhancement", "Prop1");
        assertEquals("\n\r\t", s);
        
        cm.createTemporaryProperties("EscapeEnhancement");
        cm.setProperty("EscapeEnhancement", "Prop1", "\n\r\t");
        cm.commit("EscapeEnhancement", "developer");
    }

    /**
     * Test the escape functionality.
     *
     * @throws Exception to JUnit.
     */
    public void testEscape4() throws Exception {
        cm.add("EscapeEnhancement", "TestEscape.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
        String s = cm.getString("EscapeEnhancement", " \n\rProp3 ==!\t\\:!# ");
        assertEquals("hello", s);
    }

    /**
     * Test the escape functionality.
     *
     * @throws Exception to JUnit.
     */
    public void testEscape5() throws Exception {
        try {
            cm.add("EscapeEnhancement", "InvalidEscape.properties", ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Exception should be thrown for invalid escape.");
        } catch (Exception e) {
            // ok
        }
    }

}
