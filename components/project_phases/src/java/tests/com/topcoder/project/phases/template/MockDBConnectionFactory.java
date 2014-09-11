/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Properties;

import com.topcoder.db.connectionfactory.ConnectionProducer;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer;

/**
 * <p>
 * This class is simply a sub-class of DBConnectionFactoryImpl, to support the use of ObjectFactory.
 * It contains only a default constructor.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> It is thread-safe since its parent class is thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 *
 */
public class MockDBConnectionFactory extends DBConnectionFactoryImpl {

    /**
     * <p>
     * Default constructor that does the necessary initialization work.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public MockDBConnectionFactory() throws Exception {
        // create a JDBCConnectionProducer
        Properties parameters = new Properties();
        Class.forName("com.informix.jdbc.IfxDriver");
        parameters.setProperty(JDBCConnectionProducer.JDBC_DRIVER_CLASS_PROPERTY, "com.informix.jdbc.IfxDriver");
        String url = "jdbc:informix-sqli://" + TestHelper.HOST + ":" + TestHelper.PORT + "/" + TestHelper.DATABASE
            + ":INFORMIXSERVER=" + TestHelper.SERVER_NAME + ";user=" + TestHelper.USER + ";password="
            + TestHelper.PASSWORD;
        ConnectionProducer producer = new JDBCConnectionProducer(url, parameters);

        // add the producer
        this.add("test", producer);
        this.add("MyConnection", producer);
        this.setDefault("test");

        // create a producer for the root
        String rootUrl = "jdbc:informix-sqli://" + TestHelper.HOST + ":" + TestHelper.PORT + ":INFORMIXSERVER="
            + TestHelper.SERVER_NAME + ";user=" + TestHelper.USER + ";password=" + TestHelper.PASSWORD;
        producer = new JDBCConnectionProducer(rootUrl, parameters);
        this.add("root", producer);
    }
}
