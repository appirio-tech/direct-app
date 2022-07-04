/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.startdategenerator;

import java.util.Calendar;
import java.util.Date;

import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.Helper;
import com.topcoder.project.phases.template.StartDateGenerationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test on class <code>RelativeWeekTimeStartDateGenerator</code>.
 * </p>
 * @author flying2hk
 * @version 1.0
 */
public class UnitTestRelativeWeekTimeStartDateGenerator extends TestCase {
    /**
     * <p>
     * Configuration file containing bad namespaces.
     * </p>
     */
    private static final String BAD_CONFIG =
        "test_files/bad_config/Relative_Week_Time_Start_Date_Generator_Bad_Config.xml";

    /**
     * <p>
     * Good configuration file.
     * </p>
     */
    private static final String CONFIG = "test_files/config/Project_Phase_Template_Config.xml";

    /**
     * <p>
     * Good configuration namespace.
     * </p>
     */
    private static final String NAMESPACE =
        "com.topcoder.project.phases.template.startdategenerator.RelativeWeekTimeStartDateGenerator";

    /**
     * <p>
     * Value of day of week. THURSDAY is used.
     * </p>
     */
    private static final int DAY_OF_WEEK = Calendar.THURSDAY;

    /**
     * <p>
     * Value of hour. 9 is used.
     * </p>
     */
    private static final int HOUR = 9;

    /**
     * <p>
     * Value of minute. 0 is used.
     * </p>
     */
    private static final int MINUTE = 0;

    /**
     * <p>
     * Value of second. 0 is used.
     * </p>
     */
    private static final int SECOND = 0;

    /**
     * <p>
     * Value of week offset. 1 is used to indicate the next week.
     * </p>
     */
    private static final int WEEK_OFFSET = 1;

    /**
     * <p>
     * Instance of <code>RelativeWeekTimeStartDateGenerator</code> used in this test.
     * </p>
     */
    private RelativeWeekTimeStartDateGenerator generator1 = null;

    /**
     * <p>
     * Instance of <code>RelativeWeekTimeStartDateGenerator</code> used in this test.
     * </p>
     */
    private RelativeWeekTimeStartDateGenerator generator2 = null;
    /**
     * <p>
     * Set up environment.
     * </p>
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        // load configuration files
        Helper.loadConfig(CONFIG);
        Helper.loadConfig(BAD_CONFIG);
        // create generator from scratch
        this.generator1 = new RelativeWeekTimeStartDateGenerator(DAY_OF_WEEK, HOUR, MINUTE, SECOND,
                WEEK_OFFSET);
        // create generator from configuration
        this.generator2 = new RelativeWeekTimeStartDateGenerator(NAMESPACE);
    }

    /**
     * <p>
     * Clear the configuration.
     * </p>
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        Helper.clearConfig();
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for accuracy.
     * </p>
     */
    public void testCtor1_Success() {
        assertNotNull("Failed to create RelativeWeekTimeStartDateGenerator.", this.generator1);
    }
    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The dayOfWeek is less than 1,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_DayOfWeekTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator(0, HOUR, MINUTE, SECOND, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The dayOfWeek is larger than 7,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_DayOfWeekTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator(8, HOUR, MINUTE, SECOND, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The hour is less than 0,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_HourTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator(DAY_OF_WEEK, -1, MINUTE, SECOND, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The hour is larger than 23,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_HourTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator(DAY_OF_WEEK, 24, MINUTE, SECOND, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The minute is less than 0,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_MinuteTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator(DAY_OF_WEEK, HOUR, -1, SECOND, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The minute is larger than 59,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_MinuteTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator(DAY_OF_WEEK, HOUR, 60, SECOND, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The second is less than 0,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_SecondTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator(DAY_OF_WEEK, HOUR, MINUTE, -1, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(int dayOfWeek, int hour, int minute,
     * int second, int weekOffset)</code> for failure. The second is larger than 59,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCtor1_SecondTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator(DAY_OF_WEEK, HOUR, MINUTE, 60, WEEK_OFFSET);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }


    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for accuracy.
     * </p>
     */
    public void testCtor2_Success() {
        assertNotNull("Failed to create RelativeWeekTimeStartDateGenerator.", this.generator2);
    }
    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The namespace is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws ConfigurationException to JUnit
     */
    public void testCtor2_NullNamespace() throws ConfigurationException {
        try {
            new RelativeWeekTimeStartDateGenerator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The namespace is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws ConfigurationException to JUnit
     */
    public void testCtor2_EmptyNamespace() throws ConfigurationException {
        try {
            new RelativeWeekTimeStartDateGenerator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The namespace is unknown, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_UnknownNamespace() {
        try {
            new RelativeWeekTimeStartDateGenerator("unknown");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "week_offset" is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_WeekOffsetMissing() {
        try {
            new RelativeWeekTimeStartDateGenerator("week_offset_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "week_offset" is malformed, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_WeekOffsetMalformed() {
        try {
            new RelativeWeekTimeStartDateGenerator("week_offset_malformed");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "day_of_week" is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_DayOfWeekMissing() {
        try {
            new RelativeWeekTimeStartDateGenerator("day_of_week_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "day_of_week" is malformed, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_DayOfWeekMalformed() {
        try {
            new RelativeWeekTimeStartDateGenerator("day_of_week_malformed");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "day_of_week" is less than 1, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_DayOfWeekTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator("day_of_week_too_small");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "day_of_week" is larger than 7, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_DayOfWeekTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator("day_of_week_too_large");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "hour" is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_HourMissing() {
        try {
            new RelativeWeekTimeStartDateGenerator("hour_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "hour" is malformed, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_HourMalformed() {
        try {
            new RelativeWeekTimeStartDateGenerator("hour_malformed");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "hour" is less than 0, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_HourTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator("hour_too_small");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "hour" is larger than 23, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_HourTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator("hour_too_large");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "minute" is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_MinuteMissing() {
        try {
            new RelativeWeekTimeStartDateGenerator("minute_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "minute" is malformed, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_MinuteMalformed() {
        try {
            new RelativeWeekTimeStartDateGenerator("minute_malformed");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "minute" is less than 0, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_MinuteTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator("minute_too_small");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "minute" is larger than 59, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_MinuteTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator("minute_too_large");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "second" is missing, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_SecondMissing() {
        try {
            new RelativeWeekTimeStartDateGenerator("second_missing");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "second" is malformed, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_SecondMalformed() {
        try {
            new RelativeWeekTimeStartDateGenerator("second_malformed");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "second" is less than 0, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_SecondTooSmall() {
        try {
            new RelativeWeekTimeStartDateGenerator("second_too_small");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test constructor <code>RelativeWeekTimeStartDateGenerator(String namespace)</code> for failure.
     * The "second" is larger than 59, <code>ConfigurationException</code> is expected.
     * </p>
     */
    public void testCtor2_SecondTooLarge() {
        try {
            new RelativeWeekTimeStartDateGenerator("second_too_large");
            fail("ConfigurationException expected.");
        } catch (ConfigurationException ex) {
            // expected
        }
    }

    /**
     * <p>
     * Test on method <code>generateStartDate()</code> for accuracy.
     * According to the configuration, 9:00 AM next Thursday should be generated.
     * </p>
     * @throws StartDateGenerationException to JUnit
     */
    public void testGenerateStartDate() throws StartDateGenerationException {
        Date startDate = generator1.generateStartDate();
        // initialize a Calendar with the start date
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        // verify the week
        //assertEquals("Incorrect week.", Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + 1, cal
                //.get(Calendar.WEEK_OF_YEAR)); toDo
        // verify day of week
        assertEquals("Incorrect day of week.", DAY_OF_WEEK, cal.get(Calendar.DAY_OF_WEEK));
        // verify hour
        assertEquals("Incorrect hour.", HOUR, cal.get(Calendar.HOUR_OF_DAY));
        // verify minute
        assertEquals("Incorrect minute.", MINUTE, cal.get(Calendar.MINUTE));
        // verify second
        assertEquals("Incorrect second.", SECOND, cal.get(Calendar.SECOND));
    }

    /**
     * <p>
     * Test on method <code>getHour()</code> for accuracy.
     * </p>
     */
    public void testGetHour() {
        assertEquals("Incorrect hour.", HOUR, generator1.getHour());
        assertEquals("Incorrect hour.", HOUR, generator2.getHour());
    }

    /**
     * <p>
     * Test on method <code>getMinute()</code> for accuracy.
     * </p>
     */
    public void testGetMinute() {
        assertEquals("Incorrect minute.", MINUTE, generator1.getMinute());
        assertEquals("Incorrect minute.", MINUTE, generator2.getMinute());
    }

    /**
     * <p>
     * Test on method <code>getSecond()</code> for accuracy.
     * </p>
     */
    public void testGetSecond() {
        assertEquals("Incorrect second.", SECOND, generator1.getSecond());
        assertEquals("Incorrect second.", SECOND, generator2.getSecond());
    }

    /**
     * <p>
     * Test on method <code>getWeekOffset()</code> for accuracy.
     * </p>
     */
    public void testGetWeekOffset() {
        assertEquals("Incorrect week offset.", WEEK_OFFSET, generator1.getWeekOffset());
        assertEquals("Incorrect week offset.", WEEK_OFFSET, generator2.getWeekOffset());
    }

    /**
     * <p>
     * Test on method <code>getDayOfWeek()</code> for accuracy.
     * </p>
     */
    public void testGetDayOfWeek() {
        assertEquals("Incorrect day of week.", DAY_OF_WEEK, generator1.getDayOfWeek());
        assertEquals("Incorrect day of week.", DAY_OF_WEEK, generator2.getDayOfWeek());
    }

}
