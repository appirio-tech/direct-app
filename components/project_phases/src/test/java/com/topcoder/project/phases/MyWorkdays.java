/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;

import java.util.Date;
import java.util.Set;

/**
 * <p>
 * The <code>Workdays</code> interface provides a set of generic functions to define a workday schedule (set holidays
 * and other non-work days, set whether or not weekend days are to be included as a normal workday, set the start and
 * end hours and minutes of a work day: for example, work day starts at 8:00AM and ends at 5:30PM) and a function to add
 * days, hours or minutes to an existing date and return the date that specifies how many work days it would take to
 * complete).
 * </p>
 * <p>
 * This is a mock implementation that is only targeted at unit testing the Project Phases component. Only the add(Date,
 * WorkdaysUnitOfTime, amount) method is implemented since only this method is used in the Project Phases component. The
 * mock implementation simply adds up the time, assumes that we work 24 hours a day 7 days a week. It is quite harmless
 * since we simply want to test against the proper startDate and length results in respective eneDate. The other methods
 * are practically left empty intentionally.
 * </p>
 *
 * @author littlebull
 * @version 2.0
 */
class MyWorkdays implements Workdays {
    /**
     * <p>
     * Method to add a certain amount of time to a Date to calculate the number of work days that it would take to
     * complete.
     * </p>
     * <p>
     * Only this method is implemented since only this method is used in the Project Phases component. The mock
     * implementation simply adds up the time, assumes that we work 24 hours a day 7 days a weed. It is quite harmless
     * since we simply want to test against the proper startDate and length results in respective
     * </p>
     *
     * @param startDate
     *            the date to perform the addition to.
     * @param unitOfTime
     *            the unit of time to add (minutes, hours, days).
     * @param amount
     *            the amount of time to add.
     * @return the Date result of adding the amount of time to the startDate taking into consideration the workdays
     *         definition.
     * @throws NullPointerException
     *             if startDate or unitOfTime is null.
     * @throws IllegalArgumentException
     *             if amount parameter is negative.
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

    /**
     * Adds a non-workday to the list of non-work days.
     *
     * @param nonWorkday
     *            the date to add as a non work day.
     * @throws NullPointerException
     *             if nonWorkDay is null.
     */
    public void addNonWorkday(Date nonWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * Removes a non-workday from the list of non-work days.
     *
     * @param nonWorkday
     *            the date to remove from the list
     * @throws NullPointerException
     *             is thrown if nonWorkDay is null
     * @throws IllegalArgumentException
     *             is thrown if nonWorkDay does not exist
     */
    public void removeNonWorkday(Date nonWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * Returns a Set of non-workdays.
     *
     * @return a Set of non-workdays.
     */
    public Set getNonWorkdays() {
        // NO IMPLEMENTATION
        return null;
    }

    /**
     * Clears the non-workdays.
     */
    public void clearNonWorkdays() {
        // NO IMPLEMENTATION
    }

    /**
     * Sets whether or not Saturday is to be considered a work day.
     *
     * @param isSaturdayWorkday
     *            <code>true</code> if Saturday is to be considered a workday.
     */
    public void setSaturdayWorkday(boolean isSaturdayWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * Returns whether or not Saturday is considered a workday.
     *
     * @return <code>true</code> if Saturday is considered a workday.
     */
    public boolean isSaturdayWorkday() {
        // NO IMPLEMENTATION
        return false;
    }

    /**
     * Sets whether or not Sunday is to be considered a work day.
     *
     * @param isSundayWorkday
     *            <code>true</code> if Sunday is to be considered a workday.
     */
    public void setSundayWorkday(boolean isSundayWorkday) {
        // NO IMPLEMENTATION
    }

    /**
     * Returns whether or not Sunday is considered a workday.
     *
     * @return <code>true</code> if Sunday is to be considered a workday.
     */
    public boolean isSundayWorkday() {
        // NO IMPLEMENTATION
        return false;
    }

    /**
     * Sets the hours of the workday start time. This is to be in 24 hour mode.
     *
     * @param startTimeHours
     *            the hours of the workday start time (24 hour mode).
     * @throws IllegalArgumentException
     *             if startTimeHours is not a valid hour.
     */
    public void setWorkdayStartTimeHours(int startTimeHours) {
        // NO IMPLEMENTATION
    }

    /**
     * Returns the hours of the workday start time, in 24 hour mode.
     *
     * @return the hours of the workday start time.
     */
    public int getWorkdayStartTimeHours() {
        // NO IMPLEMENTATION
        return 0;
    }

    /**
     * Sets the minutes of the workday start time.
     *
     * @param startTimeMinutes
     *            the minutes of the workday start time.
     * @throws IllegalArgumentException
     *             if startTimeMinutes is not a valid minute.
     */
    public void setWorkdayStartTimeMinutes(int startTimeMinutes) {
        // NO IMPLEMENTATION
    }

    /**
     * Returns the minutes of the workday start time.
     *
     * @return the minutes of the workday start time.
     */
    public int getWorkdayStartTimeMinutes() {
        // NO IMPLEMENTATION
        return 0;
    }

    /**
     * Sets the hours of the workday end time. This is to be in 24 hour mode.
     *
     * @param endTimeHours
     *            the hours of the workday end time (24 hour mode).
     * @throws IllegalArgumentException
     *             if endTimeHours is not a valid hour.
     */
    public void setWorkdayEndTimeHours(int endTimeHours) {
        // NO IMPLEMENTATION
    }

    /**
     * Returns the hours of the workday end time, in 24 hour mode.
     *
     * @return the hours of the workday end time.
     */
    public int getWorkdayEndTimeHours() {
        // NO IMPLEMENTATION
        return 0;
    }

    /**
     * Sets the minutes of the workday end time.
     *
     * @param endTimeMinutes
     *            the minutes of the workday end time.
     * @throws IllegalArgumentException
     *             if endTimeMinutes is not a valid minute.
     */
    public void setWorkdayEndTimeMinutes(int endTimeMinutes) {
        // NO IMPLEMENTATION
    }

    /**
     * Returns the minutes of the workday end time.
     *
     * @return the minutes of the workday end time.
     */
    public int getWorkdayEndTimeMinutes() {
        // NO IMPLEMENTATION
        return 0;
    }
}
