/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user;

import java.util.Date;
import java.io.Serializable;

/**
 * Represents the Registrant entity. It holds the attributes user id, handle, rating, reailbility, etc.
 * <p>
 * Thread safety: This class is mutable and not thread safe.
 *
 * @author murphydog
 * @version 1.1
 */
public class Registrant implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 2L;

    /**
     * Represents the user id attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can
     * be any value. The default value is null.
     */
    private Long userId;

    /**
     * Represents the handle attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can
     * be any value.The default value is null.
     */
    private String handle;

    /**
     * Represents the rating attribute of the UserInfo entity. It's set and accessed in the set/get methods. It
     * can be any value. The default value is null.
     */
    private Double rating;

    /**
     * Represents the reliability attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can be
     * any value. The default value is null.
     */
    private Double reliability;

    /**
     * Represents the registration date attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can
     * be any value. The default value is null.
     */
    private Date registrationDate;

    /**
     * Represents the submission date attribute of the UserInfo entity. It's set and accessed in the set/get methods. It can be
     * any value. The default value is 0.
     */
    private Date submissionDate;

    /**
     * Creates an instance of this class. Empty constructor.
     */
    public Registrant() {
    }	

    /**
     * Retrieves the user's user id.
     *
     * @return the user's user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user's user id.
     *
     * @param userId
     *            the user's user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the user's handle.
     *
     * @return the user's handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the user's handle.
     *
     * @param handle
     *            the user's handle
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Retrieves the rating.
     *
     * @return the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * Sets the rating.
     *
     * @param rating
     *            the rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Retrieves the user's reliability.
     *
     * @return the user's reliability
     */
    public Double getReliability() {
        return reliability;
    }

    /**
     * Sets user's reliability.
     *
     * @param reliability
     *            the user's reliability
     */
    public void setReliability(Double reliability) {
        this.reliability = reliability;
    }

    /**
     * Retrieves the user's registration date.
     *
     * @return the registration date
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the user's registration date.
     *
     * @param registrationDate
     *            the registration date to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Retrieves the submission date.
     *
     * @return the submission date
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Sets the submission date.
     *
     * @param submissionDate
     *            the submission date
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}
