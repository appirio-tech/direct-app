/*
 * Copyright (C) 2009 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean;


/**
 * The demo for contest service facade 1.1 new added method. Using the mockup as
 * implementation.
 *
 * @author TCS Developer
 * @version 1.1
 *
 */
public class MockUpDemo {
    private ContestServiceFacadeBean contestServiceFacadeBean;

    /**
     * Demo for added methods in contest service facade 1.1. Using the mockup as
     * implementation.
     *
     * @throws Exception
     * @since 1.1
     */
    public void demo() throws Exception {
        /* Init service */
        this.contestServiceFacadeBean = new ContestServiceFacadeBean();


    }
}
