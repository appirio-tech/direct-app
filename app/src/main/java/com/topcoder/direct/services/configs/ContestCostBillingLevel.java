/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Contest cost billing level class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Direct Launch Software Assembly
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billingLevel")
public class ContestCostBillingLevel {
    /**
     * <p>
     * The id field to set.
     * </p>
     */
    @XmlAttribute(name = "id")
    private String id;

    /**
     * <p>
     * The firstPlaceCost field.
     * </p>
     */
    private double firstPlaceCost;

    /**
     * <p>
     * The secondPlaceCost field.
     * </p>
     */
    private double secondPlaceCost;

    /**
     * <p>
     * The reviewBoardCost field.
     * </p>
     */
    private double reviewBoardCost;

    /**
     * <p>
     * The reliabilityBonusCost field.
     * </p>
     */
    private double reliabilityBonusCost;

    /**
     * <p>
     * The drCost field.
     * </p>
     */
    private double drCost;
    /**
     * <p>
     * The description.
     * </p>
     */
    private String description;

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
     * Sets the id.
     * </p>
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * <p>
     * Sets the <code>firstPlaceCost</code> field value.
     * </p>
     *
     * @param firstPlaceCost the value to set
     */
    public void setFirstPlaceCost(double firstPlaceCost) {
        this.firstPlaceCost = firstPlaceCost;
    }

    /**
     * <p>
     * Gets the <code>firstPlaceCost</code> field value.
     * </p>
     *
     * @return the <code>firstPlaceCost</code> field value
     */
    public double getFirstPlaceCost() {
        return this.firstPlaceCost;
    }

    /**
     * <p>
     * Sets the <code>secondPlaceCost</code> field value.
     * </p>
     *
     * @param secondPlaceCost the value to set
     */
    public void setSecondPlaceCost(double secondPlaceCost) {
        this.secondPlaceCost = secondPlaceCost;
    }

    /**
     * <p>
     * Gets the <code>secondPlaceCost</code> field value.
     * </p>
     *
     * @return the <code>secondPlaceCost</code> field value
     */
    public double getSecondPlaceCost() {
        return this.secondPlaceCost;
    }

    /**
     * <p>
     * Sets the <code>reviewBoardCost</code> field value.
     * </p>
     *
     * @param reviewBoardCost the value to set
     */
    public void setReviewBoardCost(double reviewBoardCost) {
        this.reviewBoardCost = reviewBoardCost;
    }

    /**
     * <p>
     * Gets the <code>reviewBoardCost</code> field value.
     * </p>
     *
     * @return the <code>reviewBoardCost</code> field value
     */
    public double getReviewBoardCost() {
        return this.reviewBoardCost;
    }

    /**
     * <p>
     * Sets the <code>reliabilityBonusCost</code> field value.
     * </p>
     *
     * @param reliabilityBonusCost the value to set
     */
    public void setReliabilityBonusCost(double reliabilityBonusCost) {
        this.reliabilityBonusCost = reliabilityBonusCost;
    }

    /**
     * <p>
     * Gets the <code>reliabilityBonusCost</code> field value.
     * </p>
     *
     * @return the <code>reliabilityBonusCost</code> field value
     */
    public double getReliabilityBonusCost() {
        return this.reliabilityBonusCost;
    }

    /**
     * <p>
     * Sets the <code>drCost</code> field value.
     * </p>
     *
     * @param drCost the value to set
     */
    public void setDrCost(double drCost) {
        this.drCost = drCost;
    }

    /**
     * <p>
     * Gets the <code>drCost</code> field value.
     * </p>
     *
     * @return the <code>drCost</code> field value
     */
    public double getDrCost() {
        return this.drCost;
    }
    /**
     * <p>
     * Gets the <code>description</code> field value.
     * </p>
     *
     * @return the <code>description</code> field value
     */    
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the <code>description</code> field value.
     * </p>
     *
     * @param description the value to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
