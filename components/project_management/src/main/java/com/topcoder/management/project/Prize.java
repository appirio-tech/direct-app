/*
 * Copyright (C) 2010-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * <p>
 * This is a Prize entity which represents the project prizes. It extends from AuditableObject class. Added in version
 * 1.2.
 * </p>
 *
 * <p>
 * Added projectId field. Added in version 1.2.1.
 * </p>
 *
 * <p>
 * <strong>Thread-Safety:</strong> This class is mutable and not thread safe. But it will be used as entity so it'll not
 * cause any thread safe problem.
 * </p>
 *
 * @author flytoj2ee, TCSDEVELOPER, frozenfx
 * @version 1.2.1
 * @since 1.2.1 Release Assembly - TopCoder Direct Prize-Project Link Update
 */
@SuppressWarnings("serial")
public class Prize extends AuditableObject {
    /**
     * Represents the place of Prize. The default value is 0. It's changeable. It could be any value. It's accessed in
     * setter and getter.
     */
    private int place;

    /**
     * Represents the id of the project the prize is associated with.
     * The default value is 0. It's changeable. It could be any value. It's accessed in setter and getter.
     */
    private long projectId;

    /**
     * Represents the prizeAmount of Prize. The default value is 0. It's changeable. It could be any value. It's
     * accessed in setter and getter.
     */
    private double prizeAmount;

    /**
     * Represents the prizeType of Prize. The default value is null. It's changeable. It could not set to null. It's
     * accessed in setter and getter.
     */
    private PrizeType prizeType;

    /**
     * Represents the numberOfSubmissions of Prize. The default value is 1. It's changeable. It could be any value. It's
     * accessed in setter and getter.
     */
    private int numberOfSubmissions = 1;

    /**
     * Represents the id of Prize. The default value is 0. It's changeable. It should be positive value.
     * It's accessed in setter and getter.
     */
    private long id;

    /**
     * Empty constructor.
     */
    public Prize() {
    }

    /**
     * Returns the value of place attribute.
     *
     * @return the value of place.
     */
    public int getPlace() {
        return this.place;
    }

    /**
     * Sets the given value to place attribute.
     *
     * @param place
     *            the given value to set.
     */
    public void setPlace(int place) {
        this.place = place;
    }

    /**
     * Returns the value of prizeAmount attribute.
     *
     * @return the value of prizeAmount.
     */
    public double getPrizeAmount() {
        return this.prizeAmount;
    }

    /**
     * Sets the given value to prizeAmount attribute.
     *
     * @param prizeAmount
     *            the given value to set.
     */
    public void setPrizeAmount(double prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    /**
     * Returns the value of prizeType attribute.
     *
     * @return the value of prizeType.
     */
    public PrizeType getPrizeType() {
        return this.prizeType;
    }

    /**
     * Sets the given value to prizeType attribute.
     *
     * @param prizeType
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the parameter is null
     */
    public void setPrizeType(PrizeType prizeType) {
        Helper.checkObjectNotNull(prizeType, "prizeType");
        this.prizeType = prizeType;
    }

    /**
     * Returns the value of numberOfSubmissions attribute. #### Parameters
     *
     * @return the value of numberOfSubmissions.
     */
    public int getNumberOfSubmissions() {
        return this.numberOfSubmissions;
    }

    /**
     * Sets the given value to numberOfSubmissions attribute.
     *
     * @param numberOfSubmissions
     *            the given value to set.
     */
    public void setNumberOfSubmissions(int numberOfSubmissions) {
        this.numberOfSubmissions = numberOfSubmissions;
    }

    /**
     * Returns the value of id attribute.
     *
     * @return the value of id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the given value to id attribute.
     *
     * @param id
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the id parameter is non-positive
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Returns the value of projectId attribute.
     *
     * @return the value of projectId.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * Sets the given value to projectId attribute.
     *
     * @param projectId
     *            the given value to set.
     * @throws IllegalArgumentException
     *             if the projectId parameter is non-positive
     */
    public void setProjectId(long projectId) {
        Helper.checkNumberPositive(projectId, "projectId");
        this.projectId = projectId;
    }
}
