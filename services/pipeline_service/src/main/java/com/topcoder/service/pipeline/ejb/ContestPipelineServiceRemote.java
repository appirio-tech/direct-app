/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.ejb;

import javax.ejb.Remote;

import com.topcoder.service.pipeline.ContestPipelineService;
/**
 * <p>An interface providing remote access to {@link ContestPipelineService} implementation available within the remote
 * application instance or outside of the <code>EJB</code> container.</p>
 *
 * <p><b>Thread safety:</b> The implementations of this interface must operate in a thread-safe manner to be used inside
 * the <code>EJB</code> container.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface ContestPipelineServiceRemote extends ContestPipelineService {
}
