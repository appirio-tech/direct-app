/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

import com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices;
import com.topcoder.util.config.ConfigManager;

/**
 * Test helper class containing constants and some utility methods.
 *
 * @author cyberjag
 * @version 1.0
 */
public final class TestHelper {
    /**
     * Represents the test_files folder.
     */
    public static final String TEST_FILES = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator;

    /**
     * Represents the exception message used in testing.
     */
    public static final String EXCEPTION_MESSAGE = "Message to test";

    /**
     * The project id used for testing.
     */
    public static final long PROJECT_ID = 10;

    /**
     * The project phase id used for testing.
     */
    public static final long PROJECT_PHASE_ID = 1;

    /**
     * The submission id used for testing.
     */
    public static final long SUBMISSION_ID = 1001;

    /**
     * The submission status id used for testing.
     */
    public static final long SUBMISSION_STATUS_ID = 1;

    /**
     * The user id used for testing.
     */
    public static final long USER_ID = 600;

    /**
     * The end point of the Axis UploadService.
     */
    public static final String END_POINT = "http://localhost:8888/axis/services/UploadService";

    /**
     * No instances allowed.
     */
    private TestHelper() {
        // empty
    }

    /**
     * <p>
     * Loads the configuration from the given configuration file.
     * </p>
     *
     * @param file
     *            the file to load
     * @throws Exception
     *             exception to junit.
     */
    public static void loadConfigs(String file) throws Exception {
        releaseConfigs();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(TEST_FILES + file);
    }

    /**
     * <p>
     * Releases the configurations.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void releaseConfigs() throws Exception {
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
}
