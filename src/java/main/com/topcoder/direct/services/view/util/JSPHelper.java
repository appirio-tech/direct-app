/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>This class provides the set of static functions for use by JSP views.</p>
 *
 * @author isv
 * @version 1.0
 */
public class JSPHelper {

    /**
     * <p>Constructs new <code>JSPHelper</code> instance. This implementation does nothing.</p>
     */
    private JSPHelper() {
    }

    /**
     * <p>Gets the day from the specified date.</p>
     *
     * @param date a <code>Date</code> providing the date.
     * @return an <code>int</code> providing the day from the specified value.
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * <p>Gets the month from the specified date.</p>
     *
     * @param date a <code>Date</code> providing the date.
     * @return an <code>int</code> providing the month from the specified value (0-based).
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * <p>Gets the year from the specified date.</p>
     *
     * @param date a <code>Date</code> providing the date.
     * @return an <code>int</code> providing the year from the specified value.
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * <p>Gets the textual presentation of specified date.</p>
     *
     * @param date a <code>Date</code> providing the date to be verified.
     * @param pattern a <code>String</code> providing the template for date formatting.
     * @return a <code>String</code> providing the date text.
     */
    public static String getDateText(Date date, String pattern) {
        Date now = new Date();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        if (isSameDay(now, date)) {
            return "Today";
        } else if (isSameDay(date, yesterday.getTime())) {
            return "Yesterday";
        } else if (isSameDay(date, tomorrow.getTime())) {
            return "Tomorrow";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }

    }

    /**
     * <p>Gets the textual description of the period in which the specified date (contest completion) will be reached.
     * </p>
     *
     * @param date a <code>Date</code> providing the date of contest completion.
     * @return a <code>String</code> providing the textual description of the period in which the specified date will
     *         be reached.
     */
    public static String getEndText(Date date) {
        if (date == null) {
            return "";
        } else {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                return "Finished";
            } else {
                long diff = date.getTime() - now.getTime();
                long seconds = diff / 1000L;
                long minutes = seconds / 60L;
                long hours = minutes / 60L;
                long days = hours / 24L;
                if (days > 0) {
                    return "Ends in " + days + " days";
                } else if (hours > 0) {
                    return "Ends in " + hours + " hours";
                } else if (minutes > 0) {
                    return "Ends in " + minutes + " minutes";
                } else {
                    return "Ends in " + seconds + " seconds";
                }
            }
        }
    }

    /**
     * <p>Checks if specified dates represent same day.</p>
     *
     * @param d1 a first date.
     * @param d2 a second date.
     * @return <code>true</code> if dates represent same day; <code>false</code> otherwise.
     */
    private static boolean isSameDay(Date d1, Date d2) {
        return (getDay(d1) == getDay(d2)) & (getMonth(d1) == getMonth(d2)) && (getYear(d1) == getYear(d2));
    }
}
