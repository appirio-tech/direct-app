/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project.ejb;

import javax.ejb.Remote;

import com.topcoder.service.facade.project.ProjectServiceFacadeWebService;

/**
 * <p>
 * An interface providing remote access to {@link ProjectServiceFacadeWebService} implementation available within the
 * same application instance or <code>EJB</code> container.
 * </p>
 * <p>
 * <b>Thread safety:</b> The implementations of this interface must operate in a thread-safe manner to be used inside
 * the <code>EJB</code> container.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
@Remote
public interface ProjectServiceFacadeWebServiceRemote extends ProjectServiceFacadeWebService {
}
