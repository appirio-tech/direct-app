/*
 * Copyright (C) 2006 - 2018 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * The Upload class is the one of the main modeling classes of this component. It represents an uploaded document. The
 * Upload class is simply a container for a few basic data fields. All data fields in this class are mutable and have
 * get and set methods.
 * </p>
 * <p>
 * Change in 1.2:
 * <ul>
 * <li>Added description property, and the corresponding setter and getter.</li>
 * </ul>
 * </p>
 * <p>
 * This class is highly mutable. All fields can be changed.
 * </p>
 *
 * <p>
 * Version 1.3 - Topcoder - Change Download URL in Direct Application
 * - Add url property
 * </p>
 * @author aubergineanode, singlewood
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.3
 */
public class Upload extends AuditedDeliverableStructure {
    /**
     * The value that the owner field will have (and that the getOwner method will return) when the
     * owner field has not been through the setOwner method.
     */
    public static final long UNSET_OWNER = -1;

    /**
     * The value that the project field will have (and that the getProject method will return) when
     * the project field has not been through the setProject method.
     */
    public static final long UNSET_PROJECT = -1;

    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = -7816079429736635756L;

    /**
     * The type of the upload. This field can be null or non-null and is mutable. The default value
     * is null, which indicates that this field has not been set. This field can be set through the
     * setUploadType method and retrieved through the getUploadType method.
     */
    private UploadType uploadType = null;

    /**
     * The status of the upload. This field can be null or non-null and is mutable. The default
     * value is null, which indicates that this field has not been set. This field can be set
     * through the setUploadStatus method and retrieved through the getUploadStatus method.
     */
    private UploadStatus uploadStatus = null;

    /**
     * The owner of the upload, i.e. the person responsible for the upload. This field can be greater than 0 or
     * UNSET_OWNER and is mutable. The default value is UNSET_OWNER, which indicates that this field
     * has not been set or that no owner is associated with the upload. This field can be set
     * through the setOwner method and retrieved through the getOwner method.
     */
    private long owner = UNSET_OWNER;

    /**
     * The project that the upload is associated with. This field can be greater than 0 or UNSET_PROJECT and is
     * mutable. The default value is UNSET_PROJECT, which indicates that this field has not been
     * set. This field can be set through the setProject method and retrieved through the getProject
     * method.
     */
    private long project = UNSET_PROJECT;

    /**
     * The project phase the upload is associated with.
     */
    private Long projectPhase;
    
    /**
     * The parameter that identifies the uploaded file (and implicitly contains information about
     * how to get the uploaded file). This field can be null or non-null and is mutable. The default
     * value is null, which indicates that this field has not been set. This field can be set
     * through the setParameter method and retrieved through the getParameter method.
     */
    private String parameter = null;

    /**
     * Represents the description of this upload.
     *
     * @since 1.2
     */
    private String description;

    /**
     * Represent the s3 url
     *
     */
    private String url;

    /**
     * Creates a new Upload.
     */
    public Upload() {
        super();
    }

    /**
     * Creates a new Upload.
     *
     * @param id The id of the Upload
     * @throws IllegalArgumentException If id is <= 0
     */
    public Upload(long id) {
        super(id);
    }

    /**
     * Sets the upload type of the upload. The value can be either null or non-null.
     *
     * @param uploadType The type of the upload.
     */
    public void setUploadType(UploadType uploadType) {
        this.uploadType = uploadType;
    }

    /**
     * Gets the upload type of the upload. The return value may be null or non-null.
     *
     * @return The type of the upload
     */
    public UploadType getUploadType() {
        return uploadType;
    }

    /**
     * Sets the status of the upload. The value can be null or non-null.
     *
     * @param uploadStatus The status of the upload.
     */
    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    /**
     * Gets the status of the upload. The return value may be null or non-null.
     *
     * @return The status of the upload.
     */
    public UploadStatus getUploadStatus() {
        return uploadStatus;
    }

    /**
     * Sets the owner of the upload. This method does not allow the owner (once set) to be unset,
     * but it does allow the owner to be changed.
     *
     * @param owner The owner of the upload
     * @throws IllegalArgumentException If owner is <= 0
     */
    public void setOwner(long owner) {
        DeliverableHelper.checkGreaterThanZero(owner, "owner", null);
        this.owner = owner;
    }

    /**
     * Gets the owner of the upload. This method will return UNSET_OWNER or a value greater than 0.
     *
     * @return The owner of the upload.
     */
    public long getOwner() {
        return owner;
    }

    /**
     * Sets the project the upload is associated with. This method does not allow the project (once
     * set) to be unset, but it does allow the project to be changed.
     *
     * @param project The project the upload is associated with
     * @throws IllegalArgumentException If project is <= 0
     */
    public void setProject(long project) {
        DeliverableHelper.checkGreaterThanZero(project, "project", null);
        this.project = project;
    }

    /**
     * Gets the project that the upload is associated with. This method will return UNSET_PROJECT or
     * a value greater than 0.
     *
     * @return The project the upload is associated with.
     */
    public long getProject() {
        return project;
    }

    /**
     * Sets the parameter that identifies the uploaded file (tells where to find it). The value may
     * be null or non-null, and when non-null, any value is allowed.
     *
     * @param parameter The identifier for the uploaded file.
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * Gets the parameter that identifies the uploaded file (tells where it is). The return value
     * may be null or non-null.
     *
     * @return The identifier for the uploaded file
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * Retrieves the description of this upload.
     *
     * @return the description of this upload.
     * @since 1.2
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this upload.
     *
     * @param description
     *            the description of this upload.
     * @since 1.2
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the project phase of this upload.
     * 
     * @return the project phase of this upload.
     */
    public Long getProjectPhase() {
        return projectPhase;
    }

    /**
     * Sets the project phase of this upload.
     * 
     * @param projectPhase the project phase of this upload
     */
    public void setProjectPhase(Long projectPhase) {
        this.projectPhase = projectPhase;
    }

    /**
     * Tells whether all the required fields of this Submission have values set.
     *
     * @return True if all fields required for persistence are present
     */
    public boolean isValidToPersist() {
        // This method returns true if all of the following are true: id is not UNSET_ID, uploadType
        // is not null, uploadStatus is not null, owner is not UNSET_OWNER, project is not
        // UNSET_PROJECT, parameter is not null, base.isValidToPersist
        return ((super.getId() != UNSET_ID)
                    && (uploadType != null)
                    && (uploadType.isValidToPersist())
                    && (uploadStatus != null)
                    && (uploadStatus.isValidToPersist())
                    && (owner != UNSET_OWNER)
                    && (project != UNSET_PROJECT)
                    && (parameter != null)
                    && (super.isValidToPersist()));
    }

    /**
     * Get url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
