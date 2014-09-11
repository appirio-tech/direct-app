/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.failuretests;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Failure test helper class.
 *
 * @author extra
 * @version 1.0
 */
final class FailureTestHelper {

    /**
     * <p>
     * The Spring bean factory.
     * </p>
     */
    private static BeanFactory beanFactory = null;
    /**
     * <p>
     * The Spring bean factory.
     * </p>
     */
    private static BeanFactory failBeanFactory = null;

    /**
     * <p>
     * Gets Spring bean factory.
     * </p>
     *
     * @return Spring bean factory
     */
    public static BeanFactory getBeanFactory() {
        if (beanFactory == null) {
            ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
                    new String[] {"failure/applicationContext.xml" });
            beanFactory = (BeanFactory) appContext;
        }
        return beanFactory;
    }

    /**
     * <p>
     * Gets Spring bean factory.
     * </p>
     *
     * @return Spring bean factory
     */
    public static BeanFactory getFailBeanFactory() {
        if (failBeanFactory == null) {
            ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
                    new String[] {"failure/failApplicationContext.xml" });
            failBeanFactory = (BeanFactory) appContext;
        }
        return failBeanFactory;
    }

}
