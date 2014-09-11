/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import javax.ejb.Local;

/**
 * <p>This is EJB <code>Local</code> interface extending the <code>CatalogService</code>.</p>
 * <p>Used for referencing to <code>CatalogService</code> inside the same EJB application.</p>
 *
 * <p><strong>Thread-safety:</strong></p>
 * <p>implementations of this interface must be thread-safe to be used inside EJB container.</p>
  *
  * @author caru, Retunsky
  * @version 1.0
  */
@Local
public interface CatalogServiceLocal extends CatalogService {
}



