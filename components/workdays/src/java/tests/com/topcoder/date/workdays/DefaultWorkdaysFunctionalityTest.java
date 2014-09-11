/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import junit.framework.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This class is used to check the behavior of method add(Date, WorkdaysUnitOfTime, int) of  the
 * <code>DefaultWorkdays</code> class, including accuracy and stress test.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultWorkdaysFunctionalityTest extends TestCase {
    /** The DefaultWorkdays instance to test. */
    private DefaultWorkdays workdays = null;

    /** The startDay initialized in setUp to test. */
    private Calendar startDay = Calendar.getInstance();

    /** The dateFormat to format date such as 2005:01:01 00:00:00. */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", new Locale("en", "US"));

    /**
     * Initialize the DefaultWorkdays instance with default configuration, and initialize the startDay as Jan 1, 2005.
     *
     */
    protected void setUp() {
        this.workdays = new DefaultWorkdays();
        this.startDay.set(2005, 0, 1, 0, 0, 0);
    }

    /**
     * Call add method with null startDay or null unitOfTime or negative amount or illegal state of time setting.
     */
    public void testAddIllegally() {
        // null startDay
        try {
            this.workdays.add(null, WorkdaysUnitOfTime.DAYS, 1);
            fail("Should throw NullPointerException");
        } catch (NullPointerException npe) {
        }

        // null unitOfTime
        try {
            this.workdays.add(this.startDay.getTime(), null, 1);
            fail("Should throw NullPointerException");
        } catch (NullPointerException npe) {
        }

        // negative amount
        try {
            this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, -1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
        }

        // illegal state of time setting
        try {
            this.workdays.setWorkdayStartTimeHours(20);
            this.workdays.setWorkdayEndTimeHours(19);
            this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException ise) {
        }

        // illegal start/end time
        try {
            this.workdays.setWorkdayEndTimeHours(24);
            this.workdays.setWorkdayEndTimeMinutes(1);
            this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 1);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException ise) {
        }
    }

    /**
     * Test Accuracy of add(Date, WorkdaysUnitOfTime, int).
     *
     * @throws ParseException if the date cannot be parsed, but this will never happen.
     */
    public void testAddAccuracy() throws ParseException {
        // 2005.01.01 is Saturday, so the result is
        assertEquals("Test add 0 day", "2005.01.01 00:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 0)));
        assertEquals("Test add one day", "2005.01.03 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 1)));
        assertEquals("Test add 7 day", "2005.01.11 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 7)));
        assertEquals("Test add 1 minute", "2005.01.03 09:01:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.MINUTES, 1)));

        // set the startDay as 2004.12.31 16:59:34, then add 1 minutes, the answer is 2004.12.31 17:00:00,
        // because the second field will be ignored.
        assertEquals("Test add 1 minute", "2004.12.31 17:00:00",
            this.dateFormat.format(this.workdays.add(this.dateFormat.parse("2004.12.31 16:59:34"),
                    WorkdaysUnitOfTime.MINUTES, 1)));

        assertEquals("Test add 1 minute", "2005.01.03 09:01:00",
            this.dateFormat.format(this.workdays.add(this.dateFormat.parse("2004.12.31 17:00:00"),
                    WorkdaysUnitOfTime.MINUTES, 1)));
        // set Saturday as workday
        // add 2005.01.04, 2005.01.12 as nonWorkdays
        this.workdays.setSaturdayWorkday(true);
        this.workdays.addNonWorkday(this.dateFormat.parse("2005.01.04 00:00:00"));
        this.workdays.addNonWorkday(this.dateFormat.parse("2005.01.12 00:00:00"));

        assertEquals("Test add 0 day", "2005.01.01 00:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 0)));
        assertEquals("Test add one day", "2005.01.01 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 1)));
        assertEquals("Test add 7 day", "2005.01.10 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 7)));
        assertEquals("Test add 15 day", "2005.01.20 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 15)));
        assertEquals("Test add 1 minute", "2005.01.01 09:01:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.MINUTES, 1)));
        assertEquals("Test add 50 hours", "2005.01.10 11:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.HOURS, 50)));

        // set Sunday as workday
        // add 2005.01.02, 2005.01.10 as nonWorkdays
        this.workdays.setSundayWorkday(true);
        this.workdays.addNonWorkday(this.dateFormat.parse("2005.01.02 00:00:00"));
        this.workdays.addNonWorkday(this.dateFormat.parse("2005.01.10 00:00:00"));

        assertEquals("Test add 0 day", "2005.01.01 00:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 0)));

        assertEquals("Test add one day", "2005.01.01 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 1)));
        assertEquals("Test add 7 day", "2005.01.09 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 7)));
        assertEquals("Test add 15 day", "2005.01.19 17:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.DAYS, 15)));
        assertEquals("Test add 1 minute", "2005.01.01 09:01:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.MINUTES, 1)));
        assertEquals("Test add 50 hours", "2005.01.09 11:00:00",
            this.dateFormat.format(this.workdays.add(this.startDay.getTime(), WorkdaysUnitOfTime.HOURS, 50)));
    }

    /**
     * Add 100000 nonWorkdays to DefaultWorkdays instance, see the performance of add method. As the test result, we
     * can see the algorithm to implement add(Date, WorkdaysUnitOfTime, int) is stable. The complexity of the
     * algorithm is O(log2(N) * log2(M)), where N is the parameter amout in add method,  M is the non-workdays count.
     * But in this worst situation, the complexity of designer's algorithm is O( M/N * log2(M)).
     */
    public void testAddStress() {
        this.workdays.setSaturdayWorkday(true);
        this.workdays.setSundayWorkday(true);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, this.workdays.getWorkdayStartTimeHours());
        cal.set(Calendar.MINUTE, this.workdays.getWorkdayStartTimeMinutes());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date startDate = cal.getTime();

        // this is just to calculate the run time
        Date initTime = new Date();

        // add 100000 non-workdays
        for (int i = 0; i < 100000; i++) {
            if (i == 50000) {
                cal.add(Calendar.DATE, 1);

                // this is a workday
                continue;
            }

            this.workdays.addNonWorkday(((Calendar) cal.clone()).getTime());
            cal.add(Calendar.DATE, 1);
        }

        // this is just to calculate the run time
        Date startTime = new Date();
        Date answer = this.workdays.add(startDate, WorkdaysUnitOfTime.DAYS, 2);
        // this is just to calculate the run time
        Date endTime = new Date();

        cal.set(Calendar.HOUR_OF_DAY, this.workdays.getWorkdayEndTimeHours());
        assertEquals("Stress test", this.dateFormat.format(cal.getTime()), this.dateFormat.format(answer));

        // time to initialize the nonWorkdays
        System.out.println("time to initialize the nonWorkdays: " + (startTime.getTime() - initTime.getTime()) + "ms");

        // time to calculate the answer
        System.out.println("time to calculate the answer: " + (endTime.getTime() - startTime.getTime()) + "ms");
    }
}
