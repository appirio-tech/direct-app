/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import junit.framework.TestCase;

import java.io.FileInputStream;

import java.util.Properties;


/**
 * This class is used to check the behavior of the <code>DefaultWorkdaysFactory</code> class, including tests of
 * creating a workdays instance by DefaultWorkdaysFactory.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultWorkdaysFactoryTest extends TestCase {
    /** A WorkdaysFactory instance to create Workdays instance to test. */
    private WorkdaysFactory factory = new DefaultWorkdaysFactory();

    /** The properties to load the DefaultWorkdaysFactory's configuration file. */
    private Properties prop = new Properties();

    /** The FileInputStream instance to load the DefaultWorkdaysFactory's configuration file. */
    private FileInputStream file = null;

    /**
     * Load the DefaultWorkdaysFactory's configuration file to initialize the parameter prop.
     *
     * @throws Exception if can't load the configuration file
     */
    protected void setUp() throws Exception {
        file = new FileInputStream(DefaultWorkdaysFactoryTest.class.getClassLoader()
                .getResource(DefaultWorkdaysFactory.CONFIGURATION_FILE).getFile());
        prop.load(file);
    }

    /**
     * Clear the properties and close the file input stream.
     *
     * @throws Exception if any IOException throws when close the file
     */
    protected void tearDown() throws Exception {
        prop.clear();
        file.close();
    }

    /**
     * Test the content of the instance created by DefaultWorkdaysFactory, this method only test the file_name and
     * file_format in the instance, other properties will be tested in the method testing the construtor of
     * DefaultWorkdays.
     */
    public void testCreateWorkdaysInstance() {
        DefaultWorkdays workdays = (DefaultWorkdays) factory.createWorkdaysInstance();
        assertNotNull("Test instance created by DefaultWorkdaysFactory", workdays);
        assertEquals("Test the file_name", prop.getProperty(DefaultWorkdaysFactory.FILE_NAME_PROPERTY),
            workdays.getFileName());

        String fileFormat = prop.getProperty(DefaultWorkdaysFactory.FILE_FORMAT_PROPERTY);

        if (fileFormat == null) {
            // if the file_format property is not specified in configuration file,
            // set XML_FILE_FORMAT as default value
            fileFormat = DefaultWorkdays.XML_FILE_FORMAT;
        }

        assertEquals("Test the file_format", fileFormat, workdays.getFileFormat());
    }
}
