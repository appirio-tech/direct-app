/*
 * Copyright (c) 2003, TopCoder Software, Inc. All rights reserved.
 *
 * @(#) PropConfigProperties.java
 *
 * 2.1 05/07/2003
 */
package com.topcoder.util.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

/**
 * An extension of <code>ConfigProperties</code> to be used to maintain the
 * properties list using  .properties files.
 *
 * @author  ilya
 * @author  isv
 * @author  WishingBone
 * @version 2.1  05/07/2003
 */
class PropConfigProperties extends ConfigProperties {

    /**
     * Length of the unicode string.
     */
    private static final int UNICODE_LENGTH = 4;

    /**
     * The base number for 'a' in a hex number.
     */
    private static final int HEX_LETTER_BASE = 10;

    /**
     * The number of bits that every hex number represents.
     */
    private static final int HEX_BIT_COUNT = 4;

    /**
     * A source of properties.
     */
    private URL source = null;

    /**
     * An <code>PrintWriter</code> used to output the properties.
     */
    private PrintWriter writer = null;

    /**
     * A private no-arg constructor (for clone).
     */
    private PropConfigProperties() {
    }

    /**
     * Creates a new PropConfigProperties from data in the InputStream.
     * Reads a property list (key and element pairs) from the input stream. The
     * stream is assumed to be using the ISO 8859-1 character encoding.
     * Every property occupies one line of the input stream. Each line is
     * terminated by a line terminator (\n or \r or \r\n). Lines from the input
     * stream are processed until end of file is reached on the input stream.
     *
     * A line that contains only whitespace or whose first non-whitespace
     * character is an ASCII # or ! is ignored (thus, # or ! indicate comment
     * lines).
     *
     * @param  source a source of configuration properties
     * @throws ConfigParserException if any I/O error occurs during the reading
     *         data from given <code>InputStream</code>
     * @throws IOException if any I/O error occurs while reading from source
     *         file
     */
    PropConfigProperties(URL source) throws IOException {
        this.source = source;
        load();
    }

    /**
     * Write a property node to output.
     *
     * @param  property the property node
     * @param  key the parent key
     */
    private void writeProperty(Property property, String key) {
        // construct current key
        if (key.equals("")) {
            key = property.getName();
        } else {
            key += "." + property.getName();
        }

        // write comments
        List comments = property.getComments();
        if (comments != null) {
            for (Iterator itr = comments.iterator(); itr.hasNext();) {
                writer.println(itr.next());
            }
        }

        // write values
        String[] values = property.getValues();
        if (values != null && values.length > 0) {
            writer.print(key);
            writer.print(property.getSeparator());
            writer.println(mergeEscaped(values, getListDelimiter()));
        }

        // write sub properties
        List subproperties = property.list();
        for (Iterator itr = subproperties.iterator(); itr.hasNext();) {
            writeProperty((Property) itr.next(), key);
        }
    }

    /**
     * Saves the data(properties and their values) from properties tree into
     * source .properties file.
     *
     * @throws UnsupportedOperationException if the source is not a physical file
     * @throws IOException if any I/O error occurs while writing to source file
     */
    protected void save() throws IOException {
        if (!source.getProtocol().equals("file")) {
            throw new UnsupportedOperationException("source is not a physical file");
        }

        try {
            writer = new PrintWriter(new FileWriter(ConfigManager.decodeURL(source.getFile())));
            if (getListDelimiter() != ';') {
                writer.print("ListDelimiter=");
                writer.println(getListDelimiter());
            }
            writeProperty(getRoot(), "");
        } finally {
            if (writer != null) {
                writer.close();
                writer = null;
            }
        }
    }

    /**
     * Loads the properties and their values from source .properties file.
     *
     * @throws IOException if any I/O error occurs while reading from source
     *         file
     */
    protected void load() throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(source.openStream()));
            Property root = new Property();
            // comment cache
            List comments = new ArrayList();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                String trim = line.trim();
                if (trim.length() == 0 || trim.charAt(0) == '#' || trim.charAt(0) == '!') {
                    // put comment in cache
                    comments.add(line);
                } else {
                    int pos = findDelimiter(line, '=');
                    if (pos == -1) {
                        pos = findDelimiter(line, ':');
                    }
                    if (pos == -1) {
                        pos = findDelimiter(line, ' ');
                    }
                    if (pos == -1) {
                        throw new ConfigParserException("unrecognized line : " + line);
                    }
                    String key = line.substring(0, pos);
                    if (key.equals("ListDelimiter")) {
                        String delim = line.substring(pos + 1);
                        if (delim.length() != 1) {
                            throw new ConfigParserException("invalid delimiter");
                        }
                        setListDelimiter(delim.charAt(0));
                        continue;
                    }
                    key = parseString(key);

                    // parse value list
                    List valueList = parseValueString(line.substring(pos + 1), getListDelimiter());

                    // construct property
                    Property property = root.find(key);
                    if (property != null && property.getValue() != null) {
                        throw new ConfigParserException("contains duplicate property " + key);
                    }
                    root.setProperty(key, (String[]) valueList.toArray(new String[valueList.size()]));
                    property = root.find(key);
                    property.setSeparator(line.charAt(pos));
                    if (comments.size() > 0) {
                        for (Iterator itr = comments.iterator(); itr.hasNext();) {
                            property.addComment((String) itr.next());
                        }
                        comments.clear();
                    }
                }
            }
            setRoot(root);
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception exception) {
            throw new ConfigParserException(exception.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }
        }
    }

    /**
     * Parse the escaped characters in the given string, and return the parsed string.
     *
     * @param s the string to parse
     * @return the parsed string
     * @throws ConfigParserException if the given string is invalid
     */
    private static String parseString(String s) throws ConfigParserException {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch == '\\') {
                if (i + 1 >= s.length()) {
                    throw new ConfigParserException("Missing escaped character after \'\\\'.");
                }
                i++;
                ch = s.charAt(i);
                if (ch == 't') {
                    buffer.append('\t');
                } else if (ch == 'r') {
                    buffer.append('\r');
                } else if (ch == 'n') {
                    buffer.append('\n');
                } else if (ch == 'u') {
                    if (i + UNICODE_LENGTH >= s.length()) {
                        throw new ConfigParserException("Invalid escape after \'\\u\'.");
                    }
                    String hexStr = s.substring(i + 1, i + 1 + UNICODE_LENGTH);
                    i += UNICODE_LENGTH;
                    buffer.append(parseHexString(hexStr));
                } else {
                    buffer.append(ch);
                }
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    /**
     * Fine delimiter, and return the index of the found delimiter, return -1 if not found.
     *
     * @param s the string to find delimiter
     * @param delimiter the delimiter to find position for
     * @return the index of the delimiter, return -1 if not found
     */
    private static int findDelimiter(String s, char delimiter) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\\') {
                i++;
            } else if (s.charAt(i) == delimiter) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Parse a hex string to get a character.
     *
     * @param s the hex string to parse
     * @return the corresponding character of the given hex string
     * @throws ConfigParserException if the given string is invalid
     */
    private static char parseHexString(String s) throws ConfigParserException {
        s = s.trim().toLowerCase();
        int r = 0;
        for (int i = 0; i < s.length(); i++) {
            int t;
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                t = ch - '0';
            } else if (ch >= 'a' && ch <= 'f') {
                t = ch - 'a' + HEX_LETTER_BASE;
            } else {
                throw new ConfigParserException("Invalid hex string.");
            }
            r = (r << HEX_BIT_COUNT) + t;
        }
        return (char) r;
    }

    /**
     * Parse a string possibly separated with a list delimiter into a list of values.
     *
     * @param  value the value string to parse
     * @param  listDelimiter the list delimiter
     * @return the parsed list
     * @throws ConfigParserException if the given string is invalid
     */
    static List parseValueString(String value, char listDelimiter) throws ConfigParserException {
        List list = new ArrayList();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < value.length(); ++i) {
            char ch = value.charAt(i);
            if (ch == '\\') {
                if (i + 1 >= value.length()) {
                    throw new ConfigParserException("Missing escaped character after \'\\\'.");
                }
                i++;
                ch = value.charAt(i);
                String escapes = " !:=\\#";
                if (ch == listDelimiter || escapes.indexOf(ch) >= 0) {
                    buffer.append(ch);
                } else if (ch == 't') {
                    buffer.append('\t');
                } else if (ch == 'r') {
                    buffer.append('\r');
                } else if (ch == 'n') {
                    buffer.append('\n');
                } else if (ch == 'u') {
                    if (i + UNICODE_LENGTH >= value.length()) {
                        throw new ConfigParserException("Invalid escape after \'\\u\'.");
                    }
                    String hexStr = value.substring(i + 1, i + 1 + UNICODE_LENGTH);
                    i += UNICODE_LENGTH;
                    buffer.append(parseHexString(hexStr));
                } else {
                    throw new ConfigParserException("Invalid escape after '\\'.");
                }
            } else if (ch == listDelimiter) {
                list.add(buffer.toString());
                buffer.delete(0, buffer.length());
            } else {
                buffer.append(ch);
            }
        }
        list.add(buffer.toString());
        return list;
    }

    /**
     * Merge a list of values into a single value with specified list delimiter.
     * Possible occurance of list delimiter in the value itself should be escaped.
     *
     * @param  values an array of values to merge
     * @param  listDelimiter the list delimiter to use
     * @return the merged string
     */
    private static String mergeEscaped(String[] values, char listDelimiter) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < values.length; ++i) {
            if (i > 0) {
                buffer.append(listDelimiter);
            }
            for (int k = 0; k < values[i].length(); ++k) {
                char ch = values[i].charAt(k);
                String escapes = " !:=\\#";
                if (ch == listDelimiter || escapes.indexOf(ch) >= 0) {
                    buffer.append('\\');
                    buffer.append(ch);
                } else if (ch == '\n') {
                    buffer.append("\\n");
                } else if (ch == '\r') {
                    buffer.append("\\r");
                } else if (ch == '\t') {
                    buffer.append("\\t");
                } else {
                    buffer.append(ch);
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Gets the clone copy of this object.
     *
     * @return a clone copy of this object.
     */
    public Object clone() {
        PropConfigProperties properties = new PropConfigProperties();
        properties.source = source;
        properties.setRoot((Property) getRoot().clone());
        return properties;
    }

}
