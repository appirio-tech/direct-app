/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;

/**
 * <p>
 * This class represents a piece of screening result of the screening task. Each screening result is
 * identified by a unique id. Each screening task is associated with multiple screening results.
 * </p>
 * <p>
 * When the screening task is first initiated, it contains no screening results. Screening results
 * are normally entered by the screener, consisting of the screening response and dynamic text.
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
public class ScreeningResult implements Serializable {

    /**
     * <p>
     * Represents the unique identifier of the screening result.
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
     * Represents the screening response associated with the screening result.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null once it's specified. It is accessed in
     * the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private ScreeningResponse screeningResponse = null;

    /**
     * <p>
     * Represents the dynamic text of the screening result.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null and non-empty once it's specified. It is
     * accessed in the getter method and changed in the setter method.
     * </p>
     *
     */
    private String dynamicText = null;

    /**
     * <p>
     * Default constructor. Does nothing.
     * </p>
     *
     */
    public ScreeningResult() {
        // empty
    }

    /**
     * <p>
     * Sets the unique identifier of the screening result, which should be positive.
     * </p>
     *
     *
     * @param id
     *            the unique identifier of the screening result
     * @throws IllegalArgumentException
     *             if id is non-positive
     */
    public void setId(long id) {
        Helper.checkNonPositive(id, "id");
        this.id = id;
    }

    /**
     * <p>
     * Returns the unique identifier of the screening result, or -1 if it is not specified.
     * </p>
     *
     *
     *
     * @return the unique identifier of the screening result
     */
    public long getId() {

        return id;
    }

    /**
     * <p>
     * Sets the screening response associated with the screening result.
     * </p>
     *
     *
     * @param screeningResponse
     *            the screening response associated with the screening result
     * @throws IllegalArgumentException
     *             if screeningResponse is null
     */
    public void setScreeningResponse(ScreeningResponse screeningResponse) {
        Helper.checkNull(screeningResponse, "screeningResponse");
        this.screeningResponse = screeningResponse;
    }

    /**
     * <p>
     * Returns the screening response associated with the screening result, or null if it is not
     * specified.
     * </p>
     *
     *
     *
     * @return the screening response associated with the screening result
     */
    public ScreeningResponse getScreeningResponse() {

        return screeningResponse;
    }

    /**
     * <p>
     * Sets the dynamic text of the screening result.
     * </p>
     *
     *
     * @param dynamicText
     *            the dynamic text of the screening result
     * @throws IllegalArgumentException
     *             if dynamicText is null or empty String
     */
    public void setDynamicText(String dynamicText) {
        Helper.checkString(dynamicText, "dynamicText");
        this.dynamicText = dynamicText;
    }

    /**
     * <p>
     * Returns the dynamic text of the screening result, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the dynamic text of the screening result
     */
    public String getDynamicText() {

        return dynamicText;
    }
}
