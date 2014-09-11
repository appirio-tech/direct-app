/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) NamespaceTestCase.java
 *
 * 2.1  02/01/2004
 */
package com.topcoder.util.config;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;

/**
 * Tests the behavior of Namespace.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class NamespaceTestCase extends TestCase {

    /**
     * Prepares a multiple namespace xml config properties.
     *
     * @return a multiple namespace xml config properties.
     * @throws Exception to JUnit.
     */
    private XMLConfigProperties prepareProperties() throws Exception {
        File file = File.createTempFile("unittest", ".xml", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <Config name=\"component a\">");
        writer.println("    </Config>");
        writer.println("    <Config name=\"component b\">");
        writer.println("    </Config>");
        writer.println("</CMConfig>");
        writer.close();
        XMLConfigProperties xmlcp = new XMLConfigProperties(file.toURL(), "component b");
        file.delete();
        return xmlcp;
    }

    /**
     * Tests create Namespace.
     * Also tests the getters.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateNamespace() throws Exception {
        URL file = new URL("http://localhost");
        Namespace ns = new Namespace("ns", file, "format", 5, null);
        assertTrue(ns.getName().equals("ns"));
        assertTrue(ns.getFile() == file);
        assertTrue(ns.getFormat().equals("format"));
        assertTrue(ns.getExceptionLevel() == 5);
        assertNull(ns.getProperties());
        ConfigProperties cp = prepareProperties();
        ns.setProperties(cp);
        assertTrue(ns.getProperties() == cp);
        try {
            ns.setProperties(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests lock methods.
     *
     * @throws Exception to JUnit.
     */
    public void testLockMethods() throws Exception {
        URL file = new URL("http://localhost");
        Namespace ns = new Namespace("ns", file, "format", 5, null);
        assertTrue(ns.canLock("a"));
        ns.lock("a");
        assertFalse(ns.canLock("b"));
        assertTrue(ns.canLock("a"));
        ns.releaseLock();
        assertTrue(ns.canLock("a"));
        assertTrue(ns.canLock("b"));
    }

    /**
     * Tests setDocument.
     *
     * @throws Exception to JUnit.
     */
    public void testSetDocument() throws Exception {
        URL file = new URL("http://localhost");
        XMLConfigProperties cp1 = prepareProperties();
        XMLConfigProperties cp2 = prepareProperties();
        Namespace ns1 = new Namespace("ns1", file, ConfigManager.CONFIG_MULTIPLE_XML_FORMAT, 5, cp1);
        Namespace ns2 = new Namespace("ns2", file, ConfigManager.CONFIG_MULTIPLE_XML_FORMAT, 5, cp2);
        ns1.setDocument(ns2);
        assertTrue(cp1.getDocument() == cp2.getDocument());
    }

}
