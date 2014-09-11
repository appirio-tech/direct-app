/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import java.util.Calendar;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.project.phases.template.StartDateGenerator;
import com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator;
import com.topcoder.util.config.ConfigManager;

/**
 * The class <code>RelativeWeekTimeStartDateGeneratorAccuracyTests</code> contains tests for the class
 * {@link <code>RelativeWeekTimeStartDateGenerator</code>}.
 * @author FireIce
 * @version 1.0
 */
public class RelativeWeekTimeStartDateGeneratorAccuracyTests extends TestCase {

    /**
     * An instance of the class being tested.
     * @see RelativeWeekTimeStartDateGenerator
     */
    private RelativeWeekTimeStartDateGenerator relativeWeekTimeStartDateGenerator;

    /**
     * Run the RelativeWeekTimeStartDateGenerator(String) constructor test.
     * @throws Exception
     */
    public void testRelativeWeekTimeStartDateGenerator1Accuracy() throws Exception {
        String namespace = "com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator";
        relativeWeekTimeStartDateGenerator = new RelativeWeekTimeStartDateGenerator(namespace);

        assertEquals("incorrect week_offset.", 1, relativeWeekTimeStartDateGenerator.getWeekOffset());
        assertEquals("incorrect day_of_week.", 5, relativeWeekTimeStartDateGenerator.getDayOfWeek());
        assertEquals("incorrect hour.", 9, relativeWeekTimeStartDateGenerator.getHour());
        assertEquals("incorrect minute.", 0, relativeWeekTimeStartDateGenerator.getMinute());
        assertEquals("incorrect second.", 0, relativeWeekTimeStartDateGenerator.getSecond());
    }

    /**
     * Run the RelativeWeekTimeStartDateGenerator(int,int,int,int,int) constructor test.
     * @throws Exception
     */
    public void testRelativeWeekTimeStartDateGenerator2Accuracy() throws Exception {
        int dayOfWeek = 1;
        int hour = 1;
        int minute = 1;
        int second = 1;
        int weekOffset = 1;
        relativeWeekTimeStartDateGenerator = new RelativeWeekTimeStartDateGenerator(dayOfWeek, hour, minute, second,
            weekOffset);
        assertNotNull(relativeWeekTimeStartDateGenerator);
        assertEquals("incorrect hour.", 1, relativeWeekTimeStartDateGenerator.getHour());
        assertEquals("incorrect day_of_week.", 1, relativeWeekTimeStartDateGenerator.getDayOfWeek());
        assertEquals("incorrect minute.", 1, relativeWeekTimeStartDateGenerator.getMinute());
        assertEquals("incorrect second.", 1, relativeWeekTimeStartDateGenerator.getSecond());
        assertEquals("incorrect week_offset.", 1, relativeWeekTimeStartDateGenerator.getWeekOffset());
    }

    /**
     * Run the java.util.Date generateStartDate() method test.
     * @throws Exception
     */
    public void testGenerateStartDateAccyracy() throws Exception {
        java.util.Date result = relativeWeekTimeStartDateGenerator.generateStartDate();
        assertNotNull(result);
        // initialize a Calendar with the start date
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        // verify the week
        assertEquals("Incorrect week.", Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + 1, cal
            .get(Calendar.WEEK_OF_YEAR));
        // verify day of week
        assertEquals("Incorrect day of week.", 5, cal.get(Calendar.DAY_OF_WEEK));
        // verify hour
        assertEquals("Incorrect hour.", 9, cal.get(Calendar.HOUR_OF_DAY));
        // verify minute
        assertEquals("Incorrect minute.", 0, cal.get(Calendar.MINUTE));
        // verify second
        assertEquals("Incorrect second.", 0, cal.get(Calendar.SECOND));
    }

    /**
     * Run the int getDayOfWeek() method test.
     * @throws Exception
     */
    public void testGetDayOfWeekAccuracy() throws Exception {
        assertEquals("incorrect day_of_week.", 5, relativeWeekTimeStartDateGenerator.getDayOfWeek());
    }

    /**
     * Run the int getHour() method test.
     * @throws Exception
     */
    public void testGetHourAccuracy() throws Exception {
        assertEquals("incorrect hour.", 9, relativeWeekTimeStartDateGenerator.getHour());
    }

    /**
     * Run the int getMinute() method test.
     * @throws Exception
     */
    public void testGetMinuteAccuracy() throws Exception {
        assertEquals("incorrect minute.", 0, relativeWeekTimeStartDateGenerator.getMinute());
    }

    /**
     * Run the int getSecond() method test.
     * @throws Exception
     */
    public void testGetSecondAccuracy() throws Exception {
        assertEquals("incorrect second.", 0, relativeWeekTimeStartDateGenerator.getSecond());
    }

    /**
     * Run the int getWeekOffset() method test.
     * @throws Exception
     */
    public void testGetWeekOffsetAccuracy() throws Exception {
        assertEquals("incorrect week_offset.", 1, relativeWeekTimeStartDateGenerator.getWeekOffset());
    }

    /**
     * <p>
     * Test implementation.
     * </p>
     * <p>
     * <code>RelativeWeekTimeStartDateGenerator</code> should be a sub-class of <code>StartDateGenerator</code>.
     * </p>
     */
    public void testImplementation() {
        assertTrue("inheritance fails", relativeWeekTimeStartDateGenerator instanceof StartDateGenerator);
    }

    /**
     * Perform pre-test initialization.
     * @throws Exception
     *             if the initialization fails for some reason
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        ConfigManager.getInstance().add("test_files/accuracytests/RelativeWeekTimeStartDateGenerator.xml");
        relativeWeekTimeStartDateGenerator = new RelativeWeekTimeStartDateGenerator(
            "com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator");
    }

    /**
     * Perform post-test clean-up.
     * @throws Exception
     *             if the clean-up fails for some reason
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        ConfigManager configManager = ConfigManager.getInstance();
        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(RelativeWeekTimeStartDateGeneratorAccuracyTests.class);
    }
}
