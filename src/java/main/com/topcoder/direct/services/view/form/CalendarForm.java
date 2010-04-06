/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;
import java.util.Calendar;

/**
 * <p>A form bean providing the parameters for viewing calendar events.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CalendarForm implements Serializable {

    /**
     * <p>A <code>int</code> providing the month for viewing calendar events.</p>
     */
    private int month;

    /**
     * <p>A <code>int</code> providing the year for viewing the calendar events.</p>
     */
    private int year;

    /**
     * <p>Constructs new <code>CalendarForm</code> instance. This implementation does nothing.</p>
     */
    public CalendarForm() {
        Calendar now = Calendar.getInstance();
        this.month = now.get(Calendar.MONTH) + 1;
        this.year = now.get(Calendar.YEAR);
    }

    /**
     * <p>Gets the year for viewing the calendar events.</p>
     *
     * @return a <code>int</code> providing the year for viewing the calendar events.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * <p>Sets the year for viewing the calendar events.</p>
     *
     * @param year a <code>int</code> providing the year for viewing the calendar events.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * <p>Gets the month for viewing calendar events.</p>
     *
     * @return a <code>int</code> providing the month for viewing calendar events.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * <p>Sets the month for viewing calendar events.</p>
     *
     * @param month a <code>int</code> providing the month for viewing calendar events.
     */
    public void setMonth(int month) {
        this.month = month;
    }
}
