/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

/**
 * <p>
 * This interface defines the contract to pre-process an Object. Such pre-processing will simply do something
 * with the input (like a translation, conversion, or perhaps pruning of sensitive data) and return the
 * resulting output. This will typically be utilized before serialization with AJAXDataSerializer.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> Implementations are not required to be thread safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface AJAXResultPreProcessor {
    /**
     * <p>
     * Pre-process the given object. This could range from removing certain sensitive data to actually adding
     * or translating data. This will typically be called before serialization with AJAXDataSerializer.
     * </p>
     *
     * @param data
     *            the data to be pre-processed
     * @return The pre-processed object.
     *
     * @throws AJAXDataPreProcessingException
     *             if there any error with pre-processing
     */
    public Object preProcessData(Object data) throws AJAXDataPreProcessingException;
}
