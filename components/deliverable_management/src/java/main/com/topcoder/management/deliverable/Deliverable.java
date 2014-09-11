/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.Date;

/**
 * <p>
 * The Deliverable class is the one of the main modeling classes of this component. It represents an
 * item that must be delivered for the project. The Deliverable class is simply a container for a
 * few basic data fields, but unlike the Upload and Submission class, a deliverable is largely
 * immutable. The data fields (except for completion date) have only get methods.
 * </p>
 * <p>
 * This class is highly immutable.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public class Deliverable extends NamedDeliverableStructure {
    /**
     * <p>
     * The serial version id.
     * </p>
     */
    private static final long serialVersionUID = 8418943162037718549L;

    /**
     * The identifier of the project the Deliverable is associated with. This field is set in the
     * constructor and is immutable. This field must always be greater than 0.
     */
    private final long project;

    /**
     * The identifier of the phase the Deliverable is associated with. This field is set in the
     * constructor and is immutable. This field must always be greater than 0.
     */
    private final long phase;

    /**
     * The identifier of the resource the Deliverable is associated with. This field is set in the
     * constructor and is immutable. This field must always be greater than 0.
     */
    private final long resource;

    /**
     * The identifier of the submission that is associated with the deliverable. This field is set
     * in the constructor and is immutable. This field may be null or non-null. If non-null, it must
     * be greater than 0. A deliverable can be associated with a submission only if it is a "per submission"
     * deliverable.
     */
    private Long submission;

    /**
     * Tells whether the deliverable is required to be completed for the project phase to end. This
     * field is set in the constructor.
     */
    private final boolean required;

    /**
     * The date on which the deliverable was completed. If the deliverable has not been completed,
     * this field will be null. This field is set in the constructor, can be null or non-null, and
     * is mutable.
     */
    private Date completionDate = null;

    /**
     * Creates a new Deliverable. Sets all fields to the given values.
     *
     * @param project The project that the deliverable is associated with
     * @param phase The phase that the deliverable is associated with
     * @param resource The resource that the deliverable is associated with
     * @param submission The submission that the deliverable is associated with, can be null
     * @param required True if the deliverable is required for the project phase to end
     * @throws IllegalArgumentException If submission is not null, but <= 0
     * @throws IllegalArgumentException If project, phase, resource is <= 0
     */
    public Deliverable(long project, long phase, long resource, Long submission, boolean required) {
        DeliverableHelper.checkGreaterThanZero(project, "project", null);
        DeliverableHelper.checkGreaterThanZero(phase, "phase", null);
        DeliverableHelper.checkGreaterThanZero(resource, "resource", null);

        // If is submission is not null, check if it is greater than 0.
        if (submission != null) {
            DeliverableHelper.checkGreaterThanZero(submission.longValue(), "submission", null);
        }

        this.project = project;
        this.phase = phase;
        this.resource = resource;
        this.submission = submission;
        this.required = required;
    }

    /**
     * Gets the project that the deliverable is associated with. This method will return a value greater than
     * 0.
     *
     * @return The project the deliverable is associated with
     */
    public long getProject() {
        return project;
    }

    /**
     * Gets the phase that the deliverable is associated with. This method will return a value greater than 0.
     *
     * @return The phase the deliverable is associated with
     */
    public long getPhase() {
        return phase;
    }

    /**
     * Gets the identifier of the resource that the deliverable is related to. This method will
     * return a value greater than 0.
     *
     * @return The identifier of the resource the deliverable is related to
     */
    public long getResource() {
        return resource;
    }

    /**
     * Sets the submission that is associated with this deliverable. If no submission is associated
     * with the deliverable, sets the submission to null, otherwise the submission value should greater
     * than 0.
     *
     * @param submission the deliverable is associated with
     * @throws IllegalArgumentException If submission &lt 0 when submission is not null.
     */
    public void setSubmission(Long submission) {
        if (submission != null) {
            DeliverableHelper.checkGreaterThanZero(submission.longValue(), "submission", null);
        }
        this.submission = submission;
    }

    /**
     * Gets the submission that is associated with this deliverable. If no submission is associated
     * with the deliverable, this method will return null, otherwise the return value will be greater than 0.
     *
     * @return The submission the deliverable is associated with
     */
    public Long getSubmission() {
        return submission;
    }

    /**
     * Tells whether the deliverable is required for the project phase to end.
     *
     * @return True if deliverable is required, false if not.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Marks the deliverable as being completed on the given date.
     *
     * @param completionDate The date the deliverable was completed.
     * @throws IllegalArgumentException If completionDate is null.
     */
    public void setCompletionDate(Date completionDate) {
        DeliverableHelper.checkObjectNotNull(completionDate, "completionDate", null);
        this.completionDate = completionDate;
    }

    /**
     * Gets the completion date of the deliverable. If the deliverable is not complete, the return
     * will be null.
     *
     * @return The completion date of the deliverable.
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Tells whether the deliverable is complete. A deliverable is complete if its completion date
     * has been set.
     *
     * @return True if the deliverable is complete, false if not complete.
     */
    public boolean isComplete() {
        return (completionDate != null);
    }

    /**
     * Tells whether the deliverable is required for each submission.
     *
     * @return True if the deliverable is required per submission.
     */
    public boolean isPerSubmission() {
        // A deliverable is "per submission" if and only if the submission field is non-null.
        return (submission != null);
    }

}
