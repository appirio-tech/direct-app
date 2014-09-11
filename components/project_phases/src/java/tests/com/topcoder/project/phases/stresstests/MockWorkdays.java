/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.stresstests;

import java.util.Date;
import java.util.Set;

import com.topcoder.date.workdays.Workdays;
import com.topcoder.date.workdays.WorkdaysUnitOfTime;

/**
 * Dummy Workdays implementation.
 *
 * @author King_Bette
 * @version 2.0
 *
 */
public class MockWorkdays implements Workdays {


    /**
     * @see com.topcoder.date.workdays.Workdays#addNonWorkday(java.util.Date)
     */
    public void addNonWorkday(Date nonWorkday) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#removeNonWorkday(java.util.Date)
     */
    public void removeNonWorkday(Date nonWorkday) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#getNonWorkdays()
     */
    public Set getNonWorkdays() {
        return null;
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#clearNonWorkdays()
     */
    public void clearNonWorkdays() {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#setSaturdayWorkday(boolean)
     */
    public void setSaturdayWorkday(boolean isSaturdayWorkday) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#isSaturdayWorkday()
     */
    public boolean isSaturdayWorkday() {
        return false;
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#setSundayWorkday(boolean)
     */
    public void setSundayWorkday(boolean isSundayWorkday) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#isSundayWorkday()
     */
    public boolean isSundayWorkday() {
        return false;
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#setWorkdayStartTimeHours(int)
     */
    public void setWorkdayStartTimeHours(int startTimeHours) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#getWorkdayStartTimeHours()
     */
    public int getWorkdayStartTimeHours() {
        return 0;
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#setWorkdayStartTimeMinutes(int)
     */
    public void setWorkdayStartTimeMinutes(int startTimeMinutes) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#getWorkdayStartTimeMinutes()
     */
    public int getWorkdayStartTimeMinutes() {
        return 0;
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#setWorkdayEndTimeHours(int)
     */
    public void setWorkdayEndTimeHours(int endTimeHours) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#getWorkdayEndTimeHours()
     */
    public int getWorkdayEndTimeHours() {
        return 0;
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#setWorkdayEndTimeMinutes(int)
     */
    public void setWorkdayEndTimeMinutes(int endTimeMinutes) {
    }

    /**
     * @see com.topcoder.date.workdays.Workdays#getWorkdayEndTimeMinutes()
     */
    public int getWorkdayEndTimeMinutes() {
        return 0;
    }

    /**
     *  Returns new date with increased by amount of hours.
     */
    public Date add(Date startDate, WorkdaysUnitOfTime unitOfTime, int amount) {
        return new Date(startDate.getTime() + amount * 60 * 1000);
    }
}
