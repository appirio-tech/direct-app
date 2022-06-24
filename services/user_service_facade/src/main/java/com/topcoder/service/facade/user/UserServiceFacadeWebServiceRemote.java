/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user;

import javax.ejb.Remote;

/**
 * <p>
 * This is the remote interface for EJB 3.0 bean. It is a marker interface which simply extends the
 * <code>{@link UserServiceFacadeWebService}</code> contract. It allows the
 * <code>{@link UserServiceFacadeWebService}</code> to also be accessed as a remote bean.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author waits
 * @since Cockpit Security Facade V1.0
 * @version 1.0
 */
@Remote
public interface UserServiceFacadeWebServiceRemote extends UserServiceFacadeWebService {

}
