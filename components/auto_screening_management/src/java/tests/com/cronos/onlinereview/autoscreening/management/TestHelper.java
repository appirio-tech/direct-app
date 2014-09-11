/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * Defines test utilities used in various tests.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
final class TestHelper {
    /**
     * The constant of test config file name.
     */
    private static final String CONFIG_FILE = "config.xml";

    /**
     * A SQL statement to delete project_category_lu table.
     */
    private static final String DELETE1 = "delete from project_category_lu";

    /**
     * A SQL statement to delete project table.
     */
    private static final String DELETE2 = "delete from project";

    /**
     * A SQL statement to delete resource_info_type_lu table.
     */
    private static final String DELETE3 = "delete from resource_info_type_lu";

    /**
     * A SQL statement to delete resource table.
     */
    private static final String DELETE4 = "delete from resource";

    /**
     * A SQL statement to delete resource_info table.
     */
    private static final String DELETE5 = "delete from resource_info";

    /**
     * A SQL statement to delete upload table.
     */
    private static final String DELETE6 = "delete from upload";

    /**
     * A SQL statement to delete screening_status_lu table.
     */
    private static final String DELETE7 = "delete from screening_status_lu";

    /**
     * A SQL statement to delete screening_task table.
     */
    private static final String DELETE8 = "delete from screening_task";

    /**
     * A SQL statement to delete response_severity_lu table.
     */
    private static final String DELETE9 = "delete from response_severity_lu";

    /**
     * A SQL statement to delete screening_response_lu table.
     */
    private static final String DELETE10 = "delete from screening_response_lu";

    /**
     * A SQL statement to delete screening_result table.
     */
    private static final String DELETE11 = "delete from screening_result";

    /**
     * A SQL statement insert records into project_category_lu table.
     */
    private static final String INSERT_SQL_1 = "insert into project_category_lu("
            + "project_category_id) values(?)";

    /**
     * A SQL statement insert records into project table.
     */
    private static final String INSERT_SQL_2 = "insert into project("
            + "project_id, project_category_id) values(?, ?)";

    /**
     * A SQL statement insert records into resource_info_type_lu table.
     */
    private static final String INSERT_SQL_3 = "insert into resource_info_type_lu("
            + "resource_info_type_id, name) values(?, ?)";

    /**
     * A SQL statement insert records into resource table.
     */
    private static final String INSERT_SQL_4 = "insert into resource(resource_id) values(?)";

    /**
     * A SQL statement insert records into resource_info table.
     */
    private static final String INSERT_SQL_5 = "insert into resource_info("
            + "resource_id, resource_info_type_id, value) values(?, ?, ?)";

    /**
     * A SQL statement insert records into upload table.
     */
    private static final String INSERT_SQL_6 = "insert into upload("
            + "upload_id, project_id, resource_id) values(?, ?, ?)";

    /**
     * A SQL statement insert records into screening_status_lu table.
     */
    private static final String INSERT_SQL_7 = "insert into screening_status_lu("
            + "screening_status_id, name, description, create_user, create_date, modify_user"
            + ", modify_date) " + "values(?, ?, ?, ?, ?, ?, ?)";

    /**
     * A SQL statement insert records into screening_task table.
     */
    private static final String INSERT_SQL_8 = "insert into screening_task("
            + "screening_task_id, upload_id, screening_status_id, screener_id, start_timestamp, create_user"
            + ", create_date, modify_user, modify_date) " + "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * A SQL statement insert records into response_severity_lu table.
     */
    private static final String INSERT_SQL_9 = "insert into response_severity_lu("
            + "response_severity_id, name, description, create_user, create_date, modify_user"
            + ", modify_date) values(?, ?, ?, ?, ?, ?, ?)";

    /**
     * A SQL statement insert records into screening_response_lu table.
     */
    private static final String INSERT_SQL_10 = "insert into screening_response_lu("
            + "screening_response_id, response_severity_id, response_code, response_text, create_user, create_date"
            + ", modify_user, modify_date) " + "values(?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * A SQL statement insert records into screening_result table.
     */
    private static final String INSERT_SQL_11 = "insert into screening_result("
            + "screening_result_id, screening_task_id, screening_response_id, dynamic_response_text, create_user"
            + ", create_date, modify_user, modify_date) " + "values(?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents namespace for DBConnectionFactoryImpl.
     */
    private static final String DBCONNECTIONFACTORYIMPL_NAMESPACE =
        "com.cronos.onlinereview.autoscreening.management.db";

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. This should never be called.
     * </p>
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Load all namespaces for testing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public static void loadNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            releaseNamespaces();
            configManager.add(CONFIG_FILE);
        } catch (ConfigManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Release all test namespaces from ConfigManager.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public static void releaseNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * <p>
     * Prepare test data in db.
     * </p>
     *
     * @throws Exception
     *             if fails
     */
    public static void prepareData() throws Exception {
        clearData();

        Connection conn = new DBConnectionFactoryImpl(DBCONNECTIONFACTORYIMPL_NAMESPACE)
                .createConnection();

        /*
         * following code will insert data into tables for test
         */
        // project_category_lu table
        PreparedStatement stmt = conn.prepareStatement(INSERT_SQL_1);
        stmt.setLong(1, 1);
        stmt.executeUpdate();
        stmt.close();
        // project table
        stmt = conn.prepareStatement(INSERT_SQL_2);
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 10);
            stmt.setLong(2, 1);
            stmt.executeUpdate();
        }
        stmt.close();
        // resource_info_type_lu table
        stmt = conn.prepareStatement(INSERT_SQL_3);
        stmt.setLong(1, 31);
        stmt.setString(2, "resource_info_type_id_31");
        stmt.executeUpdate();
        stmt.close();
        // resource table
        stmt = conn.prepareStatement(INSERT_SQL_4);
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 40);
            stmt.executeUpdate();
        }
        stmt.close();
        // resource_info table
        stmt = conn.prepareStatement(INSERT_SQL_5);
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 40);
            stmt.setLong(2, 31);
            stmt.setString(3, "resource_info_" + i);
            stmt.executeUpdate();
        }
        stmt.close();
        // upload table
        stmt = conn.prepareStatement(INSERT_SQL_6);
        for (int i = 1; i <= 6; ++i) {
            stmt.setLong(1, i + 50);
            stmt.setLong(2, i + 10);
            stmt.setLong(3, i + 40);
            stmt.executeUpdate();
        }
        stmt.close();
        // screening_status_lu table
        stmt = conn.prepareStatement(INSERT_SQL_7);
        stmt.setLong(1, 61);
        stmt.setString(2, "Pending");
        stmt.setString(3, "screening_status_lu_description");
        stmt.setString(4, "screening_status_lu_create_user");
        stmt.setDate(5, new Date(System.currentTimeMillis() + 100000));
        stmt.setString(6, "screening_status_lu_modify_user");
        stmt.setDate(7, new Date(System.currentTimeMillis() + 200000));
        stmt.executeUpdate();
        // add Screening
        stmt.setLong(1, 62);
        stmt.setString(2, "Screening");
        stmt.setString(3, "screening_status_lu_description");
        stmt.setString(4, "screening_status_lu_create_user");
        stmt.setDate(5, new Date(System.currentTimeMillis() + 100000));
        stmt.setString(6, "screening_status_lu_modify_user");
        stmt.setDate(7, new Date(System.currentTimeMillis() + 200000));
        stmt.executeUpdate();
        stmt.close();

        // screening_task table
        stmt = conn.prepareStatement(INSERT_SQL_8);
        for (int i = 1; i <= 5; ++i) {
            stmt.setLong(1, i + 70);
            stmt.setLong(2, i + 50);
            stmt.setLong(3, 62);
            // add some null value to field 4 and 5
            stmt.setLong(4, i + 80);
            stmt.setDate(5, new Date(System.currentTimeMillis() + (i + 10) * 100000));
            if (i == 3) {
                stmt.setObject(4, null);
                stmt.setObject(5, null);
            } else if (i == 4) {
                stmt.setObject(4, null);
            } else if (i == 5) {
                stmt.setObject(5, null);
            }
            stmt.setString(6, "screening_task_create_user_" + i);
            stmt.setDate(7, new Date(System.currentTimeMillis() + (i + 10) * 200000));
            stmt.setString(8, "screening_task_modify_user_" + i);
            stmt.setDate(9, new Date(System.currentTimeMillis() + (i + 10) * 300000));
            stmt.executeUpdate();
        }

        stmt.close();
        // response_severity_lu table
        stmt = conn.prepareStatement(INSERT_SQL_9);
        for (int i = 1; i <= 5; ++i) {
            stmt.setLong(1, i + 90);
            stmt.setString(2, "response_severity_lu_name_" + i);
            stmt.setString(3, "response_severity_lu_description_" + i);
            stmt.setString(4, "response_severity_lu_create_user_" + i);
            stmt.setDate(5, new Date(System.currentTimeMillis() + (i + 20) * 100000));
            stmt.setString(6, "response_severity_lu_modify_user_" + i);
            stmt.setDate(7, new Date(System.currentTimeMillis() + (i + 20) * 200000));
            stmt.executeUpdate();
        }
        stmt.close();
        // screening_response_lu table
        stmt = conn.prepareStatement(INSERT_SQL_10);
        for (int i = 1; i <= 5; ++i) {
            stmt.setLong(1, i + 100);
            stmt.setLong(2, i + 90);
            stmt.setString(3, "response_code_" + i);
            stmt.setString(4, "response_text" + i);
            stmt.setString(5, "create_user" + i);
            stmt.setDate(6, new Date(System.currentTimeMillis() + (i + 30) * 100000));
            stmt.setString(7, "modify_user" + i);
            stmt.setDate(8, new Date(System.currentTimeMillis() + (i + 30) * 100000));
            stmt.executeUpdate();
        }
        stmt.close();
        // screening_result table
        stmt = conn.prepareStatement(INSERT_SQL_11);
        for (int i = 1; i <= 5; ++i) {
            stmt.setLong(1, i + 110);
            stmt.setLong(2, 71);
            stmt.setLong(3, i + 100);
            stmt.setString(4, "dynamic_response_text" + i);
            stmt.setString(5, "create_user" + i);
            stmt.setDate(6, new Date(System.currentTimeMillis() + (i + 40) * 100000));
            stmt.setString(7, "modify_user" + i);
            stmt.setDate(8, new Date(System.currentTimeMillis() + (i + 40) * 100000));
            stmt.executeUpdate();
        }
        stmt.close();

        conn.close();
    }

    /**
     * <p>
     * Clear all the data in db.
     * </p>
     *
     * @throws Exception
     *             if fails
     */
    public static void clearData() throws Exception {
        Connection conn = new DBConnectionFactoryImpl(DBCONNECTIONFACTORYIMPL_NAMESPACE)
                .createConnection();

        /*
         * The following code will remove all records from test tables.
         */
        PreparedStatement stmt = conn.prepareStatement(DELETE11);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE10);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE9);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE8);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE7);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE6);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE5);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE4);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE3);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE2);
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement(DELETE1);
        stmt.executeUpdate();
        stmt.close();

        conn.close();
    }
}
