/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The contest overview class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestType")
public class ContestOverview {
    @XmlAttribute(name="id")
    private String id;

    /**
     * The studio subtype overview list. It will have values for id of "STUDIO"
     */
    @XmlElement(name = "subType")
    private List<StudioSubtypeOverview> studioSubtypeOverviews;

    @XmlElement(name = "minPrize")
    private String minPrize;

    @XmlElement(name = "averagePrize")
    private String averagePrize;

    @XmlElement(name = "maxExposure")
    private String maxExposure;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<StudioSubtypeOverview> getStudioSubtypeOverviews() {
        if(studioSubtypeOverviews == null) {
            studioSubtypeOverviews = new ArrayList<StudioSubtypeOverview>();
        }
        return studioSubtypeOverviews;
    }

    public void setStudioSubtypeOverviews(List<StudioSubtypeOverview> studioSubtypeOverviews) {
        this.studioSubtypeOverviews = studioSubtypeOverviews;
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
