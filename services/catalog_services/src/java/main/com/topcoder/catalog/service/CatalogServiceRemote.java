/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import javax.ejb.Remote;

/**
  * <p>This EJB Remote interface extending the <code>CatalogService</code>.</p>
  * <p>Used to reference the <code>CatalogService</code> outside EJB container (e.g. on client side)
  * or in another EJB application.</p>
  * <p><strong>Thread-safety:</strong></p>
  * <p>implementations of this interface must be thread-safe to be used inside EJB container.</p>
  *
  * @author caru, Retunsky
  * @version 1.0
  */
@Remote
public interface CatalogServiceRemote extends CatalogService {
}



