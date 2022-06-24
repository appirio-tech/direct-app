/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) PluggableConfigPropertiesTestCase.java
 *
 * 2.1  02/01/2004
 */
package com.topcoder.util.config;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests the behavior of PluggableConfigProperties.
 * The test cases use custom implementation of PluggableConfigSource for testing.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class PluggableConfigPropertiesTestCase extends TestCase {

    /**
     * The config file.
     */
    private File file = null;
    
    /**
     * The URL of the config file.
     */
    private URL url = null;

    /**
     * The PluggableConfigProperties instance to test.
     */
    private PluggableConfigProperties pcp = null;

    /**
     * Set up testing environment.
     * Make up a sample config file and construct PluggableConfigProperties with it.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        file = File.createTempFile("unittest", ".config", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("prop1=value1");
        writer.println("classname=com.topcoder.util.config.CustomPluggableConfigSource");
        writer.println("prop2=value2");
        writer.close();
        url = file.toURL();
        pcp = new PluggableConfigProperties(url);
    }

    /**
     * Tear down testing environment.
     * Remove the file.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        pcp = null;
        if (file != null) {
            file.delete();
            file = null;
        }
        CustomPluggableConfigSource.clear();
    }

    /**
     * Tests create PluggableConfigProperties with file.
     */
    public void testCreatePluggableConfigPropertiesFile() {
        assertNotNull(pcp);
        // verify the source has been configured
        Properties props = CustomPluggableConfigSource.getProps();
        assertNotNull(props);
        assertTrue(props.size() == 3);
        assertTrue(props.getProperty("prop1").equals("value1"));
        assertTrue(props.getProperty("prop2").equals("value2"));
        assertTrue(props.getProperty("classname").equals("com.topcoder.util.config.CustomPluggableConfigSource"));
        // verify the property tree is loaded
        Property root = pcp.getRoot();
        assertTrue(root.list().size() == 3);
        assertTrue(root.getValue("prop1").equals("value1"));
        assertTrue(root.getValue("prop2").equals("value2"));
        assertTrue(root.getValue("prop3").equals("value3"));
    }

    /**
     * Tests create failure sitaution - no file.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFailureNoFile() throws Exception {
        file.delete();
        try {
            pcp = new PluggableConfigProperties(url);
            fail("Should have thrown IOExceptoin");
        } catch (IOException ioe) {
        } finally {
            file = null;
        }
    }

    /**
     * Tests create failure situation - no class name.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFailureNoClassName() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("prop1=value1");
        writer.println("prop2=value2");
        writer.close();
        try {
            pcp = new PluggableConfigProperties(url);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
    }

    /**
     * Tests create failure situation - class not found.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFailureClassNotFound() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("classname=com.nonexist.ConfigSource");
        writer.close();
        try {
            pcp = new PluggableConfigProperties(url);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
    }

    /**
     * Tests create failure situation - incorrect class.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateFailureIncorrectClass() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("classname=java.util.HashMap");
        writer.close();
        try {
            pcp = new PluggableConfigProperties(url);
            fail("Should have thrown ConfigManagerException");
        } catch (ConfigManagerException cme) {
        }
    }

    /**
     * Tests create failure situation - underlying exception.
     * Underlying exception is propogated.
     */
    public void testCreateFailureUnderlyingException() {
        CustomPluggableConfigSource.setExceptional(true);
        try {
            pcp = new PluggableConfigProperties(url);
            fail("Should have thrown IOException");
        } catch (IOException ioe) {
        }
    }

    /**
     * Tests load().
     * Only tests if exception is propogated.
     *
     * @throws Exception to JUnit.
     */
    public void testLoad() throws Exception {
        CustomPluggableConfigSource.setExceptional(true);
        try {
            pcp.load();
            fail("Should have thrown ConfigParserException");
        } catch (ConfigParserException cpe) {
        }
    }

    /**
     * Tests save().
     * Tests whether the property tree is saved to the underlying source.
     *
     * @throws Exception to JUnit.
     */
    public void testSave() throws Exception {
        Property root = new Property();
        pcp.setRoot(root);
        pcp.save();
        assertTrue(CustomPluggableConfigSource.getRoot() == root);
    }

    /**
     * Tests failure situation - underlying exception.
     */
    public void testFailureUnderlyingException() {
        CustomPluggableConfigSource.setExceptional(true);
        try {
            pcp.save();
            fail("Should have thrown IOException");
        } catch (IOException ioe) {
        }
    }

    /**
     * Tests clone().
     */
    public void testClone() {
        PluggableConfigProperties copy = (PluggableConfigProperties) pcp.clone();
        // verify the clone
        Property root = copy.getRoot();
        Property prop1 = root.getProperty("prop1");
        Property prop2 = root.getProperty("prop2");
        Property prop3 = root.getProperty("prop3");
        assertNotNull(prop1);
        assertTrue(prop1.getValue().equals("value1"));
        assertNotNull(prop2);
        assertTrue(prop2.getValue().equals("value2"));
        assertNotNull(prop3);
        assertTrue(prop3.getValue().equals("value3"));
        // the clone should not be a shallow copy
        root = pcp.getRoot();
        assertTrue(root.getProperty("prop1") != prop1);
        assertTrue(root.getProperty("prop2") != prop2);
        assertTrue(root.getProperty("prop3") != prop3);
    }

}

/**
 * Custom implementation of PluggableConfigSource for testing purpose only.
 *
 * @author  WishingBone
 * @version 2.1
 */
class CustomPluggableConfigSource implements PluggableConfigSource {

    /**
     * Whether to throw exception on method invocation.
     */
    private static boolean exceptional = false;

    /**
     * Set whether to throw exception on method invocation.
     *
     * @param  exceptional whether to throw exception on method invocation.
     */
    public static void setExceptional(boolean exceptional) {
        CustomPluggableConfigSource.exceptional = exceptional;
    }

    /**
     * The configure properties.
     */
    private static Properties props = null;

    /**
     * Get the configure properties.
     *
     * @return the configure properties.
     */
    public static Properties getProps() {
        return props;
    }

    /**
     * The saved root of the property tree.
     */
    private static Property root = null;

    /**
     * Get the saved root of the property tree.
     *
     * @return the saved root of the property tree.
     */
    public static Property getRoot() {
        return root;
    }

    /**
     * Set fields to default.
     */
    public static void clear() {
        exceptional = false;
        props = null;
        root = null;
    }

    /**
     * Configure the source.
     *
     * @param  props the properties to configure the source.
     * @throws ConfigParserException if it is unable to parse config.
     */
    public void configure(Properties props) throws ConfigParserException {
        if (exceptional) {
            throw new ConfigParserException();
        }
        CustomPluggableConfigSource.props = props;
    }

    /**
     * Save the property tree to underlying source.
     *
     * @param  root the root of the property tree.
     * @throws IOException if underlying source is unable to save.
     */
    public void save(Property root) throws IOException {
        if (exceptional) {
            throw new IOException();
        }
        CustomPluggableConfigSource.root = root;
    }

    /**
     * Get a list of properties from the underlying source.
     */
    public List getProperties() throws ConfigParserException {
        if (exceptional) {
            throw new ConfigParserException();
        }
        List list = new ArrayList();
        list.add(new Property("prop1", "value1"));
        list.add(new Property("prop2", "value2"));
        list.add(new Property("prop3", "value3"));
        return list;
    }

}
