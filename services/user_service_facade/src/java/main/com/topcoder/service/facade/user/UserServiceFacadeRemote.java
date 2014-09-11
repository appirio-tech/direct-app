/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user;

import javax.ejb.Remote;

/**
 * <p>
 * This is the remote interface for EJB 3.0 bean. It is a marker interface which simply extends the
 * <code>{@link UserService}</code> contract. It allows the <code>{@link UserService}</code> to also be accessed
 * as a local bean.
 * </p>
 * <p>
 * Implementations will simply need to implement the base contract - <code>{@link UserSyncService}</code>. The marker
 * interface itself requires no special implementation.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 * 
 * @author snow01
 * 
 * @since Jira & Confluence User Service
 * @version 1.0
 */
@Remote
public interface UserServiceFacadeRemote extends UserServiceFacade {

}
