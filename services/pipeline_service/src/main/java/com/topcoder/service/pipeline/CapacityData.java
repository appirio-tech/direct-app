/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Simple DTO class for capacity retrieval
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author pulky
 * @version 1.0 (Cockpit Pipeline Release Assembly 2 - Capacity)
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capacityData", propOrder = { "date", "numScheduledContests", "contests"})
public class CapacityData implements Serializable {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 600295947647235401L;

    /**
     * Represents the date
     */
    private XMLGregorianCalendar date;

    /**
     * Represents the number of scheduled contests
     */
    private int numScheduledContests;


     /**
     * Represents contest ids
     */
    private List contests = new ArrayList();

    /**
     * Default constructor.
     */
    public CapacityData() {
    }

    /**
     * Constructor using fields
     *
     * @param date the date to set
     * @param numScheduledContests the number of scheduled contests to set
     */
    public CapacityData(XMLGregorianCalendar date, int numScheduledContests) {
        super();
        this.date = date;
        this.numScheduledContests = numScheduledContests;
    }


     /**
     * Constructor using fields
     *
     * @param date the date to set
     * @param numScheduledContests the number of scheduled contests to set
     * @param contests the contests in the date
     */
    public CapacityData(XMLGregorianCalendar date, int numScheduledContests, List contests) {
        super();
        this.date = date;
        this.numScheduledContests = numScheduledContests;
        this.contests = contests;
    }

    /**
     * Returns the date.
     *
     * @return the date.
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

     /**
     * Updates the date with the specified value.
     *
     * @param date the date to set.
     */
    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    /**
     * Returns the number of scheduled contests.
     *
     * @return the number of scheduled contests.
     */
    public int getNumScheduledContests() {
        return numScheduledContests;
    }

     /**
     * Updates the number of scheduled contests with the specified value.
     *
     * @param numScheduledContests the number of scheduled contests to set.
     */
    public void setNumScheduledContests(int numScheduledContests) {
        this.numScheduledContests = numScheduledContests;
    }

     /**
     * Returns contest ids.
     *
     * @return contest ids.
     */
    public List getContests() {
        return contests;
    }

    /**
     * set contests
     *
     * @param contests the contests to set.
     */
    public void setContests(List contests) {
        if (contests != null)
        {
            this.contests = contests;
        }
    }
}
