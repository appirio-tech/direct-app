/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

/**
 * <p>
 * This interface defines the contract to serialize an Object to a string representation (typically a JSON
 * string).
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong>Implementations should be "conditionally" thread safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface AJAXDataSerializer {
    /**
     * <p>
     * Serialize given data to string representation(typically a JSON string).
     * </p>
     *
     * @param data
     *            the data to be serialized.
     * @param actionName
     *            the name of the action that is serializing the data (we allow null)
     * @return The string representation serialized from given object
     *
     * @throws IllegalArgumentException
     *             If the actionName is an empty string.
     * @throws AJAXDataSerializationException
     *             if there were issues with serialization
     */
    public String serializeData(String actionName, Object data) throws AJAXDataSerializationException;
}
