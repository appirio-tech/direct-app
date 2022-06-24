/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The copilot fee class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since TC Direct - Software Contest Creation Update Assembly
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestType")
public class CopilotFee {

    /**
     * <p>
     * Contest type id.
     * </p>
     */
    @XmlAttribute(required = true)
    private long contestTypeId;

    /**
     * <p>
     * The copilotFee field.
     * </p>
     */
    private double copilotFee;


    /**
     * <p>
     * Gets the contest type id.
     * </p>
     *
     * @return the contest type id
     */
    public long getContestTypeId() {
        return contestTypeId;
    }

    /**
     * <p>
     * Set the contest type id.
     * </p>
     *
     * @param contestTypeId the contest type id
     */
    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    /**
     * <p>
     * Sets the <code>copilotFee</code> field value.
     * </p>
     *
     * @param copilotFee the copilotFee value to set
     */
    public void setCopilotFee(double copilotFee) {
        this.copilotFee = copilotFee;
    }

    /**
     * <p>
     * Gets the <code>copilotFee</code> field value.
     * </p>
     *
     * @return the <code>copilotFee</code> field value
     */
    public double getCopilotFee() {
        return this.copilotFee;
    }

}
