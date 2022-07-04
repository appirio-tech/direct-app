/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.persistence;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is a databse helper class. It loads data set from file into database. It requires configured connection.
 * </p>
 *
 * @author kr00tki
 * @version 1.0
 */
public class DbManager {
    /**
     * Database connection used by the manager.
     */
    private Connection connection = null;

    /**
     * DBConnectionFactory used to create connections.
     */
    private DBConnectionFactory factory = null;
    /**
     * Te database connection name.
     */
    private String connName = null;

    /**
     * The table names.
     */
    private String[] tableNames = null;

    /**
     * Default constructor. It loads condifuration from the default namespace.
     *
     * @throws Exception on configuration error.
     */
    public DbManager() throws Exception {
        this(DbManager.class.getName());
    }

    /**
     * Creates DbManager instance using configuration from namespace.
     *
     * @param namespace the condifuration namespace.
     * @throws Exception on configuration error.
     */
    public DbManager(String namespace) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        String factorNs = cm.getString(namespace, "connection_factory_ns");
        connName = cm.getString(namespace, "connection_name");
        tableNames = cm.getStringArray(namespace, "tableNames");

        factory = new DBConnectionFactoryImpl(factorNs);
    }

    /**
     * Load data set from specified file.
     *
     * @param path dataset path.
     * @throws Exception if error occurs during opertion.
     */
    public void loadDataSet(String path) throws Exception {
        Document doc = parse(path);
        persistDocument(doc);
    }

    /**
     * Parses given XML input file.
     *
     * @param path path to the file.
     * @return the Document with parsed file.
     * @throws Exception on error.
     */
    private Document parse(String path) throws Exception {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        FileReader reader = new FileReader(path);
        try {
            return builder.parse(new InputSource(reader));
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Persistes given document to database.
     *
     * @param doc the docuemnt to persist.
     * @throws Exception if error occurs during operation.
     */
    private void persistDocument(Document doc) throws Exception {
        Element root = doc.getDocumentElement();
        NodeList rows = root.getChildNodes();
        for (int i = 0; i < rows.getLength(); i++) {
            if (rows.item(i) instanceof Element) {
                Element el = (Element) rows.item(i);
                String table = el.getTagName();
                NamedNodeMap attribs = el.getAttributes();
                persistElement(table, attribs);
            }
        }
    }

    /**
     * Inserts element into table. Attributes names are the column names and values are column values.
     *
     * @param table the table name.
     * @param attribs the attributes map containing the data to insert.
     * @throws Exception if operation fail.
     */
    private void persistElement(String table, NamedNodeMap attribs) throws Exception {
        StringBuffer buffer = new StringBuffer("INSERT INTO ");
        buffer.append(table).append(" (");
        for (int i = 0; i < attribs.getLength(); i++) {
            buffer.append(((Attr) attribs.item(i)).getName()).append(", ");
        }

        buffer.delete(buffer.length() - 2, buffer.length());
        buffer.append(") VALUES(");
        for (int i = 0; i < attribs.getLength(); i++) {
            buffer.append(((Attr) attribs.item(i)).getValue()).append(", ");
        }

        buffer.delete(buffer.length() - 2, buffer.length());
        buffer.append(");");
        execute(buffer.toString());
    }

    /**
     * Executes SQL query (mostly insert query).
     *
     * @param sql the SQL query.
     * @throws Exception if error occurs.
     */
    private void execute(String sql) throws Exception {
        Connection conn = getConnection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Creates the database connection.
     *
     * @return open connection object.
     * @throws Exception if error occurs.
     */
    private Connection getConnection() throws Exception {
        if ((connection == null) || (connection.isClosed())) {
            connection =  connName == null ? factory.createConnection() : factory.createConnection(connName);
        }

        return connection;
    }

    /**
     * Realeses the DbManager. Closes the internal connection.
     *
     * @throws SQLException if error occur.
     */
    public void release() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Clears configured tables.
     *
     * @throws Exception if error occurs.
     */
    public void clearTables() throws Exception {
        clearTables(tableNames);
    }

    /**
     * Clears the given tables.
     *
     * @param names the tables names.
     * @throws Exception if oexception occurs.
     */
    public void clearTables(String[] names) throws Exception {
        Connection conn = getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            for (int i = 0; i < names.length; i++) {
                stmt.executeUpdate("DELETE FROM " + names[i]);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
