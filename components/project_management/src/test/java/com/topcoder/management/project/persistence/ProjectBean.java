/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;

/**
 * The bean class used for EJB Demo.
 *
 * @author fuyun
 * @version 1.1
 */
public class ProjectBean implements SessionBean {

    /**
     * Represents the session context.
     */
    private SessionContext context;

    /**
     * Represents the persistence class.
     */
    private AbstractInformixProjectPersistence persistence;

    /**
     * Creates the bean.
     *
     * @throws CreateException
     *             if fails to create the bean.
     */
    public void ejbCreate() throws CreateException {
        try {
            persistence = new UnmanagedTransactionInformixProjectPersistence(
                "com.topcoder.management.project.persistence");
        } catch (Exception e) {
            throw new CreateException("Fails to create the bean.");
        }
    }

    /**
     * sets the session context, called by EJB container.
     *
     * @param ctx
     *            the session context to set.
     */
    public void setSessionContext(SessionContext ctx) {
        context = ctx;
    }

    /**
     * The business method to create the project.
     *
     * @param project
     *            the project to create in database.
     * @param operator
     *            the operator who create the project.
     * @throws PersistenceException
     *             if fails to create the project in database.
     * @throws IllegalArgumentException
     *             if any arguments is <code>null</code> or the operator is empty string.
     */
    public void createProject(Project project, String operator) throws PersistenceException {

        if (project == null) {
            throw new IllegalArgumentException("The argument project could not be null.");
        }
        if (operator == null) {
            throw new IllegalArgumentException("The argument operator could not be null.");
        }
        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("The argument operator could not be empty string.");
        }
        try {

            persistence.createProject(project, operator);

        } catch (PersistenceException e) {
            // if the operation fails, rollback the transaction
            context.setRollbackOnly();
            throw e;
        }
    }

    /**
     * It is called when the bean is removed.
     */
    public void ejbRemove() {
        System.out.println("ejbRemove");
    }

    /**
     * It is called when the bean is activated.
     */
    public void ejbActivate() {
        System.out.println("ejbActive");
    }

    /**
     * It is called when the bean is passivated.
     */
    public void ejbPassivate() {
        System.out.println("ejbPassivate");
    }
}