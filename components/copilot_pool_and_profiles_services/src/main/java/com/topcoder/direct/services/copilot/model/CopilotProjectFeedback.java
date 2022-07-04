/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The DTO for transferring the data of copilot feedback.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Copilot Feedback Updates)
 * <ul>
 *     <li>Add properties: {@link #timelineRating}, {@link #qualityRating}, {@link #communicationRating}
 *          and {@link #managementRating}
 *     </li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Module Assembly - TopCoder Copilot Feedback Integration)
 */
public class CopilotProjectFeedback implements Serializable {

    /**
     * The feedback text.
     */
    private String text;

    /**
     * The feedback answer.
     */
    private boolean answer;

    /**
     * The user id of the feedback author.
     */
    private long authorId;

    /**
     * The submit date of the feedback.
     */
    private Date submitDate;

    /**
     * The status of the feedback.
     */
    private String status;

    /**
     * The user id of the updater.
     */
    private long updaterId;

    /**
     * The timeline rating.
     *
     * @since 1.1
     */
    private int timelineRating;

    /**
     * The quality rating.
     *
     * @since 1.1
     */
    private int qualityRating;

    /**
     * The communication rating.
     *
     * @since 1.1
     */
    private int communicationRating;

    /**
     * The management rating.
     *
     * @since 1.1
     */
    private int managementRating;

    /**
     * Gets the feedback text.
     *
     * @return the feedback text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the feedback text.
     *
     * @param text the feedback text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the feedback answer.
     *
     * @return the feedback answer.
     */
    public boolean isAnswer() {
        return answer;
    }

    /**
     * Sets the feedback answer.
     *
     * @param answer the feedback answer.
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    /**
     * Gets the feedback author id.
     *
     * @return the feedback author id.
     */
    public long getAuthorId() {
        return authorId;
    }

    /**
     * Sets the feedback author id.
     *
     * @param authorId the feedback author id.
     */
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    /**
     * Gets the feedback submit date.
     *
     * @return the feedback submit date.
     */
    public Date getSubmitDate() {
        return submitDate;
    }

    /**
     * Sets the feedback submit date.
     *
     * @param submitDate the feedback submit date.
     */
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * Gets the feedback status.
     *
     * @return the feedback status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the feedback status.
     *
     * @param status the feedback status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the feedback updater id.
     *
     * @return the updater id.
     */
    public long getUpdaterId() {
        return updaterId;
    }

    /**
     * Sets the feedback updater id.
     *
     * @param updaterId the updater id.
     */
    public void setUpdaterId(long updaterId) {
        this.updaterId = updaterId;
    }

    /**
     * Gets the timelie rating.
     *
     * @return the timeline rating.
     * @since 1.1
     */
    public int getTimelineRating() {
        return timelineRating;
    }

    /**
     * Sets the timeline rating.
     *
     * @param timelineRating the timeline rating.
     * @since 1.1
     */
    public void setTimelineRating(int timelineRating) {
        this.timelineRating = timelineRating;
    }

    /**
     * Gets the quality rating.
     *
     * @return the quality rating.
     * @since 1.1
     */
    public int getQualityRating() {
        return qualityRating;
    }

    /**
     * Sets the quality rating.
     *
     * @param qualityRating the quality rating.
     * @since 1.1
     */
    public void setQualityRating(int qualityRating) {
        this.qualityRating = qualityRating;
    }

    /**
     * Gets the communication rating.
     *
     * @return the communication rating.
     * @since 1.1
     */
    public int getCommunicationRating() {
        return communicationRating;
    }

    /**
     * Sets the communication rating.
     *
     * @param communicationRating the communication rating.
     * @since 1.1
     */
    public void setCommunicationRating(int communicationRating) {
        this.communicationRating = communicationRating;
    }

    /**
     * Gets the management rating.
     *
     * @return the management rating.
     * @since 1.1
     */
    public int getManagementRating() {
        return managementRating;
    }

    /**
     * Sets the management rating.
     *
     * @param managementRating the management rating.
     * @since 1.1
     */
    public void setManagementRating(int managementRating) {
        this.managementRating = managementRating;
    }
}
