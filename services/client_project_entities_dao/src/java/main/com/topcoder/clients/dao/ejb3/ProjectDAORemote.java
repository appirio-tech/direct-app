/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import javax.ejb.Remote;

import com.topcoder.clients.dao.ProjectDAO;

/**
 * <p>
 * This interface represents the ProjectDAORemote remote interface of the
 * session bean.
 * </p>
 * <p>
 * See base interface for other available operations.
 * </p>
 * <p>
 * Defines a static String variable containing the JNDI name of the remote
 * interface.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be
 * thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface ProjectDAORemote extends ProjectDAO {
    /**
     * <p>
     * This static final String field represents the 'JNDI_NAME' identifier of
     * the ProjectDAORemote interface. Represents the JNDI name of this remote
     * interface.
     * </p>
     * <p>
     * It is initialized to a default value: ProjectDAO.BEAN_NAME + "/remote"
     * String during runtime.
     * </p>
     */
    public static final String JNDI_NAME = ProjectDAO.BEAN_NAME + "/remote";
}
