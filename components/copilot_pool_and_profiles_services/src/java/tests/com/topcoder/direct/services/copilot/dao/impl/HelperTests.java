/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOInitializationException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * <p>Tests <code>{@link Helper}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperTests {

    /**
     * <p>Represents param used for testing methods.</p>
     */
    private static final String PARAM = "param";

    /**
     * <p>Represents paramName used for testing methods.</p>
     */
    private static final String PARAM_NAME = "paramName";

    /**
     * <p>Represents log used for testing methods.</p>
     */
    private static final Log LOG = LogManager.getLog("HelperTests");

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {

        return new JUnit4TestAdapter(HelperTests.class);
    }

    /**
     * <p>Tests <code>{@link Helper#Helper()}</code> constructor.</p>
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testCtor() throws Exception {
        Constructor constructor = Helper.class.getDeclaredConstructor((Class[]) null);

        constructor.setAccessible(true);

        Assert.assertEquals("Constructor should have private modifier.", Modifier.PRIVATE,
                constructor.getModifiers() & Modifier.PRIVATE);
        Assert.assertNotNull("Instance was not created.", constructor.newInstance((Object[]) null));
    }

    /**
     * <p>Tests <code>{@link Helper#checkIsNotNull}</code> method.</p>
     */
    @Test
    public void testCheckIsNotNull() {
        Helper.checkIsNotNull(PARAM, PARAM_NAME);
    }

    /**
     * <p>Tests <code>{@link Helper#checkIsNotNull}</code> method when param is <code>null</code>.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckIsNotNullParamNull() {
        Helper.checkIsNotNull(null, PARAM_NAME);
    }

    /**
     * <p>Tests <code>{@link Helper#checkIsPositive}</code> method.</p>
     */
    @Test
    public void testCheckIsPositive() {
        Helper.checkIsPositive(1L, PARAM_NAME);
    }

    /**
     * <p>Tests <code>{@link Helper#checkIsPositive}</code> method when param is negative.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckIsPositiveNegative() {
        Helper.checkIsPositive(-1L, PARAM_NAME);
    }

    /**
     * <p>Tests <code>{@link Helper#checkIsPositive}</code> method when param is zero.</p>
     *
     * <p>{@link IllegalArgumentException} is expected.</p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckIsPositiveZero() {
        Helper.checkIsPositive(0L, PARAM_NAME);
    }

    /**
     * <p>Tests <code>{@link Helper#checkPropertyNotNull}</code> method.</p>
     */
    @Test
    public void testCheckPropertyNotNull() {
        Helper.checkPropertyNotNull(PARAM, PARAM_NAME);
    }

    /**
     * <p>Tests <code>{@link Helper#checkPropertyNotNull}</code> method when param is <code>null</code>.</p>
     *
     * <p>{@link CopilotDAOInitializationException} is expected.</p>
     */
    @Test(expected = CopilotDAOInitializationException.class)
    public void testCheckPropertyNotNullParamNull() {
        Helper.checkPropertyNotNull(null, PARAM_NAME);
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodEntered}</code> method.</p>
     */
    @Test
    public void testLogMethodEntered1() {
        Helper.logMethodEntered(LOG, getClass().getSimpleName(), "testLogMethodEntered1");
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodEntered}</code> method.</p>
     */
    @Test
    public void testLogMethodEntered1Null() {
        Helper.logMethodEntered(null, getClass().getSimpleName(), "testLogMethodEntered1Null");
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodEntered}</code> method.</p>
     */
    @Test
    public void testLogMethodEntered2() {
        Helper.logMethodEntered(LOG, getClass().getSimpleName(), "testLogMethodEntered2", new String[]{PARAM_NAME},
                new Object[]{PARAM});
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodEntered}</code> method.</p>
     */
    @Test
    public void testLogMethodEntered2Null() {
        Helper.logMethodEntered(null, getClass().getSimpleName(), "testLogMethodEntered2Null", new String[]{PARAM_NAME},
                new Object[]{PARAM});
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodExited}</code> method.</p>
     */
    @Test
    public void testLogMethodExited1() {
        Helper.logMethodExited(LOG, getClass().getSimpleName(), "testLogMethodExited1", System.currentTimeMillis());
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodExited}</code> method.</p>
     */
    @Test
    public void testLogMethodExited1Null() {
        Helper.logMethodExited(null, getClass().getSimpleName(), "testLogMethodExited1Null",
                System.currentTimeMillis());
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodExited}</code> method.</p>
     */
    @Test
    public void testLogMethodExited2() {
        Helper.logMethodExited(LOG, getClass().getSimpleName(), "testLogMethodExited2",
                System.currentTimeMillis(), PARAM);
    }

    /**
     * <p>Tests <code>{@link Helper#logMethodExited}</code> method.</p>
     */
    @Test
    public void testLogMethodExitedNull2() {
        Helper.logMethodExited(null, getClass().getSimpleName(), "testLogMethodExited2Null",
                System.currentTimeMillis(), PARAM);
    }

    /**
     * <p>Tests <code>{@link Helper#logError}</code> method.</p>
     */
    @Test
    public void testLogError() {
        Helper.logError(LOG, "Error message", new Exception());
    }

    /**
     * <p>Tests <code>{@link Helper#logError}</code> method.</p>
     */
    @Test
    public void testLogErrorNull() {
        Helper.logError(null, "Error message", new Exception());
    }
}