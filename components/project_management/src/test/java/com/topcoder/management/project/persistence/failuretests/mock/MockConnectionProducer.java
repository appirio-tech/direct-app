/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence.failuretests.mock;

import com.topcoder.db.connectionfactory.ConnectionProducer;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.util.config.Property;

import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;

/**
 * <p>A mock implementation of {@link ConnectionProducer} class to be used for testing. Overrides the protected methods
 * declared by a super-class. The overridden methods are declared with package private access so only the test cases
 * could invoke them. The overridden methods simply call the corresponding method of a super-class.
 *
 * @author isv
 * @version 1.0
 */
public class MockConnectionProducer implements ConnectionProducer {

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Map</code>s mapping the <code>
     * String</code> names of the arguments to <code>Object</code>s representing the values of  arguments which have
     * been provided by the caller of the method.</p>
     */
    private static Map methodArguments = new HashMap();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Exception</code>s to be thrown
     * by methods.</p>
     */
    private static Map throwExceptions = new HashMap();

    /**
     * <p>A <code>Map</code> mapping the <code>String</code> method signatures to <code>Object</code>s to be returned by
     * methods.</p>
     */
    private static Map methodResults = new HashMap();

    /**
     * <p>A <code>Throwable</code> representing the exception to be thrown from any method of the mock class.</p>
     */
    private static Throwable globalException = null;

    /**
     * <p>Constructs new <code>MockConnectionProducer</code> instance.</p>
     */
    public MockConnectionProducer() {
    }

    /**
     * <p>Constructs new <code>MockConnectionProducer</code> instance.</p>
     *
     * @param property a <code>Property</code> providing the configuration for new instance.
     */
    public MockConnectionProducer(Property property) {
    }

    /**
     * <p>A mock implementation of the method. The method either throws an exception which might have been specified
     * through {@link #throwException(String, Throwable)} method or return a result specified through {@link
     * #setMethodResult(String, Object)} method.</p>
     *
     * @throws com.topcoder.db.connectionfactory.DBConnectionException
     * @see ConnectionProducer#createConnection()
     */
    public Connection createConnection() throws DBConnectionException {
        if (MockConnectionProducer.globalException != null) {
            if (MockConnectionProducer.globalException instanceof DBConnectionException) {
                throw (DBConnectionException) MockConnectionProducer.globalException;
            } else {
                throw new RuntimeException("The test may not be configured properly",
                                           MockConnectionProducer.globalException);
            }
        }

        String methodName = "createConnection";

        Throwable exception = (Throwable) MockConnectionProducer.throwExceptions.get(methodName);
        if (exception != null) {
            if (exception instanceof DBConnectionException) {
                throw (DBConnectionException) exception;
            } else {
                throw new RuntimeException("The test may not be configured properly", exception);
            }
        }

        HashMap arguments = new HashMap();
        MockConnectionProducer.methodArguments.put(methodName, arguments);

        return (Connection) MockConnectionProducer.methodResults.get(methodName);

    }

    /**
     * <p>Sets the result to be returned by the specified method.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * declared by the implemented interface/class.
     * @param result an <code>Object</code> representing the result to be returned by specified method.
     */
    public static void setMethodResult(String methodSignature, Object result) {
        MockConnectionProducer.methodResults.put(methodSignature, result);
    }

    /**
     * <p>Gets the value of the specified argument which has been passed to the specified method by the caller.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * @param argumentName a <code>String</code> providing the name of the argument to get the value for.
     * @return an <code>Object</code> (including <code>null</code>) providing the value of the specified argument which
     *         has been supplied by the caller of the specified method.
     * @throws IllegalArgumentException if the specified argument does not exist.
     */
    public static Object getMethodArgument(String methodSignature, String argumentName) {
        Map arguments = (Map) MockConnectionProducer.methodArguments.get(methodSignature);
        if (!arguments.containsKey(argumentName)) {
            throw new IllegalArgumentException("The argument name " + argumentName + " is unknown.");
        }
        return arguments.get(argumentName);
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param methodSignature a <code>String</code> uniquelly distinguishing the target method among other methods
     * @param exception a <code>Throwable</code> representing the exception to be thrown when the specified method is
     * called. If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwException(String methodSignature, Throwable exception) {
        if (exception != null) {
            MockConnectionProducer.throwExceptions.put(methodSignature, exception);
        } else {
            MockConnectionProducer.throwExceptions.remove(methodSignature);
        }
    }

    /**
     * <p>Sets the exception to be thrown when the specified method is called.</p>
     *
     * @param exception a <code>Throwable</code> representing the exception to be thrown whenever any method is called.
     * If this argument is <code>null</code> then no exception will be thrown.
     */
    public static void throwGlobalException(Throwable exception) {
        MockConnectionProducer.globalException = exception;
    }

    /**
     * <p>Releases the state of <code>MockConnectionProducer</code> so all collected method arguments, configured method
     * results and exceptions are lost.</p>
     */
    public static void releaseState() {
        MockConnectionProducer.methodArguments.clear();
        MockConnectionProducer.methodResults.clear();
        MockConnectionProducer.throwExceptions.clear();
        MockConnectionProducer.globalException = null;
    }

    /**
     * <p>Initializes the initial state for all created instances of <code>MockConnectionProducer</code> class.</p>
     */
    public static void init() {
    }

}
