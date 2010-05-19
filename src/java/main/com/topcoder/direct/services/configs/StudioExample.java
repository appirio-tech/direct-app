/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The studio example class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "example")
public class StudioExample {
    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String thumbImage;

    @XmlElement(required = true)
    private String fullsizeImage;

    @XmlElement(required = true)
    private String contestLink;

    @XmlElement(required = true)
    private String submissionsLink;

    @XmlElement(required = true)
    private String winnersLink;

    @XmlElement(required = true)
    private String prizes;

    @XmlElement(required = true)
    private String duration;

    @XmlElement(required = true)
    private String registrants;

    @XmlElement(required = true)
    private String submissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getFullsizeImage() {
        return fullsizeImage;
    }

    public void setFullsizeImage(String fullsizeImage) {
        this.fullsizeImage = fullsizeImage;
    }

    public String getContestLink() {
        return contestLink;
    }

    public void setContestLink(String contestLink) {
        this.contestLink = contestLink;
    }

    public String getSubmissionsLink() {
        return submissionsLink;
    }

    public void setSubmissionsLink(String submissionsLink) {
        this.submissionsLink = submissionsLink;
    }

    public String getWinnersLink() {
        return winnersLink;
    }

    public void setWinnersLink(String winnersLink) {
        this.winnersLink = winnersLink;
    }

    public String getPrizes() {
        return prizes;
    }

    public void setPrizes(String prizes) {
        this.prizes = prizes;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRegistrants() {
        return registrants;
    }

    public void setRegistrants(String registrants) {
        this.registrants = registrants;
    }

    public String getSubmissions() {
        return submissions;
    }

    public void setSubmissions(String submissions) {
        this.submissions = submissions;
    }
}
