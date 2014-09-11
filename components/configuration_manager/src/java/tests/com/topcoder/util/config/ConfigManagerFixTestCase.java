/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) ConfigManagerFixTestCase.java
 */
package com.topcoder.util.config;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * The new test cases during fix.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class ConfigManagerFixTestCase extends TestCase {

    /**
     * The xml config file.
     */
    private File file = null;

    /**
     * The XMLConfigProperties to test on.
     */
    private XMLConfigProperties xmlcp = null;

    /**
     * Set up testing environment.
     * Creates a sample xml config file and constructs the XMLConfigProperties instance.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        file = File.createTempFile("unittest", ".xml", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <Property name=\"a\">");
        writer.println("        <Value>a</Value>");
        writer.println("        <Property name=\"b\">");
        writer.println("            <Value>ab</Value>");
        writer.println("            <Property name=\"c\">");
        writer.println("                <Value>abc</Value>");
        writer.println("            </Property>");
        writer.println("        </Property>");
        writer.println("    </Property>");
        writer.println("</CMConfig>");
        writer.close();
    }

    /**
     * Tear down testing environment.
     * Remove the file.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        xmlcp = null;
        if (file != null) {
            file.delete();
            file = null;
        }
    }

    /**
     * The initial version does not handle preload file correctly.
     * This test case is oriented to check if this is fixed.
     *
     * @throws Exception to JUnit.
     */
    public void testFixPreload() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        assertTrue(cm.existsNamespace("com.topcoder.util.config.ConfigManager"));
    }

    /**
     * The initial version does not load multi-xml through classpath.
     * This test case is oriented to check if this is fixed.
     *
     * @throws Exception to JUnit.
     */
    public void testLoadMultiXML() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("com/topcoder/util/config/SampleMultiXML.xml");
        assertTrue(cm.existsNamespace("Component A"));
        assertTrue(cm.existsNamespace("Component B"));
    }

    /**
     * The initial version does not handle nested property element.
     * This test case is oriented to check if nested properties are loaded correctly.
     *
     * @throws Exception to JUnit.
     */
    public void testLoadNested() throws Exception {
        xmlcp = new XMLConfigProperties(file.toURL(), null);
        xmlcp.load();
        Property root = xmlcp.getRoot();
        Property a = root.getProperty("a");
        assertTrue(a.getValue().equals("a"));
        Property ab = root.getProperty("a.b");
        assertTrue(ab.getValue().equals("ab"));
        Property abc = root.getProperty("a.b.c");
        assertTrue(abc.getValue().equals("abc"));
    }

    /**
     * The initial version does not persist properties into nested tags.
     * This test case is oriented to check if properties are saved correctly.
     *
     * @throws Exception to JUnit.
     */
    public void testSaveNested() throws Exception {
        xmlcp = new XMLConfigProperties(file.toURL(), null);
        Property root = new Property();
        root.setProperty("a", "a");
        root.setProperty("a.b", "ab");
        root.setProperty("a.b.c", "abc");
        xmlcp.setRoot(root);
        xmlcp.save();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        assertTrue(reader.readLine().equals("<?xml version=\"1.0\"?>"));
        assertTrue(reader.readLine().equals("<CMConfig>"));
        assertTrue(reader.readLine().equals("    <Property name=\"a\">"));
        assertTrue(reader.readLine().equals("        <Value>a</Value>"));
        assertTrue(reader.readLine().equals("        <Property name=\"b\">"));
        assertTrue(reader.readLine().equals("            <Value>ab</Value>"));
        assertTrue(reader.readLine().equals("            <Property name=\"c\">"));
        assertTrue(reader.readLine().equals("                <Value>abc</Value>"));
        assertTrue(reader.readLine().equals("            </Property>"));
        assertTrue(reader.readLine().equals("        </Property>"));
        assertTrue(reader.readLine().equals("    </Property>"));
        assertTrue(reader.readLine().equals("</CMConfig>"));
        assertNull(reader.readLine());
        reader.close();
    }

}
