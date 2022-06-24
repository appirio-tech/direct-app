/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.impl;

import javax.ejb.Remote;

import com.topcoder.service.project.ProjectService;

/**
 * <p>
 * The bridge used to expose the <code>ProjectServiceLocal</code>.
 * </p>
 *
 * <p>
 * Itself is declared as remote interface and extends <code>ProjectService</code> directly.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
@Remote
public interface ProjectServiceLocalBridge extends ProjectService {

}
