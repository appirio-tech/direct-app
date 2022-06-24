/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.admin.ejb;

import javax.ejb.Local;

import com.topcoder.service.facade.admin.AdminServiceFacade;

/**
 * <p>
 * This is the local interface for EJB 3.0 bean. It is a marker interface which simply extends the
 * <code>{@link AdminServiceFacade}</code> contract. It allows the <code>{@link AdminServiceFacade}</code> to also be accessed
 * as a local bean.
 * </p>
 * <p>
 * Implementations will simply need to implement the base contract - <code>{@link AdminServiceFacade}</code>. The marker
 * interface itself requires no special implementation.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Configurable Contest Fees v1.0 Assembly
 */
@Local
public interface AdminServiceFacadeLocal extends AdminServiceFacade {

}
