/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.io.Serializable;

/**
 * <p>
 * This class represents the response severity of the screening response. Each response severity is
 * identified by a unique id. Each screening response is associated with a response severity.
 * </p>
 * <p>
 * Response severity indicates how serious the response is on the submission. Examples of response
 * severity include "fatal", "warning", etc.
 * </p>
 * <p>
 * This class provides the default constructor (accepting no arguments) for easy use as bean class.
 * It is not thread-safe by being mutable. However it will be used by the component in a thread-safe
 * manner (the attributes are set one by one).
 * </p>
 *
 * @author colau, haozhangr
 * @version 1.0
 */
public class ResponseSeverity implements Serializable {

    /**
     * <p>
     * Represents the unique identifier of the response severity.
     * </p>
     * <p>
     * It is initially set to -1, but should be positive once it's specified. It is accessed in the
     * getter method and changed in the setter method.
     * </p>
     */
    private long id = -1;

    /**
     * <p>
     * Represents the name of the response severity.
     * </p>
     * <p>
     * It is initially set to null, but should be non-null and non-empty once it's specified. It is
     * accessed in the getter method and changed in the setter method.
     * </p>
     */
    private String name = null;

    /**
     * <p>
     * Default constructor. It does nothing.
     * </p>
     *
     */
    public ResponseSeverity() {
        // empty
    }

    /**
     * <p>
     * Sets the unique identifier of the response severity, which should be positive.
     * </p>
     *
     *
     * @param id
     *            the unique identifier of the response severity
     * @throws IllegalArgumentException
     *             if id is non-positive
     */
    public void setId(long id) {
        Helper.checkNonPositive(id, "id");

        this.id = id;
    }

    /**
     * <p>
     * Returns the unique identifier of the response severity, or -1 if it is not specified.
     * </p>
     *
     *
     *
     * @return the unique identifier of the response severity
     */
    public long getId() {

        return id;
    }

    /**
     * <p>
     * Sets the name of the response severity.
     * </p>
     *
     *
     * @param name
     *            the name of the response severity
     * @throws IllegalArgumentException
     *             if name is null or empty String
     */
    public void setName(String name) {
        Helper.checkString(name, "name");
        this.name = name;
    }

    /**
     * <p>
     * Returns the name of the response severity, or null if it is not specified.
     * </p>
     *
     *
     * @return the name of the response severity
     */
    public String getName() {

        return name;
    }
}
