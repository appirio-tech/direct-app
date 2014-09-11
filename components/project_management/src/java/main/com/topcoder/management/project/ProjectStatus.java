/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a project status from the persistence. Project statuses
 * are stored in 'project_status_lu' table. A project status instance contains
 * id, name and description. This class is used in Project class to specify the
 * project status of a project. This class implements Serializable interface to
 * support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
public class ProjectStatus implements Serializable {

    
    /**
     * represents 'Active' status
     */
    public static final ProjectStatus ACTIVE = new ProjectStatus(1, "Active", "Active");

    /**
     * @deprecated 
     *represents 'Inactive' status
     */
    public static final ProjectStatus INACTIVE =  new ProjectStatus(2, "Inactive", "Inactive");


    /**
     * represents 'DRAFT' status
     */
    public static final ProjectStatus DRAFT =  new ProjectStatus(2, "Draft", "Draft");

    /**
     * represents 'Completed' status
     */
    public static final ProjectStatus COMPLETED =  new ProjectStatus(7, "Completed", "Completed");

    
    /**
     * represents 'Deleted' status
     */
    public static final ProjectStatus DELETED =  new ProjectStatus(3, "Deleted", "Deleted");

    /**
     * represents 'Cancelled - Client Request' status
     */
    public static final ProjectStatus CANCELLED_CLIENT_REQUEST =  new ProjectStatus(9, "Cancelled - Client Request", "Cancelled - Client Request");

    /**
     * represents 'Cancelled - Client Request' status
     */
    public static final ProjectStatus CANCELLED_REQUIREMENTS_INFEASIBLE =  new ProjectStatus(10, "Cancelled - Requirements Infeasible", "Cancelled - Requirements Infeasible");

    /**
     * represents 'Cancelled - Client Request' status
     */
    public static final ProjectStatus CANCELLED_ZERO_REGISTRATIONS =  new ProjectStatus(11, "Cancelled - Zero Registrations", "Cancelled - Zero Registrations");

    /**
     * represents 'Cancelled - Failed Review' status
     */
    public static final ProjectStatus CANCELLED_FAILED_REVIEW =  new ProjectStatus(4, "Cancelled - Failed Review", "Cancelled - Failed Review");

    /**
     * represents 'Cancelled - Failed Screening' status
     */
    public static final ProjectStatus CANCELLED_FAILED_SCREENING =  new ProjectStatus(5, "Cancelled - Failed Screening", "Cancelled - Failed Screening");

    /**
     * represents 'Cancelled - Zero Submissions' status
     */
    public static final ProjectStatus CANCELLED_ZERO_SUBMISSIONS =  new ProjectStatus(6, "Cancelled - Zero Submissions", "Cancelled - Zero Submissions");

    /**
     * represents 'Cancelled - Winner Unresponsive' status
     */
    public static final ProjectStatus CANCELLED_WINNER_UNRESPONSIVE =  new ProjectStatus(8, "Cancelled - Winner Unresponsive", "Cancelled - Winner Unresponsive");

    /**
     * represents 'compoleted' status id
     */
    public static final long  COMPLETED_STATUS_ID =  7;

    /**
     * represents 'Cancelled - Failed Review' status id
     */
    public static final long  CANCELLED_FAILED_REVIEW_ID =  4;

    /**
     * represents 'Cancelled - Failed Screening' status id
     */
    public static final long  CANCELLED_FAILED_SCREENING_ID =  5;

    /**
     * represents 'Cancelled - Zero Submissions' status id
     */
    public static final long  CANCELLED_ZERO_SUBMISSION_ID =  6;

    /**
     * represents 'Cancelled - Winner Unresponsive' status id
     */
    public static final long  CANCELLED_WINNER_UNRESPONSIVE_ID =  8;



    /**
     * Represents the id of this instance. Only values greater than zero is
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the name of this instance. Null or empty values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private String name = null;

    /**
     * Represents the description of this instance. Null value is not allowed.
     * This variable is initialized in the constructor and can be accessed in
     * the corresponding getter/setter method.
     */
    private String description = null;

    /**
     * Create a new ProjectStatus instance with the given id and name. The two
     * fields are required for a this instance to be persisted.
     *
     * @param id
     *            The project status id.
     * @param name
     *            The project status name.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, or name is null or
     *             empty string.
     */
    public ProjectStatus(long id, String name) {
        this(id, name, "");
    }

	 /**
     * Create a new ProjectStatus instance with the given id and name. The two
     * fields are required for a this instance to be persisted.
     *
     */
    public ProjectStatus(){
     
    }

    /**
     * Create a new ProjectStatus instance with the given id, name and
     * description. The two first fields are required for a this instance to be
     * persisted.
     *
     * @param id
     *            The project status id.
     * @param name
     *            The project status name.
     * @param description
     *            The project status description.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, or name is null or
     *             empty string, or description is null.
     */
    public ProjectStatus(long id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    /**
     * Sets the id for this project status instance. Only positive values are
     * allowed.
     *
     * @param id
     *            The id of this project status instance.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the id of this project status instance.
     *
     * @return the id of this project status instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project status instance. Null or empty values are
     * not allowed.
     *
     * @param name
     *            The name of this project status instance.
     * @throws IllegalArgumentException
     *             If project status name is null or empty string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project status instance.
     *
     * @return the name of this project status instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description for this project status instance. Null value are not
     * allowed.
     *
     * @param description
     *            The description of this project status instance.
     * @throws IllegalArgumentException
     *             If project status description is null.
     */
    public void setDescription(String description) {
        Helper.checkObjectNotNull(description, "description");
        this.description = description;
    }

    /**
     * Gets the description of this project status instance.
     *
     * @return the description of this project status instance.
     */
    public String getDescription() {
        return description;
    }
}
