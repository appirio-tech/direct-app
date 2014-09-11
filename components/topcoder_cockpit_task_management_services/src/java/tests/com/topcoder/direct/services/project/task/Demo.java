/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.direct.services.project.task.model.UserDTO;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public class Demo {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>
     * Demo API usage of model classes.
     * </p>
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoModel() {
        // Create an instance of UserDTO
        UserDTO userDTO = new UserDTO();
        // Set properties
        userDTO.setUserId(1);
        userDTO.setHandle("handle");

        // Get properties
        long userId = userDTO.getUserId();
        String handle = userDTO.getHandle();

        // Other model classes can be used similarly
    }

    /**
     * <p>
     * Demo API usage of exceptions.
     * </p>
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoException() {
        // Create a TaskManagementException exception
        TaskManagementException exception1 = new TaskManagementException();

        // Create a TaskManagementException exception with message
        TaskManagementException exception2 = new TaskManagementException("The error message.");

        Exception cause = new Exception("Exception for testing.");

        // Create a TaskManagementException exception with inner cause
        TaskManagementException exception3 = new TaskManagementException(cause);

        // Create a TaskManagementException exception with inner cause
        TaskManagementException exception4 = new TaskManagementException("The error message.", cause);
    }
}
