/*
 * ConfigManagerFailureTest.java vesion 1.0 Created on Feb 8, 2004
 *
 * Copyright © 2003, TopCoder, Inc. All rights reserved
 * */
package com.topcoder.util.config.failuretests;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownConfigFormatException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * Failure tests of methods of DefaultConfigManager
 *
 * @author WishingBone
 * @version 1.0
 */
public class DefaultConfigManagerFailureTest extends TestCase {
    /** instance to test */
    ConfigManager cm = null;
    /** prepared test files to destroy */
    List createdFiles = new ArrayList();

    public DefaultConfigManagerFailureTest(String str) {
        super(str);
    }

    /**
     * initializing cm
     */
    public void setUp() {
        cm = ConfigManager.getInstance();
    }

    public void tearDown() {
        Iterator it = createdFiles.iterator();
        while (it.hasNext()) {
            File f = (File) it.next();
            f.delete();
        }
    }

    /**
     * Prepares an xml config file.
     *
     * @return an xml config file.
     * @throws Exception to JUnit.
     */
    private String prepareXMLFile(String fileName) throws Exception {
        File file = File.createTempFile(fileName, ".xml", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <Property name=\"prop1\">");
        writer.println("        <Value>value1</Value>");
        writer.println("    </Property>");
        writer.println("    <Property name=\"prop1.prop2\">");
        writer.println("        <Value>value2</Value>");
        writer.println("        <Value>value3</Value>");
        writer.println("    </Property>");
        writer.println("</CMConfig>");
        writer.close();
        createdFiles.add(file);
        String s= file.getName();
        s = s.substring(0, s.indexOf('.'));
        return s;
    }
    /**
     * testing exceptions for add with one String argument
     *
     */
    public void testAdd() {
        try {
            cm.add((String) null);
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        try {
            cm.add("");
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae);
        }
        try {
            cm.add("aaaaaaaaaa");
        } catch (ConfigManagerException e) {
            System.out.println(e);
        }
    }

    /**
     * testing exceptions for add with two String argument
     */
    public void testAdd2() throws Exception {
        try {
            cm.add(null, ConfigManager.CONFIG_XML_FORMAT);
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        try {
            cm.add("", ConfigManager.CONFIG_XML_FORMAT);
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae);
        }
        try {
            cm.add("aaaaaaaaaa", ConfigManager.CONFIG_XML_FORMAT);
        } catch (ConfigManagerException e) {
            System.out.println("after aaaaaaa: " +e);
        }
        // at least sometimes everything works
        String s = prepareXMLFile("failure-test");
        cm.add(s, ConfigManager.CONFIG_XML_FORMAT);
        try {
            cm.add("aaaaaaaaaa", ".badFormat");
        } catch (UnknownConfigFormatException e) {
            System.out.println("bad format " +e);
        }
    }
    /**
     * testing exceptions for add with two String argument
     * and exception level
     */
    public void testAdd3() throws Exception {
        try {
            cm.add(null, ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        try {
            cm.add("", ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae);
        }
        try {
            cm.add("aaaaaaaaaa", ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (ConfigManagerException e) {
            System.out.println("after aaaaaaa: " +e);
        }
        // at least sometimes everything works
        String s = prepareXMLFile("failure-test1");
        cm.add(s, ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
        try {
            cm.add("aaaaaaaaaa", ".badFormat", ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (UnknownConfigFormatException e) {
            System.out.println("bad format " +e);
        }
        try {
            cm.add("failure-test2", ConfigManager.CONFIG_XML_FORMAT, 99);
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println("bad exception level " +e);
        }
    }

    /**
     * testing exceptions for add with arguments:
     * namespace, filename, format, exception level
     * and exception level
     */
    public void testAdd4() throws Exception {
        try {
            cm.add(null, null, ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        try {
            cm.add("", "", ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (ConfigManagerException cme) {
            fail("Should be NUllPointerException");
        } catch (IllegalArgumentException iae) {
            System.out.println(iae);
        }
        try {
            cm.add("aaaaaaaaaa", "aaaaa.xml", ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (ConfigManagerException e) {
            System.out.println("after aaaaaaa: " +e);
        }
        // at least sometimes everything works
        String sname = prepareXMLFile("failure-test");
        File file = new File("test_files" + System.getProperties().getProperty("file.separator") + sname + ".xml");
        cm.add("name1", file.getAbsolutePath(), ConfigManager.CONFIG_XML_FORMAT, ConfigManager.EXCEPTIONS_ALL);
        try {
            cm.add("name2", file.getAbsolutePath(), ".badFormat", ConfigManager.EXCEPTIONS_ALL);
            fail();
        } catch (UnknownConfigFormatException e) {
            System.out.println("bad format " +e);
        }
        try {
            cm.add("name1", file.getAbsolutePath(), ConfigManager.CONFIG_XML_FORMAT, 99);
        } catch (IllegalArgumentException e) {
            System.out.println("bad exception level " +e);
        }
    }

    public void testGet() throws Exception {
        try {
            cm.getConfigFilename(null);
            fail();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            cm.getConfigFilename("aaa");
            fail();
        } catch (UnknownNamespaceException e) {
            System.out.println(e);
        }
        try {
            cm.getConfigFormat(null);
            fail();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            cm.getConfigFormat("aaa");
            fail();
        } catch (UnknownNamespaceException e) {
            System.out.println(e);
        }
        try {
            cm.getProperty(null, "aa");
            fail();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            cm.getProperty("test", null);
            fail();
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            cm.getProperty("aaa", "aaa");
            fail();
        } catch (UnknownNamespaceException e) {
            System.out.println(e);
        }
    }

    /**
     * returns suite of tests in this class
     * @return Test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DefaultConfigManagerFailureTest.class);
        return suite;
    }
}
