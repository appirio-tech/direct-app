/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import junit.framework.TestCase;


/**
 * This class is used to check the behavior of the <code>WorkdaysUnitOfTime</code> class, including tests of MINUTES,
 * HOURS and DAYS.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WorkdaysUnitOfTimeTest extends TestCase {
    /**
     * Test name and value of MINUTES instance.
     */
    public void testMinutes() {
        WorkdaysUnitOfTime minutes = WorkdaysUnitOfTime.MINUTES;
        assertEquals("Test of MINUTES", "MINUTES", minutes.toString());
        assertEquals("Test of MINUTES", 0, minutes.getValue());
    }

    /**
     * Test name and value of HOURS instance.
     */
    public void testHours() {
        WorkdaysUnitOfTime hours = WorkdaysUnitOfTime.HOURS;
        assertEquals("Test of HOURS", "HOURS", hours.toString());
        assertEquals("Test of HOURS", 1, hours.getValue());
    }

    /**
     * Test name and value of DAYS instance.
     */
    public void testDays() {
        WorkdaysUnitOfTime days = WorkdaysUnitOfTime.DAYS;
        assertEquals("Test of DAYS", "DAYS", days.toString());
        assertEquals("Test of DAY", 2, days.getValue());
    }
}
