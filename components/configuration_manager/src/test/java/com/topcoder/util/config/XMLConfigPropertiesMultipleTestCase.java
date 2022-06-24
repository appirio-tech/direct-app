/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) XMLConfigPropertiesMultipleTestCase.java
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
import java.util.Vector;
import java.util.Enumeration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Tests the behavior of XMLConfigProperties with multiple namespaces.
 *
 * @author  WishingBone
 * @version 2.1
 */
public class XMLConfigPropertiesMultipleTestCase extends TestCase {

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
        writer.println("    <Config name=\"component a\">");
        writer.println("        <Property name=\"a\">");
        writer.println("            <Value>a</Value>");
        writer.println("        </Property>");
        writer.println("    </Config>");
        writer.println("    <Config name=\"component b\">");
        writer.println("        <Property name=\"b\">");
        writer.println("            <Value>b</Value>");
        writer.println("        </Property>");
        writer.println("        <Property name=\"b.c\">");
        writer.println("            <Value>c</Value>");
        writer.println("        </Property>");
        writer.println("    </Config>");
        writer.println("</CMConfig>");
        writer.close();
        xmlcp = new XMLConfigProperties(file.toURL(), "component b");
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
     * Test create XMLConfigProperties with source and namespace.
     */
    public void testCreatePropConfigPropertiesSourceNamespace() {
        assertNotNull(xmlcp);
        // checks the properties are really loaded
        Property root = xmlcp.getRoot();
        Property b = root.getProperty("b");
        Property bc = root.getProperty("b.c");
        assertNotNull(b);
        assertTrue(b.getValue().equals("b"));
        assertNotNull(bc);
        assertTrue(bc.getValue().equals("c"));
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
        assertTrue(reader.readLine().equals("    <Config name=\"component a\">"));
        assertTrue(reader.readLine().equals("        <Property name=\"a\">"));
        assertTrue(reader.readLine().equals("            <Value>a</Value>"));
        assertTrue(reader.readLine().equals("        </Property>"));
        assertTrue(reader.readLine().equals("    </Config>"));
        assertTrue(reader.readLine().equals("    <Config name=\"component b\">"));
        assertTrue(reader.readLine().equals("        <!--newcomment-->"));
        assertTrue(reader.readLine().equals("        <Property name=\"a\">"));
        assertTrue(reader.readLine().equals("            <Value>value1</Value>"));
        assertTrue(reader.readLine().equals("        </Property>"));
        assertTrue(reader.readLine().equals("        <!--comment2-->"));
        assertTrue(reader.readLine().equals("        <Property name=\"a.b.c\">"));
        assertTrue(reader.readLine().equals("            <Value>newvalue</Value>"));
        assertTrue(reader.readLine().equals("        </Property>"));
        assertTrue(reader.readLine().equals("        <Property name=\"b\">"));
        assertTrue(reader.readLine().equals("            <Value>value2</Value>"));
        assertTrue(reader.readLine().equals("        </Property>"));
        assertTrue(reader.readLine().equals("    </Config>"));
        assertTrue(reader.readLine().equals("</CMConfig>"));
        assertNull(reader.readLine());
        reader.close();
    }
*/
    /**
     * Tests set/get document.
     */
    public void testSetGetDocument() {
        Document document = xmlcp.getDocument();
        xmlcp.setDocument(null);
        assertNull(xmlcp.getDocument());
        xmlcp.setDocument(document);
        assertTrue(xmlcp.getDocument() == document);
    }

    /**
     * Tests traverseToGetNamespaceNodes(...).
     */
    public void testTraverseToGetNamespaceNodes() {
        Vector vector = new Vector();
        XMLConfigProperties.traverseToGetNamespaceNodes(xmlcp.getDocument(), vector);
        assertTrue(vector.size() == 2);
        Node node = (Node) vector.elementAt(0);
        assertTrue(node.getNodeType() == Node.ELEMENT_NODE);
        assertTrue(node.getNodeName().equals("Config"));
        node = (Node) vector.elementAt(1);
        assertTrue(node.getNodeType() == Node.ELEMENT_NODE);
        assertTrue(node.getNodeName().equals("Config"));
    }

    /**
     * Tests getNamespaces(...).
     *
     * @throws Exception to JUnit.
     */
    public void testGetNamespaces() throws Exception {
        Enumeration enu = XMLConfigProperties.getNamespaces(file.toURL());
        assertTrue(enu.hasMoreElements());
        assertTrue(enu.nextElement().equals("component a"));
        assertTrue(enu.hasMoreElements());
        assertTrue(enu.nextElement().equals("component b"));
        assertFalse(enu.hasMoreElements());
    }

}
