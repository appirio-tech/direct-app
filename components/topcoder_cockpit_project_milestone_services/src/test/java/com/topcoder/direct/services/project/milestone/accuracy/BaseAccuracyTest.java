/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.accuracy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.After;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * <p>
 * The base class for accuracy unit test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseAccuracyTest {
    /**
     * <p>
     * Represents the <code>ApplicationContext</code> for tests.
     * </p>
     */
    protected static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new FileSystemXmlApplicationContext("test_files/accuracy/applicationContext.xml");
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        executeSQL("test_files/accuracy/DBClear.sql");
    }

    /**
     * Run the given SQL file.
     *
     * @param filePath SQL file path
     *
     * @throws Exception to JUnit
     */
    private static void executeSQL(String filePath) throws Exception {
        DataSource dataSource = (DataSource) APP_CONTEXT.getBean("dataSource");
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String content = getFileAsString(filePath);
            for (String st : content.split(";")) {
                preparedStatement = connection.prepareStatement(st);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * <p>
     * Gets the file content as string.
     * </p>
     *
     * @param filePath the file path
     *
     * @return the file content
     *
     * @throws Exception to JUnit
     */
    private static String getFileAsString(String filePath) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try {
            String s;
            while ((s = reader.readLine()) != null) {
                buf.append(s);
            }
            return buf.toString();
        } finally {
            reader.close();
        }
    }
}
