/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.stresstests;

import java.lang.reflect.Field;
import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * BaseUnitTest class for the stress tests.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 * 
 * @author onsky
 * @version 1.0
 */
public class BaseStressTest extends TestCase {

    /** The test count. */
    protected static final int testCount = 20;

    /** time started to test */
    protected long start = 0;

    /** The last error occurred */
    protected Throwable lastError;

    /**
     * Initialize variables.
     * 
     * @throws Exception
     *             if anything goes wrong
     */
    public void setUp() throws Exception {
        start = new Date().getTime();
        lastError = null;
    }

    /**
     * <p>Sets the value of a private field in the given class.</p>
     *
     * @param type the type of the class.
     * @param instance the instance which the private field belongs to.
     * @param name the name of the private field to be retrieved.
     * @param value the value to be set
     */
    public static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // Get the reflection of the field and get the value
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // Reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * Test Thread
     * @author TCSDEVLOPER
     */
    class TestThread extends Thread {
        private int index;

        /**
         * The constructor
         * @param index the thread index
         */
        public TestThread(int index) {
            super();
            this.index = index;
        }

        /**
         * Get the index.
         * @return the index
         */
        public int getIndex() {
            return index;
        }
    }
}
