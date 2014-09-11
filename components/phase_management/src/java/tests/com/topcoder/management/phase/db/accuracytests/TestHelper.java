/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.phase.db.InformixPhasePersistence;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper utilities used for accuracy tests.
 *
 * @author mayi, Savior
 * @version 1.1
 */
public class TestHelper {
    /**
     * Represents the connection name used to retrieve connection from configuration.
     */
    public static final String CONNECTION_NAME = "informix";

    /**
     * Represents the namespace used to instantiate connection factory.
     */
    public static final String CONNECTION_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /**
     * Represents the namespace used to instantiate <code>InformixPhasePersistence</code> instance.
     */
    public static final String PHASE_PERSISTENCE_NAMESPACE = InformixPhasePersistence.class.getName();

    /**
     * Represents the config file of connection factory.
     */
    public static final String CONNECTION_FACTORY_CONF = "test_files/accuracy/DBConnectionFactory.xml";

    /**
     * Represents the config file of phase persistence.
     */
    public static final String PHASE_PERSISTENCE_CONF = "test_files/accuracy/InformixPhasePersistence.xml";

    /**
     * Represents the sql script to fill records into database.
     */
    public static final String SCRIPT_FILL = "test_files/accuracy/fill.sql";

    /**
     * Represents the sql script to clear records in database.
     */
    public static final String SCRIPT_CLEAR = "test_files/accuracy/clear.sql";

    /**
     * A SQL <em>SELECT</em> clause to select phase records from database.
     */
    private static final String SELECT_PHASE =
        "SELECT project_phase_id, project_id, phase_type_id, phase_status_id,"
            + " fixed_start_time, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time,"
            + " duration, create_user, create_date, modify_user, modify_date FROM project_phase ";

    /**
     * A SQL <em>SELECT</em> clause to select phase_dependency records from database.
     */
    private static final String SELECT_PHASE_DEPENDENCY =
        "SELECT dependency_phase_id, dependent_phase_id,"
            + " dependency_start, dependent_start, lag_time FROM phase_dependency ";

    /**
     * Represents all the connections used during testing.
     * <p>
     * It can be treated as a cache. In order to avoid ugly try-finally clause spreading everywhere in tests,
     * we store all the created connections used during testing, and close all of them in tear-down routine.
     * </p>
     */
    private static List connections = new ArrayList();

    /**
     * The <code>DBConnectionFactory</code> instance to create connection for testing.
     */
    private static DBConnectionFactory dbfactory = null;

    /**
     * Private empty constructor.
     */
    private TestHelper() {
        // empty
    }

    /**
     * Load configurations from file.
     *
     * @throws Exception to JUnit.
     */
    public static void loadConfigurations() throws Exception {
        clearAllConfigurations();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File(CONNECTION_FACTORY_CONF).getAbsolutePath());
        cm.add(new File(PHASE_PERSISTENCE_CONF).getAbsolutePath());
    }

    /**
     * Clear all the loaded configurations.
     *
     * @throws Exception to JUnit.
     */
    public static void clearAllConfigurations() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator i = cm.getAllNamespaces(); i.hasNext();) {
            cm.removeNamespace((String) i.next());
        }
    }

    /**
     * Try to close all the un-closed connections used in testing.
     *
     * @throws Exception to JUnit.
     */
    public static void closeAllConnections() throws Exception {
        for (Iterator itr = connections.iterator(); itr.hasNext();) {
            Connection conn = (Connection) itr.next();
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                // ignore
            }
        }

        connections.clear();
    }

    /**
     * Create a connection for testing purpose.
     *
     * @return a connection
     * @throws Exception to JUnit.
     */
    public static Connection createConnection() throws Exception {
        if (dbfactory == null) {
            dbfactory = new DBConnectionFactoryImpl(CONNECTION_FACTORY_NAMESPACE);
        }

        Connection connection = dbfactory.createConnection(CONNECTION_NAME);
        connections.add(connection);
        return connection;
    }

    /**
     * Get <em>phase</em> records from database.
     *
     * @param criteria the <em>WHERE</em> clause to filter the records.
     * @return all the records meet the criteria
     * @throws Exception to JUnit.
     */
    public static DBRecord[] getPhaseRecords(String criteria) throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_PHASE + criteria);

        List records = new ArrayList();
        while (resultSet.next()) {
            DBRecord record = new DBRecord();
            record.addValue("project_phase_id", new Long(resultSet.getLong("project_phase_id")));
            record.addValue("project_id", new Long(resultSet.getLong("project_id")));
            record.addValue("phase_type_id", new Long(resultSet.getLong("phase_type_id")));
            record.addValue("phase_status_id", new Long(resultSet.getLong("phase_status_id")));

            record.addValue("fixed_start_time", resultSet.getTimestamp("fixed_start_time"));
            record.addValue("scheduled_start_time", resultSet.getTimestamp("scheduled_start_time"));
            record.addValue("scheduled_end_time", resultSet.getTimestamp("scheduled_end_time"));
            record.addValue("actual_start_time", resultSet.getTimestamp("actual_start_time"));
            record.addValue("actual_end_time", resultSet.getTimestamp("actual_end_time"));

            record.addValue("duration", new Long(resultSet.getLong("duration")));
            record.addValue("create_user", resultSet.getString("create_user"));
            record.addValue("create_date", resultSet.getTimestamp("create_date"));
            record.addValue("modify_user", resultSet.getString("modify_user"));
            record.addValue("modify_date", resultSet.getTimestamp("modify_date"));

            records.add(record);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return (DBRecord[]) records.toArray(new DBRecord[0]);
    }

    /**
     * Get <em>phase_dependency</em> records from database.
     *
     * @param criteria the <em>WHERE</em> clause to filter the records.
     * @return all the records meet the criteria
     * @throws Exception to JUnit.
     */
    public static DBRecord[] getDependencyRecords(String criteria) throws Exception {
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_PHASE_DEPENDENCY + criteria);

        List records = new ArrayList();
        while (resultSet.next()) {
            DBRecord record = new DBRecord();
            record.addValue("dependency_phase_id", new Long(resultSet.getLong("dependency_phase_id")));
            record.addValue("dependent_phase_id", new Long(resultSet.getLong("dependent_phase_id")));
            record.addValue("dependency_start", new Boolean(resultSet.getBoolean("dependency_start")));
            record.addValue("dependent_start", new Boolean(resultSet.getBoolean("dependent_start")));
            record.addValue("lag_time", new Long(resultSet.getLong("lag_time")));
            /*
             * record.addValue("create_user", resultSet.getString("create_user"));
             * record.addValue("create_date", resultSet.getTimestamp("create_date"));
             * record.addValue("modify_user", resultSet.getString("modify_user"));
             * record.addValue("modify_date", resultSet.getTimestamp("modify_date"));
             */
            records.add(record);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return (DBRecord[]) records.toArray(new DBRecord[0]);
    }

    /**
     * Execute the sql script from the given file <code>name</code>.
     *
     * @param name the name of the script file
     * @throws Exception to JUnit
     */
    public static void executeDBScript(String name) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(name));
        Connection connection = createConnection();
        Statement statement = connection.createStatement();

        String command = "";
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // ignore the empty and commenting lines
                line = line.trim();
                if (line.length() == 0 || line.startsWith("--")) {
                    continue;
                }

                // execute the sql clause
                command += " " + line;
                if (command.endsWith(";")) {
                    statement.execute(command);
                    command = "";
                }
            }
        } finally {
            reader.close();
            statement.close();
            connection.close();
        }
    }

    /**
     * Gets the value of specified object's field.
     *
     * @param obj Object, which field value will be get.
     * @param fieldName Name of the field, which value should be get.
     * @return Value of specified field.
     * @throws Exception To JUnit.
     */
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * Invokes specified method using reflection and returns result. Here, necessary method will be get from
     * specified class: clazz.getDeclaredMethod().
     *
     * @param clazz Class, which method will be get by name.
     * @param obj Object, which method will be invoked.
     * @param methodName Name of method, that will be invoked.
     * @param parametersTypes Array of parameter types, used for getting method.
     * @param parameters Array with arguments, that will be passed to method.
     * @return Result of method invoking.
     * @throws Exception To JUnit.
     */
    public static Object invokeMethod(Class clazz, Object obj, String methodName, Class[] parametersTypes,
        Object[] parameters) throws Exception {
        Method method = clazz.getDeclaredMethod(methodName, parametersTypes);
        method.setAccessible(true);
        return method.invoke(obj, parameters);
    }

    /**
     * Invokes specified method using reflection and returns result. Here, necessary method will be get using
     * object's class: obj.getClass().getDeclaredMethod().
     *
     * @param obj Object, which method will be invoked.
     * @param methodName Name of method, that will be invoked.
     * @param parametersTypes Array of parameter types, used for getting method.
     * @param parameters Array with arguments, that will be passed to method.
     * @return Result of method invoking.
     * @throws Exception To JUnit.
     */
    public static Object invokeMethod(Object obj, String methodName, Class[] parametersTypes,
        Object[] parameters) throws Exception {
        return invokeMethod(obj.getClass(), obj, methodName, parametersTypes, parameters);
    }

    /**
     * Gets the constructor with specified parameter types.
     *
     * @param clazz Class, which constructor will be returned.
     * @param parametersTypes Array of parameters types, that will be used for getting constructor.
     * @return Constructor.
     * @throws Exception To JUnit.
     */
    public static Constructor getConstructor(Class clazz, Class[] parametersTypes) throws Exception {
        return clazz.getDeclaredConstructor(parametersTypes);
    }
}