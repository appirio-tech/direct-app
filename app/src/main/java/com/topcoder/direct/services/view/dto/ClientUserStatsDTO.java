/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>
 * This class is the DTO of Client User Stats.
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * </p>
 * 
 * @author leo_lol
 * @version 1.0
 * @since 1.0
 */
public class ClientUserStatsDTO implements Comparable<ClientUserStatsDTO> {

    /**
     * ID of the client.
     */
    private long clientId;

    /**
     * Name of the client.
     */
    private String clientName;

    /**
     * Year-month pair.
     */
    private String yearMonth;

    /**
     * User count Number.
     */
    private int userCount;

    /**
     * <p>
     * Getter of clientId field.
     * </p>
     * 
     * @return the clientId
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * <p>
     * Setter of clientId field.
     * </p>
     * 
     * @param clientId
     *            the clientId to set
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * <p>
     * Getter of clientName field.
     * </p>
     * 
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * <p>
     * Setter of clientName field.
     * </p>
     * 
     * @param clientName
     *            the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * <p>
     * Getter of userCount field.
     * </p>
     * 
     * @return the userCount
     */
    public int getUserCount() {
        return userCount;
    }

    /**
     * <p>
     * Setter of userCount field.
     * </p>
     * 
     * @param userCount
     *            the userCount to set
     */
    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    /**
     * <p>
     * Getter of yearMonth field.
     * </p>
     * 
     * @return the yearMonth
     */
    public String getYearMonth() {
        return yearMonth;
    }

    /**
     * <p>
     * Setter of yearMonth field.
     * </p>
     * 
     * @param yearMonth
     *            the yearMonth to set
     */
    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    /**
     * <p>
     * On default, sort by the year/month column.
     * </p>
     */
    public int compareTo(ClientUserStatsDTO o) {
        return yearMonth.compareTo(o.getYearMonth());
    }
}
