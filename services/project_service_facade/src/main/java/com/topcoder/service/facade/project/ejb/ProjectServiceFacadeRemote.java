/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project.ejb;

import com.topcoder.service.facade.project.ProjectServiceFacade;

import javax.ejb.Remote;

/**
 * <p>An interface providing remote access to {@link ProjectServiceFacade} implementation available within the remote
 * application instance or outside of the <code>EJB</code> container.</p>
 *
 * <p><b>Thread safety:</b> The implementations of this interface must operate in a thread-safe manner to be used inside
 * the <code>EJB</code> container.</p>
 *
 * @author isv
 * @version 1.0
 */
@Remote
public interface ProjectServiceFacadeRemote extends ProjectServiceFacade {
}
