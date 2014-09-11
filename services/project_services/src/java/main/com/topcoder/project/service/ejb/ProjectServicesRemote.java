/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.project.service.ejb;

import com.topcoder.project.service.ProjectServices;
import javax.ejb.Remote;

/**
 * <p>
 * This is the <code>Remote</code> interface of <code>ProjectServicesBean</code> ejb. It
 * inherits the ProjectServicesBean' methods and it has the &quot;@Remote&quot; annotation.
 * &quot;Remote&quot; refers to &quot;java.ejb.Remote&quot;. Since EJB 3.0 this is the mechanism to
 * construct the EJB interfaces.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> Implementations must be thread-safe.
 * </p>
 *
 * @author fabrizyo, znyyddf
 * @version 1.1
 * @since 1.1
 */
@Remote
public interface ProjectServicesRemote extends ProjectServices {
}