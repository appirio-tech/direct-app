/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * This class encapsulates the task of screening an uploaded submission by a screener. Each
 * screening task is identified by a unique id.
 * </p>
 * <p>
 * When the screening proceeds, the screening task can have its status changed. Screening results
 * can be added or removed depending on the screener. The auditing fields should be updated
 * accordingly.
 * </p>
 * <p>
 * This class provides the default constructor (accepting no arguments) for easy use as bean class.
 * </p>
 * <p>
 * It is not thread-safe by being mutable. However it will be used by the component in a thread-safe
 * manner (the attributes are set one by one).
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class ScreeningTask implements Serializable {

    /**
     * <p>
     * Represents the unique identifier of the screening task.
     * </p>
     * <p>
     * It is initially set to -1, but should be positive once it's specified. It is accessed in the
     * getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private long id = -1;

    /**
     * <p>
     * Represents the screening status of the screening task.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null once it's specified. It is accessed in
     * the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private ScreeningStatus screeningStatus = null;

    /**
     * <p>
     * Represents the upload identifier of the submission.
     * </p>
     * <p>
     * It is initially set to -1, but should be positive once it's specified. It is accessed in the
     * getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private long upload = -1;

    /**
     * <p>
     * Represents the screener whose job is to do the screening.
     * </p>
     * <p>
     * It is initially set to -1, but should be positive once it's specified. It is accessed in the
     * getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private long screener = -1;

    /**
     * <p>
     * Represents the timestamp when the screening starts.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null once it's specified. It is accessed in
     * the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private Date startTimestamp = null;

    /**
     * <p>
     * Represents the list of ScreeningResult instances. These should be the results entered by the
     * screener.
     * </p>
     * <p>
     * It is initialized as a LinkedList (for easy addition and removal). The reference is never
     * changed. It can be empty. The elements are accessed in the getAllScreeningResults() method,
     * added in the addScreeningResult() method, removed in the removeScreeningResult() method,
     * cleared in the clearAllScreeningResults() method.
     * </p>
     *
     *
     */
    private final List screeningResults = new LinkedList();

    /**
     * <p>
     * Represents the creation user of the screening task. Used for auditing purpose.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null and non-empty once it's specified. It is
     * accessed in the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private String creationUser = null;

    /**
     * <p>
     * Represents the creation timestamp of the screening task. Used for auditing purpose.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null once it's specified. It is accessed in
     * the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private Date creationTimestamp = null;

    /**
     * <p>
     * Represents the modification user of the screening task. Used for auditing purpose.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null and non-empty once it's specified. It is
     * accessed in the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private String modificationUser = null;

    /**
     * <p>
     * Represents the modification timestamp of the screening task. Used for auditing purpose.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null once it's specified. It is accessed in
     * the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private Date modificationTimestamp = null;

    /**
     * <p>
     * Default constructor. Does nothing.
     * </p>
     *
     */
    public ScreeningTask() {
        // empty
    }

    /**
     * <p>
     * Sets the unique identifier of the screening task, should be positive.
     * </p>
     *
     *
     * @param id
     *            the unique identifier of the screening task
     * @throws IllegalArgumentException
     *             if id is non-positive
     */
    public void setId(long id) {
        Helper.checkNonPositive(id, "id");
        this.id = id;
    }

    /**
     * <p>
     * Returns the unique identifier of the screening task, or -1 if it is not specified.
     * </p>
     *
     *
     *
     * @return the unique identifier of the screening task
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the screening status of the screening task.
     * </p>
     *
     *
     * @param screeningStatus
     *            the screening status of the screening task
     * @throws IllegalArgumentException
     *             if screeningStatus is null
     */
    public void setScreeningStatus(ScreeningStatus screeningStatus) {
        Helper.checkNull(screeningStatus, "screeningStatus");
        this.screeningStatus = screeningStatus;
    }

    /**
     * <p>
     * Returns the screening status of the screening task, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the screening status of the screening task
     */
    public ScreeningStatus getScreeningStatus() {

        return screeningStatus;
    }

    /**
     * <p>
     * Sets the upload identifier of the submission, which should be positive.
     * </p>
     *
     *
     * @param upload
     *            the upload identifier of the submission
     * @throws IllegalArgumentException
     *             if upload is non-positive
     */
    public void setUpload(long upload) {
        Helper.checkNonPositive(upload, "upload");
        this.upload = upload;
    }

    /**
     * <p>
     * Returns the upload identifier of the submission, or -1 if it is not specified.
     * </p>
     *
     *
     * @return the upload identifier of the submission
     */
    public long getUpload() {

        return upload;
    }

    /**
     * <p>
     * Sets the screener whose job is to do the screening, which should be positive.
     * </p>
     *
     *
     * @param screener
     *            the screener whose job is to do the screening
     * @throws IllegalArgumentException
     *             if screener is non-positive
     */
    public void setScreener(long screener) {
        Helper.checkNonPositive(screener, "screener");
        this.screener = screener;
    }

    /**
     * <p>
     * Returns the screener whose job is to do the screening, or -1 if it is not specified.
     * </p>
     *
     *
     *
     * @return the screener whose job is to do the screening
     */
    public long getScreener() {

        return screener;
    }

    /**
     * <p>
     * Sets the timestamp when the screening starts.
     * </p>
     *
     *
     * @param startTimestamp
     *            the timestamp when the screening starts
     * @throws IllegalArgumentException
     *             if startTimestamp is null
     */
    public void setStartTimestamp(Date startTimestamp) {
        Helper.checkNull(startTimestamp, "startTimestamp");
        this.startTimestamp = startTimestamp;
    }

    /**
     * <p>
     * Returns the timestamp when the screening starts, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the timestamp when the screening starts
     */
    public Date getStartTimestamp() {

        return startTimestamp;
    }

    /**
     * <p>
     * Adds the specified screening result to this screening task.
     * </p>
     *
     *
     * @param screeningResult
     *            the screening result to add
     * @throws IllegalArgumentException
     *             if screeningResult is null, or the screeningResult has already been added
     */
    public void addScreeningResult(ScreeningResult screeningResult) {
        Helper.checkNull(screeningResult, "screeningResult");
        if (screeningResults.contains(screeningResult)) {
            throw new IllegalArgumentException("The 'screeningResult' has already been added.");
        }
        screeningResults.add(screeningResult);
    }

    /**
     * <p>
     * Removes the specified screening result from this screening task. If the screening result does
     * not exist, nothing happens.
     * </p>
     *
     *
     * @param screeningResult
     *            the screening result to remove
     * @throws IllegalArgumentException
     *             if screeningResult is null
     */
    public void removeScreeningResult(ScreeningResult screeningResult) {
        Helper.checkNull(screeningResult, "screeningResult");
        screeningResults.remove(screeningResult);
    }

    /**
     * <p>
     * Gets all the screening results of this screening task. If there are no screening results,
     * return an empty array.
     * </p>
     *
     *
     *
     * @return he screening results of this screening task
     */
    public ScreeningResult[] getAllScreeningResults() {

        return (ScreeningResult[]) screeningResults.toArray(new ScreeningResult[0]);
    }

    /**
     * <p>
     * Clears all the screening results of this screening task.
     * </p>
     *
     */
    public void clearAllScreeningResults() {
        screeningResults.clear();
    }

    /**
     * <p>
     * Sets the creation user of the screening task. Used for auditing purpose.
     * </p>
     *
     *
     * @param creationUser
     *            the creation user of the screening task
     * @throws IllegalArgumentException
     *             if creationUser is null or empty String
     */
    public void setCreationUser(String creationUser) {
        Helper.checkString(creationUser, "creationUser");
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Returns the creation user of the screening task, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the creation user of the screening task
     */
    public String getCreationUser() {

        return creationUser;
    }

    /**
     * <p>
     * Sets the creation timestamp of the screening task. Used for auditing purpose.
     * </p>
     *
     *
     * @param creationTimestamp
     *            the creation timestamp of the screening task
     * @throws IllegalArgumentException
     *             if creationTimestamp is null
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        Helper.checkNull(creationTimestamp, "creationTimestamp");
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * <p>
     * Returns the creation timestamp of the screening task, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the creation timestamp of the screening task
     */
    public Date getCreationTimestamp() {

        return creationTimestamp;
    }

    /**
     * <p>
     * Sets the modification user of the screening task. Used for auditing purpose.
     * </p>
     *
     *
     * @param modificationUser
     *            the modification user of the screening task
     * @throws IllegalArgumentException
     *             if modificationUser is null or empty String
     */
    public void setModificationUser(String modificationUser) {
        Helper.checkString(modificationUser, "modificationUser");
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Returns the modification user of the screening task, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the modification user of the screening task
     */
    public String getModificationUser() {

        return modificationUser;
    }

    /**
     * <p>
     * Sets the modification timestamp of the screening task. Used for auditing purpose.
     * </p>
     *
     *
     * @param modificationTimestamp
     *            the modification timestamp of the screening task
     * @throws IllegalArgumentException
     *             if modificationTimestamp is null
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        Helper.checkNull(modificationTimestamp, "modificationTimestamp");
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * <p>
     * Returns the modification timestamp of the screening task, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return he modification timestamp of the screening task
     */
    public Date getModificationTimestamp() {

        return modificationTimestamp;
    }
}
