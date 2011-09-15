/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;

/**
 * <p>
 *     DTO used to store the data of direct project copilot.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Overview Update 1)
 */
public class ProjectCopilotDTO implements Serializable {

    /**
     * The handle of the copilot
     */
    private String handle;

    /**
     * The email of the copilot
     */
    private String email;

    /**
     * The TopCoder user id of the copilot.
     */
    private long userId;

    /**
     * The header image path of the copilot.
     */
    private String avatarPath;

    /**
     * The lower version of the copilot handler.
     */
    private String handleLower;

    /**
     * The name of the copilot type of the copilot.
     */
    private String copilotTypeName;

    /**
     * The copilot profile id of the copilot.
     */
    private long copilotProfileId;

    /**
     * Gets the copilot profile id.
     *
     * @return the copilot profile id.
     */
    public long getCopilotProfileId() {
        return copilotProfileId;
    }

    /**
     * Sets the copilot profile id.
     *
     * @param copilotProfileId the copilot profile id.
     */
    public void setCopilotProfileId(long copilotProfileId) {
        this.copilotProfileId = copilotProfileId;
    }

    /**
     * Gets the name of the copilot type.
     *
     * @return the name of the copilot type.
     */
    public String getCopilotTypeName() {
        return copilotTypeName;
    }

    /**
     * Sets the name of the copilot type.
     *
     * @param copilotTypeName the name of the copilot type.
     */
    public void setCopilotTypeName(String copilotTypeName) {
        this.copilotTypeName = copilotTypeName;
    }

    /**
     * Gets the handle of the copilot.
     *
     * @return the handle of the copilot.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle of the copilot
     *
     * @param handle the handle of the copilot.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Gets the email of the copilot.
     *
     * @return the email of the copilot.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the copilot.
     *
     * @param email the email of the copilot
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user id of the copilot
     *
     * @return the user id of the copilot.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id of the copilot.
     *
     * @param userId the user id of the copilot.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the avatar path of the copilot.
     *
     * @return the avatar path of the copilot.
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Sets the avatar path of the copilot.
     *
     * @param avatarPath the avatar path of the copilot.
     */
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    /**
     * Gets the lower handle of the copilot
     *
     * @return the lower handle of the copilot
     */
    public String getHandleLower() {
        return handleLower;
    }

    /**
     * Sets the lower handle of the copilot
     *
     * @param handleLower the lower handle of the copilot
     */
    public void setHandleLower(String handleLower) {
        this.handleLower = handleLower;
    }
}
