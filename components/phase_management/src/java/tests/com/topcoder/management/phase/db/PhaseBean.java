/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.management.phase.AbstractDbPhasePersistence;
import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.project.phases.Phase;

/**
 * The bean class used for EJB Demo.
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class PhaseBean implements SessionBean {

    /**
     * Represents the session context.
     */
    private SessionContext context;

    /**
     * Represents the persistence class.
     */
    private AbstractDbPhasePersistence persistence;

    /**
     * Creates the bean with the given namespace. <code>ProjectManager</code>
     * object.
     * @throws CreateException if fails to create the bean.
     */
    public void ejbCreate() throws CreateException {
        try {
            persistence = new UnmanagedTransactionInformixPhasePersistence(
                    "PhaseManagementConfiguration");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("Fails to create the bean.");
        }
    }

    /**
     * sets the session context, called by EJB container.
     * @param ctx the session context to set.
     */
    public void setSessionContext(SessionContext ctx) {
        context = ctx;
    }

    /**
     * The business method to create the phase.
     * @param phase the phase to create in database.
     * @param operator the operator who create the project.
     * @throws PhasePersistenceException if fails to create the phase in database.
     * @throws IllegalArgumentException if any arguments is <code>null</code>
     *             or the operator is empty string.
     */
    public void createPhase(Phase phase, String operator)
        throws PhasePersistenceException {

        if (phase == null) {
            throw new IllegalArgumentException(
                    "The argument phase could not be null.");
        }
        if (operator == null) {
            throw new IllegalArgumentException(
                    "The argument operator could not be null.");
        }
        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "The argument operator could not be empty string.");
        }
        try {

            persistence.createPhase(phase, operator);

        } catch (PhasePersistenceException e) {
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