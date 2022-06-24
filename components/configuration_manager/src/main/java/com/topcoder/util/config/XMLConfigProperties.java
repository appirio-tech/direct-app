/**
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) XMLConfigProperties.java
 *
 * 2.1  05/07/2003
 */
package com.topcoder.util.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Vector;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Comment;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * An extension of <code>ConfigProperties</code> to be used to maintain the
 * properties list using .xml files.
 *
 * @author  ilya
 * @author  debedeb
 * @author  danno
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
class XMLConfigProperties extends ConfigProperties implements ErrorHandler {

    /**
     * An XML document
     */
    private Document document = null;

    /**
     * A source .xml file containing the configuration properties.
     *
     * @since Configuration Manager 2.1
     */
    private URL source = null;

    /**
     * A namespace of configuration properties contained within this <code>
     * XMLConfigProperties</code>
     *
     * @since Configuration Manager 2.1
     */
    private String namespace = null;

    /**
     * An <code>PrintWriter</code> used to output the properties
     */
    private PrintWriter writer = null;

    /**
     * A private no-arg constructor (for clone).
     */
    private XMLConfigProperties() {
    }

    /**
     * Creates a new <code>XMLConfigProperties</code> that holds the properties
     * for given namespace read from given InputStream.
     *
     * If given <code>namespace</code> is <code>null</code>, assumes that given
     * stream is a single-namespace XML file. Otherwise, reads data
     * corresponding to given <code>namespace</code> assuming that given stream
     * represents a multi-namespace XML file.
     *
     * @param  source a file containing properties data
     * @param  namespace a namespace to get the properties for
     * @throws ConfigParserException if a parsing error occurs, that is
     *         the given stream's format cannot be parsed by the implementing
     *         parser
     * @throws IOException if any I/O error occurs while reading from given
     *         <code>source</code>
     */
    XMLConfigProperties(URL source, String namespace) throws ConfigParserException, IOException {
        this.source = source;
        this.namespace = namespace;
        load();
    }

    /**
     * Creates a new <code>XMLConfigProperties</code> that holds the properties
     * read from given InputStream. Assumes that given stream is a
     * single-namespace XML file.
     *
     * @param  source a file containing properties data
     * @throws ConfigParserException if a parsing error occurs, that is
     *         the given stream's format cannot be parsed by the implementing
     *         parser.
     * @throws IOException if any I/O error occurs while reading from given
     *         <code>source</code>
     */
    XMLConfigProperties(URL source) throws ConfigParserException, IOException {
        this.source = source;
        load();
    }

    /**
     * Gets the list of namespaces defined in a multi-namespace file
     *
     * @param url the url to get namespaces from.
     * @return all namespaces stored in filename.
     * @throws ConfigParserException for badly formed files.
     */
    static Enumeration getNamespaces(URL url) throws ConfigParserException {
        // create DOM parser and parse
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (FactoryConfigurationError e) {
            throw new ConfigParserException("Error occurred obtaining Document Builder Factory: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new ConfigParserException("The underlying parser does not support the requested features: " + e.getMessage());
        }
        Document doc = null;
        try {
            InputSource is = new InputSource(url.openStream());
            doc = builder.parse(is);
        } catch (SAXException saxe) {
            throw new ConfigParserException(saxe.getMessage());
        } catch (IOException ioe) {
            throw new ConfigParserException(ioe.getMessage());
        }

        // retrieve namespaces from document
        Vector namespaceNodes = new Vector();
        traverseToGetNamespaceNodes(doc, namespaceNodes);
        Vector namespaces = new Vector();
        for (Enumeration enu = namespaceNodes.elements(); enu.hasMoreElements();) {
            Node value = ((Element) enu.nextElement()).getAttributeNode("name");
            if (value == null) {
                throw new ConfigParserException("incorrect xml format");
            } else {
                namespaces.add(value.getNodeValue());
            }
        }
        return namespaces.elements();
    }

    /**
     * Traverses through an XML node tree and extract the nodes representing
     * namespaces.
     *
     * @param node a root node to traverse through
     * @param namespaceNodes currently built list of namespace nodes.  Start
     *        as a new Vector.
     */
    protected static void traverseToGetNamespaceNodes(Node node, Vector namespaceNodes) {
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Config")) {
            namespaceNodes.add(node);
        } else {
            NodeList list = node.getChildNodes();
            for (int i = 0; i < list.getLength(); ++i) {
                traverseToGetNamespaceNodes(list.item(i), namespaceNodes);
            }
        }
    }

    /**
     * Sets document variable to doc.
     *
     * @param doc document for this XMLConfigProperties.
     */
    void setDocument(Document doc) {
        this.document = doc;
    }

    /**
     * Returns the document for this XMLConfigProperties.
     *
     * @return document for this XMLConfigProperties.
     */
    Document getDocument(){
        return document;
    }

    /**
     * Update DOM tree with property tree.
     *
     * @param  node DOM tree node
     * @param  property property node
     */
    private void updateProperty(Node node, Property property) {
        // comment first
        List comments = property.getComments();
        if (comments != null) {
            for (Iterator itr = comments.iterator(); itr.hasNext();) {
                node.appendChild(document.createComment((String) itr.next()));
            }
        }

        // the property itself if it contains values or subproperties
        String[] values = property.getValues();
        List subproperties = property.list();
        if ((values != null && values.length > 0) || !subproperties.isEmpty()) {
            Element element = document.createElement("Property");
            element.setAttribute("name", property.getName());

            if (values != null) {
                for (int i = 0; i < values.length; ++i) {
                    Node value = document.createElement("Value");
                    value.appendChild(document.createTextNode(values[i]));
                    element.appendChild(value);
                }
            }

            // here goes the subproperties
            for (Iterator itr = subproperties.iterator(); itr.hasNext();) {
                updateProperty(element, (Property) itr.next());
            }

            node.appendChild(element);
        }
    }

    /**
     * Write normalized string to xml file.
     *
     * @param  string the string to write
     */
    private void writeNormalized(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char ch = string.charAt(i);
            switch (ch) {
                case '<': {
                    writer.print("&lt;");
                    break;
                }
                case '>': {
                    writer.print("&gt;");
                    break;
                }
                case '&': {
                    writer.print("&amp;");
                    break;
                }
                case '"': {
                    writer.print("&quot;");
                    break;
                }
                case '\r':
                case '\n': {
                    writer.print("&#");
                    writer.print(Integer.toString(ch));
                    writer.print(';');
                    break;
                }
                default: {
                    writer.print(ch);
                }
            }
        }
    }

    /**
     * Write DOM tree to xml file.
     *
     * @param  node DOM tree node
     * @param  indent indentation
     */
    private void writeProperty(Node node, int indent) {
        switch (node.getNodeType()) {
            // document
            case Node.DOCUMENT_NODE: {
                Document document = (Document) node;
                writer.println("<?xml version=\"1.0\"?>");
                if (document.getDoctype() != null) {
                    writeProperty(document.getDoctype(), 0);
                }
                if (document.getDocumentElement() != null) {
                    writeProperty(document.getDocumentElement(), 0);
                }
                break;
            }

            // doctype
            case Node.DOCUMENT_TYPE_NODE: {
                DocumentType doctype = (DocumentType) node;
                writer.print("<!DOCTYPE ");
                writer.print(doctype.getName());
                String publicId = doctype.getPublicId();
                String systemId = doctype.getSystemId();
                if (publicId != null) {
                    writer.print(" PUBLIC '");
                    writer.print(publicId);
                    writer.print("' '");
                    writer.print(systemId);
                    writer.print('\'');
                } else {
                    writer.print(" SYSTEM '");
                    writer.print(systemId);
                    writer.print('\'');
                }
                String internalSubset = doctype.getInternalSubset();
                if (internalSubset != null) {
                    writer.println(" [");
                    writer.print(internalSubset);
                    writer.print(']');
                }
                writer.println('>');
                break;
            }

            // element
            case Node.ELEMENT_NODE: {
                for (int i = 0; i < indent; ++i) {
                    writer.print("    ");
                }
                writer.print('<');
                writer.print(node.getNodeName());
                NamedNodeMap map = node.getAttributes();
                for (int i = 0; i < map.getLength(); ++i) {
                    Attr attr = (Attr) map.item(i);
                    writer.print(' ');
                    writer.print(attr.getNodeName());
                    writer.print("=\"");
                    writeNormalized(attr.getNodeValue());
                    writer.print('"');
                }
                writer.print('>');
                if (!node.getNodeName().equals("Value") && !node.getNodeName().equals("ListDelimiter")) {
                    writer.println();
                }

                Node child = node.getFirstChild();
                while (child != null) {
                    writeProperty(child, indent + 1);
                    child = child.getNextSibling();
                }

                if (!node.getNodeName().equals("Value") && !node.getNodeName().equals("ListDelimiter")) {
                    for (int i = 0; i < indent; ++i) {
                        writer.print("    ");
                    }
                }
                writer.print("</");
                writer.print(node.getNodeName());
                writer.println('>');
                break;
            }

            // only write text if it is enclosed in the Value tag or ListDelimiter tag
            case Node.TEXT_NODE: {
                Node parent = node.getParentNode();
                if (parent.getNodeType() == Node.ELEMENT_NODE &&
                    (parent.getNodeName().equals("Value") || parent.getNodeName().equals("ListDelimiter"))) {
                    writer.print(node.getNodeValue());
                }
                break;
            }

            // write comments
            case Node.COMMENT_NODE: {
                if (namespace == null) {
                    writer.print("    ");
                } else {
                    writer.print("        ");
                }
                writer.print("<!--");
                writer.print(((Comment) node).getData());
                writer.println("-->");
            }
        }
    }

    /**
     * Saves the data(properties and their values) from properties tree into
     * persistent storage.
     *
     * @throws UnsupportedOperationException if the source is not a physical file
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    protected void save() throws IOException {
        if (!source.getProtocol().equals("file")) {
            throw new UnsupportedOperationException("source is not a physical file");
        }

        // remove the properties
        Node node = getNamespaceRootNode(document);
        while (node.getFirstChild() != null) {
            node.removeChild(node.getFirstChild());
        }

        // list delimiter if not default
        if (getListDelimiter() != ';') {
            Node delimiter = document.createElement("ListDelimiter");
            delimiter.appendChild(document.createTextNode("" + getListDelimiter()));
            node.appendChild(delimiter);
        }

        // populate with new properties
        for (Iterator itr = getRoot().list().iterator(); itr.hasNext();) {
            updateProperty(node, (Property) itr.next());
        }
        try {
            writer = new PrintWriter(new FileWriter(ConfigManager.decodeURL(source.getFile())));
            writeProperty(document, 0);
        } finally {
            if (writer != null) {
                writer.close();
                writer = null;
            }
        }
    }

    /**
     * Get the root node for specified namespace. If namespace is null, return the "CMConfig" node.
     *
     * @param  node the current node
     * @return the respective node, or null if such node does not exist
     * @throws ConfigParserException if xml format is incorrect
     */
    private Node getNamespaceRootNode(Node node) throws ConfigParserException {
        // check current node
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            if (namespace == null) {
                if (node.getNodeName().equals("CMConfig")) {
                    return node;
                }
            } else {
                if (node.getNodeName().equals("Config")) {
                    Node value = ((Element) node).getAttributeNode("name");
                    if (value == null) {
                        throw new ConfigParserException("incorrect xml format");
                    } else {
                        if (value.getNodeValue().equals(namespace)) {
                            return node;
                        }
                    }
                }
            }
        }

        // check child nodes
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            Node ret = getNamespaceRootNode(list.item(i));
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    /**
     * Load node as a property.
     *
     * @param  root the root property
     * @param  node the current node
     * @param  prefix the prefix of the property
     *
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    private void loadNode(Property root, Node node, String prefix) throws IOException {
        List comments = new ArrayList();
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            node = list.item(i);

            // put commment into cache
            if (node.getNodeType() == Node.COMMENT_NODE) {
                comments.add(((Comment) node).getData());
                continue;
            }

            // find a property
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getNodeName().equals("Property")) {
                    boolean nested = false;
                    Node value = ((Element) node).getAttributeNode("name");
                    if (value == null) {
                        throw new ConfigParserException("incorrect xml format");
                    }
                    String key = prefix + value.getNodeValue();

                    // retrieve all values
                    List valueList = new ArrayList();
                    NodeList values = node.getChildNodes();
                    for (int j = 0; j < values.getLength(); ++j) {
                        Node subNode = values.item(j);

                        // put comment into cache
                        if (subNode.getNodeType() == Node.COMMENT_NODE) {
                            comments.add(((Comment) subNode).getData());
                            continue;
                        }

                        if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                            // nested element
                            if (subNode.getNodeName().equals("Property")) {
                                nested = true;
                            } else if (subNode.getNodeName().equals("Value")) {
                                Node text = subNode.getFirstChild();
                                if (text == null) {
                                    valueList.add("");
                                } else if (text.getNodeType() == Node.TEXT_NODE) {
                                    valueList.addAll(PropConfigProperties.parseValueString(
                                            ((Text) text).getData(), getListDelimiter()));
                                } else {
                                    throw new ConfigParserException("invalid subnode");
                                }
                                if (text != null && text.getNextSibling() != null) {
                                    throw new ConfigParserException("invalid subnode");
                                }
                            } else {
                                throw new ConfigParserException("unrecognized element " + subNode.getNodeName());
                            }
                        }
                    }

                    // construct property
                    if (valueList.size() > 0) {
                        Property property = root.find(key);
                        if (property != null && property.getValue() != null) {
                            throw new ConfigParserException("contains duplicate property " + key);
                        }
                        root.setProperty(key, (String[]) valueList.toArray(new String[valueList.size()]));
                        if (comments.size() > 0) {
                            property = root.find(key);
                            for (Iterator itr = comments.iterator(); itr.hasNext();) {
                                property.addComment((String) itr.next());
                            }
                            comments.clear();
                        }
                    }
                    if (nested) {
                        loadNode(root, node, key + ".");
                    }
                } else if (node.getNodeName().equals("ListDelimiter")) {

                    // find a list delimiter
                    Node text = node.getFirstChild();
                    if (text == null) {
                        throw new ConfigParserException("invalid delimiter");
                    } else if (text.getNodeType() == Node.TEXT_NODE) {
                        String delim = ((Text) text).getData();
                        if (delim == null || delim.length() != 1) {
                            throw new ConfigParserException("invalid delimiter");
                        }
                        setListDelimiter(delim.charAt(0));
                    } else {
                        throw new ConfigParserException("invalid delimiter");
                    }
                    if (text.getNextSibling() != null) {
                        throw new ConfigParserException("invalid subnode");
                    }
                } else if (!node.getNodeName().equals("Value")) {
                    throw new ConfigParserException("unrecognized element " + node.getNodeName());
                }
            }
        }
    }

    /**
     * Loads the properties and their values from persistent storage.
     *
     * @throws IOException if any exception related to underlying persistent
     *         storage occurs
     */
    protected void load() throws IOException {
        // create DOM parser and parse
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (FactoryConfigurationError e) {
            throw new ConfigParserException("Error occurred obtaining Document Builder Factory: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            throw new ConfigParserException("The underlying parser does not support the requested features: " + e.getMessage());
        }
        builder.setErrorHandler(this);
        InputSource is = new InputSource(source.openStream());
        try {
            document = builder.parse(is);
        } catch (SAXException saxe) {
            throw new ConfigParserException(saxe.getMessage());
        } catch (IOException ioe) {
            throw new ConfigParserException(ioe.getMessage());
        }

        // retrieve namespace root node
        Node node = getNamespaceRootNode(document);
        if (node == null) {
            throw new ConfigParserException("can not locate namespace " + namespace);
        }

        try {
            // load property tree
            Property root = new Property();
            loadNode(root, node, "");
            setRoot(root);
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception exception) {
            throw new ConfigParserException(exception.getMessage());
        }
    }

    /**
     * Receives notification of a warning and outputs message to standard
     * error output.
     *
     * @throws SAXException any SAX exception, possibly wrapping another
     *         exception.
     */
    public void warning(SAXParseException e) throws SAXException {
        System.err.println("warning : " + e.getMessage());
    }

    /**
     * Receives notification of a recoverable error, outputs message to standard
     * error output and throws new SAXException wrapping caught
     * SAXParseException
     *
     * @param e the error information encapsulated in a SAX parse exception
     * @throws SAXException wrapping <code>SAXParserException</code> occured
     */
    public void error(SAXParseException e) throws SAXException {
        System.err.println("error : " + e.getMessage());
        throw e;
    }

    /**
     * Receives notification of a non-recoverable error, outputs message to
     * standard error output and throws new SAXException wrapping caught
     * SAXParseException
     *
     * @param  e the error information encapsulated in a SAX parse exception
     * @throws SAXException wrapping <code>SAXParserException</code> occured
     */
    public void fatalError(SAXParseException e) throws SAXException {
        System.err.println("fatal error : " + e.getMessage());
        throw e;
    }

    /**
     * Gets the clone copy of this object
     *
     * @return a clone copy of this object
     */
    public Object clone() {
        XMLConfigProperties properties = new XMLConfigProperties();
        properties.source = source;
        properties.namespace = namespace;
        properties.document = document;
        properties.setRoot((Property) getRoot().clone());
        return properties;
    }

}
