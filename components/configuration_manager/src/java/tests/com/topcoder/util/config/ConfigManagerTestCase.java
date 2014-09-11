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
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Tests the behavior of ConfigManager.
 *
 * @author  WishingBone
 * @since 2.1
 * @author kr00tki
 * @version 2.1.5
 */
public class ConfigManagerTestCase extends TestCase {

    /**
     * The singleton ConfigManager instance.
     */
    private ConfigManager cm = null;

    /**
     * The list of created files.
     */
    private List createdFiles = new ArrayList();

    /**
     * Set up testing environment.
     * Clear the config manager.
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
     * Tear down testing environment.
     * Clear the config manager and remove created files.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
        cm = null;
        for (int i = 0; i < createdFiles.size(); ++i) {
            ((File) createdFiles.get(i)).delete();
        }
        createdFiles.clear();
    }

    /**
     * Prepares a properties config file.
     *
     * @return a properties config file.
     * @throws Exception to JUnit.
     */
    private File preparePropertiesFile() throws Exception {
        File file = File.createTempFile("unittest", ".properties", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("prop1=value1");
        writer.println("prop1.prop2=value2;value3");
        writer.close();
        createdFiles.add(file);
        return file;
    }

    /**
     * Prepares an xml config file.
     *
     * @return an xml config file.
     * @throws Exception to JUnit.
     */
    private File prepareXMLFile() throws Exception {
        File file = File.createTempFile("unittest", ".xml", new File("test_files"));
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
        return file;
    }

    /**
     * Prepares a multiple namespace xml config file.
     *
     * @return a multiple namespace xml config file.
     * @throws Exception to JUnit.
     */
    private File prepareMultipleXMLFile() throws Exception {
        File file = File.createTempFile("unittest", ".xml", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <Config name=\"component a\">");
        writer.println("        <Property name=\"prop1\">");
        writer.println("            <Value>value1</Value>");
        writer.println("        </Property>");
        writer.println("    </Config>");
        writer.println("    <Config name=\"component b\">");
        writer.println("        <Property name=\"prop1.prop2\">");
        writer.println("            <Value>value2</Value>");
        writer.println("            <Value>value3</Value>");
        writer.println("        </Property>");
        writer.println("    </Config>");
        writer.println("</CMConfig>");
        writer.close();
        createdFiles.add(file);
        return file;
    }

    /**
     * Tests methods from ConfigManagerInterface.
     */
    public void testConfigManagerInterface() {
        assertTrue(cm.getNamespace().equals(ConfigManager.CONFIG_MANAGER_NAMESPACE));
        assertNotNull(cm.getConfigPropNames());
    }

    /**
     * Test add(namespace, filename, format, exceptionLevel).
     *
     * @throws Exception to JUnit.
     */
    public void testAddNamespaceFilenameFormatExceptionLevel() throws Exception {
        String filename = preparePropertiesFile().getAbsolutePath();
        cm.add(
            "test.ns",
            filename,
            ConfigManager.CONFIG_PROPERTIES_FORMAT,
            ConfigManager.EXCEPTIONS_ALL);
        assertTrue(cm.existsNamespace("test.ns"));
        assertTrue(cm.getString("test.ns", "prop1").equals("value1"));
        assertTrue(cm.getString("test.ns", "prop1.prop2").equals("value2;value3"));
        // namespace is null
        try {
            cm.add(
                null,
                filename,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace is empty
        try {
            cm.add(
                "   ",
                filename,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iea) {
        }
        // filename is null
        try {
            cm.add(
                "test.ns",
                null,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // filename is empty
        try {
            cm.add(
                "test.ns",
                "   ",
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iea) {
        }
        // format is null
        try {
            cm.add(
                "test.ns",
                filename,
                null,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // format is invalid
        try {
            cm.add(
                "test.ns",
                filename,
                "UNKNOWN",
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown UnknownConfigFormatException");
        } catch (UnknownConfigFormatException ucfe) {
        }
        // exceptionLevel is invalid
        try {
            cm.add(
                "test.ns",
                filename,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                -101);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iea) {
        }
        // can not locate file
        try {
            cm.add(
                "test.ns",
                "nonexist.properties",
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // namespace already exists
        try {
            cm.add(
                "test.ns",
                filename,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NamespaceAlreadyExistsException");
        } catch (NamespaceAlreadyExistsException naee) {
        }
    }

    /**
     * Tests add(namespace, format, exceptionLevel).
     *
     * @throws Exception to JUnit.
     */
    public void testAddNamespaceFormatExceptionLevel() throws Exception {
        String filename = preparePropertiesFile().getAbsolutePath();
        filename = filename.substring(filename.indexOf("test_files") + 11);
        String namespace = filename.substring(0, filename.lastIndexOf("."));
        cm.add(
            namespace,
            ConfigManager.CONFIG_PROPERTIES_FORMAT,
            ConfigManager.EXCEPTIONS_ALL);
        assertTrue(cm.existsNamespace(namespace));
        assertTrue(cm.getString(namespace, "prop1").equals("value1"));
        assertTrue(cm.getString(namespace, "prop1.prop2").equals("value2;value3"));
        // namespace is null
        try {
            cm.add(
                null,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace is empty
        try {
            cm.add(
                "   ",
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // format is null
        try {
            cm.add(
                namespace,
                null,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // format is invalid
        try {
            cm.add(
                namespace,
                "UNKNOWN",
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown UnknownConfigFormatException");
        } catch (UnknownConfigFormatException ucfe) {
        }
        // exceptionLevel is invalid
        try {
            cm.add(
                namespace,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                -101);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iea) {
        }
        // can not locate file
        try {
            cm.add(
                "test.ns",
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // namespace already exists
        try {
            cm.add(
                namespace,
                ConfigManager.CONFIG_PROPERTIES_FORMAT,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NamespaceAlreadyExistsException");
        } catch (NamespaceAlreadyExistsException naee) {
        }
    }

    /**
     * Tests add(filename, exceptionLevel).
     *
     * @throws Exception to JUnit.
     */
    public void testAddFilenameExceptionLevel() throws Exception {
        String filename = prepareMultipleXMLFile().getAbsolutePath();
        cm.add(
            filename,
            ConfigManager.EXCEPTIONS_ALL);
        assertTrue(cm.existsNamespace("component a"));
        assertTrue(cm.getString("component a", "prop1").equals("value1"));
        assertTrue(cm.existsNamespace("component b"));
        assertTrue(cm.getString("component b", "prop1.prop2").equals("value2;value3"));
        // filename is null
        try {
            cm.add((String) null, ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // filename is empty
        try {
            cm.add(
                "   ",
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // exceptionLevel is invalid
        try {
            cm.add(
                filename,
                -101);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // can not locate file
        try {
            cm.add(
                "nonexist.properties",
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // namespace already exists
        try {
            cm.add(
                filename,
                ConfigManager.EXCEPTIONS_ALL);
            fail("Should have thrown NamespaceAlreadyExistsException");
        } catch (NamespaceAlreadyExistsException naee) {
        }
    }

    /**
     * Test add(namespace, filename, format).
     *
     * @throws Exception to JUnit.
     */
    public void testAddNamespaceFilenameFormat() throws Exception {
        String filename = preparePropertiesFile().getAbsolutePath();
        cm.add(
            "test.ns",
            filename,
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.existsNamespace("test.ns"));
        assertTrue(cm.getString("test.ns", "prop1").equals("value1"));
        assertTrue(cm.getString("test.ns", "prop1.prop2").equals("value2;value3"));
        // namespace is null
        try {
            cm.add(
                null,
                filename,
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace is empty
        try {
            cm.add(
                "   ",
                filename,
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iea) {
        }
        // filename is null
        try {
            cm.add(
                "test.ns",
                null,
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // filename is empty
        try {
            cm.add(
                "test.ns",
                "   ",
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iea) {
        }
        // format is null
        try {
            cm.add(
                "test.ns",
                filename,
                null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // format is invalid
        try {
            cm.add(
                "test.ns",
                filename,
                "UNKNOWN");
            fail("Should have thrown UnknownConfigFormatException");
        } catch (UnknownConfigFormatException ucfe) {
        }
        // can not locate file
        try {
            cm.add(
                "test.ns",
                "nonexist.properties",
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // namespace already exists
        try {
            cm.add(
                "test.ns",
                filename,
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown NamespaceAlreadyExistsException");
        } catch (NamespaceAlreadyExistsException naee) {
        }
    }

    /**
     * Tests add(namespace, format).
     *
     * @throws Exception to JUnit.
     */
    public void testAddNamespaceFormat() throws Exception {
        String filename = preparePropertiesFile().getAbsolutePath();
        filename = filename.substring(filename.indexOf("test_files") + 11);
        String namespace = filename.substring(0, filename.lastIndexOf("."));
        cm.add(
            namespace,
            ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.existsNamespace(namespace));
        assertTrue(cm.getString(namespace, "prop1").equals("value1"));
        assertTrue(cm.getString(namespace, "prop1.prop2").equals("value2;value3"));
        // namespace is null
        try {
            cm.add(
                null,
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace is empty
        try {
            cm.add(
                "   ",
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // format is null
        try {
            cm.add(
                namespace,
                null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // format is invalid
        try {
            cm.add(
                namespace,
                "UNKNOWN");
            fail("Should have thrown UnknownConfigFormatException");
        } catch (UnknownConfigFormatException ucfe) {
        }
        // can not locate file
        try {
            cm.add(
                "test.ns",
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // namespace already exists
        try {
            cm.add(
                namespace,
                ConfigManager.CONFIG_PROPERTIES_FORMAT);
            fail("Should have thrown NamespaceAlreadyExistsException");
        } catch (NamespaceAlreadyExistsException naee) {
        }
    }

    /**
     * Tests add(filename).
     *
     * @throws Exception to JUnit.
     */
    public void testAddFilename() throws Exception {
        String filename = prepareMultipleXMLFile().getAbsolutePath();
        cm.add(
            filename);
        assertTrue(cm.existsNamespace("component a"));
        assertTrue(cm.getString("component a", "prop1").equals("value1"));
        assertTrue(cm.existsNamespace("component b"));
        assertTrue(cm.getString("component b", "prop1.prop2").equals("value2;value3"));
        // filename is null
        try {
            cm.add((String) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // filename is empty
        try {
            cm.add("   ");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }
        // can not locate file
        try {
            cm.add(
                "nonexist.properties");
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // namespace already exists
        try {
            cm.add(
                filename);
            fail("Should have thrown NamespaceAlreadyExistsException");
        } catch (NamespaceAlreadyExistsException naee) {
        }
    }

    /**
     * Tests refresh().
     *
     * @throws Exception to JUnit.
     */
    public void testRefresh() throws Exception {
        File file = preparePropertiesFile();
        cm.add("test.ns", file.getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        file.delete();
        createdFiles.clear();
        try {
            cm.refresh("test.ns");
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // the namespace should be invalid
        assertFalse(cm.existsNamespace("test.ns"));
        // namespace is null
        try {
            cm.refresh(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.refresh("unknown.ns");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests refreshAll().
     *
     * @throws Exception to JUnit.
     */
    public void testRefreshAll() throws Exception {
        File file1 = preparePropertiesFile();
        cm.add("properties.ns", file1.getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        File file2 = prepareXMLFile();
        cm.add("xml.ns", file2.getAbsolutePath(), ConfigManager.CONFIG_XML_FORMAT);
        file1.delete();
        file2.delete();
        createdFiles.clear();
        try {
            cm.refreshAll();
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
        // namespaces should all be invalid
        assertFalse(cm.existsNamespace("properties.ns"));
        assertFalse(cm.existsNamespace("xml.ns"));
    }

    /**
     * Tests getPropertyObject(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testGetPropertyObjectNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        Property property = cm.getPropertyObject("test.ns", "prop1");
        assertTrue(property.getValue().equals("value1"));
        assertTrue(property.list().size() == 1);
        assertTrue(((Property) property.list().get(0)).getName().equals("prop2"));
        assertNull(cm.getPropertyObject("test.ns", "prop2"));
        // namespace is null
        try {
            cm.getPropertyObject(null, "prop1");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.getPropertyObject("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getPropertyObject("nonexist.ns", "prop1");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getString(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testGetStringNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.getString("test.ns", "prop1").equals("value1"));
        assertTrue(cm.getString("test.ns", "prop1.prop2").equals("value2;value3"));
        assertNull(cm.getString("test.ns", "prop2"));
        // namespace is null
        try {
            cm.getString(null, "prop1");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.getString("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getString("nonexist.ns", "prop1");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getStringArray(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testGetStringArrayNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.getStringArray("test.ns", "prop1").length == 1);
        assertTrue(cm.getStringArray("test.ns", "prop1")[0].equals("value1"));
        assertTrue(cm.getStringArray("test.ns", "prop1.prop2").length == 2);
        assertTrue(cm.getStringArray("test.ns", "prop1.prop2")[0].equals("value2"));
        assertTrue(cm.getStringArray("test.ns", "prop1.prop2")[1].equals("value3"));
        assertNull(cm.getStringArray("test.ns", "prop2"));
        // namespace is null
        try {
            cm.getStringArray(null, "prop1");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.getStringArray("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getStringArray("nonexist.ns", "prop1");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getProperty(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testGetPropertyNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.getProperty("test.ns", "prop1").equals("value1"));
        assertTrue(cm.getProperty("test.ns", "prop1.prop2").equals("value2;value3"));
        assertNull(cm.getProperty("test.ns", "prop2"));
        // namespace is null
        try {
            cm.getProperty(null, "prop1");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.getProperty("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getProperty("nonexist.ns", "prop1");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getPropertyNames(namespace).
     *
     * @throws Exception to JUnit.
     */
    public void testGetPropertyNamesNamespace() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        Enumeration enu = cm.getPropertyNames("test.ns");
        assertTrue(enu.hasMoreElements());
        assertTrue(enu.nextElement().equals("prop1"));
        assertFalse(enu.hasMoreElements());
        // namespace is null
        try {
            cm.getPropertyNames(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getPropertyNames("nonexist.ns");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests existsNamespace(namespace).
     *
     * @throws Exception to JUnit.
     */
    public void testExistsNamespaceNamespace() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.existsNamespace("test.ns"));
        assertFalse(cm.existsNamespace("nonexist.ns"));
        // namespace is null
        try {
            cm.existsNamespace(null);
            fail("Should have thrwon NullPointerException");
        } catch (NullPointerException npe) {
        }
    }

    /**
     * Tests getConfigFormat(namespace).
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfigFormatNamespace() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.getConfigFormat("test.ns").equals(ConfigManager.CONFIG_PROPERTIES_FORMAT));
        // namespace is null
        try {
            cm.getConfigFormat(null);
            fail("Should have thrwon NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getConfigFormat("nonexist.ns");
            fail("Should have thrwon UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getConfigFilename(namespace).
     *
     * @throws Exception to JUnit.
     */
    public void testGetConfigFilenameNamespace() throws Exception {
        String filename = preparePropertiesFile().getAbsolutePath();
        cm.add("test.ns", filename, ConfigManager.CONFIG_PROPERTIES_FORMAT);
        assertTrue(cm.getConfigFilename("test.ns").endsWith(filename.substring(filename.indexOf("test_files") + 11)));
        // namespace is null
        try {
            cm.getConfigFilename(null);
            fail("Should have thrwon NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getConfigFilename("nonexist.ns");
            fail("Should have thrwon UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getAllNamespaces().
     *
     * @throws Exception to JUnit.
     */
    public void testGetAllNamespaces() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        Iterator itr = cm.getAllNamespaces();
        assertTrue(itr.hasNext());
        assertTrue(itr.next().equals("test.ns"));
        assertFalse(itr.hasNext());
    }

    /**
     * Tests canLock(namespace, user).
     *
     * @throws Exception to JUnit.
     */
    public void testCanLockNamespaceUser() throws Exception {
        cm.add(prepareMultipleXMLFile().getAbsolutePath());
        assertTrue(cm.canLock("component a", "foo"));
        assertTrue(cm.canLock("component b", "foo"));
        cm.lock("component a", "foo");
        assertFalse(cm.canLock("component a", "bar"));
        assertFalse(cm.canLock("component b", "bar"));
        assertTrue(cm.canLock("component a", "foo"));
        assertTrue(cm.canLock("component b", "foo"));
        // namespace is null
        try {
            cm.canLock(null, "foo");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // user is null
        try {
            cm.canLock("component a", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.canLock("nonexist.ns", "foo");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests lock(namespace, user).
     *
     * @throws Exception to JUnit.
     */
    public void testLockNamespaceUser() throws Exception {
        cm.add(prepareMultipleXMLFile().getAbsolutePath());
        cm.lock("component a", "foo");
        try {
            cm.lock("component b", "bar");
            fail("Should have thrown ConfigLockedException");
        } catch (ConfigLockedException cle) {
        }
        cm.lock("component a", "foo");
        cm.lock("component b", "foo");
        // namespace is null
        try {
            cm.lock(null, "foo");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // user is null
        try {
            cm.lock("component a", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.lock("nonexist.ns", "foo");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests forceUnlock(user).
     *
     * @throws Exception to JUnit.
     */
    public void testForceUnlockUser() throws Exception {
        cm.add(prepareMultipleXMLFile().getAbsolutePath());
        cm.lock("component a", "foo");
        assertFalse(cm.canLock("component b", "bar"));
        cm.forceUnlock("component b");
        assertTrue(cm.canLock("component a", "bar"));
        assertTrue(cm.canLock("component b", "bar"));
        // namespace is null
        try {
            cm.forceUnlock(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.forceUnlock("nonexist.ns");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests createTemporaryProperties(namespace).
     *
     * @throws Exception to JUnit.
     */
    public void testCreateTemporaryPropertiesNamespace() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        try {
            cm.commit("test.ns", "foo");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
        cm.createTemporaryProperties("test.ns");
        cm.commit("test.ns", "foo");
        // namespace is null
        try {
            cm.createTemporaryProperties(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.createTemporaryProperties("nonexist.ns");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getTemporaryPropertyNames(namespace).
     *
     * @throws Exception to JUnit.
     */
    public void testGetTemporaryPropertyNames() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        cm.setProperty("test.ns", "prop4", "value5");
        Enumeration enu = cm.getTemporaryPropertyNames("test.ns");
        assertTrue(enu.hasMoreElements());
        assertTrue(enu.nextElement().equals("prop1"));
        assertTrue(enu.hasMoreElements());
        assertTrue(enu.nextElement().equals("prop4"));
        assertFalse(enu.hasMoreElements());
        // namespace is null
        try {
            cm.getTemporaryPropertyNames(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getTemporaryPropertyNames("nonexist.ns");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getTemporaryPropertyObject(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testGetTemporaryPropertyObjectNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        Property property = cm.getTemporaryPropertyObject("test.ns", "prop1");
        assertTrue(property.getValue().equals("value1"));
        assertTrue(property.list().size() == 1);
        assertTrue(((Property) property.list().get(0)).getName().equals("prop2"));
        assertNull(cm.getPropertyObject("test.ns", "prop2"));
        // namespace is null
        try {
            cm.getTemporaryPropertyObject(null, "prop1");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.getTemporaryPropertyObject("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getTemporaryPropertyObject("nonexist.ns", "prop1");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getTemporaryString(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testGetTemporaryStringNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        assertTrue(cm.getTemporaryString("test.ns", "prop1").equals("value1"));
        assertTrue(cm.getTemporaryString("test.ns", "prop1.prop2").equals("value2;value3"));
        assertNull(cm.getTemporaryString("test.ns", "prop2"));
        // namespace is null
        try {
            cm.getTemporaryString(null, "prop1");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.getTemporaryString("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getTemporaryString("nonexist.ns", "prop1");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests getTemporatyStringArray(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testGetTemporaryStringArrayNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1").length == 1);
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1")[0].equals("value1"));
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2").length == 2);
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2")[0].equals("value2"));
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2")[1].equals("value3"));
        assertNull(cm.getTemporaryStringArray("test.ns", "prop2"));
        // namespace is null
        try {
            cm.getTemporaryStringArray(null, "prop1");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.getTemporaryStringArray("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.getTemporaryStringArray("nonexist.ns", "prop1");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests setProperty(namespace, key, value).
     *
     * @throws Exception to JUnit.
     */
    public void testSetPropertyNamespaceKeyValue() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        cm.setProperty("test.ns", "prop1.prop2.prop3", "value");
        assertTrue(cm.getTemporaryString("test.ns", "prop1.prop2.prop3").equals("value"));
        // namespace is null
        try {
            cm.setProperty(null, "prop", "value");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.setProperty("test.ns", null, "value");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // value is null
        try {
            cm.setProperty("test.ns", "prop", (String) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.setProperty("nonexist.ns", "prop", "value");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests setProperty(namespace, key, values).
     *
     * @throws Exception to JUnit.
     */
    public void testSetPropertyNamespaceKeyValues() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        cm.setProperty("test.ns", "prop1.prop2.prop3", new String[] {"value1", "value2"});
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2.prop3").length == 2);
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2.prop3")[0].equals("value1"));
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2.prop3")[1].equals("value2"));
        // namespace is null
        try {
            cm.setProperty(null, "prop", new String[] {"value1", "value2"});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.setProperty("test.ns", null, new String[] {"value1", "value2"});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // values is null
        try {
            cm.setProperty("test.ns", "prop", (String[]) null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // values contains null entry
        try {
            cm.setProperty("test.ns", "prop", new String[] {"value", null});
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.setProperty("nonexist.ns", "prop", new String[] {"value1", "value2"});
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests addToProperty(namespace, key, value).
     *
     * @throws Exception to JUnit.
     */
    public void testAddToPropertyNamespaceKeyValue() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        cm.addToProperty("test.ns", "prop1.prop2", "value4");
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2").length == 3);
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2")[0].equals("value2"));
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2")[1].equals("value3"));
        assertTrue(cm.getTemporaryStringArray("test.ns", "prop1.prop2")[2].equals("value4"));
        cm.addToProperty("test.ns", "prop3", "value5");
        assertTrue(cm.getTemporaryString("test.ns", "prop3").equals("value5"));
        // namespace is null
        try {
            cm.addToProperty(null, "prop", "value");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.addToProperty("test.ns", null, "value");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // value is null
        try {
            cm.addToProperty("test.ns", "prop", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.addToProperty("nonexist.ns", "prop", "value");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests removeProperty(namespace, key).
     *
     * @throws Exception to JUnit.
     */
    public void testRemovePropertyNamespaceKey() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        cm.removeProperty("test.ns", "prop1");
        assertNull(cm.getTemporaryString("test.ns", "prop1"));
        assertNull(cm.getTemporaryString("test.ns", "prop1.prop2"));
        // namespace is null
        try {
            cm.removeProperty(null, "prop");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.removeProperty("test.ns", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.removeProperty("nonexist.ns", "prop");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests removeValue(namespace, key, value).
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveValueNamespaceKeyValue() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.createTemporaryProperties("test.ns");
        cm.removeValue("test.ns", "prop1.prop2", "value2");
        assertTrue(cm.getTemporaryString("test.ns", "prop1.prop2").equals("value3"));
        // namespace is null
        try {
            cm.removeValue(null, "prop", "value");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // key is null
        try {
            cm.removeValue("test.ns", null, "value");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // value is null
        try {
            cm.removeValue("test.ns", "prop", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.removeValue("nonexist.ns", "prop", "value");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests commit(namespace, user).
     *
     * @throws Exception to JUnit.
     */
    public void testCommitNamespaceUser() throws Exception {
        cm.add(prepareMultipleXMLFile().getAbsolutePath());
        cm.createTemporaryProperties("component b");
        cm.lock("component a", "foo");
        try {
            cm.commit("component b", "bar");
            fail("Should have thrown ConfigLockedException");
        } catch (ConfigLockedException cle) {
        }
        cm.commit("component b", "foo");
        cm.forceUnlock("component a");
        // namespace is null
        try {
            cm.commit(null, "foo");
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // user is null
        try {
            cm.commit("component b", null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.commit("nonexist.ns", "foo");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests removeNamespace(namespace).
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNamespaceNamespace() throws Exception {
        cm.add("test.ns", preparePropertiesFile().getAbsolutePath(), ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.removeNamespace("test.ns");
        assertFalse(cm.existsNamespace("test.ns"));
        // namespace is null
        try {
            cm.removeNamespace(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException npe) {
        }
        // namespace does not exist
        try {
            cm.removeNamespace("nonexist.ns");
            fail("Should have thrown UnknownNamespaceException");
        } catch (UnknownNamespaceException une) {
        }
    }

    /**
     * Tests the accuracy of the {@link ConfigManager#add(URL)} method.
     *
     * @throws Exception to JUnit.
     * @since 2.1.5
     */
    public void testAddURL() throws Exception {
        File file = new File("test_files/SampleMultipleConfig.xml");
        cm.add(file.toURL());
        assertTrue("Should load namespace.", cm.existsNamespace("Component A"));
        assertTrue("Should load namespace.", cm.existsNamespace("Component B"));

        assertEquals("Incorrect property value.", "Value", cm.getString("Component B", "Prop2"));
    }

    /**
     * Tests the accuracy of the {@link ConfigManager#add(URL, int)} method.
     *
     * @throws Exception to JUnit.
     * @since 2.1.5
     */
    public void testAddURLInt() throws Exception {
        File file = new File("test_files/SampleMultipleConfig.xml");
        cm.add(file.toURL(), ConfigManager.EXCEPTIONS_ALL);
        assertTrue("Should load namespace.", cm.existsNamespace("Component A"));
        assertTrue("Should load namespace.", cm.existsNamespace("Component B"));

        assertEquals("Incorrect property value.", "Value2", cm.getStringArray("Component A", "Prop2")[1]);
    }

    /**
     * Tests the failure of the {@link ConfigManager#add(URL)} method. The URL is null.
     *
     * @throws Exception to JUnit.
     * @since 2.1.5
     */
    public void testAddURL_Null() throws Exception {
        try {
            cm.add((URL) null);
            fail("Null URL, NPE expected.");
        } catch (NullPointerException ex) {
            // ok
        }
    }

    /**
     * Tests the failure of the {@link ConfigManager#add(URL)} method.
     * The source file doesn't exists.
     *
     * @throws Exception to JUnit.
     * @since 2.1.5
     */
    public void testAddURL_InvalidURL() throws Exception {
        try {
            cm.add(new File("missing").toURL());
            fail("File not exists.");
        } catch (ConfigManagerException ex) {
            // ok
        }
    }
}
