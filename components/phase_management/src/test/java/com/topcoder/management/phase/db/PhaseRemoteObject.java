/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.project.phases.Phase;

/**
 * The EJB Object used for the EJB demo.
 * @author TCSDEVELOPER
 * @version 1.1
 */
public interface PhaseRemoteObject extends EJBObject {

    /**
     * Creates the phase with the given <code>Phase</code> and operator.
     * @param phase the phase to create in database.
     * @param operator the operator who create the project.
     * @throws RemoteException if there is any remote problem.
     * @throws PhasePersistenceException if fails to create the phase in database.
     * @throws IllegalArgumentException if any arguments is <code>null</code>
     *             or the operator is empty string.
     */
    public void createPhase(Phase phase, String operator)
        throws PhasePersistenceException, RemoteException;

}