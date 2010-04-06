/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor;

/**
 * <p>An interface for processors for the requests sent to application from the client.</p>
 * 
 * @author isv
 * @version 1.0
 */
public interface RequestProcessor<T> {

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    void processRequest(T action);
}
