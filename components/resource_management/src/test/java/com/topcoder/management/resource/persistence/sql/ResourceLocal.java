/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import javax.ejb.EJBHome;

import com.topcoder.management.resource.persistence.ResourcePersistence;

/**
 * <p>
 * EJBHome interface as part of the UnmanagedResourcePersistence demo.
 * </p>
 *
 * @author mittu
 * @version 1.1
 */
public interface ResourceLocal extends EJBHome, ResourcePersistence {
}
