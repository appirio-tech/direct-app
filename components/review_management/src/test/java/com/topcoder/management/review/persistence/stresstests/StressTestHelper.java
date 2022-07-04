/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;


import java.io.FileReader;
import java.io.Reader;

import java.sql.Connection;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * The help class of the unit test.
 * </p>
 *
 * @author telly12
 * @version 1.0
 */
final class StressTestHelper {
    /**
     * The load of the stress test.
     */
    public static final int LOAD = 100;
    /**
     * The private default contructor prevent the creation of an instance outside.
     */
    private StressTestHelper() {
    }

    /**
     * Get the database connection.
     *
     * @return the connection created.
     * @throws Exception to JUnit
     */
    private static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename the sql file.
     * @throws Exception to JUnit
     */
    static void executeSqlFile(String filename) throws Exception {
        Reader file = new FileReader(filename);
        char[] buffer = new char[1024];
        int retLength = 0;
        StringBuffer content = new StringBuffer();

        while ((retLength = file.read(buffer)) >= 0) {
            content.append(buffer, 0, retLength);
        }

        file.close();

        List sqls = new ArrayList();
        int lastIndex = 0;

        //parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        //get the connection
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        try {
            //excute the sql in the file
            for (int i = 0; i < sqls.size(); i++) {
                stmt.executeUpdate((String) sqls.get(i));
            }
        } finally {
            stmt.close();
            conn.close();
        }
    }
}
