/*
 * Copyright (c) 2004, TopCoder, Inc. All rights reserved
 */
package com.topcoder.date.workdays;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * <p>
 * The Workdays Interface provides a set of generic functions to define a workday schedule (set holidays and other
 * non-work days, set whether or not weekend days are to be included as a normal workday, set the start and end hours
 * and minutes of a work day: for example, work day starts at 8:00AM and ends at 5:30PM) and a function to add days,
 * hours or minutes to an existing date and return the date that specifies how many work days it would take to
 * complete).
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@XmlSeeAlso({DefaultWorkdays.class})
public interface Workdays extends Serializable {

    /**
     * <p>
     * Adds a non-workday to the list of non-work days.
     * </p>
     *
     * @param nonWorkday the date to add as a non work day
     */
    void addNonWorkday(Date nonWorkday);

    /**
     * <p>
     * Removes a non-workday from the list of non-work days.
     * </p>
     *
     * @param nonWorkday the date to remove from the list
     */
    void removeNonWorkday(Date nonWorkday);

    /**
     * <p>
     * Returns a Set of non-workdays.
     * </p>
     *
     * @return a Set of non-workdays.
     */
    Set getNonWorkdays();

    /**
     * <p>
     * Clears the non-workdays.
     * </p>
     */
    void clearNonWorkdays();

    /**
     * <p>
     * Sets whether or not Saturday is to be considered a work day.
     * </p>
     *
     * @param isSaturdayWorkday <code>true</code> if Saturday is to be considered a workday
     */
    void setSaturdayWorkday(boolean isSaturdayWorkday);

    /**
     * <p>
     * Returns whether or not Saturday is considered a workday.
     * </p>
     *
     * @return <code>true</code> if Saturday is considered a workday.
     */
    boolean isSaturdayWorkday();

    /**
     * <p>
     * Sets whether or not Sunday is to be considered a work day.
     * </p>
     *
     * @param isSundayWorkday <code>true</code> if Sunday is to be considered a workday
     */
    void setSundayWorkday(boolean isSundayWorkday);

    /**
     * <p>
     * Returns whether or not Sunday is considered a workday.
     * </p>
     *
     * @return <code>true</code> if Sunday is to be considered a workday
     */
    boolean isSundayWorkday();

    /**
     * <p>
     * Sets the hours of the workday start time.&nbsp; This is to be in 24 hour mode.
     * </p>
     *
     * @param startTimeHours the hours of the workday start time (24 hour mode).
     */
    void setWorkdayStartTimeHours(int startTimeHours);

    /**
     * <p>
     * Returns the hours of the workday start time, in 24 hour mode.
     * </p>
     *
     * @return the hours of the workday start time
     */
    int getWorkdayStartTimeHours();

    /**
     * <p>
     * Sets the minutes of the workday start time.
     * </p>
     *
     * @param startTimeMinutes the minutes of the workday start time
     */
    void setWorkdayStartTimeMinutes(int startTimeMinutes);

    /**
     * <p>
     * Returns the minutes of the workday start time.
     * </p>
     *
     * @return the minutes of the workday start time.
     */
    int getWorkdayStartTimeMinutes();

    /**
     * <p>
     * Sets the hours of the workday end time.&nbsp; This is to be in 24 hour mode.
     * </p>
     *
     * @param endTimeHours the hours of the workday end time (24 hour mode).
     */
    void setWorkdayEndTimeHours(int endTimeHours);

    /**
     * <p>
     * Returns the hours of the workday end time, in 24 hour mode.
     * </p>
     *
     * @return the hours of the workday end time
     */
    int getWorkdayEndTimeHours();

    /**
     * <p>
     * Sets the minutes of the workday end time.
     * </p>
     *
     * @param endTimeMinutes the minutes of the workday end time
     */
    void setWorkdayEndTimeMinutes(int endTimeMinutes);

    /**
     * <p>
     * Returns the minutes of the workday end time.
     * </p>
     *
     * @return the minutes of the workday end time.
     */
    int getWorkdayEndTimeMinutes();

    /**
     * <p>
     * Method to add a certain amount of time to a Date to calculate the number of work days that it would take to
     * complete.
     * </p>
     *
     * @param startDate the date to perform the addition to
     * @param unitOfTime the unit of time to add (minutes, hours, days)
     * @param amount the amount of time to add
     *
     * @return the Date result of adding the amount of time to the startDate taking into consideration the workdays
     *         definition.
     */
    Date add(Date startDate, WorkdaysUnitOfTime unitOfTime, int amount);
}
