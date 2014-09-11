/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.persistence;

import javax.ejb.Remote;

import com.topcoder.service.project.ProjectPersistence;

/**
 * <p>
 * This interface is the remote interface for ProjectPersistenceBean.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface ProjectPersistenceRemote extends ProjectPersistence {

}
