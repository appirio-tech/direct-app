/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project.ejb;

import com.topcoder.service.facade.project.ProjectServiceFacade;

import javax.ejb.Local;

/**
 * <p>An interface providing local access to {@link ProjectServiceFacade} implementation available within the same
 * application instance or <code>EJB</code> container.</p>
 *
 * <p><b>Thread safety:</b> The implementations of this interface must operate in a thread-safe manner to be used inside
 * the <code>EJB</code> container.</p>
 *
 * @author isv
 * @version 1.0
 */
@Local
public interface ProjectServiceFacadeLocal extends ProjectServiceFacade {
}
