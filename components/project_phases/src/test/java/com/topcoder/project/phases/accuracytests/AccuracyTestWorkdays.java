/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;

import java.util.Date;
import java.util.Set;

/**
 * An implementation of <code>Workdays</code> used for accuracy tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class AccuracyTestWorkdays implements Workdays {

    /**
     * <p>Adds a non-workday to the list of non-work days.</p>
     *
     * @param nonWorkday the date to add as a non work day.
     *
     * @throws NullPointerException if nonWorkDay is null.
     */
    public void addNonWorkday(Date nonWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Removes a non-workday from the list of non-work days.</p>
     *
     * @param nonWorkday the date to remove from the list
     *
     * @throws NullPointerException is thrown if nonWorkDay is null
     * @throws IllegalArgumentException is thrown if nonWorkDay does not exist
     */
    public void removeNonWorkday(Date nonWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Returns a Set of non-workdays.</p>
     *
     * @return a Set of non-workdays.
     */
    public Set getNonWorkdays() {
        // NO IMPLEMENTATION
        return null;
    }

    /**
     * <p>Clears the non-workdays.</p>
     */
    public void clearNonWorkdays() {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Sets whether or not Saturday is to be considered a work day.</p>
     *
     * @param isSaturdayWorkday <code>true</code> if Saturday is to be considered a workday.
     */
    public void setSaturdayWorkday(boolean isSaturdayWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Returns whether or not Saturday is considered a workday.</p>
     *
     * @return <code>true</code> if Saturday is considered a workday.
     */
    public boolean isSaturdayWorkday() {
        // NO IMPLEMENTATION
        return false;
    }

    /**
     * <p>Sets whether or not Sunday is to be considered a work day.</p>
     *
     * @param isSundayWorkday <code>true</code> if Sunday is to be considered a workday.
     */
    public void setSundayWorkday(boolean isSundayWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Returns whether or not Sunday is considered a workday.</p>
     *
     * @return <code>true</code> if Sunday is to be considered a workday.
     */
    public boolean isSundayWorkday() {
        // NO IMPLEMENTATION
        return false;
    }

    /**
     * <p>Sets the hours of the workday start time.&nbsp; This is to be in 24 hour mode.</p>
     *
     * @param startTimeHours the hours of the workday start time (24 hour mode).
     *
     * @throws IllegalArgumentException if startTimeHours is not a valid hour.
     */
    public void setWorkdayStartTimeHours(int startTimeHours) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Returns the hours of the workday start time, in 24 hour mode.</p>
     *
     * @return the hours of the workday start time.
     */
    public int getWorkdayStartTimeHours() {
        // NO IMPLEMENTATION
        return 0;
    }

    /**
     * <p>Sets the minutes of the workday start time.</p>
     *
     * @param startTimeMinutes the minutes of the workday start time.
     *
     * @throws IllegalArgumentException if startTimeMinutes is not a valid minute.
     */
    public void setWorkdayStartTimeMinutes(int startTimeMinutes) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Returns the minutes of the workday start time.</p>
     *
     * @return the minutes of the workday start time.
     */
    public int getWorkdayStartTimeMinutes() {
        // NO IMPLEMENTATION
        return 0;
    }

    /**
     * <p>Sets the hours of the workday end time.&nbsp; This is to be in 24 hour mode.</p>
     *
     * @param endTimeHours the hours of the workday end time (24 hour mode).
     *
     * @throws IllegalArgumentException if endTimeHours is not a valid hour.
     */
    public void setWorkdayEndTimeHours(int endTimeHours) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Returns the hours of the workday end time, in 24 hour mode.</p>
     *
     * @return the hours of the workday end time.
     */
    public int getWorkdayEndTimeHours() {
        // NO IMPLEMENTATION
        return 0;
    }

    /**
     * <p>Sets the minutes of the workday end time.</p>
     *
     * @param endTimeMinutes the minutes of the workday end time.
     *
     * @throws IllegalArgumentException if endTimeMinutes is not a valid minute.
     */
    public void setWorkdayEndTimeMinutes(int endTimeMinutes) {
        // NO IMPLEMENTATION
    }

    /**
     * <p>Returns the minutes of the workday end time.</p>
     *
     * @return the minutes of the workday end time.
     */
    public int getWorkdayEndTimeMinutes() {
        // NO IMPLEMENTATION
        return 0;
    }

    /**
     * Just return startDate + amount(hours).
     *
     * @param startDate the start date
     * @param unitOfTime unit of time
     * @param amount the amount value
     * @return startDate + amount(hours)
     */
    public Date add(Date startDate, WorkdaysUnitOfTime unitOfTime, int amount) {
        // Argument checking is not performed.
        if (unitOfTime == WorkdaysUnitOfTime.MINUTES) {
            return new Date(startDate.getTime() + amount * 60000);
        }

        if (unitOfTime == WorkdaysUnitOfTime.HOURS) {
            return new Date(startDate.getTime() + amount * 60 * 60000);
        }

        // WorkdaysUnitOfTime.DAYS
        return new Date(startDate.getTime() + amount * 24 * 60 * 60000);
    }

}
