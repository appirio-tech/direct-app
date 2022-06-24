/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.catalog.entity.TestHelper;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import javax.persistence.EntityTransaction;
import java.io.File;
import java.sql.Connection;


/**
 * A helper class for insert some records into the database.
 *
 * @author Chenhong
 * @version 1.0
 */
public class DBUtil {
    /**
     * Connection for tests.
     */
    private static Connection connection;

    /**
     * Private ctor.
     */
    private DBUtil() {
        // Empty.
    }

    /**
     * Insert a records into the comp_versions.
     *
     * @throws Exception to junit
     */
    static void initDB() throws Exception {
        String[] querys = {
            "INSERT INTO phase (phase_id, description) VALUES (0, 'phase')",

            "INSERT INTO comp_catalog (component_id, current_version, component_name, create_time, status_id)" +
                " VALUES (0, 0, '', DATETIME (2007-12-23) YEAR TO DAY, 0)",
            "INSERT INTO categories (category_id, category_name, status_id, description)" +
                " VALUES (0, '', 0, '')",

            "INSERT INTO comp_versions (comp_vers_id, version, version_text, create_time," +
                " phase_id, phase_time, price)" +
                " VALUES(0, 0, '', DATETIME (2007-12-23) YEAR TO DAY, 0, DATETIME (2007-12-24) YEAR TO DAY, 0)",

            "INSERT INTO comp_versions (comp_vers_id, version, version_text, create_time," +
                " phase_id, phase_time, price)" +
                " VALUES(11, 1, '1', DATETIME (2007-12-23) YEAR TO DAY, 0, DATETIME (2007-12-24) YEAR TO DAY, 0)",


            "INSERT INTO technology_types (technology_type_id, technology_name, description, status_id)" +
                " VALUES (0, '', '', 0)",
            "INSERT INTO technology_types (technology_type_id, technology_name, description, status_id)" +
                " VALUES (1, '', '', 101)",

            "insert into client (client_id) values (1)",
            "insert into client (client_id) values (2)",
            "insert into client (client_id) values (3)",

            "insert into user_client (client_id, user_id) values (1, 1)",
            "insert into user_client (client_id, user_id) values (1, 2)",
            "insert into user_client (client_id, user_id) values (1, 3)",
            "insert into user_client (client_id, user_id) values (2, 1)",
            "insert into user_client (client_id, user_id) values (3, 2)"

        };

        final EntityTransaction tx = TestHelper.getEntityTransaction();
        tx.begin();
        for (String query : querys) {
            TestHelper.executeUpdate(query);
        }
        tx.commit();
    }

    /**
     * Create a jdbc connection
     *
     * @return jdbc connection
     * @throws Exception to junit
     */
    public static Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            ConfigManager cm = ConfigManager.getInstance();
            String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

            if (!cm.existsNamespace(namespace)) {
                cm.add(new File("test_files/accuracytests/DBConnectionFactory.xml").getCanonicalPath());
            }

            DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

            connection = factory.createConnection();
        }
        return connection;
    }
}
