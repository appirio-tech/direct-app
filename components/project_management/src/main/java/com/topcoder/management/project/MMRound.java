/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * <p>
 * This is a MMRound entity which represents the Marathon Match Round entry.
 * </p>
 *
 * @author TCSCODER
 * @version 1.0
 */
import java.io.Serializable;
import java.util.List;

public class MMRound implements Serializable {
    /**
     * Round Id
     */
    private long id = -1L;

    /**
     * MM contest
     */
    private MMContest contest;

    /**
     * Round name
     */
    private String name;

    /**
     * ROund short name
     */
    private String shortName;

    /**
     * List of round segments
     */
    private List<MMRoundSegment> segments;

    /**
     * Default round name format
     */
    public static final String DEFAULT_ROUND_NAME = "%s ROUND";

    /**
     * Getter for round id
     * @return round id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for round id
     * @param id round id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for round contest
     * @return contest
     */
    public MMContest getContest() {
        return contest;
    }

    /**
     * Setter for round contest
     * @param contest round contest
     */
    public void setContest(MMContest contest) {
        this.contest = contest;
    }

    /**
     * Getter for round name
     * @return round name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for round name
     * @param name round name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for round short name
     * @return short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Setter for round short name
     * @param shortName short name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Getter for list of round segments
     * @return round segments
     */
    public List<MMRoundSegment> getSegments() {
        return segments;
    }

    /**
     * Setter for round segments
     * @param segments list of round segments
     */
    public void setSegments(List<MMRoundSegment> segments) {
        this.segments = segments;
    }
}