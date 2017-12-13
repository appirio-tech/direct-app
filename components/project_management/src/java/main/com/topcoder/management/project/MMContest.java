/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This is a MMContest entity which represents the Marathon Match Contest entry.
 * </p>
 *
 * @author TCSCODER
 * @version 1.0
 */
public class MMContest implements Serializable {
    /**
     * Contest Id
     */
    private long id = -1L;

    /**
     * Contest name
     */
    private String name;

    /**
     * Contest start_date
     */
    private Date startDate;

    /**
     * Contest end_date
     */
    private Date endDate;

    /**
     * Contest registration end date
     */
    private Date regEndDate;

    /**
     * Default ctor
     */
    public MMContest() {

    }

    /**
     * Getter contest_id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for contest id
     * @param id contest id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter contest name
     * @return contest name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter contest name
     * @param name contest name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter contest start_date
     * @return contest start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter contest start_Date
     * @param startDate contest start_Date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter contest end_date
     * @return contest end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter contest end date
     * @param endDate contest end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter contest registration end_date
     * @return contest reg end_date
     */
    public Date getRegEndDate() {
        return regEndDate;
    }

    /**
     * Setter contest registration end_date
     * @param regEndDate contest reg end_date
     */
    public void setRegEndDate(Date regEndDate) {
        this.regEndDate = regEndDate;
    }
}
