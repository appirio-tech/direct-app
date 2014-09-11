/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.failuretests;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Remote home interface to the IDGeneratorBean stateless session bean for failure test.
 *
 * @author extra
 * @version 1.0
 */
public interface FailIDGeneratorHome extends EJBHome {
    /**
     * The default create() method for this home interface, which returns an
     * IDGenerator implementation.
     *
     * @return an IDGenerator stub implementation
     * @throws CreateException if the session bean cannot be created for some
     * reason
     * @throws RemoteException if an RMI error occurs between client and EJB
     * container
     */
    public FailIDGenerator create() throws CreateException, RemoteException;

}





