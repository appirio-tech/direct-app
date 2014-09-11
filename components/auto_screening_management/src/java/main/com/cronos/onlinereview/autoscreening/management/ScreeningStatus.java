/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;

/**
 * <p>
 * This class represents the screening status of the screening task. Each screening status is
 * identified by a unique id. Each screening task is associated with a screening status.
 * </p>
 * <p>
 * When the screening task is first initiated, its screening status is set to PENDING.
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
public class ScreeningStatus implements Serializable {

    /**
     * <p>
     * Represents the name of pending status when the uploaded submission is pending to be screened.
     * The screening task is set to this status when it is initiated.
     * </p>
     *
     *
     */
    public static final String PENDING = "Pending";

    /**
     * <p>
     * Represents the name of screening status when the uploaded submission is being screened by the
     * screener.
     * </p>
     *
     *
     */
    public static final String SCREENING = "Screening";

    /**
     * <p>
     * Represents the name of failed status when the uploaded submission has failed the screening.
     * This may happen when the submission does not meet the screening requirements.
     * </p>
     *
     *
     */
    public static final String FAILED = "Failed";

    /**
     * <p>
     * Represents the name of passed status when the uploaded submission has passed the screening.
     * This happens when the submission fully meets the screening requirements.
     * </p>
     *
     *
     */
    public static final String PASSED = "Passed";

    /**
     * <p>
     * Represents the name of passed status when the uploaded submission has passed the screening
     * with warning. This may happen when the submission meets the screening requirements, but has
     * some minor violations such as coding style.
     * </p>
     *
     *
     */
    public static final String PASSED_WITH_WARNING = "Passed With Warning";

    /**
     * <p>
     * Represents the unique identifier of the screening status.
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
     * Represents the name of the screening status.
     * </p>
     * <p>
     * It is initially set to null, but should be one of the defined static constants once it's
     * specified. It is accessed in the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private String name = null;

    /**
     * <p>
     * Default constructor. Does nothing.
     * </p>
     *
     */
    public ScreeningStatus() {
        // empty
    }

    /**
     * <p>
     * Sets the unique identifier of the screening status, which should be positive.
     * </p>
     *
     *
     * @param id
     *            the unique identifier of the screening status
     * @throws IllegalArgumentException
     *             if id is non-positive
     */
    public void setId(long id) {
        Helper.checkNonPositive(id, "id");
        this.id = id;
    }

    /**
     * <p>
     * Returns the unique identifier of the screening status, or -1 if it is not specified.
     * </p>
     *
     *
     *
     * @return the unique identifier of the screening status
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the name of the screening status, should be one of the defined static constants of this
     * class.
     * </p>
     *
     *
     *
     * @param name
     *            the name of the screening status
     * @throws IllegalArgumentException
     *             if name is not one of the defined static constants
     */
    public void setName(String name) {
        if (!FAILED.equals(name) && !PASSED.equals(name) && !PASSED_WITH_WARNING.equals(name)
                && !PENDING.equals(name) && !SCREENING.equals(name)) {
            throw new IllegalArgumentException("The 'name' is should be one of following name:"
                    + FAILED + "/" + PASSED + "/" + PASSED_WITH_WARNING + "/" + PENDING + "/"
                    + SCREENING);
        }
        this.name = name;
    }

    /**
     * <p>
     * Returns the name of the screening status, or null if it is not specified.
     * </p>
     *
     *
     * @return the name of the screening status
     */
    public String getName() {
        return name;
    }
}
