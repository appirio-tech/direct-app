/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The studio subtype contest fee class for xml handling.
 * </p>
 *
 * <p>
 * Version 1.1 - (TC Direct Replatforming Release 2) Change notes:
 *   <ol>
 *     <li>Added {@link #specReviewCost} field to store the specification cost of the studio contest.
 *     Also the getter/setter were added.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subType")
public class StudioSubtypeContestFee {
    /**
     * Represents the contest type id.
     */
    @XmlAttribute(name="contestTypeId")
    private long id;

    /**
     * Represents the contest fee.
     */
    @XmlElement
    private double contestFee;

    /**
     * Represents the first place cost.
     */
    @XmlElement
    private double firstPlaceCost;

    /**
     * Represents the second place cost.
     */
    @XmlElement
    private double secondPlaceCost;

    /**
     * Represents the specification review cost.
     *
     * @since 1.1
     */
    @XmlElement
    private double specReviewCost;

    /**
     * Gets the contest type id.
     *
     * @return the contest type id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the contest type id.
     *
     * @param id the contest type id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the contest fee.
     *
     * @return the contest fee.
     */
    public double getContestFee() {
        return contestFee;
    }

    /**
     * Sets the contest fee.
     *
     * @param contestFee the contest fee.
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * Gets the first place cost.
     *
     * @return the first place cost.
     */
    public double getFirstPlaceCost() {
        return firstPlaceCost;
    }

    /**
     * Sets the first place cost.
     *
     * @param firstPlaceCost the first place cost.
     */
    public void setFirstPlaceCost(double firstPlaceCost) {
        this.firstPlaceCost = firstPlaceCost;
    }

    /**
     * Gets the second place cost.
     *
     * @return the second place cost.
     */
    public double getSecondPlaceCost() {
        return secondPlaceCost;
    }

    /**
     * Sets the second place cost.
     *
     * @param secondPlaceCost the second place cost.
     */
    public void setSecondPlaceCost(double secondPlaceCost) {
        this.secondPlaceCost = secondPlaceCost;
    }

    /**
     * Gets the specification review cost.
     *
     * @return the specification review cost.
     * @since 1.1
     */
    public double getSpecReviewCost() {
        return specReviewCost;
    }

    /**
     * Sets the specification review cost.
     *
     * @param specReviewCost the specification review cost.
     * @since 1.1
     */
    public void setSpecReviewCost(double specReviewCost) {
        this.specReviewCost = specReviewCost;
    }
}
