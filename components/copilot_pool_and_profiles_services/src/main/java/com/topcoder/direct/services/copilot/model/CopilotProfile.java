/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.model;

import java.util.Date;
import java.util.Set;

/**
 * <p>This class is a container for information about a single copilot profile. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * <p>
 *     Version 1.1 - Add property handle to the DTO.
 * </p>
 *
 * @author saarixx, hohosky
 * @version 1.1
 */
public class CopilotProfile extends IdentifiableEntity {

    /**
     * <p>The ID of the copilot user. Can be any value. Has getter and setter.</p>
     */
    private long userId;

    /**
     * <p>
     *     The TopCoder handle of the copilot.
     * </p>
     * @since 1.1
     */
    private String handle;

    /**
     * <p>The copilot profile status. Can be any value. Has getter and setter.</p>
     */
    private CopilotProfileStatus status;

    /**
     * <p>The number of suspensions for this copilot. Can be any value. Has getter and setter.</p>
     */
    private int suspensionCount;

    /**
     * <p>The reliability rating of the copilot. Can be any value. Has getter and setter.</p>
     */
    private float reliability;

    /**
     * <p>The activation date of the copilot. Can be any value. Has getter and setter.</p>
     */
    private Date activationDate;

    /**
     * <p>The flag indicating whether copilot earnings can be shown. Can be any value. Has getter and setter.</p>
     */
    private boolean showCopilotEarnings;

    /**
     * <p>The additional profile details. Can be any value. Has getter and setter.</p>
     */
    private Set<CopilotProfileInfo> profileInfos;

    /**
     * <p>Creates new instance of <code>{@link CopilotProfile}</code> class.</p>
     */
    public CopilotProfile() {
        // empty constructor
    }

    /**
     * <p>Retrieves the ID of the copilot user.</p>
     *
     * @return the ID of the copilot user
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>Sets the ID of the copilot user.</p>
     *
     * @param userId the ID of the copilot user
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * <p>Retrieves the copilot profile status.</p>
     *
     * @return the copilot profile status
     */
    public CopilotProfileStatus getStatus() {
        return status;
    }

    /**
     * <p>Sets the copilot profile status.</p>
     *
     * @param status the copilot profile status
     */
    public void setStatus(CopilotProfileStatus status) {
        this.status = status;
    }

    /**
     * <p>Retrieves the number of suspensions for this copilot.</p>
     *
     * @return the number of suspensions for this copilot
     */
    public int getSuspensionCount() {
        return suspensionCount;
    }

    /**
     * <p>Sets the number of suspensions for this copilot.</p>
     *
     * @param suspensionCount the number of suspensions for this copilot
     */
    public void setSuspensionCount(int suspensionCount) {
        this.suspensionCount = suspensionCount;
    }

    /**
     * <p>Retrieves the reliability rating of the copilot.</p>
     *
     * @return the reliability rating of the copilot
     */
    public float getReliability() {
        return reliability;
    }

    /**
     * <p>Sets the reliability rating of the copilot.</p> t
     *
     * @param reliability the reliability rating of the copilot
     */
    public void setReliability(float reliability) {
        this.reliability = reliability;
    }

    /**
     * <p>Retrieves the activation date of the copilot.</p>
     *
     * @return the activation date of the copilot
     */
    public Date getActivationDate() {
        return activationDate;
    }

    /**
     * <p>Sets the activation date of the copilot.</p>
     *
     * @param activationDate the activation date of the copilot
     */
    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    /**
     * <p>Retrieves the flag indicating whether copilot earnings can be shown.</p>
     *
     * @return the flag indicating whether copilot earnings can be shown
     */
    public boolean isShowCopilotEarnings() {
        return showCopilotEarnings;
    }

    /**
     * <p>Sets the flag indicating whether copilot earnings can be shown.</p>
     *
     * @param showCopilotEarnings the flag indicating whether copilot earnings can be shown
     */
    public void setShowCopilotEarnings(boolean showCopilotEarnings) {
        this.showCopilotEarnings = showCopilotEarnings;
    }

    /**
     * <p>Retrieves the additional profile details.</p>
     *
     * @return the additional profile details
     */
    public Set<CopilotProfileInfo> getProfileInfos() {
        return profileInfos;
    }

    /**
     * <p>Sets the additional profile details.</p>
     *
     * @param profileInfos the additional profile details
     */
    public void setProfileInfos(Set<CopilotProfileInfo> profileInfos) {
        this.profileInfos = profileInfos;
    }

    /**
     * Gets the TopCoder user handle of the copilot.
     *
     * @return the TopCoder user handle.
     * @since 1.1
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the TopCoder user handle.
     *
     * @param handle the TopCoder user handle.
     * @since 1.1
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }
}

