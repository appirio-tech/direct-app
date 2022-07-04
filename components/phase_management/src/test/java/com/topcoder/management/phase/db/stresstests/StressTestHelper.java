/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.phase.AbstractDbPhasePersistence;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import java.io.File;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * This is the utility class for assisting the process of testing.
 *
 * It helps to clear the database tables, create certain records etc.
 *
 * @author KLW
 * @version 1.0
 */
final class StressTestHelper {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    private static final String DB_FACTORY_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * This is a convenience key to be used when we need to pass a <code>Connection</code>
     * object to the commit or rollback methods.
     * </p>
     */
    public static final String CONNECTION_CONTEXT_KEY = "connection";

    /**
     * Db Connection Factory config file.
     */
    private static final String DB_CONN_FACTORY_CONF = "test_files/stress/db_conf.xml";

    /**
     * Configuration file.
     */
    public static final String CONF_FILE = "test_files/stress/persistence_conf.xml";

    /**
     * Database connection name.
     */
    public static final String CONNECTION_NAME = "informix";

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private static DBConnectionFactory factory;

    /**
     * The table names in this component.
     */
    private static String[] tableNames = {
            "phase_criteria", "phase_criteria_type_lu", "phase_dependency", "project_phase",
            "phase_type_lu", "phase_status_lu", "project",
        };

    /**
     * The id generator used in tests.
     */
    private static IDGenerator idGenerator = null;

    /**
     * Private constructor.
     */
    private StressTestHelper() {
        // empty.
    }

    /**
     * Clear the config manager.
     *
     * @throws Exception throw to JUnit.
     */
    public static void clearConfigManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * config operation
     */
    public static void LoadConfigFile() throws Exception {
        clearConfigManager();
        ConfigManager cm =  ConfigManager.getInstance();
        cm.add(new File(DB_CONN_FACTORY_CONF).getCanonicalPath());
        cm.add(new File(CONF_FILE).getCanonicalPath());
        cm.add(new File("test_files/stress/db_manager_conf.xml").getCanonicalPath());
    }

    /**
     * <p>
     * Deletes data from the table used by this component.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    public static void clearTables() throws Exception {
        Connection conn = getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            for (int i = 0; i < tableNames.length; i++) {
                stmt.executeUpdate("DELETE FROM " + tableNames[i]);
            }
        } finally {
            closeResource(conn,stmt,null);
        }
    }

    /**
     * <p>
     * Returns a new connection to be used for persistence.
     * </p>
     *
     * @return the connection instance for database operation
     *
     * @throws Exception
     *             If unable to obtain a Connection
     */
    public static Connection getConnection() throws Exception {
        factory = getConnectionFactory();

        return factory.createConnection();
    }

    /**
     * Returns the DbConnectionFactory.
     *
     * @return the database connection factory.
     * @throws Exception to JUnit.
     */
    public static DBConnectionFactory getConnectionFactory()
        throws Exception {
        if (factory == null) {
            factory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
        }

        return factory;
    }





    /**
     * Returns the IdGenerator.
     *
     * @return the IDGenerator.
     * @throws Exception to JUnit.
     */
    public static IDGenerator getIDGenerator() throws Exception {
        if (idGenerator == null) {
            idGenerator = IDGeneratorFactory.getIDGenerator("phase_id_seq");
        }

        return idGenerator;
    }

    /**
     * Closes the resource if they are available.
     * @param conn the <code>Connection</code> to close.
     * @param stmt the <Code>Statement</code> to close.
     * @param rs the <code>ResultSet</code> to close.
     */
    static final void closeResource(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Executes the SQL with the given connection.
     * @param conn the connection used to execute the SQL.
     * @param sql the SQL statement to execute.
     * @throws Exception if there is any problem.
     */
    static final void executeSQL(Connection conn, String sql)
        throws Exception {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } finally {
            closeResource(null, stmt, null);
        }
    }

    /**
     * Executes the SQL.
     * @param sql the SQL statement to execute.
     * @throws Exception if there is any problem.
     */
    static final void executeSQL(String sql) throws Exception {
        Connection conn = null;

        try {
            conn = getConnection();
            executeSQL(conn, sql);
        } finally {
            closeResource(conn, null, null);
        }
    }

    /**
     * Creates a context map with the connection filled with key "connection".
     * @param conn the connection which should be in the context map.
     * @return a context map containing the connection with key "connection".
     */
    public static final Map createContextMap(Connection conn) {
        Map context = new HashMap();
        context.put(AbstractDbPhasePersistence.CONNECTION_CONTEXT_KEY, conn);

        return context;
    }
}
