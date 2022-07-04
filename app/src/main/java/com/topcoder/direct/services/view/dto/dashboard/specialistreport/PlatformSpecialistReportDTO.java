/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.specialistreport;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * The DTO for platform specialist report to store the data of one platform specialist.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Platform Specialist Utilization Report and Graph)
 */
public class PlatformSpecialistReportDTO implements Serializable {

    /**
     * The user id of the platform specialist.
     */
    private long userId;

    /**
     * The user handle of the platform specialist.
     */
    private String userHandle;

    /**
     * The member spend under the management of each platform specialist for multiple months.
     */
    private Map<Long, Double> monthsMemberSpend = new TreeMap<Long, Double>();

    /**
     * Gets the user id.
     *
     * @return the user id.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the user id.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the user handle.
     *
     * @return the user handle.
     */
    public String getUserHandle() {
        return userHandle;
    }

    /**
     * Sets the user handle.
     *
     * @param userHandle the user handle.
     */
    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    /**
     * Gets the member spend under the management of each platform specialist for multiple months.
     *
     * @return the member spend under the management of each platform specialist for multiple months.
     */
    public Map<Long, Double> getMonthsMemberSpend() {
        return monthsMemberSpend;
    }
}
