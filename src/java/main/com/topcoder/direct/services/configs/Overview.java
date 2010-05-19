/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * The overview class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "subTypes")
public class Overview {
    @XmlElement(name = "contestType")
    private List<ContestOverview> contestOverviews;

    public List<ContestOverview> getContestOverviews() {
        if(contestOverviews == null) {
            contestOverviews = new ArrayList<ContestOverview>();
        }

        return contestOverviews;
    }

    public void setContestOverviews(List<ContestOverview> contestOverviews) {
        this.contestOverviews = contestOverviews;
    }
}
