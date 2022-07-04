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
 * The studio subtype overview class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subType")
public class StudioSubtypeOverview {
    @XmlAttribute
    private long id;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "example1")
    private StudioExample example1;

    @XmlElement(name = "example2")
    private StudioExample example2;

    @XmlElement(name = "minPrize")
    private String minPrize;

    @XmlElement(name = "averagePrize")
    private String averagePrize;

    @XmlElement(name = "maxExposure")
    private String maxExposure;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StudioExample getExample1() {
        return example1;
    }

    public void setExample1(StudioExample example1) {
        this.example1 = example1;
    }

    public StudioExample getExample2() {
        return example2;
    }

    public void setExample2(StudioExample example2) {
        this.example2 = example2;
    }

    public String getMinPrize() {
        return minPrize;
    }

    public void setMinPrize(String minPrize) {
        this.minPrize = minPrize;
    }

    public String getAveragePrize() {
        return averagePrize;
    }

    public void setAveragePrize(String averagePrize) {
        this.averagePrize = averagePrize;
    }

    public String getMaxExposure() {
        return maxExposure;
    }

    public void setMaxExposure(String maxExposure) {
        this.maxExposure = maxExposure;
    }
}
