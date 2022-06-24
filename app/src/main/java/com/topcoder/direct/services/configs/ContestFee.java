/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
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
 * The contest fee class for xml handling.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit - Marathon Match Contest Detail Page)
 * <ol>
 *     <li>Add property {@link #algorithmSubtypeContestFees}</li>
 * </ol>
 * </p>
 *
 * @author BeBetter, Veve
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestType")
public class ContestFee {
    /**
     * <p>
     * id field
     * </p>
     */
    @XmlAttribute
    private String id;

    /**
     * <p>
     * Contest type id.
     * </p>
     */
    @XmlAttribute(required = true)
    private long contestTypeId;

    /**
     * <p>
     * If it is STUDIO element.
     * </p>
     */
    @XmlElement(name = "subType")
    private List<StudioSubtypeContestFee> studioSubtypeContestFees;

    /**
     * Contest Fee configurations for Algorithm contest sub types.
     *
     * @since 1.1
     */
    @XmlElement(name = "algorithmSubtype")
    private List<AlgorithmSubtypeContestFee> algorithmSubtypeContestFees;

    // Below is when the element holds the data for software
    /**
     * <p>
     * The contestFee field.
     * </p>
     */
    private double contestFee;

    /**
     * <p>
     * The specReviewCost field.
     * </p>
     */
    private double specReviewCost;

    /**
     * <p>
     * The contestCost field.
     * </p>
     */
    private ContestCost contestCost;

    /**
     * Checks whether current contest fee configuration is for studio contest.
     *
     * @return true if yes, false otherwise.
     * @since 1.1
     */
    public boolean isStudioFee() {
        return "STUDIO".equals(id);
    }

    /**
     * Checks whether current contest fee configuration is for algorithm contest.
     *
     * @return true if yes, false otherwise.
     * @since 1.1
     */
    public boolean isAlgorithmFee() {
        return "ALGORITHM".equals(id);
    }

    /**
     * <p>
     * Gets the id.
     * </p>
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * <p>
     * Set the id.
     * </p>
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * Gets the studio subtype contest fees.
     * </p>
     *
     * @return the studio subtype contest fee
     */
    public List<StudioSubtypeContestFee> getStudioSubtypeContestFees() {
        if (studioSubtypeContestFees == null) {
            studioSubtypeContestFees = new ArrayList<StudioSubtypeContestFee>();
        }
        return studioSubtypeContestFees;
    }

    /**
     * <p>
     * Gets the algorithm subtype contest fees.
     * </p>
     *
     * @return the algorithm subtype contest fee
     * @since 1.1
     */
    public List<AlgorithmSubtypeContestFee> getAlgorithmSubtypeContestFees() {
        if (algorithmSubtypeContestFees == null) {
            algorithmSubtypeContestFees = new ArrayList<AlgorithmSubtypeContestFee>();
        }
        return algorithmSubtypeContestFees;
    }

    /**
     * <p>
     * Set the studio subtype contest fees.
     * </p>
     *
     * @param studioSubtypeContestFees the studio subtype contest fees
     */
    public void setStudioSubtypeContestFees(List<StudioSubtypeContestFee> studioSubtypeContestFees) {
        this.studioSubtypeContestFees = studioSubtypeContestFees;
    }

    /**
     * <p>
     * Sets the <code>contestFee</code> field value.
     * </p>
     *
     * @param contestFee the contestFee value to set
     */
    public void setContestFee(double contestFee) {
        this.contestFee = contestFee;
    }

    /**
     * <p>
     * Gets the <code>contestFee</code> field value.
     * </p>
     *
     * @return the <code>contestFee</code> field value
     */
    public double getContestFee() {
        return this.contestFee;
    }

    /**
     * <p>
     * Sets the <code>specReviewCost</code> field value.
     * </p>
     *
     * @param specReviewCost the specReviewCost value to set
     */
    public void setSpecReviewCost(double specReviewCost) {
        this.specReviewCost = specReviewCost;
    }

    /**
     * <p>
     * Gets the <code>specReviewCost</code> field value.
     * </p>
     *
     * @return the <code>specReviewCost</code> field value
     */
    public double getSpecReviewCost() {
        return this.specReviewCost;
    }

    /**
     * <p>
     * Sets the <code>contestCost</code> field value.
     * </p>
     *
     * @param contestCost the contestCost value to set
     */
    public void setContestCost(ContestCost contestCost) {
        this.contestCost = contestCost;
    }

    /**
     * <p>
     * Gets the <code>contestCost</code> field value.
     * </p>
     *
     * @return the <code>contestCost</code> field value
     */
    public ContestCost getContestCost() {
        return this.contestCost;
    }
}
