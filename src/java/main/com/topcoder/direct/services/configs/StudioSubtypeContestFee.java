/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
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
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subType")
public class StudioSubtypeContestFee {
    @XmlAttribute(name="contestTypeId")
    private long id;

    @XmlElement
    private double contestFee;

    @XmlElement
    private double firstPlaceCost;

    @XmlElement
    private double secondPlaceCost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getContestFee() {
        return contestFee;
    }

    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    public double getFirstPlaceCost() {
        return firstPlaceCost;
    }

    public void setFirstPlaceCost(double firstPlaceCost) {
        this.firstPlaceCost = firstPlaceCost;
    }

    public double getSecondPlaceCost() {
        return secondPlaceCost;
    }

    public void setSecondPlaceCost(double secondPlaceCost) {
        this.secondPlaceCost = secondPlaceCost;
    }
}
