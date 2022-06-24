package com.topcoder.management.deliverable.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

/**
 * <p>Test helper used for setting up test environment.</p>
 *
 * @author TCSDVELOPER
 * @version 1.1
 * @since 1.1
 */
public final class TestHelper {

    /**
     * <p>Private constructor.</p>
     */
    private TestHelper() {
        // empty constructor
    }

    /**
     * <p>Configures ConfigManager, adds all required files.</p>
     *
     * @throws ConfigManagerException if any error occurs
     */
    public static void loadConfig() throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");
        cm.add("com.topcoder.util.log", "logging.xml", ConfigManager.CONFIG_XML_FORMAT);
    }

    /**
     * <p>Removes all namespace from ConfigManager.</p>
     *
     * @throws UnknownNamespaceException if there is no namespace
     */
    @SuppressWarnings("unchecked")
    public static void clearConfig() throws UnknownNamespaceException {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>Closes result set.</p>
     *
     * @param resultSet result set to close
     */
    public static void close(ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // ignore exception
            }
        }
    }

    /**
     * <p>Closes statement.</p>
     *
     * @param statement statement to close
     */
    public static void close(Statement statement) {

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore exception
            }
        }
    }

    /**
     * <p>Closes connection.</p>
     *
     * @param connection connection to close
     */
    public static void close(Connection connection) {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore exception
            }
        }
    }

    /**
     * <p>Executes a sql batch from specified file.</p>
     *
     * @param file the file that contains the sql statements.
     *
     * @throws Exception if any exception occurs.
     */
    public static void executeBatch(String file) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            // Gets db connection
            connection = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            // Gets sql statements and add to statement
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = in.readLine()) != null) {
                if (line.trim().length() != 0) {
                    statement.addBatch(line);
                }
            }

            statement.executeBatch();
            statement.clearBatch();
            connection.commit();
        } catch (SQLException exc) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    // ignore
                }
            }

            throw exc;
        } catch (IOException exc) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    // ignore
                }
            }

            throw exc;
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException exc) {
                    // ignore exception
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exc) {
                    // ignore exception
                }
            }
        }
    }
}
