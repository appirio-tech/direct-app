/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.management.project.Prize;

/**
 * <p>
 * The Submission class is the one of the main modeling classes of this component. It represents a submission, which
 * consists of an upload and a status. The Submission class is simply a container for a few basic data fields. All data
 * fields in this class are mutable and have get and set methods.
 * </p>
 * <p>
 * <em>Changes in 1.1: </em>
 * <ul>
 * <li>submissionType attribute was added together with getter and setter.</li>
 * <li>isValidToPersist() was updated to ensure that submission type is specified.</li>
 * </ul>
 * </p>
 * <p>
 * <em>Changes in 1.2: </em>
 * <ul>
 * <li>Changed to use getUploads and setUploads methods.</li>
 * <li>extra, userRank, images and prize attributes were added together with getter and setter.</li>
 * <li>isValidToPersist() was updated to ensure that each element of uploads are checked.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <em>Changes in 1.2.1: </em>
 * <ul>
 * <li>Add setUpload method to be back compatible with other components.</li> 
 * </ul>
 * </p>
 * <p>
 * <em>Changes in 1.3: </em>
 * <ul>
 * <li>Changed to use getUpload and setUpload methods.</li>
 * <li>Changed to use upload instead uploads.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <em>Changes in 1.4 (TC Direct Replatforming Release 5): </em>
 * <ul>
 * <li>Added {@link #submissionDeclaration} field and getter/setter for it.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is highly mutable. All fields can be changed.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @author saarixx, sparemax
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.4
 */
public class Submission extends AuditedDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = -4755373779145413990L;

    /**
     * <p>
     * The uploads that are associated with the submission.
     * </p>
     * <p>
     * This field can be null or non-null and is mutable. The collection can contain null elements. The default value is
     * null, which indicates that this field has not been set. This field can be set through the setUploads method and
     * retrieved through the getUploads method.
     * </p>
     * <p>
     * Changes in version 1.2:
     * <ul>
     * <li>one submission can have multiple uploads.</li>
     * </ul>
     * </p>
     * <p>
     * Changes in version 1.3:
     * <ul>
     * <li>one submission can only have a upload.</li>
     * </ul>
     * </p>
     */
    private Upload upload = null;

    /**
     * The status of the submission. This field can be null or non-null and is mutable. The default value is null, which
     * indicates that this field has not been set.
     */
    private SubmissionStatus submissionStatus = null;

    /**
     * <p>
     * The type of the submission.
     * </p>
     * <p>
     * This field can be null or non-null and is mutable. The default value is null, which indicates that this field has
     * not been set. This field can be set through the setSubmissionType method and retrieved through the
     * getSubmissionType method.
     * </p>
     *
     * @since 1.1
     */
    private SubmissionType submissionType;

    /**
     * The screening score for the submission.
     */
    private Double screeningScore;

    /**
     * The initial score for the submission.
     */
    private Double initialScore;

    /**
     * The final score for the submission.
     */
    private Double finalScore;

    /**
     * The placement for this submission.
     */
    private Long placement;

    /**
     * The priority that the submitting user wants to give to this submission.
     *
     * @since 1.2
     */
    private int userRank = 1;

    /**
     * Represents the list of images associated with this submission.
     *
     * @since 1.2
     */
    private List<SubmissionImage> images;

    /**
     * Represents whether customer wants to buy additionally this submission.
     *
     * @since 1.2
     */
    private boolean extra;

    /**
     * Represents the prize associated with the submission.
     *
     * @since 1.2
     */
    private Prize prize;

    /**
     * Represents the submission declaration associated with the submission.
     * 
     * @since 1.4
     */
    private SubmissionDeclaration submissionDeclaration;

    /**
     * Creates a new Submission.
     */
    public Submission() {
        super();
    }

    /**
     * Creates a new Submission.
     *
     * @param id
     *            The id of the submission
     * @throws IllegalArgumentException
     *             if id is <= 0
     */
    public Submission(long id) {
        super(id);
    }

    /**
     * Sets the uploads associated with the Submission. The parameter may be null or non-null.
     * <p>
     * Change in 1.2: Updated from setUpload(upload:Upload) method.
     * </p>
     * <p>
     * Change in 1.3: Updated from setUploads(uploads:List&lt;Upload&gt;) method.
     * </p>
     *
     * @param upload
     *            The upload associated with the Submission.
     * @since 1.2
     */
    public void setUpload(Upload upload) {
        this.upload = upload;
    }

    /**
     * Gets the uploads associated with the Submission. May return null or non-null.
     * <p>
     * Change in 1.2: Updated from getUpload():Upload method.
     * </p>
     * <p>
     * Change in 1.3: Updated from getUploads():Upload method.
     * </p>
     *
     * @return The upload associated with the Submission.
     */
    public Upload getUpload() {
        return upload;
    }

    /**
     * Sets the submission status of the Submission. The parameter may be null or non-null.
     *
     * @param submissionStatus
     *            The status of the submission.
     */
    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    /**
     * Gets the submission status of the Submission. May return null or non-null.
     *
     * @return The status of the submission.
     */
    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    /**
     * <p>
     * Sets the submission type of the Submission. The parameter may be null or non-null.
     * </p>
     *
     * @param submissionType
     *            the type of the submission.
     * @since 1.1
     */
    public void setSubmissionType(SubmissionType submissionType) {
        this.submissionType = submissionType;
    }

    /**
     * <p>
     * Gets the submission type of the Submission. May return null or non-null.
     * </p>
     *
     * @return the type of the submission.
     * @since 1.1
     */
    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    /**
     * Returns the finalScore field value.
     *
     * @return the finalScore
     */
    public Double getFinalScore() {
        return finalScore;
    }

    /**
     * Sets the finalScore with the given value.
     *
     * @param finalScore
     *            the finalScore to set
     */
    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    /**
     * Returns the initialScore field value.
     *
     * @return the initialScore
     */
    public Double getInitialScore() {
        return initialScore;
    }

    /**
     * Sets the initialScore with the given value.
     *
     * @param initialScore
     *            the initialScore to set
     */
    public void setInitialScore(Double initialScore) {
        this.initialScore = initialScore;
    }

    /**
     * Returns the placement field value.
     *
     * @return the placement
     */
    public Long getPlacement() {
        return placement;
    }

    /**
     * Sets the placement with the given value.
     *
     * @param placement
     *            the placement to set
     */
    public void setPlacement(Long placement) {
        this.placement = placement;
    }

    /**
     * Returns the screeningScore field value.
     *
     * @return the screeningScore
     */
    public Double getScreeningScore() {
        return screeningScore;
    }

    /**
     * Sets the screeningScore with the given value.
     *
     * @param screeningScore
     *            the screeningScore to set
     */
    public void setScreeningScore(Double screeningScore) {
        this.screeningScore = screeningScore;
    }

    /**
     * Retrieves the priority that the submitting user wants to give to this submission.
     *
     * @return the priority that the submitting user wants to give to this submission.
     * @since 1.2
     */
    public int getUserRank() {
        return userRank;
    }

    /**
     * Sets the priority that the submitting user wants to give to this submission.
     *
     * @param userRank
     *            the priority that the submitting user wants to give to this submission.
     * @since 1.2
     */
    public void setUserRank(int userRank) {
        this.userRank = userRank;
    }

    /**
     * Retrieves the list of images associated with this submission.
     *
     * @return the list of images associated with this submission.
     * @since 1.2
     */
    public List<SubmissionImage> getImages() {
        return images;
    }

    /**
     * Sets the list of images associated with this submission.
     *
     * @param images
     *            the list of images associated with this submission.
     * @since 1.2
     */
    public void setImages(List<SubmissionImage> images) {
        this.images = images;
    }

    /**
     * Retrieves the value that represents whether customer wants to buy additionally this submission.
     *
     * @return the value that represents whether customer wants to buy additionally this submission.
     * @since 1.2
     */
    public boolean isExtra() {
        return extra;
    }

    /**
     * Sets the value that represents whether customer wants to buy additionally this submission.
     *
     * @param extra
     *            the value that represents whether customer wants to buy additionally this submission.
     * @since 1.2
     */
    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    /**
     * Retrieves the prize associated with this submission.
     *
     * @return the prize associated with this submission.
     * @since 1.2
     */
    public Prize getPrize() {
        return prize;
    }

    /**
     * Sets the prize associated with this submission.
     *
     * @param prize
     *            the prize associated with this submission.
     * @since 1.2
     */
    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    /**
     * Gets the submission declaration associated with the submission.
     * 
     * @return the submission declaration associated with the submission.
     * @since 1.4
     */
    public SubmissionDeclaration getSubmissionDeclaration() {
        return submissionDeclaration;
    }

    /**
     * Sets the submission declaration associated with the submission.
     * 
     * @param submissionDeclaration the submission declaration associated with the submission.
     * @since 1.4
     */
    public void setSubmissionDeclaration(SubmissionDeclaration submissionDeclaration) {
        this.submissionDeclaration = submissionDeclaration;
    }

    /**
     * <p>
     * Tells whether all the required fields of this Submission have values set.
     * </p>
     * <p>
     * Changes in 1.1:
     * <ul>
     * <li>Added check "submissionType is not null and isValidToPersist"</li>
     * </ul>
     * </p>
     * <p>
     * Changes in 1.2:
     * <ul>
     * <li>Checking uploads instead of upload.</li>
     * <li>Added check for images.</li>
     * </ul>
     * </p>
     * <p>
     * Changes in 1.2:
     * <ul>
     * <li>Checking upload instead of uploads.</li>
     * </ul>
     * </p>
     *
     * @return True if all fields required for persistence are present
     */
    public boolean isValidToPersist() {
        // This method returns true if all of the following are true: id is not UNSET_ID, uploads is
        // not null, each element in uploads is not null and isValidToPersist, submissionStatus is not null and
        // isValidToPersist, submissionType is not null and isValidToPersist, super.isValidTopPersist is true.
        return ((super.getId() != UNSET_ID) && (upload != null && upload.isValidToPersist()) && (submissionStatus != null)
                && (submissionStatus.isValidToPersist()) && (submissionType != null)
                && (submissionType.isValidToPersist()) && super.isValidToPersist());
    }
}
