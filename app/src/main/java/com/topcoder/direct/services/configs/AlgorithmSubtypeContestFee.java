/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class presents algorithm sub type contest fee which contains contest fee and prize configuration for TopCoder.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "algorithmSubtype")
public class AlgorithmSubtypeContestFee {
    /**
     * The contest type id.
     */
    @XmlAttribute(name = "contestTypeId")
    private long id;

    /**
     * The algorithm contest fee.
     */
    @XmlElement
    private double contestFee;

    /**
     * The first place cost.
     */
    @XmlElement
    private double firstPlaceCost;

    /**
     * The second place cost.
     */
    @XmlElement
    private double secondPlaceCost;

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
}
