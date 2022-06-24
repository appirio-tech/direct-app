/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan.accuracytests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

/**
 * <p>
 * Utils class.
 * </p>
 * @author vilain
 * @version 1.0
 */
public class AccuracyTestHelper {

    /**
     * Represents db url to tcs_catalog DB.
     */
    private static String dbTcsCatalogUrl;

    /**
     * Represents db url to tcs_catalog DB.
     */
    private static String dbStudioOltpUrl;

    /**
     * Represents db username.
     */
    private static String dbUser;

    /**
     * Represents db password.
     */
    private static String dbPassword;

    /**
     * Storage for sql statements for inserting values in asserts table.
     */
    private static final String DATA_TCS_CATALOG_SQL_FILE = "test_files/accuracy/data_tcs_catalog.sql";

    /**
     * Hibernate configuration for tcs_catalog DB.
     */
    private static final String DATA_TCS_CATALOG_HIBERNATE = "hibernate_software.cfg.xml";

    /**
     * Storage for sql statements for inserting values in asserts table.
     */
    private static final String DATA_STUDIO_OLTP_SQL_FILE = "test_files/accuracy/data_studio_oltp.sql";

    /**
     * Hibernate configuration for tcs_catalog DB.
     */
    private static final String DATA_STUDIO_OLTP_HIBERNATE = "hibernate_studio.cfg.xml";

    /**
     * Storage for db properties.
     */
    private static final String DB_PROPERTIES_SQL_FILE = "test_files/accuracy/db.properties";

    /**
     * Represents the list of tables from tcs_catalog DB.
     */
    private static final List<String> TCS_CATALOG_DB_TABLES;

    /**
     * Represents the list of tables from studio_oltp DB.
     */
    private static final List<String> STUDIO_OLTP_DB_TABLES;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(DB_PROPERTIES_SQL_FILE));
            dbTcsCatalogUrl = properties.getProperty("db.tcs_catalog.url");
            dbStudioOltpUrl = properties.getProperty("db.studio_oltp.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
        } catch (Exception e) {
            // ignore
        }
        // initializing tcs_catalog DB tables to clear
        TCS_CATALOG_DB_TABLES = new ArrayList<String>();
        TCS_CATALOG_DB_TABLES.add("linked_project_xref");
        TCS_CATALOG_DB_TABLES.add("project_phase");
        TCS_CATALOG_DB_TABLES.add("tc_direct_project");
        TCS_CATALOG_DB_TABLES.add("project");
        // initializing studio_oltp DB tables to clear
        STUDIO_OLTP_DB_TABLES = new ArrayList<String>();
        STUDIO_OLTP_DB_TABLES.add("contest");
        STUDIO_OLTP_DB_TABLES.add("tc_direct_project");
    }

    /**
     * Default constructor.
     */
    private AccuracyTestHelper() {
    }

    /**
     * Adding data from sql file for testing purposes.
     * @param connection connection to database
     * @param studio whether studio
     * @throws Exception wrap all exceptions
     */
    public static void addTestData(Connection connection, boolean studio) throws Exception {
        BufferedReader reader =
            new BufferedReader(new FileReader(studio ? DATA_STUDIO_OLTP_SQL_FILE : DATA_TCS_CATALOG_SQL_FILE));
        String sql;
        while ((sql = reader.readLine()) != null) {
            // execute each sql insert statement,
            if (sql.length() > 2) {
                connection.createStatement().executeUpdate(sql);
            }
        }
    }

    /**
     * Deleting all data from specified table.
     * @param connection connection to database
     * @param studio whether studio
     * @throws Exception wrap all exceptions
     */
    public static void clearTables(Connection connection, boolean studio) throws Exception {
        for (String table : studio ? STUDIO_OLTP_DB_TABLES : TCS_CATALOG_DB_TABLES) {
            String query = "DELETE FROM " + table;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }
    }

    /**
     * Creates the connection to db.
     * @param studio whether studio
     * @return the created connection
     * @throws Exception wraps all exceptions
     */
    public static Connection createConnection(boolean studio) throws Exception {
        Class.forName("com.informix.jdbc.IfxDriver");
        Connection connection =
            DriverManager.getConnection(studio ? dbStudioOltpUrl : dbTcsCatalogUrl, dbUser, dbPassword);
        return connection;
    }

    /**
     * Setting the value of the object's field.
     * @param object provided object
     * @param fieldName the name of the field
     * @param fieldValue the value of the field
     * @throws Exception wraps all exceptions
     */
    public static void setFieldValue(Object object, String fieldName, Object fieldValue) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
    }

    /**
     * Creates entity manager.
     * @param studio whether studio
     * @return created entity manager
     */
    public static EntityManager createEntityManager(boolean studio) {
        Ejb3Configuration cfg = new Ejb3Configuration();
        cfg.configure(studio ? DATA_STUDIO_OLTP_HIBERNATE : DATA_TCS_CATALOG_HIBERNATE);
        EntityManagerFactory emf = cfg.buildEntityManagerFactory();
        return emf.createEntityManager();
    }
}