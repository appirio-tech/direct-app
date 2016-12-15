/*
 * Copyright (C) 2006 - 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a project property type from the persistence. Each
 * project property must associated with a project property type. Project
 * property type are stored in 'project_info_type_lu' table, project property in
 * 'project_info' table. A project property type instance contains id, name and
 * description. This class is used in ProjectManager#getAllProjectPropertyTypes
 * method to return project property types from the persistence. This class
 * implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * <p>
 *       Version 1.2.2 (Release Assembly - TC Cockpit Operations Dashboard Improvements 2)
 *       - Add property {@link #HISTORICAL_PROJECTED_COST_PROPERTY_KEY}.
 *       - Add property {@link #PROJECT_ACTIVATE_DATE_PROPERTY_KEY}.
 * </p>
 *
 * <p>
 *        Version 1.2.3 (Module Assembly - TC Cockpit Launch F2F contest)
 *        - Add property {@link #ALLOW_MULTIPLE_SUBMISSIONS_PROPERTY_KEY}
 * </p>
 * <p>
 *       Version 1.2.4 (Release Assembly - Publish Event on Contest Creation in TopCoder Cockpit)
 *       - Add property {@link #CLOUDSPOKES_CMC_TASK_PROPERTY_KEY}.
 * </p>
 *
 * <p>
 *     Version 1.2.5 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
 *     - Add property {@link #FORUM_TYPE}
 * </p>
 *
 * <p>
 *     Version 1.2.6 (Provide Way To Pre_register members When Launching Challenge)
 *     - Add property {@link #PRIVATE_PROJECT}
 * </p>
 *
 * @author tuenm, iamajia, flytoj2ee, tangzx, GreatKevin, TCSCODER
 * @version 1.2.6
 * @since 1.0
 */
public class ProjectPropertyType implements Serializable {

    /**
     * Represents key for Maximum Allowed Submissions property.
     *
     * @since 1.2
     */
    public static final String MAXIMUM_SUBMISSIONS_KEY = "Maximum Submissions";
    /**
     * <p>
     * Represents the "Review Cost" project property key
     * </p>
     *
     */
    public static final String REVIEW_COSTS_PROJECT_PROPERTY_KEY = "Review Cost";


    /**
     * Represents key for Allow Stock Art property.
     * 
     * @since 1.2.1
     */
    public static final String ALLOW_STOCK_ART_KEY = "Allow Stock Art";
    
    /**
     * Represents key for Viewable Submissions Flag property.
     * 
     * @since 1.2.1
     */
    public static final String VIEWABLE_SUBMISSIONS_FLAG_KEY_STRING = "Viewable Submissions Flag";
    
    /**
     * Represents key for Viewable Submitters property.
     * 
     * @since 1.2.1
     */
    public static final String VIEWABLE_SUBMITTERS_KEY = "Viewable Submitters";

	/**
     * <p>
     * Represents the "Review Cost" project property key
     * </p>
     *
     */
    public static final String SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY = "Spec Review Cost";


    /**
     * Private constant specifying project type info's component id key.
     *
     */
    public static final String COMPONENT_ID_PROJECT_PROPERTY_KEY = "Component ID";

    /**
     * Private constant specifying project type info's version id key.
     *
     */
    public static final String VERSION_ID_PROJECT_PROPERTY_KEY = "Version ID";

    /**
     * Private constant specifying project type info's version id key.
     *
     */
    public static final String EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY = "External Reference ID";

    /**
     * Private constant specifying project type info's forum id key.
     *
     */
    public static final String DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY = "Developer Forum ID";

    /**
     * Private constant specifying project type info's SVN Module key.
     *
     */
    public static final String SVN_MODULE_PROJECT_PROPERTY_KEY = "SVN Module";

    /**
     * Private constant specifying project type info's Notes key.
     *
     */
    public static final String NOTES_PROJECT_PROPERTY_KEY = "Notes";

    /**
     * Private constant specifying project type info's project name key.
     *
     */
    public static final String PROJECT_NAME_PROJECT_PROPERTY_KEY = "Project Name";

    /**
     * <p>
     * Represents the "Autopilot option" project property key
     * </p>
     */
    public static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY = "Autopilot Option";

    /**
     * Private constant specifying project type info's project name key.
     *
     */
    public static final String PROJECT_VERSION_PROJECT_PROPERTY_KEY = "Project Version";

     /**
     * Private constant specifying project type info's billing project key.
     *
     */
    public static final String BILLING_PROJECT_PROJECT_PROPERTY_KEY = "Billing Project";

    
    /**
     * <p>
     * Represents the "confidentiality type" project property key
     * </p>
     */
    public static final String CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY = "Confidentiality Type";

    /**
     * <p>
     * Represents the "Status Notification" project property key
     * </p>
     */
    public static final String STATUS_NOTIFICATION_PROJECT_PROPERTY_KEY = "Status Notification";

    /**
     * <p>
     * Represents the "Payments" project property key
     * </p>
     */
    public static final String PAYMENTS_PROJECT_PROPERTY_KEY = "Payments";

    /**
     * Represents the "Timeline Notification" project property key
     *
     */
    public static final String TIMELINE_NOTIFICATION_PROJECT_PROPERTY_KEY = "Timeline Notification";

    /**
     * Represents the "Public" project property key
     *
     */
    public static final String PUBLIC_PROJECT_PROPERTY_KEY = "Public";
    
    /**
     * Represents the "Rated" project property key
     *
     */
    public static final String RATED_PROJECT_PROPERTY_KEY = "Rated";

    /**
     * Represents the "Eligibility" project property key
     *
     */
    public static final String ELIGIBILITY_PROJECT_PROPERTY_KEY = "Eligibility";


    /**
     * Represents the "Digital Run Flag" project property key
     *
     */
    public static final String DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY = "Digital Run Flag";


    /**
     * Represents the "Digital Run Flag" project property key
     *
     */
    public static final String DR_POINTS_PROJECT_PROPERTY_KEY = "DR points";

    /**
     * Represents the "Admin Fee" project property key
     *
     */
    public static final String ADMIN_FEE_PROJECT_PROPERTY_KEY = "Admin Fee";

    /**
     * Represents the "Cost Level" project property key
     *
     */
    public static final String COST_LEVEL_PROJECT_PROPERTY_KEY = "Cost Level";

    /**
     * Represents the "First Place Cost" project property key
     *
     */
    public static final String FIRST_PLACE_COST_PROJECT_PROPERTY_KEY = "First Place Cost";


    /**
     * Represents the "Second Place Cost" project property key
     *
     */
    public static final String SECOND_PLACE_COST_PROJECT_PROPERTY_KEY = "Second Place Cost";


    /**
     * Represents the "Reliability Bonus Cost" project property key
     *
     */
    public static final String RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY = "Reliability Bonus Cost";


    /**
     * Represents the "Checkpoint Bonus Cost" project property key
     *
     */
    public static final String CHECKPOINT_BONUS_COST_PROJECT_PROPERTY_KEY = "Checkpoint Bonus Cost";

    /**
     * Represents the "Root Catalog ID" project property key
     *
     */
    public static final String ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY = "Root Catalog ID";

    
    /**
     * Represents the "Approval Required" project property key
     *
     */
    public static final String APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY = "Approval Required";

    /**
     * Represents the "Requires Other Fixes" project property key
     *
     */
    public static final String REQUIRED_OTHER_FIXES_PROJECT_PROPERTY_KEY = "Requires Other Fixes";

    /**
     * Represents the "Send Winner Emails" project property key
     *
     */
    public static final String SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY = "Send Winner Emails";

    /**
     * Represents the "Post-Mortem Required" project property key
     *
     */
    public static final String POST_MORTEM_REQUIRED_PROJECT_PROPERTY_KEY = "Post-Mortem Required";

    /**
     * Represents the "Reliability Bonus Eligible" project property key
     *
     */
    public static final String RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY = "Reliability Bonus Eligible";

    /**
     * Represents the "Member Payments Eligible" project property key
     *
     */
    public static final String MEMBER_PAYMENT_ELIGIBLE_PROJECT_PROPERTY_KEY = "Member Payments Eligible";


    /**
     * Represents the "Track Late Deliverables" project property key
     *
     */
    public static final String TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY = "Track Late Deliverables";

    /**
     * Represents the "Copilot Cost" project property key
     *
     */
    public static final String COPILOT_COST_PROJECT_PROPERTY_KEY = "Copilot Cost";
	
	/**
     * Represents the "Contest Fee Percentage" project property key
     *
     */
    public static final String CONTEST_FEE_PERCENTAGE_PROJECT_PROPERTY_KEY = "Contest Fee Percentage";
	
	/**
     * Represents the "Review Feedback Flag" project property key
     *
     */
    public static final String REVIEW_FEEDBACK_FLAG_PROJECT_PROPERTY_KEY = "Review Feedback Flag";

	/**
     * Represents the "Historical Projected Cost" project property key
     *
     * @since 1.2.2
     */
    public static final String HISTORICAL_PROJECTED_COST_PROPERTY_KEY = "Historical Projected Cost";

    /**
     * Represents the "Project Activate Date" project property key
     *
     * @since 1.2.2
     */
    public static final String PROJECT_ACTIVATE_DATE_PROPERTY_KEY = "Project Activate Date";

    /**
     * Represents the "Allow Multiple Submissions" project property key
     *
     * @since 1.2.3
     */
    public static final String ALLOW_MULTIPLE_SUBMISSIONS_PROPERTY_KEY = "Allow multiple submissions";

    
    /**
     * Represents the "CloudSpokes CMC Task" project property key.
     *
     * @since 1.2.4
     */
    public static final String CLOUDSPOKES_CMC_TASK_PROPERTY_KEY = "CloudSpokes CMC Task";

    /**
     * Represents the "Forum Type" project property key.
     *
     * @since 1.2.5
     */
    public static final String FORUM_TYPE = "Forum Type";

    /**
     *  Represent "Private Project Status" project property type
     *
     *  @since 1.2.6
     */
    public static final String PRIVATE_PROJECT = "Private Project Status";

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
     * Create a new ProjectPropertyType instance with the given id and name. The
     * two fields are required for a this instance to be persisted.
     *
     * @param id
     *            The project property type id.
     * @param name
     *            The project property type name.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, or name is null or
     *             empty string.
     */
    public ProjectPropertyType(long id, String name) {
        this(id, name, "");
    }

    /**
     * Create a new ProjectPropertyType instance with the given id, name and
     * description. The two first fields are required for a this instance to be
     * persisted.
     *
     * @param id
     *            The project property type id.
     * @param name
     *            The project property type name.
     * @param description
     *            The project property type description.
     * @throws IllegalArgumentException
     *             If any id is less than or equals to zero, or name is null or
     *             empty string, or description is null.
     */
    public ProjectPropertyType(long id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    /**
     * Sets the id for this project property type instance. Only positive
     * values are allowed.
     *
     * @param id
     *            The id of this project property type instance.
     * @throws IllegalArgumentException
     *             If project property type id is less than or equals to zero.
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "id");
        this.id = id;
    }

    /**
     * Gets the id of this project property type instance.
     *
     * @return the id of this project property type instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project property type instance. Null or empty
     * values are not allowed.
     *
     * @param name
     *            The name of this project property type instance.
     * @throws IllegalArgumentException
     *             If project property type name is null or empty string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project property type instance.
     *
     * @return the name of this project property type instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description for this project property type instance. Null value
     * are not allowed.
     *
     * @param description
     *            The description of this project property type instance.
     * @throws IllegalArgumentException
     *             If project property type description is null.
     */
    public void setDescription(String description) {
        Helper.checkObjectNotNull(description, description);
        this.description = description;
    }

    /**
     * Gets the description of this project property type instance.
     *
     * @return the description of this project property type instance.
     */
    public String getDescription() {
        return description;
    }
}
