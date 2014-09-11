/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class represents a milestone.
 * </p>
 *
 * <p>
 *     Updates in version 1.1 (Module Assembly - TC Cockpit Project Milestones Management Front End)
 *     - Add property {@link #completionDate} and its getter and setter.
 *     - Add method {@link #getMapRepresentation()} to get the map representation of the milestone.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.1
 */
public class Milestone extends NamedEntity {

    /**
     * Date format for formatting the mielstone due date and completion date.
     * @since 1.1
     */
    private static final DateFormat MILESTONE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    
    /**
     * Represents the milestone description. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private String description;

    /**
     * Represents the milestone due date. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private Date dueDate;

    /**
     * Represents the persons that are managing the milestone. It is managed with a getter and setter. It may have
     * any value. It is fully mutable.
     */
    private List<ResponsiblePerson> owners;

    /**
     * Represents the a flag whether a notification should be sent. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private boolean sendNotifications;

    /**
     * Represents the flag whether the milestone has been completed. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private boolean completed;

    /**
     * Represents the ID of the project that this milestone is for. It is managed with a getter and setter. It may
     * have any value. It is fully mutable.
     */
    private long projectId;

    /**
     * The completion date of the project milestone.
     * @since 1.1
     */
    private Date completionDate;

    /**
     * Represents the milestone status. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private MilestoneStatus status;

    /**
     * Empty constructor.
     */
    public Milestone() {
        // Empty
    }

    /**
     * <p>
     * Getter method for description, simply return the value of milestone description.
     * </p>
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Setter method for description, simply assign the value to milestone description.
     * </p>
     *
     * @param description
     *            the value of description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Getter method for dueDate, simply return the value of milestone due date.
     * </p>
     *
     * @return the value of dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * <p>
     * Setter method for dueDate, simply assign the value to milestone due date.
     * </p>
     *
     * @param dueDate
     *            the value of dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the completion date of the project milestone.
     *
     * @return the completion date of the project milestone.
     * @since 1.1
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets the completion date of the project milestone.
     *
     * @param completionDate the completion date of the project milestone.
     * @since 1.1
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * <p>
     * Getter method for owners, simply return the persons that are managing the milestone.
     * </p>
     *
     * @return the value of owners
     */
    public List<ResponsiblePerson> getOwners() {
        return owners;
    }

    /**
     * <p>
     * Setter method for owners, simply assign the value to the persons that are managing the milestone.
     * </p>
     *
     * @param owners
     *            the value of owners to set
     */
    public void setOwners(List<ResponsiblePerson> owners) {
        this.owners = owners;
    }

    /**
     * <p>
     * Getter method for sendNotifications, simply return the a flag whether a notification should be sent.
     * </p>
     *
     * @return the value of sendNotifications
     */
    public boolean isSendNotifications() {
        return sendNotifications;
    }

    /**
     * <p>
     * Setter method for sendNotifications, simply set the a flag whether a notification should be sent.
     * </p>
     *
     * @param sendNotifications
     *            the value of sendNotifications to set
     */
    public void setSendNotifications(boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

    /**
     * <p>
     * Getter method for completed, simply return the flag whether the milestone has been completed.
     * </p>
     *
     * @return the value of completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * <p>
     * Setter method for completed, simply set the flag whether the milestone has been completed.
     * </p>
     *
     * @param completed
     *            the value of completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * <p>
     * Getter method for projectId, simply return the ID of the project that this milestone is for.
     * </p>
     *
     * @return the value of projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Setter method for projectId, simply set the ID of the project that this milestone is for.
     * </p>
     *
     * @param projectId
     *            the value of projectId to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Getter method for status, simply return the milestone status.
     * </p>
     *
     * @return the value of status
     */
    public MilestoneStatus getStatus() {
        return status;
    }

    /**
     * <p>
     * Setter method for status, simply set the milestone status.
     * </p>
     *
     * @param status
     *            the value of status to set
     */
    public void setStatus(MilestoneStatus status) {
        this.status = status;
    }

    /**
     * The toString method.
     *
     * @return the string of entity
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{id:").append(getId());
        sb.append(", name:").append(getName());
        sb.append(", description:").append(description);
        sb.append(", dueDate:").append(dueDate);
        sb.append(", owners:").append(owners);
        sb.append(", sendNotifications:").append(sendNotifications);
        sb.append(", completed:").append(completed);
        sb.append(", projectId:").append(projectId);
        sb.append(", status:").append(status).append("}");
        return sb.toString();
    }

    /**
     * Gets the map representation of the milestone.
     *
     * @return the map representation of the milestone.
     * @since 1.1
     */
    public Map getMapRepresentation() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id", getId());
        map.put("name", getName());
        map.put("description", getDescription());
        map.put("dueDate", MILESTONE_DATE_FORMAT.format(getDueDate()));
        map.put("status", getStatus().toString().toLowerCase());
        map.put("completed", isCompleted());
        map.put("projectId", projectId);
        map.put("notification", isSendNotifications());
        if(getOwners() != null && getOwners().size() > 0) {
            map.put("ownerId", getOwners().get(0).getUserId());
            map.put("ownerName", getOwners().get(0).getName());
        }
        if(isCompleted()) {
            map.put("completionDate", MILESTONE_DATE_FORMAT.format(getCompletionDate()));
        }

        return map;
    }
}
