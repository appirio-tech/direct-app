/**
 * Copyright (c) 2004, TopCoder Inc. All rights reserved.
 */
package com.topcoder.project.phases.failuretests;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;

import java.util.Date;
import java.util.Set;

/**
 * A mock implementation of WorkDays interface.
 *
 * @author oldbig
 * @version 1.0
 */
class FailureTestWorkdays implements Workdays {

    /**
     * Adds a non-workday to the list of non-work days.
     * @param nonWorkday the date to add as a non work day.
     */
    public void addNonWorkday(Date nonWorkday) {
    }

    /**
     * Removes a non-workday from the list of non-work days.
     * @param nonWorkday the date to remove from the list
     */
    public void removeNonWorkday(Date nonWorkday) {
    }

    /**
     * Returns a Set of non-workdays.
     * @return a Set of non-workdays.
     */
    public Set getNonWorkdays() {
        return null;
    }

    /**
     * Clears the non-workdays.
     */
    public void clearNonWorkdays() {
    }

    /**
     * Sets whether or not Saturday is to be considered a work day.
     * @param isSaturdayWorkday <code>true</code> if Saturday is to be considered a workday.
     */
    public void setSaturdayWorkday(boolean isSaturdayWorkday) {
    }

    /**
     * Returns whether or not Saturday is considered a workday.
     * @return <code>true</code> if Saturday is considered a workday.
     */
    public boolean isSaturdayWorkday() {
        return false;
    }

    /**
     * Sets whether or not Sunday is to be considered a work day.
     * @param isSundayWorkday <code>true</code> if Sunday is to be considered a workday.
     */
    public void setSundayWorkday(boolean isSundayWorkday) {
    }

    /**
     * Returns whether or not Sunday is considered a workday.
     * @return <code>true</code> if Sunday is to be considered a workday.
     */
    public boolean isSundayWorkday() {
        return false;
    }

    /**
     * Sets the hours of the workday start time.&nbsp; This is to be in 24 hour mode.
     * @param startTimeHours the hours of the workday start time (24 hour mode).
     */
    public void setWorkdayStartTimeHours(int startTimeHours) {
    }

    /**
     * Returns the hours of the workday start time, in 24 hour mode.
     * @return the hours of the workday start time.
     */
    public int getWorkdayStartTimeHours() {
        return 0;
    }

    /**
     * Sets the minutes of the workday start time.
     * @param startTimeMinutes the minutes of the workday start time.
     */
    public void setWorkdayStartTimeMinutes(int startTimeMinutes) {
    }

    /**
     * Returns the minutes of the workday start time.
     * @return the minutes of the workday start time.
     */
    public int getWorkdayStartTimeMinutes() {
        return 0;
    }

    /**
     * Sets the hours of the workday end time.&nbsp; This is to be in 24 hour mode.
     * @param endTimeHours the hours of the workday end time (24 hour mode).
     */
    public void setWorkdayEndTimeHours(int endTimeHours) {
    }

    /**
     * Returns the hours of the workday end time, in 24 hour mode.
     * @return the hours of the workday end time.
     */
    public int getWorkdayEndTimeHours() {
        return 0;
    }

    /**
     * Sets the minutes of the workday end time.
     * @param endTimeMinutes the minutes of the workday end time.
     */
    public void setWorkdayEndTimeMinutes(int endTimeMinutes) {
    }

    /**
     * Returns the minutes of the workday end time.
     * @return the minutes of the workday end time.
     */
    public int getWorkdayEndTimeMinutes() {
        return 0;
    }

    /**
     * Method to add a certain amount of time to a Date to calculate the number of work days that it would take
     * to complete.
     *
     * Only this method is implemented since only this method is used in the Project Phases component. The mock
     * implemetation simply adds up the time, assumes that we work 24 hours a day 7 days a weed. It is quite
     * harmless since we simply want to test against the proper startDate and length results in respective
     *
     * @param startDate the date to perform the addition to.
     * @param unitOfTime the unit of time to add (minutes, hours, days).
     * @param amount the amount of time to add.
     *
     * @return the Date result of adding the amount of time to the startDate taking into consideration the
     *         workdays definition.
     */
    public Date add(Date startDate, WorkdaysUnitOfTime unitOfTime, int amount) {
        return new Date(startDate.getTime() + amount);
    }

}
