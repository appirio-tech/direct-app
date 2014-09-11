/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) PropConfigPropertiesTestCase.java
 *
 * 2.1  02/01/2004
 */
package com.topcoder.util.config;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Tests the behavior of PropConfigProperties.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class PropConfigPropertiesTestCase extends TestCase {

    /**
     * The file associated with the PropConfiProperties.
     */
    File file = null;

    /**
     * The PropConfigProperties instance.
     */
    PropConfigProperties pcp = null;

    /**
     * Set up testing environment.
     * Creates a tempoary file and populate it with data.
     * Then create PropConfigProperties and load data.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        file = File.createTempFile("unittest", ".properties", new File("test_files"));
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("#comment1");
        writer.println("a=value1");
        writer.println("!comment2");
        writer.println("a.b.c=value3;value4");
        writer.println("b=value2");
        writer.close();
        pcp = new PropConfigProperties(file.toURL());
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
    }

    /**
     * Test create PropConfigProperties with source.
     */
    public void testCreatePropConfigPropertiesSource() {
        assertNotNull(pcp);
        // checks the properties are really loaded
        Property root = pcp.getRoot();
        Property a = root.getProperty("a");
        Property b = root.getProperty("b");
        Property abc = root.getProperty("a.b.c");
        assertNotNull(a);
        assertTrue(a.getValue().equals("value1"));
        assertNotNull(b);
        assertTrue(b.getValue().equals("value2"));
        assertNotNull(abc);
        assertTrue(abc.getValues().length == 2);
        assertTrue(abc.getValues()[0].equals("value3"));
        assertTrue(abc.getValues()[1].equals("value4"));
        // the comments should also be in place
        List comments = a.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("#comment1"));
        assertNull(b.getComments());
        comments = abc.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("!comment2"));
    }

    /**
     * Test load().
     * When the file is changed, load should be able to fetch a new copy of the properties.
     *
     * @throws Exception to JUnit.
     */
    public void testLoad() throws Exception {
        // modify the file
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("#newcomment");
        writer.println("a=value1");
        writer.println("!comment2");
        writer.println("a.b.c=newvalue");
        writer.println("b=value2");
        writer.close();
        // now load and verify the changes
        pcp.load();
        Property root = pcp.getRoot();
        Property a = root.getProperty("a");
        List comments = a.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("#newcomment"));
        Property abc = root.getProperty("a.b.c");
        assertTrue(abc.getValue().equals("newvalue"));
    }

    /**
     * Test save().
     * The new property tree is saved to the file.
     *
     * @throws Exception to JUnit.
     */
    public void testSave() throws Exception {
        // construct a new property tree
        Property root = new Property();
        root.setProperty("a", "value1");
        root.setProperty("b", "value2");
        root.setProperty("a.b.c", "newvalue");
        root.getProperty("a").addComment("#newcomment");
        root.getProperty("a.b.c").addComment("!comment2");
        pcp.setRoot(root);
        // now save it and verify the underlying file
        pcp.save();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        assertTrue(reader.readLine().equals("#newcomment"));
        assertTrue(reader.readLine().equals("a=value1"));
        assertTrue(reader.readLine().equals("!comment2"));
        assertTrue(reader.readLine().equals("a.b.c=newvalue"));
        assertTrue(reader.readLine().equals("b=value2"));
        assertNull(reader.readLine());
        reader.close();
    }

    /**
     * Tests failure situation - no file.
     *
     * @throws Exception to JUnit.
     */
    public void testFailureNoFile() throws Exception {
        file.delete();
        file = null;
        try {
            pcp.load();
            fail("Should have thrown IOException");
        } catch (IOException ioe) {
        }
    }

    /**
     * Tests failure situation - empty property name.
     *
     * @throws Exception to JUnit.
     */
    public void testFailureEmptyPropertyName() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("   =value");
        writer.close();
        try {
            pcp.load();
            fail("Should have thrown ConfigParserException");
        } catch (ConfigParserException cpe) {
        }
    }

    /**
     * Tests failure situation - duplicate property name.
     *
     * @throws Exception to JUnit.
     */
    public void testFailureDuplicatePropertyName() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("prop=value1");
        writer.println("prop=value2");
        writer.close();
        try {
            pcp.load();
            fail("Should have thrown ConfigParserException");
        } catch (ConfigParserException cpe) {
        }
    }

    /**
     * Tests clone().
     */
    public void testClone() {
        PropConfigProperties copy = (PropConfigProperties) pcp.clone();
        // verify the clone
        Property root = copy.getRoot();
        Property a = root.getProperty("a");
        Property b = root.getProperty("b");
        Property abc = root.getProperty("a.b.c");
        assertNotNull(a);
        assertTrue(a.getValue().equals("value1"));
        assertNotNull(b);
        assertTrue(b.getValue().equals("value2"));
        assertNotNull(abc);
        assertTrue(abc.getValues().length == 2);
        assertTrue(abc.getValues()[0].equals("value3"));
        assertTrue(abc.getValues()[1].equals("value4"));
        // the comments should also be in place
        List comments = a.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("#comment1"));
        assertNull(b.getComments());
        comments = abc.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("!comment2"));
        // the clone should not be a shallow copy
        root = pcp.getRoot();
        assertTrue(root.getProperty("a") != a);
        assertTrue(root.getProperty("b") != b);
        assertTrue(root.getProperty("a.b.c") != abc);
    }

}
