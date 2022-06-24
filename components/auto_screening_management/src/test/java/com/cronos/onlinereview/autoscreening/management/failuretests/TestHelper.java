/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;


/**
 * Helper class assisting the failure tests.
 *
 * @author GavinWang
 * @version 1.0
 */
public class TestHelper {
    /**
     * Prevents instantiation.
     */
    private TestHelper() {
    }

    /**
     * Loads all config namespaces needed by the failure tests.
     *
     * @throws Exception if any error occurs
     */
    public static void loadConfig() throws Exception {
        cleanConfig();

        ConfigManager cm = ConfigManager.getInstance();

        cm.add("failure/config.xml");
    }

    /**
     * Cleans all config namespaces needed by this conponent.
     *
     * @throws Exception if any error occurs
     */
    public static void cleanConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            String ns = (String) iter.next();

            if (cm.existsNamespace(ns)) {
                cm.removeNamespace(ns);
            }

            iter.remove();
        }
    }

    /**
     * Load a SQL file and run each line as a statement.
     *
     * @param filename file where the statements reside
     *
     * @throws Exception if fails
     */
    public static void executeSQLFile(String filename)
        throws Exception {
        String fullName = TestHelper.class.getClassLoader().getResource(filename).getFile();
        BufferedReader in = new BufferedReader(new FileReader(fullName));
        Connection conn = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")
            .createConnection();
        Statement stmt = conn.createStatement();
        String str;

        while ((str = in.readLine()) != null) {
            if (str.trim().length() != 0) {
                stmt.executeUpdate(str);
            }
        }

        stmt.close();
        conn.close();
        in.close();
    }
}
