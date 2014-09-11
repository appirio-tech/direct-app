/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;

/**
 * The EJB Object used for the EJB demo.
 *
 * @author fuyun
 * @version 1.1
 */
public interface ProjectObject extends EJBObject {

    /**
     * Creates the project with the given <code>Project</code> and operator.
     *
     * @param project
     *            the project to create in database.
     * @param operator
     *            the operator who create the project.
     * @throws RemoteException
     *             if there is any remote problem.
     * @throws PersistenceException
     *             if fails to create the project in database.
     * @throws IllegalArgumentException
     *             if any arguments is <code>null</code> or the operator is empty string.
     */
    public void createProject(Project project, String operator) throws RemoteException, PersistenceException;

}