/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax.processors;

import java.util.List;

import com.topcoder.direct.services.view.ajax.AJAXResultPreProcessor;

/**
 * <p>
 * This an implementation of the AJAXResultPreProcessor contract which simply removes some (byte based) data
 * from the objects before serialization. In this implementation we will simply remove the byte[] contents
 * from the UploadedDocument instances.
 * </p>
 *
 * <p>
 * <strong>Thread-Safety:</strong> Technically this is not thread safe since it modifies the input data.But if
 * we assume that there is no other thread will be modifying the data input object at the same time,then this
 * can be considered thread safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class DefaultAJAXResultPreProcessor implements AJAXResultPreProcessor {
    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public DefaultAJAXResultPreProcessor() {
    }

    /**
     * <p>
     * Pre-process the given object. In this implementation, it will treat some specific object types and
     * process some data pruning from these elements. No modifications will be made in this implementation.
     * </p>
     *
     * @param data
     *            the data to be pre-processed
     * @return The pre-processed object.
     */
    public Object preProcessData(Object data) {
        return data;
    }
}
