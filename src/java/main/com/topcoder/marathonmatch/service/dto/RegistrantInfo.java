/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.marathonmatch.service.dto;

import java.util.Date;

/**
 * This DTO holds a single registrant's information.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 *
 * <p>
 *     Version 1.1 - PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress
 *     <ol>
 *         <li>Add property {@link userId }</li>
 *     </ol>
 * </p>
 *
 * @author sampath01, zhu_tao
 * @version 1.1
 * @since 1.0
 */
public class RegistrantInfo {
    /**
     * The handle of the registrant.
     */
    private String handle;

    /**
     * The rating of the registrant.
     */
    private Integer rating;

    /**
     * The registration time of the registrant
     */
    private Date registrationTime;

    /**
     * The registrant user id in topcoder.
     *
     * @since 1.1
     */
    private Long userId;

    /**
     * Default constructor.
     */
    public RegistrantInfo() {
        super();
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param handle
     *            the name-sake field to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param rating
     *            the name-sake field to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Date getRegistrationTime() {
        return registrationTime;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param registrationTime
     *            the name-sake field to set
     */
    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
