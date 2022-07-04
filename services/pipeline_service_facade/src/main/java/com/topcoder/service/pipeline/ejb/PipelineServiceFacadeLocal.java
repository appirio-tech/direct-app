/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.ejb;

import javax.ejb.Local;

import com.topcoder.service.pipeline.PipelineServiceFacade;

/**
 * <p>
 * An interface providing local access to {@link ContestPipelineService} implementation available within the local
 * application instance or outside of the <code>EJB</code> container.
 * </p>
 * 
 * <p>
 * <b>Thread safety:</b> The implementations of this interface must operate in a thread-safe manner to be used inside
 * the <code>EJB</code> container.
 * </p>
 * 
 * @author snow01
 * @version 1.0
 * @since Pipeline Conversion Cockpit Integration Assembly 2 v1.0
 */
@Local
public interface PipelineServiceFacadeLocal extends PipelineServiceFacade {
}
