/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.stresstests;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;

/**
 * <p>
 * Stress tests for HibernateResponsiblePersonService class.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class HibernateResponsiblePersonServiceStressTest extends BaseStressTest {
    /**
     * <p>
     * Represents the responsiblePersonService for testing.
     * </p>
     */
    @Autowired
    private ResponsiblePersonService responsiblePersonService;

    /**
     * <p>
     * Represents the last error.
     * </p>
     */
    private Throwable lastError;

    /**
     * <p>
     * Creates a test suite for unit tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(HibernateResponsiblePersonServiceStressTest.class);
    }

    /**
     * <p>
     * Stress test for {@link ResponsiblePersonService#getAllResponsiblePeople(long)} method.
     * </p>
     *
     * @throws Throwable
     *             to JUnit
     */
    @Test
    public void test_getAllResponsiblePeople() throws Throwable {
        Thread[] threads = new Thread[(int) NUMBER];
        lastError = null;
        for (int i = 0; i < NUMBER; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    try {
                        Assert.assertEquals("fail to all responsiblePeople", 2, responsiblePersonService
                                .getAllResponsiblePeople(1).size());
                    } catch (Throwable e) {
                        lastError = e;
                    }
                }
            });
        }

        start();

        // start the threads
        for (int i = 0; i < NUMBER; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER; i++) {
            threads[i].join();
        }
        // throw the last error
        if (lastError != null) {
            throw lastError;
        }
        printResult("ResponsiblePersonService#getAllResponsiblePeople", NUMBER);
    }
}
