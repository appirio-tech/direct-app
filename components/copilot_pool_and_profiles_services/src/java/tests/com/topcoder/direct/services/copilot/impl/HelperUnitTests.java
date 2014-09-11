/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.dto.CopilotProfileDTO;
import com.topcoder.direct.services.copilot.dto.CopilotProjectDTO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLog;

/**
 * UnitTest cases of the {@link Helper} class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTests {
    /**
     * Test to see if a private Constructor exists.
     *
     * @throws Exception to JUnit, indicates error
     */
    @Test
    public void testPrivateCtor() throws Exception {
        Constructor<?> privateCtor = Helper.class.getDeclaredConstructors()[0];
        assertTrue(Modifier.isPrivate(privateCtor.getModifiers()));
        privateCtor.setAccessible(true);
        privateCtor.newInstance(new Object[] {});
    }

    /**
     * Accuracy test case for checkNull.The argument is not null so <code>IllegalArgumentException</code> should not
     * be thrown.
     */
    @Test
    public void testCheckNull() {
        Helper.checkNull("non-null", "non-null name");
    }

    /**
     * Failure test case for checkNull.The argument is null so <code>IllegalArgumentException</code> should be
     * thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNullFailure() {
        Helper.checkNull(null, "null-name");
    }

    /**
     * Accuracy test case for checkNull.The argument is not null so <code>IllegalArgumentException</code> should not
     * be thrown.
     */
    @Test
    public void testCheckNullWithLogging() {
        Helper.checkNullWithLogging("obj", "obj", "method", LogManager.getLog());
    }

    /**
     * Failure test case for checkNull.The argument is null so <code>IllegalArgumentException</code> should be
     * thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNullWithLoggingFailure() {
        Helper.checkNullWithLogging(null, "obj", "method", LogManager.getLog());
    }

    /**
     * Accuracy test case for checkNullForInjectedValue. The argument is not null so
     * <code>CopilotServiceInitializationException</code> should not be thrown.
     */
    @Test
    public void testCheckNullForInjectedValue() {
        Helper.checkNullForInjectedValue("obj", "obj");
    }

    /**
     * Failure test case for checkNullForInjectedValue. The argument is null so
     * <code>CopilotServiceInitializationException</code> should be thrown.
     */
    @Test(expected = CopilotServiceInitializationException.class)
    public void testCheckNullForInjectedValueFailure() {
        Helper.checkNullForInjectedValue(null, "obj");
    }

    /**
     * Accuracy test case for checkPositive. The argument is 1 so <code>IllegalArgumentException</code> should not be
     * thrown.
     */
    @Test
    public void testCheckPositive() {
        Helper.checkPositive(1, "obj", "method", LogManager.getLog());
    }

    /**
     * Failure test case for checkPositive. The argument is 0 so <code>IllegalArgumentException</code> should be
     * thrown.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckPositiveFailure() {
        Helper.checkPositive(0, "obj", "method", LogManager.getLog());
    }

    /**
     * Accuracy test case for logError.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testLogError1() throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Constructor privateCtor = BasicLog.class.getDeclaredConstructor(String.class, PrintStream.class);
        privateCtor.setAccessible(true);
        Log log = (Log) privateCtor.newInstance("name", new PrintStream(byteStream));
        Helper.logError(new IllegalArgumentException("test error"), "methodName", log);
        String out = byteStream.toString();
        assertTrue("Should contain 'test error'", out.indexOf("test error") != -1);
    }

    /**
     * Accuracy test case for logError.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testLogError2() throws Exception {
        IllegalArgumentException iae = new IllegalArgumentException("test error");
        assertTrue("should be same with iae", iae == Helper.logError(iae, "aa", null));
    }

    /**
     * Accuracy test case for logMethodEntrance.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testLogMethodEntrance() throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Constructor privateCtor = BasicLog.class.getDeclaredConstructor(String.class, PrintStream.class);
        privateCtor.setAccessible(true);
        Log log = (Log) privateCtor.newInstance("name", new PrintStream(byteStream));
        Helper.logMethodEntrance("method", log, new Date());
        String out = byteStream.toString();
        assertTrue("Should contain 'Entering method: '.", out.indexOf("Entering method: ") != -1);
    }

    /**
     * <p>
     * Accuracy test case for logMethodExit.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testLogMethodExit() throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Constructor privateCtor = BasicLog.class.getDeclaredConstructor(String.class, PrintStream.class);
        privateCtor.setAccessible(true);
        Log log = (Log) privateCtor.newInstance("name", new PrintStream(byteStream));
        Helper.logMethodExit("m", log, new Date());
        String out = byteStream.toString();
        assertTrue("Should contain 'Exiting method: '.", out.indexOf("Exiting method: ") != -1);
    }

    /**
     * <p>
     * Accuracy test case for logInputArguments.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testLogInputArguments() throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Constructor privateCtor = BasicLog.class.getDeclaredConstructor(String.class, PrintStream.class);
        privateCtor.setAccessible(true);
        Log log = (Log) privateCtor.newInstance("name", new PrintStream(byteStream));
        List<Long> list = new ArrayList<Long>();
        list.add(1l);
        list.add(2l);
        Helper.logInputArguments(log, new String[] {"list", "user", "CopilotProfile", "null parameter",
            "CopilotProfileDTO", "CopilotProjectDTO", "CopilotPoolMember"}, new Object[] {list, "user",
                new CopilotProfile(), null, new CopilotProfileDTO(), new CopilotProjectDTO(), new CopilotPoolMember()});
        String out = byteStream.toString();
        assertTrue("Should contain 'Input arguments:'.", out.indexOf("Input arguments:") != -1);
    }

    /**
     * <p>
     * Accuracy test case for logReturnValue.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testLogReturnValue() throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        Constructor privateCtor = BasicLog.class.getDeclaredConstructor(String.class, PrintStream.class);
        privateCtor.setAccessible(true);
        Log log = (Log) privateCtor.newInstance("name", new PrintStream(byteStream));
        Helper.logReturnValue(log, "sss");
        String out = byteStream.toString();
        assertTrue("Should contain 'sss'.", out.indexOf("sss") != -1);
    }
}