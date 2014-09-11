/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * The EJB home used for EJB Demo.
 * @author TCSDEVELOPER
 * @version 1.1
 */
public interface PhaseRemoteHome extends EJBHome {

    /**
     * Create the <code>PhaseObject</code>.
     * @return the created <code>ProjectObject</code> object.
     * @throws RemoteException if there is anything remote problem.
     * @throws CreateException if fails to create the object.
     */
    PhaseRemoteObject create() throws CreateException, RemoteException;
}