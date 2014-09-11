/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) XMLConfigPropertiesTestCase.java
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
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Tests the behavior of XMLConfigProperties.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class XMLConfigPropertiesTestCase extends TestCase {

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
        writer.println("    <!--comment1-->");
        writer.println("    <Property name=\"a\">");
        writer.println("        <Value>value1</Value>");
        writer.println("    </Property>");
        writer.println("    <!--comment2-->");
        writer.println("    <Property name=\"a.b.c\">");
        writer.println("        <Value>value3</Value>");
        writer.println("        <Value>value4</Value>");
        writer.println("    </Property>");
        writer.println("    <Property name=\"b\">");
        writer.println("        <Value>value2</Value>");
        writer.println("    </Property>");
        writer.println("</CMConfig>");
        writer.close();
        xmlcp = new XMLConfigProperties(file.toURL());
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
     * Test create XMLConfigProperties with source.
     */
    public void testCreatePropConfigPropertiesSource() {
        assertNotNull(xmlcp);
        // checks the properties are really loaded
        Property root = xmlcp.getRoot();
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
        assertTrue(comments.get(0).equals("comment1"));
        assertNull(b.getComments());
        comments = abc.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("comment2"));
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
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <!--newcomment-->");
        writer.println("    <Property name=\"a\">");
        writer.println("        <Value>value1</Value>");
        writer.println("    </Property>");
        writer.println("    <!--comment2-->");
        writer.println("    <Property name=\"a.b.c\">");
        writer.println("        <Value>newvalue</Value>");
        writer.println("    </Property>");
        writer.println("    <Property name=\"b\">");
        writer.println("        <Value>value2</Value>");
        writer.println("    </Property>");
        writer.println("</CMConfig>");
        writer.close();
        // now load and verify the changes
        xmlcp.load();
        Property root = xmlcp.getRoot();
        Property a = root.getProperty("a");
        List comments = a.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("newcomment"));
        Property abc = root.getProperty("a.b.c");
        assertTrue(abc.getValue().equals("newvalue"));
    }

    /**
     * Test save().
     * The new property tree is saved to the file.
     *
     * The test case is commented out in during the fix.
     * The output file should support nested property element.
     *
     * @throws Exception to JUnit.
     */
/*    public void testSave() throws Exception {
        // construct a new property tree
        Property root = new Property();
        root.setProperty("a", "value1");
        root.setProperty("b", "value2");
        root.setProperty("a.b.c", "newvalue");
        root.getProperty("a").addComment("newcomment");
        root.getProperty("a.b.c").addComment("comment2");
        xmlcp.setRoot(root);
        // now save it and verify the underlying file
        xmlcp.save();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        assertTrue(reader.readLine().equals("<?xml version=\"1.0\"?>"));
        assertTrue(reader.readLine().equals("<CMConfig>"));
        assertTrue(reader.readLine().equals("    <!--newcomment-->"));
        assertTrue(reader.readLine().equals("    <Property name=\"a\">"));
        assertTrue(reader.readLine().equals("        <Value>value1</Value>"));
        assertTrue(reader.readLine().equals("    </Property>"));
        assertTrue(reader.readLine().equals("    <!--comment2-->"));
        assertTrue(reader.readLine().equals("    <Property name=\"a.b.c\">"));
        assertTrue(reader.readLine().equals("        <Value>newvalue</Value>"));
        assertTrue(reader.readLine().equals("    </Property>"));
        assertTrue(reader.readLine().equals("    <Property name=\"b\">"));
        assertTrue(reader.readLine().equals("        <Value>value2</Value>"));
        assertTrue(reader.readLine().equals("    </Property>"));
        assertTrue(reader.readLine().equals("</CMConfig>"));
        assertNull(reader.readLine());
        reader.close();
    }
*/

    /**
     * Tests failure situation - no file.
     *
     * @throws Exception to JUnit.
     */
    public void testFailureNoFile() throws Exception {
        file.delete();
        file = null;
        try {
            xmlcp.load();
            fail("Should have thrown IOException");
        } catch (IOException ioe) {
        }
    }

    /**
     * Tests failure situation - incorrect format.
     *
     * @throws Exception to JUnit.
     */
    public void testFailureIncorrectFormat() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <Foo name=\"a\">");
        writer.println("        <Bar>value1</Bar>");
        writer.println("    </Foo>");
        writer.println("</CMConfig>");
        writer.close();
        try {
            xmlcp.load();
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
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <Property name=\"prop\">");
        writer.println("        <Value>value1</Value>");
        writer.println("    </Property>");
        writer.println("    <Property name=\"prop\">");
        writer.println("        <Value>value2</Value>");
        writer.println("    </Property>");
        writer.println("</CMConfig>");
        writer.close();
        try {
            xmlcp.load();
            fail("Should have thrown ConfigParserException");
        } catch (ConfigParserException cpe) {
        }
    }

    /**
     * Test list delimiter.
     * List delimiter should be handled.
     *
     * @throws Exception to JUnit.
     */
    public void testListDelimiter() throws Exception {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<CMConfig>");
        writer.println("    <ListDelimiter>%</ListDelimiter>");
        writer.println("    <Property name=\"prop\">");
        writer.println("        <Value>value1</Value>");
        writer.println("        <Value>value2%value3</Value>");
        writer.println("        <Value>value;4</Value>");
        writer.println("    </Property>");
        writer.println("</CMConfig>");
        writer.close();
        xmlcp.load();
        // verify
        Property root = xmlcp.getRoot().getProperty("prop");
        assertTrue(root.getValues().length == 4);
        assertTrue(root.getValues()[0].equals("value1"));
        assertTrue(root.getValues()[1].equals("value2"));
        assertTrue(root.getValues()[2].equals("value3"));
        // default delimeter is overriden!
        assertTrue(root.getValues()[3].equals("value;4"));
    }

    /**
     * Tests clone().
     */
    public void testClone() {
        XMLConfigProperties copy = (XMLConfigProperties) xmlcp.clone();
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
        assertTrue(comments.get(0).equals("comment1"));
        assertNull(b.getComments());
        comments = abc.getComments();
        assertTrue(comments.size() == 1);
        assertTrue(comments.get(0).equals("comment2"));
        // the clone should not be a shallow copy
        root = xmlcp.getRoot();
        assertTrue(root.getProperty("a") != a);
        assertTrue(root.getProperty("b") != b);
        assertTrue(root.getProperty("a.b.c") != abc);
    }

    /**
     * Tests the handler methods.
     * These method should wrap the incomming exception and rethrow.
     * Messages are logged to standard error.
     */
    public void testHandlerMethods() {
        SAXParseException saxpe = new SAXParseException("unit test - this should appear on stderr", "a", "b", 1, 2);
        try {
            xmlcp.warning(saxpe);
        } catch (SAXException saxe) {
            fail("No exception should be thrown.");
        }
        try {
            xmlcp.error(saxpe);
            fail("Should have thrown SAXException");
        } catch (SAXException saxe) {
            assertTrue(saxe.getMessage().indexOf("unit test - this should appear on stderr") != -1);
        }
        try {
            xmlcp.fatalError(saxpe);
            fail("Should have thrown SAXException");
        } catch (SAXException saxe) {
            assertTrue(saxe.getMessage().indexOf("unit test - this should appear on stderr") != -1);
        }
    }

}
