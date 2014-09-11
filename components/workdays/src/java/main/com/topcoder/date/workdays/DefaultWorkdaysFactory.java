/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * This is the default implementation of WorkdaysFactory. It creates instances of DefaultWorkdays type.&nbsp;It loads
 * the configuration file under the constant namespace. If no error occurs and the namespace contains 2 properties:
 * file_name and file_format, it will create a DefaultWorkdays using the constructor with 2 string arguments. If the
 * file_format is missing, the DefaultWorkdays.XML_FILE_FORMAT is used.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultWorkdaysFactory implements WorkdaysFactory {
    /**
     * <p>
     * Represents the default name of the configuration file for the DefaultWorkdaysFactory.
     * </p>
     */
    public static final String CONFIGURATION_FILE = "com/topcoder/date/workdays/defaultWorkdaysFactory.properties";

    /**
     * <p>
     * Represents the namespace under the file which is loaded by the configuration manager.
     * </p>
     */
    public static final String NAMESPACE = "com.topcoder.date.workdays";

    /**
     * <p>
     * Represents the file_name property name under the default workdays factory namespace which is loaded by the
     * configuration manager.
     * </p>
     */
    public static final String FILE_NAME_PROPERTY = "file_name";

    /**
     * <p>
     * Represents the file_format property name under the default workdays factory namespace which is loaded by the
     * configuration manager.
     * </p>
     */
    public static final String FILE_FORMAT_PROPERTY = "file_format";

    /**
     * <p>
     * Creates a DefaultWorkdays. It loads the configuration file under the constant namespace. If no error occurs and
     * the namespace contains 2 properties: file_name and file_format, it will create a DefaultWorkdays using the
     * constructor with 2 string arguments. If the file_format is missing, the DefaultWorkdays.XML_FILE_FORMAT is
     * used.
     * </p>
     *
     * <p>
     * If an error loading the file occurs, or the namespace doesn't contain a file name, or the DefaultWorkdays throw
     * an exception, a DefaultWorkdays constructed with the constructor with no arguments is returned.
     * </p>
     *
     * @return a DefaultWorkdays instance
     */
    public Workdays createWorkdaysInstance() {
        try {
            ConfigManager manager = ConfigManager.getInstance();

            if (!manager.existsNamespace(NAMESPACE)) {
                // NAMESPACE is not in configManager
                // add the namespace of DefaultWorkdaysFactory to configManager to get the file_name and file_format
                manager.add(NAMESPACE, CONFIGURATION_FILE, ConfigManager.CONFIG_PROPERTIES_FORMAT);
            }

            String fileName = manager.getString(NAMESPACE, FILE_NAME_PROPERTY);
            String fileFormat = manager.getString(NAMESPACE, FILE_FORMAT_PROPERTY);

            if (fileFormat == null) {
                // the file_format is missing, the DefaultWorkdays.XML_FILE_FORMAT is used.
                fileFormat = DefaultWorkdays.XML_FILE_FORMAT;
            }

            return new DefaultWorkdays(fileName, fileFormat);
        } catch (Exception e) {

            // an error loading the file occurs, or the namespace doesn't contain a file name,
            // or the DefaultWorkdays throw an exception
            return new DefaultWorkdays();
        }
    }
}
