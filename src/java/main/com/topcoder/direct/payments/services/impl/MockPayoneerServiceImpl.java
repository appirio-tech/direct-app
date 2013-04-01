/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.services.impl;

import com.topcoder.direct.payments.services.ServiceException;

/**
 * Class represented mock implementation with hard-coded values.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class MockPayoneerServiceImpl extends PayoneerServiceImpl {

    /**
     * This is just mock implementation. Always returns <code>0</code> value.
     * 
     * @return always <code>0</code> value
     */
    @Override
    public double getBalanceAmount()
        throws ServiceException {
        return 0;
    }
}
