/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.permission.ejb;

import com.topcoder.service.facade.permission.PermissionServiceFacade;

import javax.ejb.Remote;

/**
 * Remote interface definition for TopCoder permission service facade.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface PermissionServiceFacadeRemote extends PermissionServiceFacade {
}
