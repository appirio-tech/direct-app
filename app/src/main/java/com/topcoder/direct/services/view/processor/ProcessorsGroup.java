/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor;

import com.topcoder.direct.services.view.action.TopCoderDirectAction;

/**
 * <p>A request processors which groups other processors and executes them sequentially for processing the requests.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProcessorsGroup implements RequestProcessor<TopCoderDirectAction> {

    /**
     * <p>A <code>RequestProcessor</code> array listing the processors.</p>
     */
    private RequestProcessor[] processors;

    /**
     * <p>Constructs new <code>ProcessorsGroup</code> instance with specified list of processors to execute.</p>
     *
     * @param processors a <code>RequestProcessor</code> array listing the processors.
     */
    public ProcessorsGroup(RequestProcessor[] processors) {
        if (processors == null) {
            throw new IllegalArgumentException("The parameter [processors] is NULL");
        }
        this.processors = processors;
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(TopCoderDirectAction action) {
        for (int i = 0; i < this.processors.length; i++) {
            RequestProcessor processor = this.processors[i];
            processor.processRequest(action);
            if (TopCoderDirectAction.RC_UNEXPECTED_ERROR == action.getResultCode()) {
                return;
            }
        }
    }
}
