/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.IdGenerator;
import com.topcoder.catalog.entity.TestHelper;
import junit.framework.TestCase;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * <p>Unit test case for {@link IdGenerator}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class IdGeneratorTest extends TestCase {
    /**
     * <p>Represents IdGenerator instance for testing.</p>
     */
    private IdGenerator idGenerator;

    /**
     * <p>Creates a new instance of IdGenerator.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.bindIdGenerator();
        idGenerator = new IdGenerator();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        idGenerator = null;
        TestHelper.unbindIdGenerator();
        super.tearDown();
    }

    /**
     * <p>Tests <code>IdGenerator()</code> constructor when EJB is bound (in the <code>setUp</code> method).</p>
     */
    public void testIdGeneratorBound() {
        assertNotNull("Unable to instantiate IdGenerator", idGenerator);
    }

    /**
     * <p>Tests <code>IdGenerator()</code> constructor when EJB is unbound.</p>
     */
    public void testIdGeneratorUnbound() {
        TestHelper.unbindIdGenerator();
        try {
            new IdGenerator();
            fail("There is no EJB bound, exception expected.");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * <p>Tests <code>configure()</code> when sequence is configured.</p>
     *
     * @throws Exception to jUnit
     */
    public void testConfigureOK() throws Exception {
        final Properties properties = new Properties();
        final String value = "seq";
        properties.put(IdGenerator.SEQUENCE_NAME, value);
        idGenerator.configure(null, properties, null);
        final Field field = IdGenerator.class.getDeclaredField("seqName");
        field.setAccessible(true);
        assertEquals("Invalid sequence name after configuring", value, field.get(idGenerator));
    }

    /**
     * <p>Tests <code>configure()</code> when sequence is not configured.</p>
     *
     * @throws Exception to jUnit
     */
    public void testConfigureFailure() throws Exception {
        final Properties properties = new Properties();
        try {
            idGenerator.configure(null, properties, null);
            fail("Exception expected as no sequence name configured");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * <p>Tests <code>configure()</code> when properties is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testConfigureNullProperties() throws Exception {
        try {
            idGenerator.configure(null, null, null);
            fail("Exception expected as null properties provided");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * <p>Tests <code>configure()</code> when properties contain not a String for SEQUENCE_NAME.</p>
     *
     * @throws Exception to jUnit
     */
    public void testConfigureInvalidClass() throws Exception {
        try {
            final Properties properties = new Properties();
            final Long value = 1L;
            properties.put(IdGenerator.SEQUENCE_NAME, value);
            idGenerator.configure(null, properties, null);
            fail("Exception expected as properties contains invalid type for SEQUENCE_NAME key");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * <p>Tests <code>configure()</code> when properties contain empty String for SEQUENCE_NAME.</p>
     *
     * @throws Exception to jUnit
     */
    public void testConfigureEmptyString() throws Exception {
        try {
            final Properties properties = new Properties();
            final String value = " ";
            properties.put(IdGenerator.SEQUENCE_NAME, value);
            idGenerator.configure(null, properties, null);
            fail("Exception expected as properties contains an empty String for SEQUENCE_NAME key");
        } catch (HibernateException e) {
            // expected
        }
    }

    /**
     * <p>Tests <code>generate()</code> method.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGenerate() throws Exception {
        final Properties properties = new Properties();
        final String value = "COMPONENT_SEQ";
        properties.put(IdGenerator.SEQUENCE_NAME, value);
        idGenerator.configure(null, properties, null);
        final Serializable id = idGenerator.generate(null, null);
        assertTrue("Must be Long", id instanceof Long);
        final Serializable id2 = idGenerator.generate(null, null);
        assertFalse("Next Id must differ", id.equals(id2));
    }

    /**
     * <p>Tests <code>generate()</code> method when <code>configure</code> was not called.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGenerateFailure() throws Exception {
        try {
            idGenerator.generate(null, null);
            fail("Exception expected as class was not configured");
        } catch (HibernateException e) {
            // expected
        }
    }


}
