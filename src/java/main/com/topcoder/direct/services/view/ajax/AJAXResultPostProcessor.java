/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

/**
 * <p>
 * This interface defines the contract to post-process a String such post-processing will simply do something
 * with the input (like a additional translation, conversion, or perhaps pruning of sensitive data) and return
 * the resulting output. This will typically be utilized after serialization with AJAXDataSerializer.
 * </p>
 *
 * <p>
 * <strong>Thread-Safety</strong> Implementations are not required to be thread safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public interface AJAXResultPostProcessor {
    /**
     * <p>
     * Post-process the given object. This could range from removing certain sensitive data to actually adding
     * or translating data. This will typically be called after serialization with AJAXDataSerializer.
     * </p>
     *
     * @param data
     *            the data to be post-processed
     * @return The post-processed string.
     *
     * @throws IllegalArgumentException
     *             If given data is null.
     * @throws AJAXDataPostProcessingException
     *             if there were issues with post-processing
     */
    public String postProcessData(String data) throws AJAXDataPostProcessingException;
}
