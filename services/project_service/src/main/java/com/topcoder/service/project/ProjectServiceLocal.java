/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import javax.ejb.Local;

/**
 * <p>
 * This is the local interface for EJB 3.0 bean. It is a marker interface which simply extends the
 * <code>{@link ProjectService}</code> contract. It allows the <code>{@link ProjectService}</code> to also be
 * accessed as a local bean.
 * </p>
 * <p>
 * Implementations will simply need to implement the base contract - <code>{@link ProjectService}</code>. The marker
 * interface itself requires no special implementation.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations must be thread safe.
 * </p>
 *
 * @author humblefool, FireIce
 * @version 1.0
 */
@Local
public interface ProjectServiceLocal extends ProjectService {
}
