/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import junit.framework.TestCase;

import java.util.Date;
import java.util.Set;


/**
 * This class show all the usage of workdays component, getting a Workdays instance from a WorkdaysFactory,  directly
 * constructing a DefaultWorkdays instance, initializing a Workdays instance with properties of XML  configuration
 * file, adding non-workdays to the instance, setting the work time, and the most importantly, adding amount of
 * unitOfTimes to a specified start date to get the end date.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * Test all functionality in this method.
     *
     * @throws Exception if any error occurs.
     */
    public void testFunctionality() throws Exception {
        // Get a Workdays instance from a WorkdaysFactory, to get a instance correct, you must
        // config the file com/topcoder/date/workdays/defaultWorkdaysFactory.properties with two properties:
        // file_name and file_format. File_format has two type, properties and XML. If you didn't specify
        // file_format, it would be set to XML as default. The file_name property is the name of configuration
        // file which config the DefaultWorkdays instance, the format of the file is described in component spec.
        WorkdaysFactory factory = new DefaultWorkdaysFactory();

        // If the factory can construct a Workdays instance with the given file_name and file_format,
        // a default instance of DefaultWorkdays will return.
        Workdays workdays = factory.createWorkdaysInstance();

        // Another way to get a Workdays instance, construct it directly.
        // A default configurated instance.
        workdays = new DefaultWorkdays();

        // Or construct it with file_name and file_format, a properties type file
        workdays = new DefaultWorkdays("test_files/workdays_unittest.properties",
                        DefaultWorkdays.PROPERTIES_FILE_FORMAT);

        // Or a XML type file
        workdays = new DefaultWorkdays("test_files/workdays_unittest.xml", DefaultWorkdays.XML_FILE_FORMAT);

        // You can add non-workdays to the instance, for example, add today as a non-workday.
        workdays.addNonWorkday(new Date());

        // Then, you can also get all non-workdays as a set.
        Set allNonWorkdays = workdays.getNonWorkdays();

        // You can set Saturday or Sunday as workdays, the default is not.
        workdays.setSaturdayWorkday(true);
        workdays.setSundayWorkday(false);

        // You can refresh the configuration from config file, but it's DefaultWorkdays's method
        DefaultWorkdays defaultWorkdays = (DefaultWorkdays) workdays;
        defaultWorkdays.refresh();

        // You can set the work time according your own situation.
        workdays.setWorkdayStartTimeHours(10);
        workdays.setWorkdayStartTimeMinutes(0);
        workdays.setWorkdayEndTimeHours(20);
        workdays.setWorkdayEndTimeMinutes(30);

        // You also can save the change
        defaultWorkdays.save();

        // or maybe you want to save as another file,
        // first you should set the file_name and file_format
        defaultWorkdays.setFileName("test_files/save_as_demo.XML");
        defaultWorkdays.setFileFormat(DefaultWorkdays.XML_FILE_FORMAT);

        // now save as the file you set
        defaultWorkdays.save();

        // the most important, this component can help you calculate the end date that there are amount units of time
        // between the start time and end time.
        Date endDate = workdays.add(new Date(), WorkdaysUnitOfTime.DAYS, 100);

        // or
        endDate = workdays.add(new Date(), WorkdaysUnitOfTime.HOURS, 10);

        // or
        endDate = workdays.add(new Date(), WorkdaysUnitOfTime.MINUTES, 10000);
    }
}
