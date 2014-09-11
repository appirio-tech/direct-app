/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;

/**
 * <p>
 * This class represents the screening response associated with a screening result. Each screening
 * response is identified by a unique id. Each screening result is associated with a screening
 * response.
 * </p>
 * <p>
 * When the submission is being screened, screening response is normally entered by the screener,
 * consisting of response severity, response code and fixed response text.
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
public class ScreeningResponse implements Serializable {

    /**
     * <p>
     * Represents the unique identifier of the screening response.
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
     * Represents the response severity of the screening response.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null once it's specified. It is accessed in
     * the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private ResponseSeverity responseSeverity = null;

    /**
     * <p>
     * Represents the response code of the screening response.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null and non-empty once it's specified. It is
     * accessed in the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private String responseCode = null;

    /**
     * <p>
     * Represents the fixed response text of the screening response.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null and non-empty once it's specified. It is
     * accessed in the getter method and changed in the setter method.
     * </p>
     *
     *
     */
    private String responseText = null;

    /**
     * <p>
     * Default constructor. Does nothing.
     * </p>
     *
     */
    public ScreeningResponse() {
        // empty
    }

    /**
     * <p>
     * Sets the unique identifier of the screening response, which should be positive.
     * </p>
     *
     *
     * @param id
     *            the unique identifier of the screening response
     * @throws IllegalArgumentException
     *             if id is non-positive
     */
    public void setId(long id) {
        Helper.checkNonPositive(id, "id");
        this.id = id;
    }

    /**
     * <p>
     * Returns the unique identifier of the screening response, or -1 if it is not specified.
     * </p>
     *
     *
     *
     * @return the unique identifier of the screening response
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the response severity of the screening response.
     * </p>
     *
     *
     * @param responseSeverity
     *            the response severity of the screening response
     * @throws IllegalArgumentException
     *             if responseSeverity is null
     */
    public void setResponseSeverity(ResponseSeverity responseSeverity) {
        Helper.checkNull(responseSeverity, "responseSeverity");
        this.responseSeverity = responseSeverity;
    }

    /**
     * <p>
     * Returns the response severity of the screening response, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the response severity of the screening response
     */
    public ResponseSeverity getResponseSeverity() {
        return responseSeverity;
    }

    /**
     * <p>
     * Sets the response code of the screening response.
     * </p>
     *
     *
     * @param responseCode
     *            the response code of the screening response
     * @throws IllegalArgumentException
     *             if responseCode is null or empty String
     */
    public void setResponseCode(String responseCode) {
        Helper.checkString(responseCode, "responseCode");
        this.responseCode = responseCode;
    }

    /**
     * <p>
     * Returns the response code of the screening response, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the response code of the screening response
     */
    public String getResponseCode() {

        return responseCode;
    }

    /**
     * <p>
     * Sets the fixed response text of the screening response.
     * </p>
     *
     *
     * @param responseText
     *            the fixed response text of the screening response
     * @throws IllegalArgumentException
     *             if responseText is null or empty String
     */
    public void setResponseText(String responseText) {
        Helper.checkString(responseText, "responseText");
        this.responseText = responseText;
    }

    /**
     * <p>
     * Returns the fixed response text of the screening response, or null if it is not specified.
     * </p>
     *
     *
     *
     * @return the fixed response text of the screening response
     */
    public String getResponseText() {

        return responseText;
    }
}
