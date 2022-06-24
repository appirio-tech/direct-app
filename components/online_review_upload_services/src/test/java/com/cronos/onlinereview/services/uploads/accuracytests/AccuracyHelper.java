/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads.accuracytests;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import com.topcoder.util.config.ConfigManager;

/**
 * Accuracy helper class.
 * 
 * @author kshatriyan
 * @version 1.0
 */
public final class AccuracyHelper {

    /**
     * Represents the current folder.
     */
    public static final String CURRENT_DIR = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator;

    /**
     * Represents the test_files folder.
     */
    public static final String TEST_FILES = CURRENT_DIR + "accuracy" + File.separator;

    /**
     * The project id used for testing.
     */
    public static final long PROJECT_ID = 100;

    /**
     * The project phase id used for testing.
     */
    public static final long PROJECT_PHASE_ID = 1000;

    /**
     * The submission id used for testing.
     */
    public static final long SUBMISSION_ID = 10;

    /**
     * The submission status id used for testing.
     */
    public static final long SUBMISSION_STATUS_ID = 1;

    /**
     * The file name used for testing.
     */
    public static final String FILE_NAME = "output.jar";

    /**
     * The user id used for testing.
     */
    public static final long USER_ID = 1;

    /**
     * No instances allowed.
     */
    private AccuracyHelper() {
        // empty
    }

    /**
     * <p>
     * Loads the default configuration.
     * </p>
     * 
     * @throws Exception
     *             exception to junit.
     */
    public static void loadConfig() throws Exception {
        release();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(TEST_FILES + "accuracy_config.xml");
    }

    /**
     * <p>
     * Releases the configurations.
     * </p>
     * 
     * @throws Exception
     *             exception to junit.
     */
    public static void release() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iterator = cm.getAllNamespaces(); iterator.hasNext();) {
            cm.removeNamespace((String) iterator.next());
        }
    }

    /**
     * <p>
     * Gets the field value of a given object.
     * </p>
     * 
     * @param object
     *            the object where to get the field value.
     * @param fieldName
     *            the name of the field.
     * @return the field value
     * @throws Exception
     *             any exception occurs.
     */
    public static Object getFieldValue(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * Creates a data handler for the input file.
     * 
     * @return the created data handler.
     */
    public static DataHandler getDataHandler() {
        DataHandler dataHandler = new DataHandler(new FileDataSource(TEST_FILES + "test/input.jar"));
        return dataHandler;
    }
}
