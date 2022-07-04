/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.hibernate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;

/**
 * <p>
 * The base unit test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@ContextConfiguration(locations = {"classpath:beans.xml"})
public class BaseUnitTest extends AbstractTransactionalJUnit4SpringContextTests {
    /**
     * <p>
     * Represents the empty string.
     * </p>
     */
    protected static final String EMPTY_STRING = " \t ";

    /**
     * <p>
     * Represents the <code>ApplicationContext</code> for tests.
     * </p>
     */
    protected static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Represents the invalid <code>ApplicationContext</code> for tests.
     * </p>
     */
    protected static final ApplicationContext APP_CONTEXT_INVALID;

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    private static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Represents the connection for testing.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("beans.xml");
        APP_CONTEXT_INVALID = new ClassPathXmlApplicationContext("invalid_beans.xml");
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        connection = getConnection(APP_CONTEXT);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        try {
            clearDB();
        } finally {
            closeConnection(connection);
        }
        connection = null;
    }

    /**
     * <p>
     * Gets a connection.
     * </p>
     *
     * @param appContext
     *            the application context.
     * @return the connection.
     * @throws Exception
     *             to JUnit.
     */
    private static Connection getConnection(ApplicationContext appContext) throws Exception {
        DataSource dataSource = (DataSource) appContext.getBean("dataSource");

        return dataSource.getConnection();
    }

    /**
     * <p>
     * Closes the given connection.
     * </p>
     *
     * @param connection
     *            the given connection.
     * @throws Exception
     *             to JUnit.
     */
    private static void closeConnection(Connection connection) throws Exception {
        if ((connection != null) && (!connection.isClosed())) {
            connection.close();
        }
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Empty lines will be ignored.
     * </p>
     *
     * @param connection
     *            the connection.
     * @param file
     *            the file.
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSQL(Connection connection, String file) throws Exception {
        String[] values = readFile(file).split(";");

        Statement statement = connection.createStatement();
        try {

            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();
                if (sql.length() != 0) {
                    statement.executeUpdate(sql);
                }
            }
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     * @return a string represents the content.
     * @throws IOException
     *             if any error occurs during reading.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            // Create a StringBuilder instance
            StringBuilder sb = new StringBuilder();

            // Buffer for reading
            char[] buffer = new char[1024];

            // Number of read chars
            int k = 0;

            // Read characters and append to string builder
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            // Return read content
            return sb.toString().replace("\r\n", "\n");
        } finally {
            reader.close();
        }
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void clearDB() throws Exception {
        executeSQL(connection, TEST_FILES + "DBClear.sql");
    }

    /**
     * Create the milestone instance.
     *
     * @param index
     *            the index number
     * @return the milestone instance
     */
    protected Milestone createMilestone(int index) {
        Milestone milestone = new Milestone();
        milestone.setName("name" + index);
        milestone.setDescription("description" + index);
        milestone.setDueDate(new Date(10000L));
        milestone.setSendNotifications(false);
        milestone.setCompleted(true);
        milestone.setProjectId(index);

        List<ResponsiblePerson> owners = new ArrayList<ResponsiblePerson>();
        milestone.setOwners(owners);
        ResponsiblePerson person1 = new ResponsiblePerson();
        person1.setName("person" + index + "_1");
        person1.setUserId(1);
        owners.add(person1);

        ResponsiblePerson person2 = new ResponsiblePerson();
        person2.setName("person" + index + "_2");
        person2.setUserId(2);
        owners.add(person2);

        return milestone;
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     * @return the field value.
     */
    protected static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                try {
                    declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    // Ignore
                }
            }
            declaredField.setAccessible(true);

            try {
                value = declaredField.get(obj);

            } finally {
                declaredField.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            // Ignore
        }

        return value;
    }

}
